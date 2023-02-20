package com.topbusiness.scientificresearch.activities;

import android.content.Intent;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.topbusiness.scientificresearch.adapters.OtherLibAdapter;
import com.topbusiness.scientificresearch.databinding.ActivityOtherLibrariesBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.models.OtherLibModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.services.Api;
import com.topbusiness.scientificresearch.services.Services;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OtherLibrariesActivity extends AppCompatActivity implements UserListener {
    private ActivityOtherLibrariesBinding otherLibrariesBinding;
    private RecyclerView.LayoutManager manager;
    private OtherLibAdapter adapter;
    private List<OtherLibModel> otherLibModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otherLibrariesBinding = DataBindingUtil.setContentView(this,R.layout.activity_other_libraries);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        otherLibrariesBinding.setEvent(this);
        otherLibModelList = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        otherLibrariesBinding.recView.setLayoutManager(manager);
        adapter = new OtherLibAdapter(otherLibModelList,this);
        otherLibrariesBinding.recView.setAdapter(adapter);
        otherLibrariesBinding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);

        getLibraries();


    }

    private void getLibraries() {
        Retrofit retrofit = Api.getRetrofit();
        retrofit.create(Services.class)
                .getLibraries()
                .enqueue(new Callback<List<OtherLibModel>>() {
                    @Override
                    public void onResponse(Call<List<OtherLibModel>> call, Response<List<OtherLibModel>> response) {
                        if (response.isSuccessful())
                        {
                            otherLibModelList.clear();
                            if (response.body().size()>0)
                            {
                                otherLibrariesBinding.progBar.setVisibility(View.GONE);

                                otherLibModelList.addAll(response.body());
                                adapter.notifyDataSetChanged();

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<OtherLibModel>> call, Throwable t) {
                        otherLibrariesBinding.progBar.setVisibility(View.GONE);

                        Log.e("Error",t.getMessage());
                        Toast.makeText(OtherLibrariesActivity.this, R.string.netconn, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void setLink(String link)
    {
        Intent intent = new Intent(this,OtherWebViewActivity.class);
        intent.putExtra("url",link);
        startActivity(intent);
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
