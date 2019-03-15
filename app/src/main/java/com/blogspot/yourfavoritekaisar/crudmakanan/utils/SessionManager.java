package com.blogspot.yourfavoritekaisar.crudmakanan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.login.LoginActivity;

public class SessionManager {
    // Membuat variable global untuk SharedPreferences

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        // Membuat object SharedPreferces
        pref = context.getSharedPreferences(Constants.pref_name, 0);
        // Membuat pref dengan mode edit
        editor = pref.edit();
    }


    // Function untuk membuat session login
    public void createSession(LoginData dataItem){
        // Memasukkan data login user yang udah login dalam SharedPreferences
        editor.putBoolean(Constants.is_Login, true);
        editor.putString(Constants.USER_ID, dataItem.getId_user());
        editor.putString(Constants.USER_NAMA, dataItem.getNama_user());
        editor.putString(Constants.USER_ALAMAT, dataItem.getAlamat());
        editor.putString(Constants.USER_JENKEL, dataItem.getJenkel());
        editor.putString(Constants.USER_NOTELP, dataItem.getNo_telp());
        editor.putString(Constants.USER_USERNAME, dataItem.getUsername());
        editor.putString(Constants.USER_LEVEL, dataItem.getLevel());
        // Mengeksekusi Penyimpanan
        editor.commit();
    }

    // Function Untuk Mencek apakah user udah pernah login
    public boolean is_login() {
        // ngeReturn nilai boolean dengan mengambil data dari pref is_Login di Constant
        return pref.getBoolean(Constants.is_Login, false);
    }

    // Function untuk melakukan logout atau menghapus isi di dalam shared preference
    public void logOut(){
        // Memanggil method clear untuk menghapus data sharedPreference
        editor.clear();
        // Mengesekusi perintah clear
        editor.commit();
        // Membuat Intent logout agar kembali ke halaman login
        Intent intent = new Intent(context, LoginActivity.class);
        // Membuat flag
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
