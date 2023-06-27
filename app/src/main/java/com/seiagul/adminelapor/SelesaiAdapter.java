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

public class SelesaiAdapter extends BaseAdapter {
    Activity activity;
    List<Data> items;
    private LayoutInflater inflater;

    public SelesaiAdapter(Activity activity, List<Data> items) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.list, null);

        TextView id = convertView.findViewById(R.id.id);
        TextView pelapor = convertView.findViewById(R.id.pelaporList);
        TextView kategori = convertView.findViewById(R.id.kategoriList);
        TextView kordinat = convertView.findViewById(R.id.kordinatList);
        TextView rincian = convertView.findViewById(R.id.rincianList);
        ImageView gambar = convertView.findViewById(R.id.gambarList);

        Data data = items.get(position);

        id.setText(data.getId());
        pelapor.setText(data.getPelaporList());
        kategori.setText(data.getKategoriList());
        kordinat.setText(data.getKordinatList());
        rincian.setText(data.getRincianList());

        // Muat gambar dari URL menggunakan Picasso
        Picasso.get().load(data.getGambarList()).into(gambar);

        return convertView;
    }

}
