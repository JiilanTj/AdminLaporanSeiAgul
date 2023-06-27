package com.seiagul.adminelapor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private static final int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(DashboardActivity.this);

        Button btnProses = findViewById(R.id.btnProses);
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProsesActivity.class);
                startActivity(intent);
            }
        });

        Button btnDitolak = findViewById(R.id.btnDitolak);
        btnDitolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DitolakActivity.class);
                startActivity(intent);
            }
        });

        Button btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SelesaiActivity.class);
                startActivity(intent);
            }
        });

        Button btnPengguna = findViewById(R.id.buttonPengguna);
        btnPengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PenggunaActivity.class);
                startActivity(intent);
            }
        });

        Button btnLaporan = findViewById(R.id.buttonLaporan);
        btnLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LaporanActivity.class);
                startActivity(intent);
            }
        });

        Button btnAduan = findViewById(R.id.buttonAduan);
        btnAduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, LaporanActivity.class);
                startActivity(intent);
            }
        });

        Button btnHome = findViewById(R.id.buttonHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CetakActivity.class);
                startActivity(intent);
            }
        });

        // Memeriksa dan meminta izin akses ke media/storage
        checkStoragePermission();
    }

    private void checkStoragePermission() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean hasPermission = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
                break;
            }
        }

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, permissions, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin akses ke media/storage diberikan
                Toast.makeText(this, "Izin akses ke media/storage diberikan.", Toast.LENGTH_SHORT).show();
            } else {
                // Izin akses ke media/storage ditolak
                Toast.makeText(this, "Izin akses ke media/storage ditolak.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
