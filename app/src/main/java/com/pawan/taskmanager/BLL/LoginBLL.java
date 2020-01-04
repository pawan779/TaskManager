package com.pawan.taskmanager.BLL;

import com.pawan.taskmanager.Api.UsersAPI;
import com.pawan.taskmanager.ServerResponse.SignupResponse;
import com.pawan.taskmanager.Url.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginBLL {

    boolean isSuccess=false;
    public boolean checkUser(String username,String password){
        UsersAPI usersAPI= Url.getInstace().create(UsersAPI.class);

        Call<SignupResponse> usersCall=usersAPI.checkUser(username,password);

        try {
            Response<SignupResponse> loginResponse=usersCall.execute();
            if (loginResponse.isSuccessful() && loginResponse.body().getStatus().equals("Login success!")){

                Url.token += loginResponse.body().getToken();

                isSuccess=true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
