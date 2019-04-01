package com.blogspot.yourfavoritekaisar.crudmakanan.ui.makananbycategory;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface MakananByCategory {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByCategory(List<MakananData> foodNewsList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodByCategory(String idCategory);
    }
}
