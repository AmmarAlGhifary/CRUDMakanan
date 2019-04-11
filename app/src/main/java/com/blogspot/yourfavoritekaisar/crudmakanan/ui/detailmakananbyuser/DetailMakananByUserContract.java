package com.blogspot.yourfavoritekaisar.crudmakanan.ui.detailmakananbyuser;

import android.content.Context;
import android.net.Uri;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface DetailMakananByUserContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showMessage(String msg);
        void successDelete();
        void showSpinnerCategory(List<MakananData> categoryDataList);
        void successUpdate();

    }


    interface Presenter{
        void getCategory();
        void getDetailMakanan(String idMakanan);
        void updateDataMakanan(Context context,
                               Uri filepath,
                               String namaMakanan,
                               String descMakanan,
                               String idCategory,
                               String namaFotoMakanan,
                               String idMakanan);

        void deleteMakanan(String idMakanan, String namaFotoMakanan);
    }
}
