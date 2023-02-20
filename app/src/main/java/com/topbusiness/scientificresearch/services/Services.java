package com.topbusiness.scientificresearch.services;

import com.topbusiness.scientificresearch.models.NotificationCount;
import com.topbusiness.scientificresearch.models.NotificationModel;
import com.topbusiness.scientificresearch.models.OtherLibModel;
import com.topbusiness.scientificresearch.models.ResponseModel;
import com.topbusiness.scientificresearch.models.TrainingModel;
import com.topbusiness.scientificresearch.models.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Services {
    @FormUrlEncoded
    @POST("/api/registration")
    Call<UserModel> CreateNewAccount(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/auth")
    Call<UserModel> Login(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/advancedtranslation")
    Call<ResponseModel> UploadTranslateFile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/Api/Scrupulousness")
    Call<ResponseModel> UploadTadqeeqFile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/Api/FormatSearch")
    Call<ResponseModel> UploadFormatSearchFile(@FieldMap Map<String,String> map);


    @FormUrlEncoded
    @POST("/Api/Documentation")
    Call<ResponseModel> UploadTawtheeqFile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/Api/ReviewSearchPlan")
    Call<ResponseModel> UploadReviewSearchPlanFile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/Api/FullSearchReview")
    Call<ResponseModel> UploadFullSearchReviewFile(@FieldMap Map<String,String> map);


    @FormUrlEncoded
    @POST("/api/arbitration")
    Call<ResponseModel> UploadTa7keemFile(@FieldMap Map<String,String> map);

    @FormUrlEncoded
    @POST("/api/analyses")
    Call<ResponseModel> UploadTa7lel(@FieldMap Map<String,String> map);
    @FormUrlEncoded
    @POST("/api/citation")
    Call<ResponseModel> Uploadeqtbas(@FieldMap Map<String,String> map);
    @GET("/Api/courses/{user_id}")
    Call<List<TrainingModel>> TrainingData(@Path("user_id") String user_id);

    @FormUrlEncoded
    @POST("/api/bookcourse")
    Call<ResponseModel> ReserveCourse(@FieldMap Map<String,String> map);

    @GET("api/libraries")
    Call<List<OtherLibModel>> getLibraries();
    @FormUrlEncoded
    @POST("/api/uploadResearche")
    Call<ResponseModel> UploadFiles(@FieldMap Map<String,String> map);

    @GET("Api/MyNotifications/{user_id}")
    Call<List<NotificationModel>> getNotification(@Path("user_id") String user_id);

    @GET("Api/UnRead/{user_id}")
    Call<NotificationCount> getNotificationCount(@Path("user_id") String user_id);

    @GET("Api/Readed/{user_id}")
    Call<ResponseModel> readNotification(@Path("user_id") String user_id);

    @FormUrlEncoded
    @POST("Api/UpdateTokenId/{user_id}")
    Call<ResponseModel> updateToken(@Path("user_id") String user_id,
                                    @Field("user_token_id") String user_token_id);
    @GET("Api/Logout/{user_id}")
    Call<ResponseModel> logOut(@Path("user_id") String user_id);

    @FormUrlEncoded
    @POST("Api/RestMyPass")
    Call<ResponseModel> resetPassword(@Field("user_email") String user_email);

    @FormUrlEncoded
    @POST("Api/askForReference")
    Call<ResponseModel> ask_references(@Field("user_id") String user_id,
                                       @Field("reference_title") String reference_title,
                                       @Field("reference_content") String reference_content,
                                       @Field("reference_type") String reference_type
                                       );
}
