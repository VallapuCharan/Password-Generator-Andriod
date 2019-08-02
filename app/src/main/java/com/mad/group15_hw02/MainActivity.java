//Assignment: Homework 02
//File Name : GROUP15_HW02.zip
//Full Name : Manideep Reddy Nukala, Sai Charan Reddy Vallapureddy
package com.mad.group15_hw02;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static String  THREAD_PASS_KEY="Thread_password";
    ExecutorService threadPool;
    int count_step = 1;
    int count_max = 10;
    int count_min = 1;
    int length_step = 1;
    int length_max = 23;
    int length_min = 7;
    double thread_count_value;
    double async_count_value;
    double thread_length_value;
    double  async_length_value;

    TextView forThread;
    TextView threadPwdLength;
    TextView threadPwdCount;
    TextView threadCount;
    TextView threadLength;
    SeekBar threadCountSeekBar;
    SeekBar threadLengthSeekBar;
    TextView forAsyncTask;
    TextView asyncPwdCount;
    TextView asyncCount;
    SeekBar asyncCountSeekBar;
    TextView asyncPwdLength;
    TextView asyncLength;
    SeekBar asyncLengthSeekBar;
    ProgressBar progressBar;
    Button generate;
    TextView progressPercent;
    TextView progressPercentLeft;
    TextView generatingPassword;
    Handler handler;
    static ArrayList<Password> passwordList = new ArrayList<Password>();
    static Password password;
    Password passwordAsync;
    boolean threadDone;
    public int total_count;
    static String passwordKey = "PasswordKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        threadPool = Executors.newFixedThreadPool(2);


        setTitle("Password Generator");



        forThread = findViewById(R.id.forThread);
        threadPwdCount = findViewById(R.id.threadPwdCount);
        threadCount = findViewById(R.id.threadCount);
        threadCountSeekBar = findViewById(R.id.threadCountSeekBar);
        threadPwdLength = findViewById(R.id.threadPwdLength);
        threadLength = findViewById(R.id.threadLength);
        threadLengthSeekBar = findViewById(R.id.threadLengthSeekBar);

        forAsyncTask = findViewById(R.id.forAsyncTask);
        asyncPwdCount = findViewById(R.id.asyncPwdCount);
        asyncCount = findViewById(R.id.asyncCount);
        asyncCountSeekBar = findViewById(R.id.asyncCountSeekBar);
        asyncPwdLength = findViewById(R.id.asyncPwdLength);
        asyncLength = findViewById(R.id.asyncLength);
        asyncLengthSeekBar = findViewById(R.id.asyncLengthSeekBar);
        generate = findViewById(R.id.generate);
        progressPercent = findViewById(R.id.progressPercent);
        progressPercentLeft = findViewById(R.id.progressPercentLeft);
        generatingPassword = findViewById(R.id.generatingPassword);

        threadCountSeekBar.setMax( (count_max - count_min) / length_step );
        asyncCountSeekBar.setMax( (count_max - count_min) / length_step );
        threadLengthSeekBar.setMax( (length_max - length_min) / length_step );
        asyncLengthSeekBar.setMax( (length_max - length_min) / length_step );

        if(getIntent() != null && getIntent().getExtras() !=null) {
            Bundle mainFlag = getIntent().getExtras().getBundle(ShowPasswords.MainKey);

        }
        threadCountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               thread_count_value = count_min + (progress * count_step);
                threadCount.setText(""+(int) thread_count_value);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        asyncCountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               async_count_value = count_min + (progress * count_step);
               asyncCount.setText(""+(int) async_count_value);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        threadLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                thread_length_value = length_min + (progress * length_step);
                threadLength.setText(""+(int) thread_length_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        asyncLengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                async_length_value = length_min + (progress * length_step);
                asyncLength.setText(""+(int) async_length_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordList.clear();
                total_count=Integer.parseInt(threadCount.getText().toString())+Integer.parseInt(asyncCount.getText().toString());
                //progressBar.setProgress(0);
                threadPool.execute(new doWork());
            }
        });
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        generatingPassword.setVisibility(View.INVISIBLE);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what)
                {
                    case doWork.STATUS_START: {
                        progressBar.setMax(Integer.parseInt((String) threadCount.getText()) + Integer.parseInt((String) asyncCount.getText()));
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(0);

                        forThread.setVisibility(View.INVISIBLE);
                        threadPwdCount.setVisibility(View.INVISIBLE);
                        threadCount.setVisibility(View.INVISIBLE);
                        threadCountSeekBar.setVisibility(View.INVISIBLE);
                        threadPwdLength.setVisibility(View.INVISIBLE);
                        threadLength.setVisibility(View.INVISIBLE);
                        threadLengthSeekBar.setVisibility(View.INVISIBLE);

                        forAsyncTask.setVisibility(View.INVISIBLE);
                        asyncPwdCount.setVisibility(View.INVISIBLE);
                        asyncCount.setVisibility(View.INVISIBLE);
                        asyncCountSeekBar.setVisibility(View.INVISIBLE);
                        asyncPwdLength.setVisibility(View.INVISIBLE);
                        asyncLength.setVisibility(View.INVISIBLE);
                        asyncLengthSeekBar.setVisibility(View.INVISIBLE);
                        generate.setVisibility(View.INVISIBLE);
                        progressPercent.setVisibility(View.VISIBLE);
                        progressPercentLeft.setVisibility(View.VISIBLE);
                        progressPercent.setText("0/"+total_count);
                        progressPercentLeft.setText("0%");
                        generatingPassword.setVisibility(View.VISIBLE);

                        progressBar.setProgress(0);
                        break;
                    }
                    case doWork.STATUS_PROGRESS:{
                        passwordList.add((Password) msg.obj);
                        int percent = msg.getData().getInt(doWork.PROGRESS_KEY);
                        progressBar.setProgress(msg.getData().getInt(doWork.PROGRESS_KEY));
                        progressPercent.setText(msg.getData().getInt(doWork.PROGRESS_KEY)+"/"+total_count);
                        Double percentDouble= (Double.parseDouble((String.valueOf(percent)))/total_count)*100;
                        progressPercentLeft.setText( String.format("%.0f", percentDouble) + "%");
                        progressPercent.setVisibility(View.VISIBLE);
                        generatingPassword.setVisibility(View.VISIBLE);
                        break;
                    }
                    case doWork.STATUS_END:
                    {
                        Intent intent = new Intent(MainActivity.this,ShowPasswords.class);
                        intent.putExtra(passwordKey,passwordList);
                        startActivity(intent);
                        break;

                    }
                }
                return false;
            }
        });
        }

    class doWork implements Runnable{
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_END = 0x02;
        static final String PROGRESS_KEY = "progress";

        @Override
        public void run() {
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);
            String numberofPasswords = (String) threadCount.getText();
            String sizeofPasswords = (String) threadLength.getText();
            Util getPassword = new Util();
            for(int i=1; i<=Integer.parseInt(numberofPasswords); i++)
            {
                Message progressMessage = new Message();
                password = new Password("thread",(String)getPassword.getPassword(Integer.parseInt(sizeofPasswords)));
                //Log.d("demo",password.password);
                progressMessage.what = STATUS_PROGRESS;
                progressMessage.obj = (Password)password;
                Bundle b = new Bundle();
                b.putInt(PROGRESS_KEY,(Integer)i);
                progressMessage.setData(b);
                handler.sendMessage(progressMessage);
                threadDone = true;
            }
            new doAsyncTask().execute(1);
        }

    }
    class doAsyncTask extends AsyncTask<Integer, Integer, Double>
    {
        String numberofPasswords = (String) asyncCount.getText();
        String sizeofPasswords = (String) asyncLength.getText();
        Util getPassword = new Util();
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_END = 0x02;
        static final String PROGRESS_KEY = "progress";
        int numberOfPasswordsAsync = Integer.parseInt((String) asyncCount.getText());
        int numerOfPasswordsThread = Integer.parseInt((String) threadCount.getText());
        int sizeOfPasswordsAsync = Integer.parseInt((String) asyncLength.getText());
        @Override
        protected Double doInBackground(Integer... integers) {
            for(int i=1; i<=numberOfPasswordsAsync; i++) {
                passwordAsync = new Password("async", (String) getPassword.getPassword(sizeOfPasswordsAsync));
                publishProgress(i+ numerOfPasswordsThread );
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            Message stopMessage = new Message();
            stopMessage.what = STATUS_END;
            handler.sendMessage(stopMessage);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Message progressMessage = new Message();
            progressMessage.what = STATUS_PROGRESS;
            progressMessage.obj = (Password)passwordAsync;
            Bundle b = new Bundle();
            Log.d("demo", String.valueOf(values[0]));
            b.putInt(PROGRESS_KEY,(Integer) values[0]);
            progressMessage.setData(b);
            handler.sendMessage(progressMessage);

        }
    }
}
