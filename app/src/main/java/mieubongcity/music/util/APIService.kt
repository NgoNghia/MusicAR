package mieubongcity.music.util

public class APIService {
    companion object {
        fun getDataService(): IDataService {
            val url: String = "https://androidwebapi.000webhostapp.com/server/"
            var iDataService: IDataService =
                APIRetrofitConnect.getClient(url).create(IDataService::class.java)
            return iDataService
        }
    }
}