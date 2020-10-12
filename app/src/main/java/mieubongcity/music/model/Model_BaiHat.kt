package mieubongcity.music.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Model_BaiHat {
    @SerializedName("idBaiHat")
    @Expose
    var idBaiHat: String? = null

    @SerializedName("idAlbum")
    @Expose
    var idAlbum: String? = null

    @SerializedName("idTheLoai")
    @Expose
    var idTheLoai: String? = null

    @SerializedName("idPlayList")
    @Expose
    var idPlayList: String? = null

    @SerializedName("tenBaiHat")
    @Expose
    var tenBaiHat: String? = null

    @SerializedName("hinhBaiHat")
    @Expose
    var hinhBaiHat: String? = null

    @SerializedName("caSy")
    @Expose
    var caSy: String? = null

    @SerializedName("linkBaiHat")
    @Expose
    var linkBaiHat: String? = null

    @SerializedName("luotThich")
    @Expose
    var luotThich: Int? = null
}