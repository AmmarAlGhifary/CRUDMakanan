package com.blogspot.yourfavoritekaisar.crudmakanan.ui.profil;

import android.content.Context;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;

public interface ProfilContract {

    interface View {
        void showProgress();
        void hideProgress();
        void showSuccessUpdateUser(String message);
        void showDataUser(LoginData loginData);
    }

    interface Presenter {
        void updateDataUser(Context context, LoginData loginData);
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}
