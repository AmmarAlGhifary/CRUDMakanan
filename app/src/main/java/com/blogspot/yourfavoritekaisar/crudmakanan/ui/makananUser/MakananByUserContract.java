package com.blogspot.yourfavoritekaisar.crudmakanan.ui.makananUser;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface MakananByUserContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByUser(List<MakananData> foodByUserList);
        void showFailureMessage(String msg);

    }

    interface Presenter{
        void getListFoodByUser(String idUser);
    }
}
