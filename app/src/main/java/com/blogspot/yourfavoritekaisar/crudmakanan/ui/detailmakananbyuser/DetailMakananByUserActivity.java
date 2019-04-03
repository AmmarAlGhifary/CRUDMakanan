package com.blogspot.yourfavoritekaisar.crudmakanan.ui.detailmakananbyuser;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan_by_user);
        ButterKnife.bind(this);

        // Melakukan pengecekkan dan permission untuk bisa mengakses gallery
        PermissionGallery();

        // Menangkap id makanan yang dikirimkan dari actvity sebelumnya
        idMakanan = getIntent().getStringExtra(Constants.KEY_EXTRA_ID_MAKANAN);

        // Mengambil data makanan
        mDetailMakananByUserPresenter.getDetailMakanan(idMakanan);

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

    @Override
    public void showDetailMakanan(MakananData makananData) {
        // Kita ambil semua data detail makanan
        mMakananData = makananData;

        // Mengambil id category
        idCategory = makananData.getIdKategori();

        // Menampilkan semua data ke layar
        edtName.setText(makananData.getNamaMakanan());
        edtDesc.setText(makananData.getDescMakanan());
        // Memilih spinner sesuai dengan data category yang ada di dablam database
        spinCategory.setSelection(Integer.valueOf(idCategory));

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
        List<String> listSpinner = new ArrayList<String>();
        for (int i = 0; i < categoryDataList.size(); i++) {
            listSpinner.add(categoryDataList.get(i).getNamaKategori());
        }

        // Membuat adapter spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSpinner);
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
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                break;
            case R.id.btn_update:
                break;
            case R.id.btn_delete:
                break;
        }
    }
}
