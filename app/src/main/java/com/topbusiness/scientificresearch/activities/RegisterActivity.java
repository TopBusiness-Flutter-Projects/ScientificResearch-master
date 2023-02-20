package com.topbusiness.scientificresearch.activities;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityRegisterBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.models.UserModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.services.Api;
import com.topbusiness.scientificresearch.services.Preferences;
import com.topbusiness.scientificresearch.services.Services;
import com.topbusiness.scientificresearch.services.Tags;
import com.topbusiness.scientificresearch.singleTone.UserSingleTone;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements UserListener {
    private ActivityRegisterBinding registerBinding;
    private String user_Type,degree;
    private UserSingleTone userSingleTone;
    private ProgressDialog dialog;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.user_type));
        registerBinding.userType.setAdapter(adapter);
        registerBinding.userType.setSelection(0);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.type));
        registerBinding.type.setAdapter(adapter2);
        registerBinding.type.setSelection(0);
        registerBinding.setEvent(this);
        registerBinding.userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try
                {
                    int pos = registerBinding.userType.getSelectedItemPosition();
                    switch (pos)
                    {
                        case 0:
                            user_Type= Tags.mo7alel;
                            break;
                        case 1:
                            user_Type= Tags.ba7eth;

                            break;
                        case 2:
                            user_Type= Tags.mo7akem;

                            break;
                        case 3:
                            user_Type = Tags.modaqeq;
                            break;

                    }
                    ((TextView)adapterView.getChildAt(0)).setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.white));

                }catch (NullPointerException e){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerBinding.type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try
                {
                    int pos = registerBinding.type.getSelectedItemPosition();
                    switch (pos)
                    {
                        case 0:
                            degree= Tags.doc;
                            break;
                        case 1:
                            degree= Tags.mo3eed;

                            break;
                        case 2:
                            degree= Tags.mo7ader;

                            break;
                        case 3:
                            degree = Tags.ostaz;
                            break;
                        case 4:
                            degree = Tags.ostazmosa3ed;
                            break;
                        case 5:
                            degree = Tags.ostazmosharek;
                            break;

                    }
                    ((TextView)adapterView.getChildAt(0)).setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.white));

                }catch (NullPointerException e){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ////////////////////////////////////////////
        preferences = new Preferences(this);
        userSingleTone = UserSingleTone.getInstance();
        CreateProgDialog();
        ////////////////////////////////////////////


    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.create_account:
                CreateNewAccount();

                break;
            case R.id.back:
                Intent intent2 = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent2);
                finish();
                break;

        }
    }

    private void CreateProgDialog()
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.create_acc));
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

    }
    private void CreateNewAccount() {

        String name = registerBinding.name.getText().toString();
        String email= registerBinding.email.getText().toString();
        String phone= registerBinding.userPhone.getText().toString();
        String country=registerBinding.country.getText().toString();
        String organization = registerBinding.organization.getText().toString();
        String user_name = registerBinding.userName.getText().toString();
        String user_pass = registerBinding.userPassword.getText().toString();
        String user_specialization = registerBinding.speciality.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            registerBinding.name.setError(getString(R.string.enter_first_last));
        }else if (TextUtils.isEmpty(email))
        {
            registerBinding.name.setError(null);
            registerBinding.email.setError(getString(R.string.enter_email));


        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            registerBinding.name.setError(null);
            registerBinding.email.setError(getString(R.string.inv_email));


        }
        else if (TextUtils.isEmpty(phone))
        {
            registerBinding.email.setError(null);
            registerBinding.userPhone.setError(getString(R.string.enter_phone));

        }
        else if (!Patterns.PHONE.matcher(phone).matches())
        {
            registerBinding.email.setError(null);
            registerBinding.userPhone.setError(getString(R.string.inv_phone));

        }
        else if (TextUtils.isEmpty(country))
        {
            registerBinding.userPhone.setError(null);
            registerBinding.country.setError(getString(R.string.enter_country));

        }
        else if (TextUtils.isEmpty(organization))
        {
            registerBinding.country.setError(null);
            registerBinding.organization.setError(getString(R.string.enter_orgn));

        }
        else if (TextUtils.isEmpty(user_specialization))
        {
            registerBinding.organization.setError(null);
            registerBinding.speciality.setError(getString(R.string.enter_spec));

        }
        else if (TextUtils.isEmpty(user_name))
        {
            registerBinding.speciality.setError(null);
            registerBinding.userPhone.setError(getString(R.string.enter_username));

        }
        else if (TextUtils.isEmpty(user_pass))
        {
            registerBinding.userName.setError(null);
            registerBinding.userPassword.setError(getString(R.string.enter_pass));

        }
        else
            {
                dialog.show();
                Map<String,String> map = new HashMap<>();
                map.put("user_name",name);
                map.put("user_username",user_name);
                map.put("user_pass",user_pass);
                map.put("user_email",email);
                map.put("user_phone",phone);
                map.put("user_country",country);
                map.put("degree",degree);
                map.put("company",organization);
                map.put("specialization",user_specialization);
                map.put("user_token_id","");
                map.put("user_type",user_Type);

                registerBinding.userPassword.setError(null);
                Retrofit retrofit = Api.getRetrofit();
                Services services = retrofit.create(Services.class);
                Call<UserModel> call = services.CreateNewAccount(map);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful())
                        {


                        UserModel userModel = response.body();
                        if (userModel.getMessage()==1)
                        {
                            preferences.CreateSharedPref(userModel);
                            userSingleTone.SetUserData(userModel);
                            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                            intent.putExtra("user_type",Tags.user_app);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }else
                            {
                                Toast.makeText(RegisterActivity.this, R.string.fail_try, Toast.LENGTH_LONG).show();
                                dialog.dismiss();


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.e("error",t.getMessage());
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, R.string.fail_try, Toast.LENGTH_LONG).show();


                    }
                });
            }



    }


}
