package com.topbusiness.scientificresearch.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topbusiness.scientificresearch.activities.CourseDetailsActivity;
import com.topbusiness.scientificresearch.databinding.VideoRowBinding;
import com.topbusiness.scientificresearch.models.TrainingModel;
import com.topbusiness.scientificresearch.R;

import java.util.List;

public class TrainingVideoAdapter extends RecyclerView.Adapter <TrainingVideoAdapter.MyHolder>{

    private List<TrainingModel.VideoModel> videoModelList;
    private Context context;
    private CourseDetailsActivity activity;

    public TrainingVideoAdapter(List<TrainingModel.VideoModel> videoModelList, Context context) {
        this.videoModelList = videoModelList;
        this.context = context;
        this.activity = (CourseDetailsActivity) context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoRowBinding videoRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.video_row,parent,false);
        return new MyHolder(videoRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        TrainingModel.VideoModel videoModel = videoModelList.get(position);
        holder.videoRowBinding.setVideoModel(videoModel);
        holder.videoRowBinding.tvDuration.setText(videoModel.getVideo_duration());
        holder.videoRowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TrainingModel.VideoModel videoModel = videoModelList.get(holder.getAdapterPosition());
                activity.setItem(videoModel);

            }
        });
    }



    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private VideoRowBinding videoRowBinding;
        public MyHolder(VideoRowBinding videoRowBinding) {
            super(videoRowBinding.getRoot());
            this.videoRowBinding = videoRowBinding;

        }
    }

}
