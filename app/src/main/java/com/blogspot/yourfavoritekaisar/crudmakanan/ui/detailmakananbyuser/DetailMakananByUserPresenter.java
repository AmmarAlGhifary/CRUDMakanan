package com.blogspot.yourfavoritekaisar.crudmakanan.ui.detailmakananbyuser;

import android.content.Context;
import android.net.Uri;

import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiClient;
import com.blogspot.yourfavoritekaisar.crudmakanan.data.remote.ApiInterface;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.detailmakanan.DetailMakananResponse;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMakananByUserPresenter implements DetailMakananByUserContract.Presenter {

    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    private final DetailMakananByUserContract.View view;

    public DetailMakananByUserPresenter(DetailMakananByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();

        Call<MakananResponse> call = mApiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1) {
                        view.showSpinnerCategory(response.body().getMakananDataList());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getDetailMakanan(String idMakanan) {
        view.showProgress();

        if (idMakanan.isEmpty()){
            view.showMessage("Theres no food with that kind of id");
            view.hideProgress();
            return;
        }

        Call<DetailMakananResponse> call = mApiInterface.getDetailMakanan(Integer.valueOf(idMakanan));
        call.enqueue(new Callback<DetailMakananResponse>() {
            @Override
            public void onResponse(Call<DetailMakananResponse> call, Response<DetailMakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showDetailMakanan(response.body().getMakananData());
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }


            @Override
            public void onFailure(Call<DetailMakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void updateDataMakanan(Context context, Uri filepath, String namaMakanan, String descMakanan, String idCategory, String namaFotoMakanan) {

    }

    @Override
    public void deleteMakanan(String idMakanan, String namaFotoMakanan) {


    }
}
