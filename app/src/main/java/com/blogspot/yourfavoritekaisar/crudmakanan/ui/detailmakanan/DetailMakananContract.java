package com.blogspot.yourfavoritekaisar.crudmakanan.ui.detailmakanan;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;

public interface DetailMakananContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getDetailMakanan(String idMakanan);
    }
}
