package com.topbusiness.scientificresearch.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityDatabaseBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class DatabaseActivity extends AppCompatActivity implements UserListener {

    private String user_type="";
    private ActivityDatabaseBinding databaseBinding;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseBinding = DataBindingUtil.setContentView(this,R.layout.activity_database);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        databaseBinding.setEvent(this);
        CreateAlertDialog();
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
    private void CreateAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.ser_not_av))
                .setPositiveButton(getString(R.string.close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                        finish();

                    }
                }).setCancelable(false).create();
    }
    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            /*case R.id.prev:
                Intent intent1 = new Intent(this,OtherWebViewActivity.class);
                intent1.putExtra("url","http://ecat.kfnl.gov.sa:88/hipmain/");
                startActivity(intent1);
                break;
            case R.id.order:
                Intent intent2 = new Intent(this,OtherWebViewActivity.class);
                intent2.putExtra("url","https://sdl.edu.sa/SDLPortal/Publishers.aspx");
                startActivity(intent2);
                break;

            case R.id.king_abdullah:
                Intent intent3 = new Intent(this,OtherWebViewActivity.class);
                intent3.putExtra("url","https://uqu.edu.sa/lib");
                startActivity(intent3);
                break;*/
            case R.id.btn_reference:
                Intent intent3 = new Intent(this,ReferencesActivity.class);
                intent3.putExtra("user_type",user_type);
                startActivity(intent3);
                break;

            case R.id.back:
                finish();
                break;
            case R.id.other_library:
                Intent intent4 = new Intent(this,OtherLibrariesActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
