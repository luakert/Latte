package com.example.latte.ui.recycler;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.latte.core.R;
import com.example.latte.ui.banners.BannerCreator;

import java.util.ArrayList;
import java.util.List;

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {
    private boolean mIsInitBanner = false;

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConverter data) {
        return new MultipleRecyclerAdapter(data.convert());
    }

    private void init() {
        addItemType(ItemType.TEXT, R.layout.item_mulitple_txt);
        addItemType(ItemType.IMAG, R.layout.item_multiple_imageview);
        addItemType(ItemType.TEXT_IMGAG, R.layout.item_mulitpe_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        setSpanSizeLookup(this);
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(@NonNull MultipleViewHolder multipleViewHolder, MultipleItemEntity multipleItemEntity) {
        String text;
        String imagUrl;
        ArrayList<String> bannerImages;
        switch (multipleViewHolder.getItemViewType()) {
            case ItemType.TEXT:
                text = multipleItemEntity.getField(MultipleFields.TEXT);
                multipleViewHolder.setText(R.id.text_single, text);
                break;
            case ItemType.IMAG:
                text = multipleItemEntity.getField(MultipleFields.TEXT);
                imagUrl = multipleItemEntity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imagUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .centerCrop()
                        .into(((ImageView) multipleViewHolder.getView(R.id.img_mulitpe)));
                multipleViewHolder.setText(R.id.tv_img_multiple, text);
                break;
            case ItemType.TEXT_IMGAG:

                break;
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImages = multipleItemEntity.getField(MultipleFields.BANNERS);
                    ConvenientBanner<String> convenientBanner = multipleViewHolder.getView(R.id.banner_recycler_item);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsInitBanner = true;
                }
                break;


        }

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
        return getData().get(i).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
