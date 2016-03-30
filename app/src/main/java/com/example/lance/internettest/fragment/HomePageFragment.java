package com.example.lance.internettest.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lance.internettest.Interface.OnInternetClickListener;
import com.example.lance.internettest.R;
import com.example.lance.internettest.util.Injector;
import com.example.lance.internettest.util.ViewInject;

/**
 * author: admin
 * time: 2016/3/30 11:59
 * e-mail: lance.cao@anarry.com
 */
public class HomePageFragment extends Fragment implements View.OnClickListener{

    @ViewInject(R.id.bt_http_url)
    private Button btHttpURL;
    @ViewInject(R.id.bt_http_client)
    private Button btHttpClient;

    private Context mContext;
    private View view;
    private OnInternetClickListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_page, null);
        Injector.initInjectedView(this,view);
        mContext = getActivity();
        setListener();
        return view;
    }

    private void setListener() {
        btHttpURL.setOnClickListener(this);
        btHttpClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_http_url:
                mListener.onHttpURLClick();
                break;
            case R.id.bt_http_client:
                mListener.onHttpClientClick();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setOnInternetClickListener(OnInternetClickListener listener) {
        this.mListener = listener;
    }
}

