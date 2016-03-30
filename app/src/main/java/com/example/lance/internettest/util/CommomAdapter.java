package com.example.lance.internettest.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * author: Lance
 * time: 2016/1/29 17:10
 * e-mail: lance.cao@anarry.com
 */
public abstract class CommomAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;
    protected LayoutInflater mInflater;
    protected int layoutID;

    public CommomAdapter(Context mContext, List<T> mList, int layoutID) {
        this.mContext = mContext;
        this.mList = mList;
        this.layoutID = layoutID;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutID, position);
        convert(holder, (T) getItem(position));
        return holder.getConvertView();
    }

    protected abstract void convert(ViewHolder holder, T t);
}
