package com.blogspot.yourfavoritekaisar.crudmakanan.ui.register;

import android.widget.EditText;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;

public interface RegisterInterface {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSucces(String message);
    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
