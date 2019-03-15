package com.blogspot.yourfavoritekaisar.crudmakanan.ui.register;

import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiClient;
import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiInterface;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterInterface.Presenter {

    private final RegisterInterface.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public RegisterPresenter(RegisterInterface.View view) {
        this.view = view;
    }

    @Override
    public void doRegisterUser(LoginData loginData) {
        if (loginData != null){
            if (!loginData.getUsername().isEmpty() &&
                    !loginData.getAlamat().isEmpty() &&
                    !loginData.getJenkel().isEmpty() &&
                    !loginData.getLevel().isEmpty() &&
                    !loginData.getNo_telp().isEmpty() &&
                    !loginData.getPassword().isEmpty() &&
                    !loginData.getUsername().isEmpty() &&
                    !loginData.getNama_user().isEmpty()
                    ) {

                view.showProgress();
                Call<LoginResponse> call = apiInterface.register(
                        loginData.getUsername(),
                        loginData.getPassword(),
                        loginData.getNama_user(),
                        loginData.getAlamat(),
                        loginData.getJenkel(),
                        loginData.getNo_telp(),
                        loginData.getLevel()
                );
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        view.hideProgress();
                        if (response.body()!=null){
                            if (response.body().getResult() == 1){
                                view.showRegisterSucces(response.body().getMessage());
                            }else {
                                view.showError(response.body().getMessage());
                            }
                        }else {
                            view.showError("Empty data");
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        view.hideProgress();
                        view.showError(t.getMessage());
                    }
                });

            }else {
                view.showError("Field Shouldn't be empty");
            }
        }

    }
}
