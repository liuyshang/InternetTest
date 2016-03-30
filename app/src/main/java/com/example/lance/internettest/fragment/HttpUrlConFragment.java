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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * author: admin
 * time: 2016/3/29 10:58
 * e-mail: lance.cao@anarry.com
 */
public class HttpUrlConFragment extends Fragment implements View.OnClickListener {

    @ViewInject(R.id.bt_get)
    private Button btGet;
    @ViewInject(R.id.bt_post)
    private Button btPost;
    @ViewInject(R.id.tv_text)
    private TextView tvText;

    private Context mContext;
    private View view;
    private WeakHandler mHandler = new WeakHandler(this);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(">>>", "HttpUrlConFragment onCreateView");
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_http, null);
        }
        Injector.initInjectedView(this, view);
        mContext = getActivity();
        tvText.setText("HttpURLConnection网络连接方式");
        setListener();
        return view;
    }

    private void setListener() {
        btGet.setOnClickListener(this);
        btPost.setOnClickListener(this);
    }

    /**
     * HttpUrlConnection post 模式
     */
    private void httpUrlConnectionPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://user.ymys365.com/api/login");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    setHttpUrlConnection(httpURLConnection);
                    setParam(httpURLConnection);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() != 200) {
                        Toast.makeText(mContext, "请求URL失败"
                                + httpURLConnection.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    sendUrlMessage(getReaderInfo(httpURLConnection), 2);

                    httpURLConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 设置HttpURLConnection Post 参数
     *
     * @param connection
     */
    private void setHttpUrlConnection(HttpURLConnection connection) {
        try {
            //设置输入，输出
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //设置超时
            connection.setReadTimeout(6 * 1000);
            connection.setConnectTimeout(6 * 1000);
            //设置POST请求方式
            connection.setRequestMethod("POST");
            //设置不能使用缓存
            connection.setUseCaches(false);
            //设置是否遵循重定向
            connection.setInstanceFollowRedirects(true);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    /**
     * 传入参数
     *
     * @param connection
     */
    private void setParam(HttpURLConnection connection) {
        try {
            //写入参数
            String data = "User[username]=" + URLEncoder.encode("13580503402", "UTF-8")
                    + "&User[password]" + URLEncoder.encode("yunmeng2", "UTF-8");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpUrlConnection  get 模式
     */
    private void httpUrlConnectionGet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建一个URL对象
                    URL url = new URL("http://wxb.anarry.com/api/today?city=深圳");
                    //使用HttpURLConnection打开链接
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //设置超时
                    urlConnection.setConnectTimeout(6 * 1000);
                    if (urlConnection.getResponseCode() != 200) {
                        Toast.makeText(mContext, "请求URL失败"
                                + urlConnection.getResponseMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    sendUrlMessage(getReaderInfo(urlConnection), 1);

                    urlConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 将输出流转化为String类型
     *
     * @param connection
     * @return
     */
    private String getByteInfo(HttpURLConnection connection) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            // 获取响应的输入流对象
            InputStream inputStream = connection.getInputStream();
            // 定义读取的长度
            int len = 0;
            // 定义缓冲区
            byte buffer[] = new byte[1024];
            // 按照缓冲区的大小，循环读取
            while ((len = inputStream.read(buffer)) != -1) {
                // 根据读取的长度写入到os对象中
                byteArrayOutputStream.write(buffer, 0, len);
            }
            inputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(byteArrayOutputStream.toByteArray());
    }

    /**
     * 将输出流转化为String类型
     *
     * @param connection
     */
    private String getReaderInfo(HttpURLConnection connection) {
        StringBuffer buffer = new StringBuffer();
        try {
            //得到读取的内容
            InputStreamReader inputStreamReader =
                    new InputStreamReader(connection.getInputStream());
            //为输出创建BufferedReader
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            //使用循环来读取数据
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 发送消息
     *
     * @param s obj
     * @param i what
     */
    private void sendUrlMessage(String s, int i) {
        Message message = new Message();
        message.what = i;
        message.obj = s;
        mHandler.sendMessage(message);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(">>>", "HttpUrlConFragment onDestroyView");
        if (view != null){
            ((ViewGroup)view.getParent()).removeView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_get:
                httpUrlConnectionGet();
                break;
            case R.id.bt_post:
                httpUrlConnectionPost();
                break;
            default:
                break;
        }
    }

    public class WeakHandler extends Handler {
        WeakReference<HttpUrlConFragment> mFragment;

        WeakHandler(HttpUrlConFragment freagment) {
            this.mFragment = new WeakReference<HttpUrlConFragment>(freagment);
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
