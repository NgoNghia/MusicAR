package mieubongcity.music.util

import mieubongcity.music.model.Model_BaiHat
import mieubongcity.music.model.Model_PlayList
import mieubongcity.music.model.Model_QuangCao
import retrofit2.Call
import retrofit2.http.GET

public interface IDataService {
    @GET("quangcao.php")
    fun getDataBanner() : Call<List<Model_QuangCao>>
    @GET("listsong.php")
    fun getDataSong() : Call<List<Model_BaiHat>>
    @GET("playlist.php")
    fun getDataPlayList (): Call<List<Model_PlayList>>
}