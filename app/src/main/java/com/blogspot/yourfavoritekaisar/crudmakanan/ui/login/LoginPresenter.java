package com.blogspot.yourfavoritekaisar.crudmakanan.ui.login;

import android.content.Context;

import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiClient;
import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiInterface;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginResponse;
import com.blogspot.yourfavoritekaisar.crudmakanan.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }


    @Override
    public void doLogin(String username, String password) {
        // Mengecek apakah username dan password memenuhi kondisi
        if (username.isEmpty()) {
            view.usernameError("Please fill in Password");
            return;
        }
        if (password.isEmpty()) {
            view.passwordError("Please Fill in Password");
            return;
        }

        view.showProgress();

        Call<LoginResponse> call = apiInterface.loginUser(username,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                // Membuat kondisi ketika response mengambil data
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getData() != null) {
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccses(message, loginData);
                        } else {
                            view.loginFailure("Data is empty");
                        }
                    } else {
                        view.loginFailure(response.body().getMessage());
                    }
                } else {
                    view.loginFailure("Data is empty");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.loginFailure(t.getMessage());
            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
     // Membuat object SessionManager
     mSessionManager = new SessionManager(context);
     // Mensave data ke SharedPreference dengan menggunakan method dari class SessionManager
     mSessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        mSessionManager = new SessionManager(context);
        Boolean isLogin = mSessionManager.is_login();
        // Mengecek apakah is_Login bernilai true
        if (isLogin){
            // Menyuruh view untuk melakukan perpindahan ke MainActivity
            view.isLogin();
        }
    }
}
