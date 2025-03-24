package com.example.latte.ui.refresh;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.latte.app.Latte;
import com.example.latte.net.RestClient;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;


    private RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView recyclerView,
                          DataConverter dataConverter,
                          PagingBean pagingBean) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        REFRESH_LAYOUT.setOnRefreshListener(this);
        this.RECYCLERVIEW = recyclerView;
        this.CONVERTER = dataConverter;
        this.BEAN = pagingBean;

        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout REFRESH_LAYOUT, RecyclerView recyclerView,
                                        DataConverter dataConverter) {
        return new RefreshHandler(REFRESH_LAYOUT, recyclerView, dataConverter, new PagingBean());
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(response->{
                    JSONObject jsonObject = JSON.parseObject(response);
                    BEAN.setTotal(jsonObject.getInteger("total"))
                            .setPageSize(jsonObject.getInteger("page_size"));
                    mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                    mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLERVIEW);
                    RECYCLERVIEW.setAdapter(mAdapter);
                    BEAN.addIndex();
                })
                .build()
                .get();

    }

    private void refesh() {
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(()->{
            REFRESH_LAYOUT.setRefreshing(false);
        }, 2000);
    }

    @Override
    public void onRefresh() {
        refesh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
