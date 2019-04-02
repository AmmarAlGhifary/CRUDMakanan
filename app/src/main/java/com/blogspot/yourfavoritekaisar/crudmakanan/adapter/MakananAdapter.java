package com.blogspot.yourfavoritekaisar.crudmakanan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.yourfavoritekaisar.crudmakanan.R;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananData;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.detailmakanan.DetailMakanan;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.detailmakananbyuser.DetailMakananByUserActivity;
import com.blogspot.yourfavoritekaisar.crudmakanan.ui.makananbycategory.MakananByCategoryActivity;
import com.blogspot.yourfavoritekaisar.crudmakanan.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {
    // Type 1 untuk makanan baru
    public static final int TYPE_1 = 1;
    // Type 2 Untuk makanan populer
    public static final int TYPE_2 = 2;
    // Type 3 untuk  category
    public static final int TYPE_3 = 3;
    // Type 4 untuk makanan by category
    public static final int TYPE_4 = 4;
    // Type 5 untuk data user
    public static final int TYPE_5 = 5;

    Integer viewType;
    private final Context context;
    private final List<MakananData> makananDataList;


    public MakananAdapter(Integer viewType, Context context, List<MakananData> makananDataList) {
        this.viewType = viewType;
        this.context = context;
        this.makananDataList = makananDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_news, null);
                return new FoodNewsViewHolder(view);
            case TYPE_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_popular, null);
                return new FoodPopulerViewHolder(view);
            case TYPE_3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_category, null);
                return new FoodKategoryViewHolder(view);
            case TYPE_4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodNewsViewHolder(view);
            case TYPE_5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodByUserViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Mengambil data lalu memasukkan ke dalam model
        final MakananData makananData = makananDataList.get(position);

        // Mengambil view type dari user atau constractor
        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options).into(foodNewsViewHolder.imgMakanan);

                // Menampilkan tittle dan jumlah view
                foodNewsViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder.txtView.setText(makananData.getView());


                foodNewsViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));


                // Membuat onclick
                foodNewsViewHolder.itemView.setOnClickListener(v -> context.startActivity(new Intent(context, DetailMakanan.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan())));

                break;
            case TYPE_2:
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options2).into(foodPopulerViewHolder.imgMakanan);
                // Menampilkan tittle dan jumlah view
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                foodPopulerViewHolder.txtTitle.setText(makananData.getNamaMakanan());

                foodPopulerViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakanan.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
            case TYPE_3:
                FoodKategoryViewHolder foodKategoryViewHolder = (FoodKategoryViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options3 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options3).into(foodKategoryViewHolder.image);

                foodKategoryViewHolder.txtNamaKategory.setText(makananData.getNamaKategori());
                foodKategoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, MakananByCategoryActivity.class).putExtra(Constants.KEY_EXTRA_ID_CATEGORY, makananData.getIdKategori()));
                    }
                });
                break;
            case TYPE_4:
                // Membuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder2 = (FoodNewsViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options4).into(foodNewsViewHolder2.imgMakanan);

                // Menampilkan tittle dan jumlah view
                foodNewsViewHolder2.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder2.txtView.setText(makananData.getView());


                foodNewsViewHolder2.txtTime.setText(newDate(makananData.getInsertTime()));


                // Membuat onclick
                foodNewsViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakanan.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
            case TYPE_5:
                // Membuat holder untuk dapat mengakses widget
                FoodByUserViewHolder foodByUserViewHolder = (FoodByUserViewHolder) holder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options5 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options5).into(foodByUserViewHolder.imgMakanan);

                // Menampilkan tittle dan jumlah view
                foodByUserViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodByUserViewHolder.txtView.setText(makananData.getView());


                foodByUserViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));


                // Membuat onclick
                foodByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, DetailMakananByUserActivity.class).putExtra(Constants.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
        }
    }

    @Override
    public int getItemCount() {
        return makananDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FoodNewsViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public class FoodPopulerViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public class FoodKategoryViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public String newDate(String insertTime) {
        Date date2 = null;
        String newDate = insertTime;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date2 = sdf.parse(insertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date2 != null) {
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date2);
        }
        return newDate;
    }

    public class FoodByUserViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        public FoodByUserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
