package com.blogspot.yourfavoritekaisar.crudmakanan.ui.uploadmakanan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;
import com.blogspot.yourfavoritekaisar.crudmakanan.utils.Constants;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadMakananActivity extends AppCompatActivity implements UploadMakananContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.layoutUploadMakanan)
    CardView layoutUploadMakanan;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    // TODO 1 Menyiapkan variable yang dibutuhkan

    private UploadMakananPresenter mUploadMakananPresenter = new UploadMakananPresenter(this);
    private String mIdCategory;
    //private String part_image;

    // Untuk Menampung lokasi gambar di dalam hp
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_makanan);
        ButterKnife.bind(this);
        PermissionGallery();
        // Mengambil data category untuk di tampilkan di spinner
        mUploadMakananPresenter.getCategory();
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        });
    }

    private void PermissionGallery() {
        // Mencek apakah user telah memberikan izin untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        //If the user has denied the permission previously your code will come to this block
        //Here you can explain why you need this permission
        //Explain here why you need this permission
                }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.STORAGE_PERMISSION_CODE) {
        //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        //Displaying a toast
                showMessage("Permission granted now you can read the storage");
                Log.i("Permission on", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            } else {
        //Displaying another toast if permission is not granted
                showMessage("Oops you just denied the permission");
                Log.i("Permission off", "onRequestPermissionsResult: " + String.valueOf(grantResults));

            }
        }
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successUpload() {
        finish();
    }

    @Override
    public void showSpinnerCategory(final List<MakananData> categoryDataList) {
        // Membuat data penampung untuk spinner
        List<String> listSpinner = new ArrayList<>();
        for (int i =0; i< categoryDataList.size(); i++){
            listSpinner.add(categoryDataList.get(i).getNamaKategori());
        }

        // Membuat penampung spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSpinner);
        // KIta setting untuk menampilkan spinner
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukkan adapter ke spinner
        spinCategory.setAdapter(categorySpinnerAdapter);
        // memanggil spinner agar ada datanya
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil id category sesuai dengan pilihan user
                mIdCategory = categoryDataList.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                showFileChooser();
                break;
            case R.id.btn_upload:
                // Mengirimkan data untuk di upload ke presenter
                mUploadMakananPresenter.uploadMakanan(this,filePath,
                        edtName.getText().toString(),
                        edtDesc.getText().toString(),
                        mIdCategory
                );
                break;
        }
    }

    private void showFileChooser() {
        // Membuka media external storage
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "Choose a picture"), Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Mencek apakah request code dan data ada
        if (requestCode == Constants.REQUEST_CODE && requestCode == RESULT_OK && data != null && data.getData() != null){
            // Mengambil Data image yang sudah diplih user
            filePath = data.getData();

            try {
                // Mengubah file bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPicture.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
            }
        }
    }
}
