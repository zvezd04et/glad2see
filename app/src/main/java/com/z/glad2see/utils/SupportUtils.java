package com.z.glad2see.utils;

import android.support.annotation.Nullable;

import io.reactivex.disposables.Disposable;

public class SupportUtils {

    public static void disposeSafely(@Nullable Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    private SupportUtils() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
