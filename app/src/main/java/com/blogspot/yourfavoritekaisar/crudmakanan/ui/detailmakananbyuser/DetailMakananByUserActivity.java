package com.blogspot.yourfavoritekaisar.crudmakanan.ui.detailmakananbyuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMakananByUserActivity extends AppCompatActivity implements DetailMakananByUserContract.View {

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
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.layoutSaveMakanan)
    CardView layoutSaveMakanan;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private final DetailMakananByUserPresenter mDetailMakananByUserPresenter = new DetailMakananByUserPresenter(this);
    private Uri filePath;
    private String idCategory, idMakanan;
    private MakananData mMakananData;
    private String namaFotoMakanan;
    private String[] mIdCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan_by_user);
        ButterKnife.bind(this);

        // Melakukan pengecekkan dan permission untuk bisa mengakses gallery
        PermissionGallery();

        // Menangkap id makanan yang dikirimkan dari actvity sebelumnya
        idMakanan = getIntent().getStringExtra(Constants.KEY_EXTRA_ID_MAKANAN);


        // Mengambil data category untuk ditambilkan di spinner
        mDetailMakananByUserPresenter.getCategory();

        // Mensetting swipe Refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                // Mengambil data detail makanan
                mDetailMakananByUserPresenter.getDetailMakanan(idMakanan);

                // Mengambil data category untuk ditampilkan di spinner
                mDetailMakananByUserPresenter.getCategory();
            }
        });
    }


    private void PermissionGallery() {
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
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }


    // Mengembil semua data
    @Override
    public void showDetailMakanan(MakananData makananData) {
        // Kita ambil semua data detail makanan
        mMakananData = makananData;

        // Mengambil data foto makanan
        Log.i("Foto Makanan kosong", "" + makananData.getFotoMakanan());
        namaFotoMakanan = makananData.getFotoMakanan();


        // Mengambil id category
        idCategory = makananData.getIdKategori();


        // Menampilkan semua data ke layar
        edtName.setText(makananData.getNamaMakanan());
        edtDesc.setText(makananData.getDescMakanan());
        // Memilih spinner sesuai dengan data category yang ada di dablam database

        for (int i = 0 ;i < mIdCategory.length; i++) {

            //
            if (Integer.valueOf(mIdCategory[i]).equals(Integer.valueOf(idCategory))) {
                spinCategory.setSelection(i);

            }

        }
//        spinCategory.setSelection(Integer.valueOf(idCategory));

        // Menampilkan gambar makanan
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(this).load(makananData.getUrlMakanan()).apply(options).into(imgPicture);

    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        finish();
    }

    @Override
    public void showSpinnerCategory(List<MakananData> categoryDataList) {
        // Membuat data penampung untuk spinner
//        List<String> listSpinner = new ArrayList<String>();

        // Menganmbil array
        String[] namaCategory = new String[categoryDataList.size()];
        mIdCategory = new String[categoryDataList.size()];

        for (int i = 0; i < categoryDataList.size(); i++) {
//            listSpinner.add(categoryDataList.get(i).getNamaKategori());
            namaCategory[i] = categoryDataList.get(i).getNamaKategori();
            mIdCategory[i] = categoryDataList.get(i).getIdKategori();

            Log.i("cek", "isi show namaCategory" + namaCategory[i]);
            Log.i("Cek", "isi show mIdCatefory" + mIdCategory[i]);
        }

        // Membuat adapter spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaCategory);
        // Menampilkan spinner 1 line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukkan adapter spinner ke dalam widget spinner kita
        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = categoryDataList.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Mengambil data makanan
        mDetailMakananByUserPresenter.getDetailMakanan(idMakanan);
    }

    @Override
    public void successUpdate() {
        mDetailMakananByUserPresenter.getCategory();
    }


    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                // Mengambil data dari storage
                showFileChoose();
                break;
            case R.id.btn_update:
                mDetailMakananByUserPresenter.updateDataMakanan(
                        this,
                        filePath,
                        edtName.getText().toString(),
                        edtDesc.getText().toString(),
                        idCategory,
                        namaFotoMakanan,
                        idMakanan
                );
                break;
            case R.id.btn_delete:
                mDetailMakananByUserPresenter.deleteMakanan(idMakanan, namaFotoMakanan);
                break;
        }
    }

    private void showFileChoose() {
        Intent intentgalery = new Intent(Intent.ACTION_PICK);
        intentgalery.setType("image/*");
        intentgalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentgalery, "select Pictures"), Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null
                ) {

            // Mengambil data foto dan memasukkan ke dalam variable filepath
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Tampilkan gambar yang baru dipilih ke layar
                imgPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


//    @Override
//    protected void onResume() {
//        super.onResume();
//        // Mengambil data category untuk ditampilkan di spinner
//        mDetailMakananByUserPresenter.getCategory();

