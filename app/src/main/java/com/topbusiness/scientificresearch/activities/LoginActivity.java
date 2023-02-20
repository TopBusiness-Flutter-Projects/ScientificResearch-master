package com.topbusiness.scientificresearch.activities;

import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityLoginBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.models.UserModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.services.Api;
import com.topbusiness.scientificresearch.services.Preferences;
import com.topbusiness.scientificresearch.services.Services;
import com.topbusiness.scientificresearch.services.Tags;
import com.topbusiness.scientificresearch.singleTone.UserSingleTone;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements UserListener {

    private ActivityLoginBinding loginBinding;
    private UserSingleTone userSingleTone;
    private ProgressDialog dialog;
    private Preferences preferences;
    private String session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        loginBinding.setEvent(this);
        userSingleTone = UserSingleTone.getInstance();

        ////////////////////////////////////////
        preferences = new Preferences(this);
        session = preferences.getSession();
        if (session!=null&&!TextUtils.isEmpty(session))
        {
            if (session.equals(Tags.Login_session))
            {
                UserModel userModel = preferences.GetUserData();
                if (userModel!=null)
                {
                    userSingleTone.SetUserData(userModel);
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    intent.putExtra("user_type",Tags.user_app);

                    startActivity(intent);
                    finish();
                    return;
                }

            }
        }
        CreateProgDialog();

        if (Locale.getDefault().equals("ar"))
        {
            loginBinding.userName.setGravity(Gravity.RIGHT);
        }else
            {
                loginBinding.userName.setGravity(Gravity.LEFT);

            }
    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.login:
                Login();
                break;
            case R.id.tv_forget_password:
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.skip:
                Intent intent2 = new Intent(this,HomeActivity.class);
                intent2.putExtra("user_type",Tags.visitor);
                startActivity(intent2);
                break;

            case R.id.create_account:
                Intent intent3 = new Intent(this,RegisterActivity.class);
                startActivity(intent3);
                break;

        }
    }



    private void CreateProgDialog()
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

    }
    private void Login() {
        String user_name = loginBinding.userName.getText().toString();
        String user_pass = loginBinding.userPassword.getText().toString();
        if (TextUtils.isEmpty(user_name))
        {
            loginBinding.userName.setError(getString(R.string.enter_user_email));

        }else if (TextUtils.isEmpty(user_pass))
        {
            loginBinding.userPassword.setError(getString(R.string.enter_pass));
            loginBinding.userName.setError(null);

        }
        else
            {
                loginBinding.userPassword.setError(null);
                dialog.show();
                Map <String,String> map = new HashMap<>();
                map.put("user",user_name);
                map.put("user_pass",user_pass);
                Retrofit retrofit = Api.getRetrofit();
                Services services = retrofit.create(Services.class);
                Call<UserModel> call = services.Login(map);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                        if (response.isSuccessful())
                        {
                            UserModel userModel = response.body();
                            Log.e("mssage",userModel.getMessage()+"");
                            if (userModel.getMessage()==1)
                            {
                                preferences.CreateSharedPref(userModel);
                                userSingleTone.SetUserData(userModel);
                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                intent.putExtra("user_type",Tags.user_app);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(LoginActivity.this, R.string.error_user_pass, Toast.LENGTH_LONG).show();
                                dialog.dismiss();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Log.e("error",t.getMessage());
                        Toast.makeText(LoginActivity.this, R.string.fail_try, Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                    }
                });
            }

    }
}
