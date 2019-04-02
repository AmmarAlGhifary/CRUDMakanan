package com.blogspot.yourfavoritekaisar.crudmakanan.ui.makanan;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface MakananContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopularList(List<MakananData> footPopularList);
        void showFoodCategoryList(List<MakananData> footCategoryList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodNews();
        void getListFoodPopular();
        void getListFoodKategory();
    }
}
