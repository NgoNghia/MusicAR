package mieubongcity.music.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Model_BaiHat() :Parcelable {
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

    constructor(parcel: Parcel) : this() {
        idBaiHat = parcel.readString()
        idAlbum = parcel.readString()
        idTheLoai = parcel.readString()
        idPlayList = parcel.readString()
        tenBaiHat = parcel.readString()
        hinhBaiHat = parcel.readString()
        caSy = parcel.readString()
        linkBaiHat = parcel.readString()
        luotThich = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idBaiHat)
        parcel.writeString(idAlbum)
        parcel.writeString(idTheLoai)
        parcel.writeString(idPlayList)
        parcel.writeString(tenBaiHat)
        parcel.writeString(hinhBaiHat)
        parcel.writeString(caSy)
        parcel.writeString(linkBaiHat)
        parcel.writeValue(luotThich)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Model_BaiHat> {
        override fun createFromParcel(parcel: Parcel): Model_BaiHat {
            return Model_BaiHat(parcel)
        }

        override fun newArray(size: Int): Array<Model_BaiHat?> {
            return arrayOfNulls(size)
        }
    }

}