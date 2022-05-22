package ru.mirea.zyryanov.socketconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private final String host = "time-a.nist.gov";
    private final int port = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        Button getTimeButton = (Button) findViewById(R.id.button);
        getTimeButton.setOnClickListener(this::onGetTimeClick);
    }

    private void onGetTimeClick(View view){
        Thread showTimeThread = new Thread(() -> {
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                reader.readLine();
                String result = reader.readLine();
                socket.close();
                textView.post(() -> textView.setText(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        showTimeThread.start();
    }
}