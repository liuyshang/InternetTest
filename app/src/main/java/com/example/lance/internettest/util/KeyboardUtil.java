package com.example.lance.internettest.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * author: admin
 * time: 2016/3/14 10:46
 * e-mail: lance.cao@anarry.com
 */
public class KeyboardUtil {

    private Context mContext;
    private InputMethodManager manager;
    private View view;

    public KeyboardUtil(Context context) {
        this.mContext = context;
        manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public KeyboardUtil(Context mContext, View view) {
        this.mContext = mContext;
        this.view = view;
        manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 设置输入法的状态
     */
    public void setKeyBoard() {
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示输入法
     */
    public void showKeyBoard() {
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 隐藏输入法
     */
    public void hideKeyBoard() {
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
