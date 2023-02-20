package com.topbusiness.scientificresearch.activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.topbusiness.scientificresearch.databinding.ActivityVideoViewBinding;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.models.TrainingModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.services.Tags;

import cn.jzvd.Jzvd;


public class VideoViewActivity extends AppCompatActivity implements UserListener {
    private ActivityVideoViewBinding binding;
    private TrainingModel.VideoModel videoModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_view);
        binding.setEvent(this);
        getDataFromIntent();



    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            videoModel = (TrainingModel.VideoModel) intent.getSerializableExtra("data");
            UpdateUI(videoModel);
        }
    }

    private void UpdateUI(TrainingModel.VideoModel videoModel) {
        binding.tvTitle.setText(videoModel.getVideo_title());
        //binding.imgPlayBtn.setVisibility(View.GONE);
        binding.videoplayer.setUp(Tags.video_path+videoModel.getVideo()
                , videoModel.getVideo_title() , Jzvd.SCREEN_WINDOW_NORMAL);
        binding.videoplayer.batteryLevel.setVisibility(View.GONE);
        binding.videoplayer.bottomProgressBar.setVisibility(View.VISIBLE);
        binding.videoplayer.loadingProgressBar.setVisibility(View.VISIBLE);
        binding.videoplayer.loadingProgressBar.setIndeterminate(true);


    }

    @Override
    public void onClickListener(View view) {
        int id = view.getId();
        /*if (id==R.id.imgPlayBtn)
        {



        }else*/ if (id==R.id.back)
        {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
//https://github.com/MarcinMoskala/VideoPlayView/raw/master/videos/cat1.mp4"
