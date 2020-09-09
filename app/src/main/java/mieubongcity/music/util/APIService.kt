package mieubongcity.music.util

public class APIService {
    companion object {
        fun getDataService(): IDataService {
            val url: String = "http://thitchoxilat.site/server/"
            var iDataService: IDataService =
                APIRetrofitConnect.getClient(url).create(IDataService::class.java)
            return iDataService
        }
    }
}