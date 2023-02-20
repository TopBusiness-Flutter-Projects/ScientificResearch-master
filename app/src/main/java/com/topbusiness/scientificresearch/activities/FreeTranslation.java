package com.topbusiness.scientificresearch.activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityFreeTranslationBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.R;

public class FreeTranslation extends AppCompatActivity implements UserListener {

    private ActivityFreeTranslationBinding translationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        translationBinding = DataBindingUtil.setContentView(this,R.layout.activity_free_translation);
        translationBinding.setEvent(this);
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.babelfish:
                Intent intent1 = new Intent(FreeTranslation.this,OtherWebViewActivity.class);
                intent1.putExtra("url","https://www.babelfish.com/");
                startActivity(intent1);
                break;
            case R.id.wordRef:
                Intent intent2 = new Intent(FreeTranslation.this,OtherWebViewActivity.class);
                intent2.putExtra("url","http://www.wordreference.com/");
                startActivity(intent2);
                break;
            case R.id.freeTrans:
                Intent intent3 = new Intent(FreeTranslation.this,OtherWebViewActivity.class);
                intent3.putExtra("url","https://www.freetranslation.com/");
                startActivity(intent3);
                break;
            case R.id.promtOnline:
                Intent intent4 = new Intent(FreeTranslation.this,OtherWebViewActivity.class);
                intent4.putExtra("url","http://translation2.paralink.com/");
                startActivity(intent4);
                break;
            case R.id.doc_trans:
                Intent intent5 = new Intent(FreeTranslation.this,OtherWebViewActivity.class);
                intent5.putExtra("url","https://www.onlinedoctranslator.com/translationform");
                startActivity(intent5);
                break;
            case R.id.back:
                finish();
                break;
        }

    }
}
