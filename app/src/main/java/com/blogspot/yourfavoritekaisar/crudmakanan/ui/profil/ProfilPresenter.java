package com.blogspot.yourfavoritekaisar.crudmakanan.ui.profil;

import android.content.Context;
import android.content.SharedPreferences;

import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiClient;
import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiInterface;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginResponse;
import com.blogspot.yourfavoritekaisar.crudmakanan.utils.Constants;
import com.blogspot.yourfavoritekaisar.crudmakanan.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilPresenter implements ProfilContract.Presenter {

    private final ProfilContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilPresenter(ProfilContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {

        // ShowProgress
        view.showProgress();

        Call<LoginResponse> call = apiInterface.updateUser(
                Integer.valueOf(loginData.getId_user()),
                loginData.getNama_user(),
                loginData.getAlamat(),
                loginData.getJenkel(),
                loginData.getNo_telp()
                );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                // Mencek response apakah ada bodynya
                if (response.isSuccessful() && response.body() != null){
                    // Mencek result apakah 1
                    if (response.body().getResult()== 1){
                        // Setelah berhasil update ke server online, lalu update ke SharedPref
                        // Membuat object sharedpref yang sudah ada di sessionmanager
                        pref = context.getSharedPreferences(Constants.pref_name, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        // Memasukkan data ke dalam sharedpref
                        editor.putString(Constants.USER_NAMA,loginData.getNama_user());
                        editor.putString(Constants.USER_ALAMAT,loginData.getAlamat());
                        editor.putString(Constants.USER_NOTELP,loginData.getNo_telp());
                        editor.putString(Constants.USER_JENKEL,loginData.getJenkel());
                        // Apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
              view.hideProgress();
              view.showSuccessUpdateUser(t.getMessage());
            }
        });

    }

    @Override
    public void getDataUser(Context context) {
        // Pengambilan data dari sharedPreference
        pref = context.getSharedPreferences(Constants.pref_name, 0);

        // Membuat object model loginData untuk penampung
        LoginData loginData = new LoginData();

        // Memasukkan data sharedPreference ke dalam model loginData
        loginData.setId_user(pref.getString(Constants.USER_ID, ""));
        loginData.setNama_user(pref.getString(Constants.USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constants.USER_ALAMAT,""));
        loginData.setNo_telp(pref.getString(Constants.USER_NOTELP,""));
        loginData.setJenkel(pref.getString(Constants.USER_JENKEL,""));

        // Mengirim data model ke View
        view.showDataUser(loginData);
    }

    @Override
    public void logoutSession(Context context) {
        // Membuat Class SessionManager untuk mengambil method input
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logOut();
    }
}
