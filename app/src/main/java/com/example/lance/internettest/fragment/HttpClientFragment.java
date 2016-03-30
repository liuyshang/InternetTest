package com.example.lance.internettest.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lance.internettest.R;
import com.example.lance.internettest.util.Injector;
import com.example.lance.internettest.util.ViewInject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * author: admin
 * time: 2016/3/29 17:43
 * e-mail: lance.cao@anarry.com
 */
public class HttpClientFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.bt_get)
    private Button btGet;
    @ViewInject(R.id.bt_post)
    private Button btPost;
    @ViewInject(R.id.tv_text)
    private TextView tvText;

    private View view;
    private Context mContext;
    private WeakHandler mHandler = new WeakHandler(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>","HttpClientFragment onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_http, null);
        }
        Injector.initInjectedView(this, view);
        mContext = getActivity();
        tvText.setText("HttpClient网络连接方式");
        setListener();
        return view;
    }

    private void setListener() {
        btGet.setOnClickListener(this);
        btPost.setOnClickListener(this);
    }

    /**
     * HttpClient get
     */
    private void HttpClientGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String httpUrl = "http://wxb.anarry.com/api/today?city=深圳";
                    //HttpClient Get 连接对象
                    HttpGet httpGet = new HttpGet(httpUrl);
                    //取得HttpClient对象
                    HttpClient httpClient = new DefaultHttpClient();
                    //请求HttpClient，取得HttpResponse
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    //判断是否请求成功
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        //取得返回的字符串
                        String result = EntityUtils.toString(httpResponse.getEntity());
                        sendClientMessage(result, 1);
                    } else {
                        Toast.makeText(mContext, httpResponse.getStatusLine().getReasonPhrase()
                                , Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * HttpClient  post
     */
    private void HttpClientPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String httpUrl = "http://user.ymys365.com/api/login";
                //HttpPost连接对象
                HttpPost httpPost = new HttpPost(httpUrl);
                //使用NameValuePair来保存要传递的Post参数
                List<NameValuePair> params = new ArrayList<>();
                //添加要传递的参数
                params.add(new BasicNameValuePair("User[username]", "13580503402"));
                params.add(new BasicNameValuePair("User[password]", "yunmeng2"));
                //设置字符集
                try {
                    HttpEntity httpEntity = new UrlEncodedFormEntity(params, "gb2312");
                    //请求httpResquest
                    httpPost.setEntity(httpEntity);
                    //取得默认的HttpClient
                    HttpClient httpClient = new DefaultHttpClient();
                    //取得HttpResponse
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    //判断是否请求成功
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        //取得返回的字符串
                        String result = EntityUtils.toString(httpPost.getEntity());
                        sendClientMessage(result, 2);
                    } else {
                        Toast.makeText(mContext, httpResponse.getStatusLine().getReasonPhrase()
                                , Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 发送消息
     */
    private void sendClientMessage(String s, int i) {
        Message message = new Message();
        message.what = i;
        message.obj = s;
        mHandler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_get:
                HttpClientGet();
                break;
            case R.id.bt_post:
                HttpClientPost();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(">>>", "HttpClientFragment onDestroyView");
        if (view != null){
            ((ViewGroup)view.getParent()).removeView(view);
        }
    }

    public class WeakHandler extends Handler {
        WeakReference<HttpClientFragment> mFragment;

        WeakHandler(HttpClientFragment freagment) {
            this.mFragment = new WeakReference<HttpClientFragment>(freagment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mFragment.get().tvText.setText((CharSequence) msg.obj);
                    break;
                case 2:
                    mFragment.get().tvText.setText((CharSequence) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
