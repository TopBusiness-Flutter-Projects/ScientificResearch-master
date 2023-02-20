package com.topbusiness.scientificresearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityAenatBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AenatActivity extends AppCompatActivity implements UserListener {
    private String user_type="";
    private ActivityAenatBinding aenatBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aenatBinding = DataBindingUtil.setContentView(this,R.layout.activity_aenat);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        aenatBinding.setEvent(this);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("user_type"))
            {
                user_type = intent.getStringExtra("user_type");
            }
        }
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn1:
                Intent intent1 = new Intent(this,Mo3adlaActivity.class);
                intent1.putExtra("user_type",user_type);
                startActivity(intent1);
                break;
            case R.id.btn2:
                Intent intent2 = new Intent(this,OtherWebViewActivity.class);
                intent2.putExtra("url","https://www.surveysystem.com/sscalc.htm");
                startActivity(intent2);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
