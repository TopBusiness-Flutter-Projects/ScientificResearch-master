package com.topbusiness.scientificresearch.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.topbusiness.scientificresearch.databinding.NotificationRowBinding;
import com.topbusiness.scientificresearch.models.NotificationModel;
import com.topbusiness.scientificresearch.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter <NotificationAdapter.MyHolder>{

    private List<NotificationModel> notificationModelList;
    private Context context;

    public NotificationAdapter(List<NotificationModel> notificationModelList, Context context) {
        this.notificationModelList = notificationModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationRowBinding notificationRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.notification_row,parent,false);
        return new MyHolder(notificationRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        NotificationModel notificationModel = notificationModelList.get(position);
        holder.notificationRowBinding.setNotificationModel(notificationModel);
        if (notificationModel.getApproved().equals("1"))
        {
            holder.notificationRowBinding.tvReply.setText("تم الموافقة على الحجز");
        }else
            {
                holder.notificationRowBinding.tvReply.setText("تم رفض الحجز");

            }
            holder.notificationRowBinding.tvCourseDate.setText("تاريخ الكورس "+notificationModel.getCourse_date());

    }



    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private NotificationRowBinding notificationRowBinding;
        public MyHolder(NotificationRowBinding notificationRowBinding) {
            super(notificationRowBinding.getRoot());
            this.notificationRowBinding = notificationRowBinding;

        }
    }

}
