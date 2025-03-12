package com.example.generators;

import com.example.latte.annotations.EntryGenerator;
import com.example.latte.wechat.WxEntryTemplate;

@EntryGenerator(
    packageName = "com.example.latte",
    entryTemplate = WxEntryTemplate.class
)
public interface WxEntry {
}
