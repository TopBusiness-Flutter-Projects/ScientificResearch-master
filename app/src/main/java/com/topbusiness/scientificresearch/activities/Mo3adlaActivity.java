package com.topbusiness.scientificresearch.activities;

import android.app.AlertDialog;
import android.content.Intent;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityMo3adlaBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class Mo3adlaActivity extends AppCompatActivity implements UserListener {

    private ActivityMo3adlaBinding mo3adlaBinding;
    private String user_type="";
    private AlertDialog alertDialog;
    private double input_num;
    private double output;
    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mo3adlaBinding = DataBindingUtil.setContentView(this,R.layout.activity_mo3adla);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        mo3adlaBinding.setEvent(this);
        getDataFromIntent();
        //CreateAlertDialog();
    }

    /*private void CreateAlertDialog() {
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
*/
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
            case R.id.result_btn:
                if (!TextUtils.isEmpty(mo3adlaBinding.input.getText().toString()))
                {
                    input_num =Double.parseDouble(mo3adlaBinding.input.getText().toString());
                    try {
                        output = ( input_num*0.50*(1.0-0.50))/(  (input_num-1.0) *((0.05*0.05)/( 1.96*1.96))+( 0.50 *(1.0-0.50))  );
                        result = (int) Math.round(output);
                        mo3adlaBinding.result.setText(String.valueOf(result));


                    }catch (ArithmeticException e)
                    {
                        Toast.makeText(this, "لايمكن القسمة على 0 أدخل حجم العينة مرة أخرى", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(this, "أدخل حجم العينة", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
