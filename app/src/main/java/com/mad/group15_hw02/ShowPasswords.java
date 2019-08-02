//Assignment: Homework 02
//File Name : GROUP15_HW02.zip
//Full Name : Manideep Reddy Nukala, Sai Charan Reddy Vallapureddy
package com.mad.group15_hw02;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowPasswords extends AppCompatActivity {
    static String MainKey = "MainKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Generated Passwords");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_passwords);
        ScrollView threadScrollView = findViewById(R.id.threadScrollView);
        ScrollView asyncScrollView = findViewById(R.id.asyncScrollView);
        if(getIntent() != null && getIntent().getExtras() !=null)
        {
            ArrayList<Password> passwordList = (ArrayList<Password>) getIntent().getExtras().getSerializable(MainActivity.passwordKey);
            // Parent layout
            LayoutInflater layoutInflater = getLayoutInflater();
            View view;

            for (Password password : passwordList){
                if(password.getPasswordType().equals("thread")) {
                    LinearLayout threadLayout = (LinearLayout)findViewById(R.id.threadlayout);
                    // Layout inflater
                    // Add the text layout to the parent layout
                    view = layoutInflater.inflate(R.layout.text_layout, threadLayout, false);
                    // In order to get the view we have to use the new view with text_layout in it
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    textView.setText(password.getPassword());
                    // Add the text view to the parent layout
                    threadLayout.addView(textView);
                }
                else
                {
                    LinearLayout asyncLayout = (LinearLayout)findViewById(R.id.asynclayout);
                    // Layout inflater
                    // Add the text layout to the parent layout
                    view = layoutInflater.inflate(R.layout.text_layout, asyncLayout, false);
                    // In order to get the view we have to use the new view with text_layout in it
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    textView.setText(password.getPassword());
                    // Add the text view to the parent layout
                    asyncLayout.addView(textView);
                }

                findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ShowPasswords.this,MainActivity.class);
                        intent.putExtra(MainKey,"main");
                        startActivity(intent);
                    }
                });

        }

    }
}}
