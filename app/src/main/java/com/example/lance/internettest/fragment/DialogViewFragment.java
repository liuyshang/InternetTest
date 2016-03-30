package com.example.lance.internettest.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * author: admin
 * time: 2016/3/22 15:42
 * e-mail: lance.cao@anarry.com
 */
public abstract class DialogViewFragment extends DialogFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(getLayout(),null);
        initView(view);
        return view;
    }

    public abstract void initView(View view);

    public abstract int getLayout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
