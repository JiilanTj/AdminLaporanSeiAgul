package com.seiagul.adminelapor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TanganiActivity extends AppCompatActivity {

    private WebView webView;
    private String id;
    private ValueCallback<Uri[]> filePathCallback;
    private Uri[] fileUris;

    private static final int REQUEST_FILE_CHOOSER = 1;
    private static final int REQUEST_CAMERA_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tangani);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        webView = findViewById(R.id.webView);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String css = "body { overflow-x: scroll; overflow-y: scroll; }";
                String js = "var style = document.createElement('style'); " +
                        "style.innerHTML = '" + css + "'; " +
                        "document.head.appendChild(style);";
                view.loadUrl("javascript:" + js);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            // For Android 5.0+
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams) {
                if (TanganiActivity.this.filePathCallback != null) {
                    TanganiActivity.this.filePathCallback.onReceiveValue(null);
                }
                TanganiActivity.this.filePathCallback = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try {
                    if (fileChooserParams.isCaptureEnabled()) {
                        // Capture photo using the camera
                        startActivityForResult(intent, REQUEST_CAMERA_CAPTURE);
                    } else {
                        // Select file from storage
                        startActivityForResult(intent, REQUEST_FILE_CHOOSER);
                    }
                } catch (Exception e) {
                    TanganiActivity.this.filePathCallback = null;
                    return false;
                }

                return true;
            }
        });

        String url = "https://tugasaja.masuk.web.id/applogindanregisterandroid/web/tanggapi.php?id=" + id;
        webView.loadUrl(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILE_CHOOSER || requestCode == REQUEST_CAMERA_CAPTURE) {
            if (filePathCallback != null) {
                Uri[] results = null;

                // Check if response is positive
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected files
                        results = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
                    } else if (requestCode == REQUEST_CAMERA_CAPTURE) {
                        // Use the captured photo URI
                        results = new Uri[]{fileUris[0]};
                    }
                }

                filePathCallback.onReceiveValue(results);
                filePathCallback = null;
            }
        }

        if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
            if (filePathCallback != null) {
                Uri cameraUri = fileUris[0];
                Uri[] results = new Uri[]{cameraUri};
                filePathCallback.onReceiveValue(results);
                filePathCallback = null;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the file URIs to handle orientation changes
        outState.putParcelableArray("fileUris", fileUris);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore the file URIs after orientation changes
        fileUris = (Uri[]) savedInstanceState.getParcelableArray("fileUris");
    }



    // Create a file URI for capturing camera image
    private Uri createCameraUri() {
        File imageFile = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String imageFileName = "IMG_" + timeStamp + ".jpg";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFile = new File(storageDir, imageFileName);

            // You can also use the cache directory instead of external files directory
            // File storageDir = getExternalCacheDir();

            // Create an empty file
            if (imageFile.createNewFile()) {
                // Return the file URI
                return Uri.fromFile(imageFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
