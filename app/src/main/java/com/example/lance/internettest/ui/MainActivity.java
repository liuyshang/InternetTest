package com.example.lance.internettest.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lance.internettest.Interface.OnInternetClickListener;
import com.example.lance.internettest.R;
import com.example.lance.internettest.fragment.HomePageFragment;
import com.example.lance.internettest.fragment.HttpClientFragment;
import com.example.lance.internettest.fragment.HttpUrlConFragment;
import com.example.lance.internettest.util.Injector;
import com.example.lance.internettest.util.ViewInject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnInternetClickListener {

    @ViewInject(R.id.toolbar)
    private Toolbar toolBar;
    @ViewInject(R.id.fab)
    private FloatingActionButton fab;
    @ViewInject(R.id.fragment)
    private ContentFrameLayout frameLayout;

    private Context mContext;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private HttpUrlConFragment httpUrlConFreagment;
    private HomePageFragment homePageFragment;
    private HttpClientFragment httpClientFragment;
    /**
     * 保存显示的fragment
     */
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.initInjectedView(this);
        setSupportActionBar(toolBar);
        mContext = MainActivity.this;
        getNetworkState();
        init();
        setListener();
    }

    private void setListener() {
        fab.setOnClickListener(this);
    }

    private void init() {
        mManager = getFragmentManager();
        mTransaction = mManager.beginTransaction();
        if (homePageFragment == null) {
            homePageFragment = new HomePageFragment();
            homePageFragment.setOnInternetClickListener(this);
        }
        mTransaction.add(R.id.fragment, homePageFragment, "home");
        currentFragment = homePageFragment;
        mTransaction.commitAllowingStateLoss();
    }

    private void getNetworkState() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        Log.i(">>>", "info: " + info);
        if (!info.isConnected()) {
            Toast.makeText(mContext, "网络不可用", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onHttpURLClick() {
        mTransaction = mManager.beginTransaction();
        mTransaction.hide(homePageFragment);
        if (httpUrlConFreagment == null) {
            httpUrlConFreagment = new HttpUrlConFragment();
        }
        mTransaction.add(R.id.fragment, httpUrlConFreagment, "url");
        currentFragment = httpUrlConFreagment;
        mTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onHttpClientClick() {
        mTransaction = mManager.beginTransaction();
        mTransaction.hide(homePageFragment);
        if (httpClientFragment == null) {
            httpClientFragment = new HttpClientFragment();
        }
        mTransaction.add(R.id.fragment, httpClientFragment, "client");
        currentFragment = httpClientFragment;
        mTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Log.i(">>>","currentFragment: " + currentFragment);
                if (currentFragment == homePageFragment) {
                    finish();
                } else {
                    mTransaction = mManager.beginTransaction();
                    mTransaction.remove(currentFragment);
                    mTransaction.show(homePageFragment);
                    currentFragment = homePageFragment;
                    mTransaction.commitAllowingStateLoss();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
