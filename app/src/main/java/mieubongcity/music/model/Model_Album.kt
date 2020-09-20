package mieubongcity.music.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Model_Album : Serializable{
    @SerializedName("idAlbum")
    @Expose
    var idAlbum: String? = null

    @SerializedName("tenAlbum")
    @Expose
    var tenAlbum: String? = null

    @SerializedName("tenCaSyAlbum")
    @Expose
    var tenCaSyAlbum: String? = null

    @SerializedName("hinhAlbum")
    @Expose
    var hinhAlbum: String? = null
}