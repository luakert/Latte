package com.example.latte.util.callback;

import androidx.annotation.Nullable;

/**
 * Created by 傅令杰
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
