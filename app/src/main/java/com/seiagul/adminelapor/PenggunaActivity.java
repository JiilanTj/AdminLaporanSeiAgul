package com.seiagul.adminelapor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PenggunaActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String URL = "https://tugasaja.masuk.web.id/applogindanregisterandroid/userList.php";
    public static final String URLDELETE = "https://tugasaja.masuk.web.id/applogindanregisterandroid/delete.php";
    public static final String URLEDIT  = "https://tugasaja.masuk.web.id/applogindanregisterandroid/edit.php";
    ListView list;
    SwipeRefreshLayout swipe;
    List<DataDua> itemList = new ArrayList<>();
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengguna);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        list = (ListView) findViewById(R.id.listdua);

        userAdapter = new UserAdapter(PenggunaActivity.this, itemList);
        list.setAdapter(userAdapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           userAdapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final CharSequence[] pilihanAksi = {"Tangani"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(PenggunaActivity.this);
                dialog.setItems(pilihanAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Toast.makeText(PenggunaActivity.this, "Laporan beres, tidak bisa ditangani", Toast.LENGTH_SHORT).show();

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
        userAdapter.notifyDataSetChanged();
        callVolley();

    }

    private void callVolley() {
        itemList.clear();
        userAdapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataDua item = new DataDua();

                        item.setId(obj.getString("id"));
                        item.setUsernameList(obj.getString("username"));
                        item.setEmailList(obj.getString("email"));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                userAdapter.notifyDataSetChanged();

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

