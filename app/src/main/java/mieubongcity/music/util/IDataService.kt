package mieubongcity.music.util

import mieubongcity.music.model.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

public interface IDataService {
    @GET("quangcao.php")
    fun getDataBanner(): Call<List<Model_QuangCao>>

    @GET("listsong.php")
    fun getDataListSong(): Call<List<Model_BaiHat>>

    @GET("playlist.php")
    fun getDataPlayList(): Call<List<Model_PlayList>>

    @GET("theloai.php")
    fun getDataTheLoai(): Call<List<Model_TheLoai>>

    @GET("album.php")
    fun getDataAlbum(): Call<List<Model_Album>>

    @FormUrlEncoded
    @POST("danhsachbaihatplaylist.php")
    fun getDataBaiHatTheoPlayList(@Field("idPlayList") id: String)
            : Call<List<Model_BaiHat>>

    @FormUrlEncoded
    @POST("danhsachbaihatplaylist.php")
    fun getDataBaiHatTheoAblum(@Field("idAlbum") id: String)
            : Call<List<Model_BaiHat>>

    @FormUrlEncoded
    @POST("danhsachbaihatplaylist.php")
    fun getDataBaiHatTheoTheLoai(@Field("idTheLoai") id: String)
            : Call<List<Model_BaiHat>>

    @FormUrlEncoded
    @POST("updateluotthich.php")
    fun updateLuotThich(@Field("idbaihat") idbaihat : String,
    @Field("luotthich") luotthich : String) :Call<String>

}