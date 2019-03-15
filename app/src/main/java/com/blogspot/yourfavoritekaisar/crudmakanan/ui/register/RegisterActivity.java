package com.blogspot.yourfavoritekaisar.crudmakanan.ui.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface.View {

    @BindView(R.id.edtnama)
    EditText edtnama;
    @BindView(R.id.edtalamat)
    EditText edtalamat;
    @BindView(R.id.edtnotelp)
    EditText edtnotelp;
    @BindView(R.id.radioLaki)
    RadioButton radioLaki;
    @BindView(R.id.radioPerempuan)
    RadioButton radioPerempuan;
    @BindView(R.id.edtusername)
    EditText edtusername;
    @BindView(R.id.edtpassword)
    TextInputEditText edtpassword;
    @BindView(R.id.edtpasswordconfirm)
    TextInputEditText edtpasswordconfirm;
    @BindView(R.id.radioAdmin)
    RadioButton radioAdmin;
    @BindView(R.id.radioUserbiasa)
    RadioButton radioUserbiasa;
    @BindView(R.id.btnregister)
    Button btnregister;

    private ProgressDialog progressDialog;
    private RegisterPresenter mRegisterPresenter = new RegisterPresenter(this);
    private String jenkel,level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // set jenkel dan level default
        setRadio();
    }

    private void setRadio() {
        if (radioAdmin.isChecked()){
            level = "1";
        }else {
            level = "0";
        }

        if (radioLaki.isChecked()){
            jenkel = "Laki-Laki";
        }else {
            jenkel = "Perempuan";
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading . . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterSucces(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @OnClick({R.id.radioLaki, R.id.radioPerempuan, R.id.radioAdmin, R.id.radioUserbiasa, R.id.btnregister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioLaki:
                jenkel = "Laki Laki";
                break;
            case R.id.radioPerempuan:
                jenkel = "Perempuan";
                break;
            case R.id.radioAdmin:
                level = "1";
                break;
            case R.id.radioUserbiasa:
                level = "2";
                break;
            case R.id.btnregister:
                // Membuat object model call LoginDat
                LoginData mLoginData = new LoginData();

                // Memasukkan data ke dalam model Logindata
                mLoginData.setUsername(edtusername.getText().toString());
                mLoginData.setPassword(edtpassword.getText().toString());
                mLoginData.setNama_user(edtnama.getText().toString());
                mLoginData.setAlamat(edtalamat.getText().toString());
                mLoginData.setNo_telp(edtnotelp.getText().toString());
                mLoginData.setJenkel(jenkel);
                mLoginData.setLevel(level);

                // Mengirimkan data ke presenter
                mRegisterPresenter.doRegisterUser(mLoginData);
                break;
        }
    }
}
