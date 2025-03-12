package com.example.generators;

import com.example.latte.annotations.PayEntryGenerator;
import com.example.latte.wechat.WxPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.example.latte",
         payEntryTemplate = WxPayEntryTemplate.class
)
public interface WxPayEntry {
}
