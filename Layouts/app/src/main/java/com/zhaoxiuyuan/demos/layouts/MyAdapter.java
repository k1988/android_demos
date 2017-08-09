package com.zhaoxiuyuan.demos.layouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author zhaohaiyang
 *         Created at 2017/7/31.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;

    public void setList(List<String> list) {
        this.list = list;
    }

    private List<String> list;

    public MyAdapter(Context con, List<String> list) {
        context = con;
        mInflater = LayoutInflater.from(con);
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return (list == null) ? null : list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.popup_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.source = (TextView) convertView.findViewById(R.id.referUrl);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView source;
    }
}
