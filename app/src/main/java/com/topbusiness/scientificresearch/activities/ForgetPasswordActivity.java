package com.topbusiness.scientificresearch.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityForgetPasswordBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.models.ResponseModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.services.Api;
import com.topbusiness.scientificresearch.services.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity implements UserListener {
    private ActivityForgetPasswordBinding binding;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_password);
        binding.setEvent(this);
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.rest_btn:
                CheckData();
                break;
            case R.id.image_back:
                finish();
                break;
        }
    }

    private void CheckData() {
        String m_email = binding.edtEmail.getText().toString();

        if (!TextUtils.isEmpty(m_email)&& Patterns.EMAIL_ADDRESS.matcher(m_email).matches())
        {
            binding.edtEmail.setError(null);
            ResetPassword(m_email);
        }else
            {
                if (TextUtils.isEmpty(m_email))
                {
                    binding.edtEmail.setError(getString(R.string.email_req));
                }else if (!Patterns.EMAIL_ADDRESS.matcher(m_email).matches())
                {
                    binding.edtEmail.setError(getString(R.string.inv_email));

                }
            }


    }

    private void ResetPassword(String m_email) {
        CreateProgressDialog(getString(R.string.wait));
        Api.getRetrofit()
                .create(Services.class)
                .resetPassword(m_email)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful())
                        {
                            progressDialog.dismiss();
                            if (response.body().getMessage()==1)
                            {
                                CreateAlertDialog();
                            }else
                                {
                                    Toast.makeText(ForgetPasswordActivity.this, R.string.mail_not_reg, Toast.LENGTH_LONG).show();

                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(ForgetPasswordActivity.this,R.string.something, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void CreateProgressDialog(String msg)
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminateDrawable(drawable);
        progressDialog.show();
    }

    private void CreateAlertDialog()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .create();
        View view = LayoutInflater.from(this).inflate(R.layout.custom_alert_text_dialog,null);
        Button btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setView(view);
        alertDialog.getWindow().getAttributes().windowAnimations=R.style.custom_dialog;
        alertDialog.show();
    }
}
