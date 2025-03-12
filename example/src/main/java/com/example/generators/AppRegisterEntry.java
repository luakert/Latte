package com.example.generators;

import com.example.latte.annotations.AppRegisterGenerator;
import com.example.latte.wechat.AppRegisterTemplate;

@AppRegisterGenerator(
        packageName = "com.example.latte",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegisterEntry {
}
