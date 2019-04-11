package com.blogspot.yourfavoritekaisar.crudmakanan.data.remote;

import com.blogspot.yourfavoritekaisar.crudmakanan.model.detailmakanan.DetailMakananResponse;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.login.LoginResponse;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.makanan.MakananResponse;
import com.blogspot.yourfavoritekaisar.crudmakanan.model.uploadmakanan.UploadMakananResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
      @Field("username") String username,
      @Field("password") String password
    );

    // Membuat endpoint Register
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginResponse> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("namauser") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("notelp") String notelp,
            @Field("level") String level
            );

    // Membuat update
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUser(
            @Field("iduser") int iduser,
            @Field("namauser") String namauser,
            @Field("alamat") String alamat,
            @Field("jenkel") String jenkel,
            @Field("notelp") String notelp
    );

    //mengambil data kategori
    @GET("getkategori.php")
    Call<MakananResponse> getKategoriMakanan();

    //mengambil data makanan baru
    @GET("getmakananbaru.php")
    Call<MakananResponse> getMakananBaru();

    //mengambil data makanan populer
    @GET("getmakananpopuler.php")
    Call<MakananResponse> getMakananPopuler();

    // Jika postman menggunakan Form Data
    // Bedanya kita make part
    // klo form musti make @MultiPart
    @Multipart
    @POST("uploadmakanan.php")
    Call<UploadMakananResponse> uploadMakanan(
            @Part("iduser") int iduser,
            @Part("idkategori") int idkategori,
            @Part("namamakanan")RequestBody namamakanan,
            @Part("descmakanan") RequestBody descmakanan,
            @Part("timeinsert") RequestBody timeinsert,
            @Part MultipartBody.Part image
            );

    // Mengambil detail makanan berdasarkan id makanan
    @GET("getdetailmakanan.php")
    Call<DetailMakananResponse> getDetailMakanan(@Query("idmakanan") int idMakanan);

    // Mengambil data list makanan berdasarkan kategori
    @GET("getmakananbykategori.php")
    Call<MakananResponse> getMakananByCategory(@Query("idkategori") int idKategori);

    // Mengambil data makanan berdasarkan usernya
    @GET("getmakananbyuser.php")
    Call<MakananResponse> getMakananByUser(@Query("iduser")int iduser);

    // Menghapus Data makanan
    @FormUrlEncoded
    @POST("deletemakanan.php")
    Call<MakananResponse> deleteMakanan(
        @Field("idmakanan") int idMakanan,
        @Field("fotomakanan") String namaFotoMakanan
    );

    // Mengupdate makanan
    @Multipart
    @POST("updatemakanan.php")
    Call<MakananResponse> updateMakanan(
            @Part("idmakanan") int idMakanan,
            @Part("idkategori") int category,
            @Part("namamakanan") RequestBody namaMakanan,
            @Part("descnamakan") RequestBody descMakanan,
            @Part("fotomakanan") RequestBody namaFotoMakanan,
            @Part("inserttime") RequestBody insertTime,
            @Part MultipartBody.Part image
    );
}
