package com.singtel.groupit.uiutil;

/**
 * Created by lanna on 6/29/16.
 *
 */

public interface OnGetDataDelegate<T> {
    void onDataChanged(T data);
    void onError(Throwable e);
}
