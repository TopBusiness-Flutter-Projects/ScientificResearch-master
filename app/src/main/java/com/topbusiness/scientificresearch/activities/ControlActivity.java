package com.topbusiness.scientificresearch.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.topbusiness.scientificresearch.userEventsListener.UserListener;
import com.topbusiness.scientificresearch.models.ResponseModel;
import com.topbusiness.scientificresearch.models.UserModel;
import com.topbusiness.scientificresearch.R;
import com.topbusiness.scientificresearch.services.Api;
import com.topbusiness.scientificresearch.services.Services;
import com.topbusiness.scientificresearch.services.Tags;
import com.topbusiness.scientificresearch.singleTone.UserSingleTone;
import com.topbusiness.scientificresearch.databinding.ActivityControlBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ControlActivity extends AppCompatActivity implements UserListener,UserSingleTone.UserDataInterface{

    private ActivityControlBinding controlBinding;
    private final int FILE_REQ=1;
    private ProgressDialog dialog;
    private UserModel userModel;
    private UserSingleTone userSingleTone;
    private String encodedFile;
    private String exten;
    private  FilePickerDialog filePickerDialog;
    private String user_type="";
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlBinding = DataBindingUtil.setContentView(this,R.layout.activity_control);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);
        controlBinding.setEvent(this);
        userSingleTone = UserSingleTone.getInstance();
        userSingleTone.GetUserData(this);
        getDataFromIntent();
        CreateProgDialog();
        CreateAlertDialog();
    }
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("user_type"))
            {
                user_type = intent.getStringExtra("user_type");
            }
        }
    }
    private void CreateProgDialog()
    {
        ProgressBar bar = new ProgressBar(this);
        Drawable drawable = bar.getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(this,R.color.ko7ly), PorterDuff.Mode.SRC_IN);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.upload_file));
        dialog.setIndeterminateDrawable(drawable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

    }
    private void CreateAlertDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.ser_not_av)
                .setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                        finish();

                    }
                }).setCancelable(false).create();
    }
    @Override
    public void onClickListener(View view) {

        int id = view.getId();
        switch (id)
        {
            case R.id.ch_file:
                if (user_type!=null && user_type.equals(Tags.visitor))
                {
                    alertDialog.show();

                }else
                {
                    SelectFile();

                }
                break;
            case R.id.upload_btn:
                if (user_type!=null &&user_type.equals(Tags.visitor))
                {
                    alertDialog.show();

                }else
                {
                    upload_file();

                }
                break;
            case R.id.back:
                finish();
                break;

        }
    }
    private void upload_file() {
        //Log.e("user_id",userModel.getUser_id());

        if (encodedFile!=null &&!TextUtils.isEmpty(encodedFile))
        {
            dialog.show();
            Map<String,String> map = new HashMap<>();
            map.put("user_id_fk",userModel.getUser_id());
            map.put("requested_file",encodedFile);
            Log.e("file",encodedFile);
            Retrofit retrofit = Api.getRetrofit();
            Services services = retrofit.create(Services.class);
            Call<ResponseModel> call = services.UploadTa7keemFile(map);
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful())
                    {
                        if (response.body().getMessage()==1)
                        {
                            dialog.dismiss();
                            Toast.makeText(ControlActivity.this, R.string.file_uploaded, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    dialog.dismiss();
                    Log.e("Error",t.getMessage());
                    Toast.makeText(ControlActivity.this, R.string.fail_try, Toast.LENGTH_SHORT).show();
                }
            });
        }else
            {
                Toast.makeText(this, R.string.choose_file, Toast.LENGTH_LONG).show();
            }

           }

    private void SelectFile() {
        String [] exten = {"doc","docx"};

        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = exten;

        filePickerDialog = new FilePickerDialog(this,properties);
        filePickerDialog.setTitle("Select file");
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                Log.e("file",files[0]+"");
                String filePath = files[0];
                controlBinding.controlFile.setText(filePath.substring(filePath.lastIndexOf("/")+1));
                File file = new File(filePath);
                try {
                    InputStream inputStream = new FileInputStream(file);
                    enCodeFile(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        filePickerDialog.show();

        /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("file/*");
        startActivityForResult(intent.createChooser(intent,"إختر الملف"),FILE_REQ);*/
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (filePickerDialog != null) {   //Show dialog if the read permission has been granted.
                        filePickerDialog.show();

                    }
                } else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(ControlActivity.this, "Permission is Required for getting list of files", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_REQ && resultCode == RESULT_OK && data != null)
        {

            Uri uri = data.getData();
            exten = getContentResolver().getType(uri);
            Log.e("type",exten);
            try {
                enCodeFile(getContentResolver().openInputStream(uri));
                controlBinding.controlFile.setText(String.valueOf(uri));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Log.e("uri",uri.toString()+"");
        }
    }
*/
    private void enCodeFile(InputStream inputStream)
    {
        ByteArrayOutputStream outputStream=null;
        byte [] bytes;
        int len=0;
        try {
            outputStream = new ByteArrayOutputStream();
            bytes = new byte[1024*11];

            while ((len=inputStream.read(bytes))!=-1)
            {
                outputStream.write(bytes,0,len);
            }

            encodedFile = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
            Log.e("file",encodedFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void getUserData(UserModel userModel) {
        this.userModel = userModel;
    }
}
