package com.topbusiness.scientificresearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityTranslateBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class TranslateActivity extends AppCompatActivity implements UserListener {

    private ActivityTranslateBinding translateBinding;
    private String user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translateBinding = DataBindingUtil.setContentView(this,R.layout.activity_translate);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        translateBinding.setEvent(this);
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
            case R.id.freeTransBtn:
                Intent intent = new Intent(TranslateActivity.this,FreeTranslation.class);
                startActivity(intent);
                break;
            case R.id.paidTransBtn:
                Intent intent1 = new Intent(TranslateActivity.this,TadqeqActivity.class);
                intent1.putExtra("user_type",user_type);
                startActivity(intent1);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
