package com.seiagul.adminelapor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.ColumnText;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;


import static android.content.ContentValues.TAG;

public class CetakActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String URL = "https://tugasaja.masuk.web.id/applogindanregisterandroid/select.php";
    // public static final String URLDELETE = "https://tugasaja.masuk.web.id/applogindanregisterandroid/delete.php";
    // public static final String URLEDIT  = "https://tugasaja.masuk.web.id/applogindanregisterandroid/edit.php";
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    SelesaiAdapter selesaiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        list = (ListView) findViewById(R.id.list);

        selesaiAdapter = new SelesaiAdapter(CetakActivity.this, itemList);
        list.setAdapter(selesaiAdapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           selesaiAdapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final CharSequence[] pilihanAksi = {"Download PDF"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(CetakActivity.this);
                dialog.setItems(pilihanAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                // Buat logika Download PDF disini
                                Toast.makeText(CetakActivity.this, "Memuat...", Toast.LENGTH_SHORT).show();

                                // Mendapatkan data dari item yang dipilih
                                Data selectedData = itemList.get(position);

                                // Mendapatkan nama pelapor dan id
                                String pelapor = selectedData.getPelaporList();
                                String id = selectedData.getId();

                                // Menggabungkan nama pelapor dan id untuk nama file PDF
                                String fileName = pelapor + "_" + id + ".pdf";

                                // Mendefinisikan path atau lokasi penyimpanan file PDF
                                String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;

                                // Membuat objek Document dari iText
                                Document document = new Document();

                                try {
                                    // Membuat objek PdfWriter untuk menulis ke file PDF
                                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

                                    // Membuka dokumen
                                    document.open();

                                    // Mendapatkan objek PdfContentByte dari PdfWriter
                                    PdfContentByte contentByte = writer.getDirectContent();

                                    // Menambahkan konten ke dokumen
                                    // Misalnya, menambahkan teks dari data yang dipilih
                                    String text = "Pelapor: " + selectedData.getPelaporList() + "\n" +
                                            "Kategori: " + selectedData.getKategoriList() + "\n" +
                                            "Koordinat: " + selectedData.getKordinatList() + "\n" +
                                            "Rincian: " + selectedData.getRincianList() + "\n" +
                                            "Link Gambar: " + selectedData.getGambarList();

                                    // Menentukan posisi teks di tengah halaman (kiri)
                                    ColumnText columnText = new ColumnText(contentByte);
                                    columnText.setSimpleColumn(document.left(3), document.top(120), document.right(), document.bottom());
                                    columnText.addElement(new Paragraph(text));
                                    columnText.go();

                                    // Menggabungkan file kop.pdf
                                    InputStream kopStream = getAssets().open("file_kop.pdf");
                                    PdfReader kopReader = new PdfReader(kopStream);
                                    PdfImportedPage kopPage = writer.getImportedPage(kopReader, 1);
                                    contentByte.addTemplate(kopPage, 0, 0);

                                    // Menutup dokumen
                                    document.close();

                                    // Menampilkan pesan sukses
                                    Toast.makeText(CetakActivity.this, "PDF berhasil dibuat: " + fileName, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();

                                    // Menampilkan pesan error
                                    Toast.makeText(CetakActivity.this, "Gagal membuat PDF", Toast.LENGTH_SHORT).show();
                                }

                                break;

                        }

                    }
                }).show();
                return false;
            }
        });

    }




    @Override
    public void onRefresh() {
        itemList.clear();
        selesaiAdapter.notifyDataSetChanged();
        callVolley();

    }

    private void callVolley() {
        itemList.clear();
        selesaiAdapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data item = new Data();

                        item.setId(obj.getString("id"));
                        item.setPelaporList(obj.getString("username"));
                        item.setKategoriList(obj.getString("kategori"));
                        item.setKordinatList(obj.getString("kordinat"));
                        item.setRincianList(obj.getString("keterangan"));
                        item.setGambarList(obj.getString("urlSelesai"));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                selesaiAdapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);

    }




}

