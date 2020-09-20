package mieubongcity.music.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Model_TheLoai : Serializable {
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