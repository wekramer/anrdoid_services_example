package com.services.simple.services;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    public TextView textView;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download done");
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download failed");
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.status);


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(DownloadService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void onClick(View view) {

        Intent intent = new Intent(this, DownloadService.class);
        // add infos for the service which file to download and where to store
        //worksintent.putExtra(DownloadService.FILENAME, "index.html");
        //intent.putExtra(DownloadService.FILENAME, "updated resume for Bill Kramer mobile app developer.doc");
        intent.putExtra(DownloadService.FILENAME, "Example Domain.html");




        //works intent.putExtra(DownloadService.URL,
       //works         "http://www.vogella.com/index.html");
       // https://drive.google.com/drive/folders/0B9kJfmMmmKkUfllVd3FIVWpmNFBzcjB6ZFZTcE8tam1EXzhxbFZmdE1XWjBqMXhoQnhxWUU
        intent.putExtra(DownloadService.URL,
              //  "https://drive.google.com/drive/folders/0B9kJfmMmmKkUfllVd3FIVWpmNFBzcjB6ZFZTcE8tam1EXzhxbFZmdE1XWjBqMXhoQnhxWUU");
                "https://example.com/");


        startService(intent);
        textView.setText("Service started");
    }
}
