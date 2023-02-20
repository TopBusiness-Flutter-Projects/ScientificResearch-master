package com.topbusiness.scientificresearch.activities;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.topbusiness.scientificresearch.adapters.NotificationAdapter;
import com.topbusiness.scientificresearch.databinding.ActivityHomeBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.models.NotificationCount;
import com.topbusiness.scientificresearch.models.NotificationModel;
import com.topbusiness.scientificresearch.models.ResponseModel;
import com.topbusiness.scientificresearch.models.UserModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.services.Api;
import com.topbusiness.scientificresearch.services.Preferences;
import com.topbusiness.scientificresearch.services.Services;
import com.topbusiness.scientificresearch.services.Tags;
import com.topbusiness.scientificresearch.singleTone.UserSingleTone;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements UserListener,UserSingleTone.UserDataInterface{
    private ActivityHomeBinding homeBinding;
    private UserSingleTone userSingleTone;
    private UserModel userModel;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private Preferences preferences;
    private String user_type;
    private BottomSheetBehavior behavior;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<NotificationModel> notificationModelList;
    private int not_count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        homeBinding.setEvent(this);
        setSupportActionBar(homeBinding.toolBar);
        notificationModelList = new ArrayList<>();
        preferences = new Preferences(this);
        userSingleTone = UserSingleTone.getInstance();
        userSingleTone.GetUserData(this);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        Log.e("home","Home");
        manager = new LinearLayoutManager(this);
        homeBinding.recView.setLayoutManager(manager);
        homeBinding.recView.setNestedScrollingEnabled(true);
        adapter = new NotificationAdapter(notificationModelList,this);
        homeBinding.recView.setAdapter(adapter);
        behavior = BottomSheetBehavior.from(homeBinding.root);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState==BottomSheetBehavior.STATE_DRAGGING)
                {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        CreateLogOutAlert();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("user_type"))
            {
                user_type = intent.getStringExtra("user_type");
                updateUI(user_type);
            }
        }
    }

    private void updateUI(String user_type) {
        if (user_type.equals(Tags.user_app))
        {
            //homeBinding.flNot.setVisibility(View.VISIBLE);
            getUnreadNotification(userModel.getUser_id());
            getNotifications(userModel.getUser_id());
            FirebaseInstanceId.getInstance()
                    .getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (task.isSuccessful())
                            {
                                String token =task.getResult().getToken();
                                UpdateToken(userModel.getUser_id(),token);
                            }
                        }
                    });
        }else if (user_type.equals(Tags.visitor))
        {
            //homeBinding.flNot.setVisibility(View.GONE);

        }
    }

    private void CreateLogOutAlert() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.Logout_)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                        CreateProgressDialog(getString(R.string.logning_out));
                        LogOut();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                }).create();

    }

    private void LogOut() {

        Api.getRetrofit()
                .create(Services.class)
                .logOut(userModel.getUser_id())
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful())
                        {
                            if (response.body().getMessage()==1)
                            {

                                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                manager.cancelAll();
                                preferences.clearPref();
                                userSingleTone.Clear();
                                progressDialog.dismiss();
                                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {

                    }
                });


    }

    private void UpdateNotificationCount(int count)
    {
        if (count>0)
        {
            homeBinding.tvNot.setVisibility(View.VISIBLE);
            homeBinding.tvNot.setText(String.valueOf(count));
            not_count=1;

        }else
            {
                not_count=0;

                homeBinding.tvNot.setVisibility(View.GONE);

            }
    }

    private void getUnreadNotification(String user_id)
    {
        Api.getRetrofit()
                .create(Services.class)
                .getNotificationCount(user_id)
                .enqueue(new Callback<NotificationCount>() {
                    @Override
                    public void onResponse(Call<NotificationCount> call, Response<NotificationCount> response) {
                        if (response.isSuccessful())
                        {
                            UpdateNotificationCount(response.body().getCount_unread());
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationCount> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                        Toast.makeText(HomeActivity.this, R.string.something, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getNotifications(String user_id)
    {
        Api.getRetrofit().create(Services.class)
                .getNotification(user_id)
                .enqueue(new Callback<List<NotificationModel>>() {
                    @Override
                    public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {

                        if (response.isSuccessful())
                        {
                            homeBinding.progBar.setVisibility(View.GONE);
                            if (response.body().size()>0)
                            {
                                notificationModelList.addAll(response.body());
                                adapter.notifyDataSetChanged();
                                homeBinding.llNoNot.setVisibility(View.GONE);
                            }else
                            {
                                homeBinding.llNoNot.setVisibility(View.VISIBLE);

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                        Toast.makeText(HomeActivity.this,R.string.something, Toast.LENGTH_SHORT).show();
                        homeBinding.progBar.setVisibility(View.GONE);

                    }
                });
    }

    private void readNotification(String user_id)
    {
        Api.getRetrofit()
                .create(Services.class)
                .readNotification(user_id)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful())
                        {
                            if (response.body().getMessage()==1)
                            {
                                UpdateNotificationCount(0);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                    }
                });
    }

    private void UpdateToken(String user_id,String token)
    {
        Api.getRetrofit()
                .create(Services.class)
                .updateToken(user_id,token)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                        if (response.isSuccessful())
                        {
                            if (response.body().getMessage()==1)
                            {
                                Log.e("Token","updated successfully");
                            }else
                                {
                                    Log.e("Token","not updated");

                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.e("Error",t.getMessage());
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

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_aenat:
                Intent intent1 = new Intent(this,AenatActivity.class);
                intent1.putExtra("user_type",user_type);
                startActivity(intent1);

                break;
            case R.id.btn_db:
                Intent intent2 = new Intent(this,DatabaseActivity.class);
                intent2.putExtra("user_type",user_type);
                startActivity(intent2);
                break;
            case R.id.btn_tawf:
                Intent intent3 = new Intent(this,TawtheeqActivity.class);
                intent3.putExtra("user_type",user_type);
                startActivity(intent3);
                break;
            case R.id.btn_t7:
                Intent intent4 = new Intent(this,Ta7lelActivity.class);
                intent4.putExtra("user_type",user_type);

                startActivity(intent4);
                break;
            case R.id.btn_tad:
                Intent intent5 = new Intent(this,TadqeqActivity.class);
                intent5.putExtra("user_type",user_type);

                //intent5.putExtra("url","https://www.bibme.org/grammar-and-plagiarism/?=bmp_BM.A.300-250&intcid=wt.BibMe.BM.A.300-250&inhousead=BM.A.300-250");
                startActivity(intent5);
                break;
            case R.id.btn_control:
                Intent intent6 = new Intent(this,ControlActivity.class);
                intent6.putExtra("user_type",user_type);

                startActivity(intent6);
                break;
            case R.id.btn_train:
                Intent intent7 = new Intent(this,TrainingActivity.class);
                intent7.putExtra("user_type",user_type);
                startActivity(intent7);
                break;
            case R.id.btn_trans:
                Intent intent8 = new Intent(this,TranslateActivity.class);
                intent8.putExtra("user_type",user_type);
                startActivity(intent8);
                break;
            case R.id.btn_eqtbas:
                Intent intent9 = new Intent(this,EqtbasActivity.class);
                intent9.putExtra("user_type",user_type);
                startActivity(intent9);
                break;
            case R.id.btn_service:
                Intent intent10 = new Intent(this,ConsultingActivity.class);
                intent10.putExtra("user_type",user_type);
                startActivity(intent10);
                break;
            case R.id.flNot :
                if (not_count==1)
                {
                    readNotification(userModel.getUser_id());
                }
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.logout:
                if (user_type!=null && user_type.equals(Tags.visitor))
                {
                    finish();
                }else
                {
                    alertDialog.show();

                }
                break;

        }
    }




    @Override
    public void getUserData(UserModel userModel) {
        this.userModel=userModel;
    }
}
