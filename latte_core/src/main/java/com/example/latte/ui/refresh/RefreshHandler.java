package com.example.latte.ui.refresh;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.latte.app.Latte;
import com.example.latte.net.RestClient;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;

    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public void firstPage(String url) {
        RestClient.builder()
                .url(url)
                .success(response->{


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
}
