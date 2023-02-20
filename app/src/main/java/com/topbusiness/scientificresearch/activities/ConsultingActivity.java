package com.topbusiness.scientificresearch.activities;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityConsultingBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.R;

import me.anwarshahriar.calligrapher.Calligrapher;

public class ConsultingActivity extends AppCompatActivity implements UserListener {
    ActivityConsultingBinding activityConsultingBinding;
    private String user_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityConsultingBinding = DataBindingUtil.setContentView(this,R.layout.activity_consulting);
        activityConsultingBinding.setEvent(this);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
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
                if (isWhatsApp_installed())
                {
                    String phone_number = "+050 5243083";
                    phone_number = phone_number.replace("+","");
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "السلام عليكم");
                    sendIntent.putExtra("jid", phone_number + "@s.whatsapp.net"); //phone number without "+" prefix
                    sendIntent.setPackage("com.whatsapp");
                    if (sendIntent.resolveActivity(getPackageManager()) == null) {
                        Toast.makeText(this, "Error/n" + "", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startActivity(sendIntent);
                }else
                {
                    Toast.makeText(this, R.string.plz_inst_wa, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn2:
                Intent intent2 = new Intent(this,ReviewPlanSearchActivity.class);
                intent2.putExtra("user_type",user_type);
                startActivity(intent2);

                break;
            case R.id.btn3:
                Intent intent3 = new Intent(this,ReviewFullSearchActivity.class);
                intent3.putExtra("user_type",user_type);
                startActivity(intent3);
                break;
            /*case R.id.btn4:
                Intent intent4 = new Intent(this,PaidTranslationActivity.class);
                intent4.putExtra("user_type",user_type);
                intent4.putExtra("type","8");

                intent4.putExtra("title",activityConsultingBinding.btn4.getText().toString());
                startActivity(intent4);
                break;*/
            case R.id.btn5:
                Intent intent5 = new Intent(this,FormatSearchActivity.class);
                intent5.putExtra("user_type",user_type);
                startActivity(intent5);
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    private boolean isWhatsApp_installed()
    {

        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
