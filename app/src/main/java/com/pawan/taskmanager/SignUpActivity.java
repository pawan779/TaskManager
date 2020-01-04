package com.pawan.taskmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pawan.taskmanager.Api.UsersAPI;
import com.pawan.taskmanager.Model.Users;
import com.pawan.taskmanager.ServerResponse.ImageResponse;
import com.pawan.taskmanager.ServerResponse.SignupResponse;
import com.pawan.taskmanager.StrictMode.StrictModeClass;
import com.pawan.taskmanager.Url.Url;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private CircleImageView imgProfile;
    private Button btnSignup;
    private EditText etFirstName, etLastName, etUsername, etPassword, etCPassword;
    String imagePath;
    private String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        imgProfile = findViewById(R.id.imgProfile);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etCPassword = findViewById(R.id.etCPassword);
        btnSignup = findViewById(R.id.btnSignup);


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().equals(etCPassword.getText().toString())) {
                    if (validate()) {
                        saveImageOnly();
                        SignUp();
                    }
                } else {
                    etPassword.setError("Password Donot match");
                    etPassword.requestFocus();
                    return;
                }

            }


        });
    }


    private boolean validate() {
        boolean status = true;
        if (TextUtils.isEmpty(etFirstName.getText())) {
            etFirstName.setError("Enter first name");
            status = false;
        }
        if (TextUtils.isEmpty(etLastName.getText())) {
            etLastName.setError("Enter last name");
            status = false;
        }
        if (TextUtils.isEmpty(etUsername.getText())) {
            etUsername.setError("Enter username");
            status = false;
        }
        if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError("Enter password");
            status = false;
        }
        if (TextUtils.isEmpty(etCPassword.getText())) {
            etCPassword.setError("Enter confirm password");
            status = false;
        }
        if (TextUtils.isEmpty(etFirstName.getText())) {
            etFirstName.setError("Enter first name");
            status = false;
        }
        if (etUsername.getText().toString().length() < 6) {
            etUsername.setError("MInimum 6 character");
            status = false;
        }
        return status;
    }


    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "PLease Select image", Toast.LENGTH_SHORT).show();

            }


        }
        Uri uri = data.getData();
//        imagePath=getRealPathFromUri(uri);
        imgProfile.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        UsersAPI usersAPI = Url.getInstace().create(UsersAPI.class);
        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

        StrictModeClass.StrictMode();

        //Synchrous Method

        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFileName();
            Toast.makeText(this, "Image Inserted", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Toast.makeText(this, "Error inserting Image" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void SignUp() {
        String fname, lname, uname, pwd, img;

        fname = etFirstName.getText().toString();
        lname = etLastName.getText().toString();
        uname = etUsername.getText().toString();
        pwd = etPassword.getText().toString();
        Users users = new Users(fname, lname, uname, pwd, imageName);

        UsersAPI usersAPI = Url.getInstace().create(UsersAPI.class);
        Call<SignupResponse> signupCall = usersAPI.registerUser(users);

        signupCall.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (!response.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this, "Code "+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SignUpActivity.this, "Register sucessfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        signupResponseCall.enqueue(new Callback<SignupResponse>() {
//            @Override
//            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(SignUpActivity.this, "Code " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Toast.makeText(SignUpActivity.this, "Registered", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<SignupResponse> call, Throwable t) {
//                Toast.makeText(SignUpActivity.this, "Error " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
