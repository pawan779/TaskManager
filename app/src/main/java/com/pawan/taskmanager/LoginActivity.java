package com.pawan.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pawan.taskmanager.BLL.LoginBLL;
import com.pawan.taskmanager.StrictMode.StrictModeClass;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername,etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);

        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=etUsername.getText().toString();
                String pwd=etPassword.getText().toString();

                LoginBLL loginBLL=new LoginBLL();

                StrictModeClass.StrictMode();
                if (loginBLL.checkUser(uname,pwd)){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                }



            }
        });
    }
}
