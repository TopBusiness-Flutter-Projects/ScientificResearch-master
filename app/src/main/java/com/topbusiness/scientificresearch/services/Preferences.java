package com.topbusiness.scientificresearch.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.topbusiness.scientificresearch.models.UserModel;

public class Preferences {
    private Context context;

    public Preferences(Context context) {
        this.context = context;
    }

    public void CreateSharedPref(UserModel userModel)
    {
        SharedPreferences Pref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Pref.edit();
        editor.putString("user_id",userModel.getUser_id());
        editor.putString("user_type",userModel.getUser_type());
        editor.putString("user_name",userModel.getUser_name());
        editor.putString("user_username",userModel.getUser_username());
        editor.putString("user_email",userModel.getUser_email());
        editor.putString("user_phone",userModel.getUser_phone());
        editor.putString("user_country",userModel.getUser_country());
        editor.putString("user_degree",userModel.getDegree());
        editor.putString("user_company",userModel.getCompany());
        editor.putString("user_specialization",userModel.getUser_id());
        editor.apply();
        CreateSession(Tags.Login_session);

    }

    public UserModel GetUserData()
    {
        SharedPreferences Pref = context.getSharedPreferences("user",Context.MODE_PRIVATE);

        String id = Pref.getString("user_id","");
        String name = Pref.getString("user_name","");
        String username = Pref.getString("user_username","");
        String email = Pref.getString("user_email","");
        String phone = Pref.getString("user_phone","");
        String company = Pref.getString("user_company","");
        String specialization = Pref.getString("user_specialization","");
        String degree = Pref.getString("user_degree","");
        String type = Pref.getString("user_type","");
        String country = Pref.getString("user_country","");

        UserModel userModel = new UserModel(id,name,username,"",email,phone,country,degree,company,specialization,type);
        return userModel;

    }

    public void clearPref()
    {
        SharedPreferences Pref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Pref.edit();
        editor.clear();
        editor.apply();
        CreateSession(Tags.Logout_session);
    }

    public void CreateSession(String session)
    {
        SharedPreferences Pref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Pref.edit();
        editor.putString("session",session);
        editor.apply();
    }

    public String getSession ()
    {
        SharedPreferences Pref = context.getSharedPreferences("user",Context.MODE_PRIVATE);
        String session = Pref.getString("session","");
        return session;
    }
}
