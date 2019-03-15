package com.blogspot.yourfavoritekaisar.crudmakanan.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.main.MainActivity;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.register.RegisterActivity;
import com.blogspot.yourfavoritekaisar.crudmakanan.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity  implements LoginContract.View{

    @BindView(R.id.btn_Login)
    Button btnLogin;
    @BindView(R.id.edt_Username)
    EditText edtUsername;
    @BindView(R.id.edt_Password)
    EditText edtPassword;
    @BindView(R.id.txt_Register)
    TextView txtRegister;
    @BindView(R.id.txt_Judul)
    TextView txtJudul;

    private ProgressDialog progressDialog;
    private LoginPresenter presenter = new LoginPresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter.checkLogin(this);

    }

    @OnClick({R.id.btn_Login, R.id.txt_Register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                presenter.doLogin(edtUsername.getText().toString(),edtPassword.getText().toString());
                break;
            case R.id.txt_Register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();

    }

    @Override
    public void loginSuccses(String message, LoginData loginData) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // Menyimpan Data ke dalam sharedPreference
        presenter.saveDataUser(this, loginData);

        LoginData mLoginData = new LoginData();
        mLoginData.setId_user(loginData.getId_user());
        mLoginData.setNama_user(loginData.getNama_user());
        mLoginData.setAlamat(loginData.getAlamat());
        mLoginData.setAlamat(loginData.getJenkel());
        mLoginData.setNo_telp(loginData.getNo_telp());
        mLoginData.setUsername(loginData.getUsername());
        mLoginData.setPassword(loginData.getPassword());
        mLoginData.setLevel(loginData.getLevel());

        startActivity(new Intent(this, MainActivity.class).putExtra(Constants.KEY_LOGIN,mLoginData));
        finish();
    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void usernameError(String message) {
        edtUsername.setError(message);
        edtUsername.setFocusable(true);
    }

    @Override
    public void passwordError(String message)  {
        edtPassword.setError(message);
        edtPassword.setFocusable(true);
    }

    @Override
    public void isLogin() {
        // Berpindah halaman apabila user sudah login
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
