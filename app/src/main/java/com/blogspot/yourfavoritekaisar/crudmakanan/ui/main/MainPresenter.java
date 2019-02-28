package com.blogspot.yourfavoritekaisar.crudmakanan.ui.main;

import android.content.Context;

import com.blogspot.yourfavoritekaisar.crudmakanan.utils.SessionManager;

public class MainPresenter implements MainContract.Presenter {

    @Override
    public void logoutSession(Context context) {
        // Membuat object sessionManager untuk dapat digunakan
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.logOut();
    }
}
