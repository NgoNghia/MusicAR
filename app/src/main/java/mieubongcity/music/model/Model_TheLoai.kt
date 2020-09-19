package mieubongcity.music.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Model_TheLoai {
    @SerializedName("idTheLoai")
    @Expose
    var idTheLoai: String? = null

    @SerializedName("idChuDe")
    @Expose
    var idChuDe: String? = null

    @SerializedName("tenTheLoai")
    @Expose
    var tenTheLoai: String? = null

    @SerializedName("hinhTheLoai")
    @Expose
    var hinhTheLoai: String? = null

}