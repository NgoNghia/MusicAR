package mieubongcity.music.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Model_PlayList : Serializable{
    @SerializedName("idPlayList")
    @Expose
    var idPlayList: String? = null

    @SerializedName("tenPlayList")
    @Expose
    var tenPlayList: String? = null

    @SerializedName("hinhNenPlayList")
    @Expose
    var hinhNenPlayList: String? = null

    @SerializedName("hinhIcon")
    @Expose
    var hinhIcon: String? = null
}