package com.topbusiness.scientificresearch.adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.topbusiness.scientificresearch.activities.OtherLibrariesActivity;
import com.topbusiness.scientificresearch.models.OtherLibModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.databinding.OtherLibRowBinding;

import java.util.List;

public class OtherLibAdapter extends RecyclerView.Adapter <OtherLibAdapter.MyHolder>{

    private List<OtherLibModel> otherLibModelList;
    private Context context;
    private OtherLibrariesActivity activity;

    public OtherLibAdapter(List<OtherLibModel> otherLibModelList, Context context) {
        this.otherLibModelList = otherLibModelList;
        this.context = context;
        this.activity = (OtherLibrariesActivity) context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OtherLibRowBinding otherLibRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.other_lib_row,parent,false);
        return new MyHolder(otherLibRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, @SuppressLint("RecyclerView") int position) {
        OtherLibModel otherLibModel = otherLibModelList.get(position);
        holder.otherLibRowBinding.setModel(otherLibModel);
        holder.otherLibRowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setLink(otherLibModelList.get(position).getLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return otherLibModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        OtherLibRowBinding otherLibRowBinding;
        public MyHolder(OtherLibRowBinding itemView) {
            super(itemView.getRoot());
            otherLibRowBinding=itemView;
        }
    }
}
