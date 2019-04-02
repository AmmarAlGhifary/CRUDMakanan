package com.blogspot.yourfavoritekaisar.crudmakanan.ui.makanan;

import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiClient;
import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiInterface;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananPresenter implements MakananContract.Presenter{

    private final MakananContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananPresenter(MakananContract.View view) {
        this.view = view;
    }


    @Override
    public void getListFoodNews() {
        view.showProgress();
        Call<MakananResponse> call = mApiInterface.getMakananBaru();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    view.showFoodNewsList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodPopular() {
        view.showProgress();
        Call<MakananResponse> call = mApiInterface.getMakananPopuler();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    view.showFoodPopularList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodKategory() {
        view.showProgress();
        Call<MakananResponse> call = mApiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    view.showFoodCategoryList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
