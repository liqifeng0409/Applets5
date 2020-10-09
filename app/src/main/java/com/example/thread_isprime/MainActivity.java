package com.example.thread_isprime;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView result = findViewById(R.id.result);;

        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                if(msg.arg1 == 11)
                    result.setText("是素数");
                else if(msg.arg1 == 12){
                    result.setText("不是素数");
                }
                else
                    result.setText("判断失败");
            }
        };

        final Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                EditText input = findViewById(R.id.input);
                int n = Integer.parseInt(input.getText().toString());
                boolean isPrime = true;
                        synchronized (this) {
                            try {
                                if (n <= 3) {
                                }
                                //当n>3时，质数无法被比它小的数整除
                                for(int i = 2; i < n; i++){
                                    if (n % i == 0) {
                                        isPrime = false;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Message msg = new Message();

                            if(isPrime){
                                msg.arg1 = 11;
                            }
                            else {
                                msg.arg1 = 12;
                            }
                            handler.sendMessage(msg);
                        }

                    }
        };

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();
            }
        });
            }
    }
