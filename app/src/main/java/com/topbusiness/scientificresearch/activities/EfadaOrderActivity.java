package com.topbusiness.scientificresearch.activities;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityEfadaOrderBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class EfadaOrderActivity extends AppCompatActivity implements UserListener {

    private ActivityEfadaOrderBinding efadaOrderBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        efadaOrderBinding = DataBindingUtil.setContentView(this,R.layout.activity_efada_order);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        efadaOrderBinding.setEvent(this);
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.back:
                finish();
                break;
        }

    }
}
