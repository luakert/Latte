package com.example.latte.ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.ItemType;
import com.example.latte.ui.recycler.MultipleFields;
import com.example.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public final class IndexDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject data = dataArray.getJSONObject(i);
            String imageUrl = data.getString("imageUrl");
            String text = data.getString("text");
            int spanSize = data.getInteger("spanSize");
            int goodsId = data.getInteger("goodsId");
            JSONArray banners = data.getJSONArray("banners");
            ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAG;
            } else if (imageUrl != null) {
                type = ItemType.TEXT_IMGAG;
            } else if (banners != null) {
                type = ItemType.BANNER;
                int size1 = banners.size();
                for (int i1 = 0; i1 < size1; i1++) {
                    String banner = banners.getString(i1);
                    bannerImages.add(banner);
                }
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.ID,goodsId)
                    .setField(MultipleFields.TEXT,text)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .setField(MultipleFields.BANNERS,bannerImages)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
