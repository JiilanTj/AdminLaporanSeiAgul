package com.seiagul.adminelapor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import java.util.List;

public class UserAdapter extends BaseAdapter {
    Activity activity;
    List<DataDua> items;
    private LayoutInflater inflater;

    public UserAdapter(Activity activity, List<DataDua> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.listdua, null);

        TextView id = convertView.findViewById(R.id.id);
        TextView username = convertView.findViewById(R.id.usernameList);
        TextView email = convertView.findViewById(R.id.emailList);

        DataDua dataDua = items.get(position);

        id.setText(dataDua.getId());
        username.setText(dataDua.getUsernameList());
        email.setText(dataDua.getEmailList());


        return convertView;
    }

}
