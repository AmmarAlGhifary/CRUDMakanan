package com.blogspot.yourfavoritekaisar.crudmakanan.ui.profil;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment implements ProfilContract.View {


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    private ProfilPresenter profilPresenter = new ProfilPresenter(this);

    private String idUser, namaUser, alamatUser, noTelp;
    private int gender;
    private Menu action;

    private String mGender;
    private static final int GENDER_MALE = 1;
    private static final int GENDER_FEMALE = 2;
    private ProgressDialog progressDialog;

    public ProfilFragment() {
        // Dibutuhkan Empty Construktor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        unbinder = ButterKnife.bind(this, view);

        setupSpinner();

        profilPresenter.getDataUser(getContext());

        setHasOptionsMenu(true);
        return view;
    }

    private void setupSpinner() {
        // Membuat array spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array
                .array_gender_options, android.R.layout.simple_spinner_item);

        // Menampilkan Spinner dalam satu line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Listener Spinner
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                // Mencek posisi apakah ada isinya
                if (!TextUtils.isEmpty(selection)) {
                    // Mencek apakah 1 atau 2 yang dipilih user
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = "L";
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Saving . . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showSuccessUpdateUser(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDataUser(LoginData loginData) {
        // Membuat Widget agar tidak bisa dibaca oleh user
        readMode();

        // Memasukkan data yang sudah dimiliki oleh presenter
        idUser = loginData.getId_user();
        namaUser = loginData.getNama_user();
        alamatUser = loginData.getAlamat();
        noTelp = loginData.getNo_telp();
        if (loginData.getJenkel().equals("L")) {
            gender = 1;
        } else {
            gender = 2;
        }

        if (!TextUtils.isEmpty(idUser)) {
            // Menser Nama title action BAr
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil" + namaUser);

            // Menampilkan data ke layar
            edtName.setText(namaUser);
            edtAlamat.setText(alamatUser);
            edtNoTelp.setText(noTelp);
            // Mencek gender dan memilih sesuai gender untuk ditampilkan pada spinner
            switch (gender) {
                case GENDER_MALE:
                    Log.i("cek Male", String.valueOf(gender));
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(1);
                    break;
            }
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profil");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        // Melakukan perintah logout ke presenter
        profilPresenter.logoutSession(getContext());
        // Menutup Activity
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_edit).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:

            editMode();
            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);

            return true;
            case R.id.menu_save:
               // Mencek idUser apakah ada isinya
               if (!TextUtils.isEmpty(idUser)){
                   // Mencek apakah semua field masih kosong
                   if (!TextUtils.isEmpty(edtName.getText().toString()) ||
                           TextUtils.isEmpty(edtAlamat.getText().toString()) ||
                           TextUtils.isEmpty(edtNoTelp.getText().toString())){
                       // Menampilkan alert dialog untuk memberitahu user tidak boleh ada field yg kosong
                       android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getContext());
                       alertDialog.setMessage("Please complete the field!");
                       alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       });
                       alertDialog.show();


                   }else {
                       // Apabila user telah memilih semua field
                       LoginData loginData = new LoginData();
                       // Mengisi inputan user ke model loginData
                       loginData.setId_user(idUser);
                       loginData.setNama_user(edtName.getText().toString());
                       loginData.setAlamat(edtAlamat.getText().toString());
                       loginData.setNo_telp(edtNoTelp.getText().toString());
                       loginData.setJenkel(mGender);

                       // Mengirim data ke presenter untuk dimasukkan ke dalam databse
                       profilPresenter.updateDataUser(getContext(), loginData);

                       readMode();
                       action.findItem(R.id.menu_edit).setVisible(true);
                       action.findItem(R.id.menu_save).setVisible(false);
                   }
               }else {
                   readMode();
                   action.findItem(R.id.menu_edit).setVisible(true);
                   action.findItem(R.id.menu_save).setVisible(false);
                   }

                   return true;
               default:
                   return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void editMode() {
        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {
        edtName.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtName.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNoTelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);
    }
}
