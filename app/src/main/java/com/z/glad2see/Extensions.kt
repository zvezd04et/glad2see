package com.z.glad2see

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(compositeDisposable: CompositeDisposable): Disposable {
    return this.apply { compositeDisposable.add(this) }
}

fun Disposable.disposeSafely() {
    if (this != null && !this.isDisposed) {
        this.dispose()
    }
}