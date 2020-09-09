package mieubongcity.music.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Model_QuangCao(
    idQuangCao: String, hinhAnh: String, noiDung: String, idBaiHat: String,
    tenBaiHat: String, hinhBaiHat: String
) {

    @SerializedName("idQuangCao")
    @Expose
    var idQuangCao: String? = null

    @SerializedName("hinhAnh")
    @Expose
    var hinhAnh: String? = null

    @SerializedName("noiDung")
    @Expose
    var noiDung: String? = null

    @SerializedName("idBaiHat")
    @Expose
    var idBaiHat: String? = null

    @SerializedName("tenBaiHat")
    @Expose
    var tenBaiHat: String? = null

    @SerializedName("hinhBaiHat")
    @Expose
    var hinhBaiHat: String? = null
}