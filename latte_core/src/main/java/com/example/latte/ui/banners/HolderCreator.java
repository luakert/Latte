package com.example.latte.ui.banners;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

public class HolderCreator implements CBViewHolderCreator<ImageHolder> {
    @Override
    public ImageHolder createHolder() {
        return new ImageHolder();
    }
}
