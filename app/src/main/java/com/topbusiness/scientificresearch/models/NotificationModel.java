package com.topbusiness.scientificresearch.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.topbusiness.scientificresearch.services.Tags;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class NotificationModel implements Serializable {
    private String book_id;
    private String user_id_fk;
    private String course_id_fk;
    private String approved;
    private String approved_date;
    private String user_read;
    private String course_id_pk;
    private String course_name;
    private String course_date;
    private String course_image;
    private String course_capacity;
    private String course_days;
    private String course_desc;
    private String course_funds;
    private String course_vedio;


    public String getBook_id() {
        return book_id;
    }

    public String getUser_id_fk() {
        return user_id_fk;
    }

    public String getCourse_id_fk() {
        return course_id_fk;
    }

    public String getApproved() {
        return approved;
    }

    public String getApproved_date() {
        return approved_date;
    }

    public String getUser_read() {
        return user_read;
    }

    public String getCourse_id_pk() {
        return course_id_pk;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_date() {
        return course_date;
    }

    public String getCourse_image() {
        return course_image;
    }

    public String getCourse_capacity() {
        return course_capacity;
    }

    public String getCourse_days() {
        return course_days;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public String getCourse_funds() {
        return course_funds;
    }

    public String getCourse_vedio() {
        return course_vedio;
    }

    @BindingAdapter("imageUri")
    public static void loadImage(ImageView imageView ,String imageUrl)
    {
        Picasso.with(imageView.getContext()).load(Tags.ImgPath+imageUrl).into(imageView);
    }
}
