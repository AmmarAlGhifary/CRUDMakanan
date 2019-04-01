package com.blogspot.yourfavoritekaisar.crudmakanan.ui.makanan;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.adapter.MakananAdapter;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.uploadmakanan.UploadMakananActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananFragment extends Fragment implements MakananContract.View{
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.rv_makanan_news)
    RecyclerView rvMakananNews;
    @BindView(R.id.rv_makanan_populer)
    RecyclerView rvMakananPopuler;
    @BindView(R.id.rv_kategori)
    RecyclerView rvKategori;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;


    private ProgressDialog progressDialog;
    private MakananPresenter mMakananPresenter = new MakananPresenter(this);

    public MakananFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makanan, container, false);
        unbinder = ButterKnife.bind(this, view);

        mMakananPresenter.getListFoodKategory();
        mMakananPresenter.getListFoodNews();
        mMakananPresenter.getListFoodPopular();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMakananPresenter.getListFoodPopular();
                mMakananPresenter.getListFoodNews();
                mMakananPresenter.getListFoodKategory();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.floating_action_button)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), UploadMakananActivity.class));
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
    public void showFoodNewsList(List<MakananData> foodNewsList) {
        rvMakananNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMakananNews.setAdapter(new MakananAdapter(MakananAdapter.TYPE_1, getContext(), foodNewsList));
    }

    @Override
    public void showFoodPopularList(List<MakananData> footPopularList) {
        rvMakananNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMakananNews.setAdapter(new MakananAdapter(MakananAdapter.TYPE_2, getContext(), footPopularList));

    }

    @Override
    public void showFoodCategoryList(List<MakananData> footCategoryList) {
        rvKategori.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvKategori.setAdapter(new MakananAdapter(MakananAdapter.TYPE_3, getContext(), footCategoryList));

    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMakananPresenter.getListFoodNews();
        mMakananPresenter.getListFoodPopular();
        mMakananPresenter.getListFoodKategory();
    }
}
