package com.blogspot.yourfavoritekaisar.crudmakanan.ui.makananbycategory;

import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiClient;
import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiInterface;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananResponse;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.makanan.MakananContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByCategoryPresenter implements MakananByCategoryContract.Presenter{

    private final MakananByCategoryContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByCategoryPresenter(MakananByCategoryContract.View view) {
        this.view = view;
    }


    @Override
    public void getListFoodByCategory(String idCategory) {
        view.showProgress();

        if (idCategory.isEmpty()){
            view.showFailureMessage("ID Category tidak ada");
            return;
        }

        Call<MakananResponse> call = mApiInterface.getMakananByCategory(Integer.valueOf(idCategory));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showFoodByCategory(response.body().getMakananDataList());
                    } else {
                        view.showFailureMessage(response.body().getMessage());
                    }
                } else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());

            }
        });
    }
}
