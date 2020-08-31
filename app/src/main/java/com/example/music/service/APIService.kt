package com.example.music.service

import android.app.Activity
import android.content.Context

public class APIService {
    private var url: String = "http://localhost:8080/music/server/"

    public fun getDataService(): IDataService {
        return APIRetrofitConnect.getClient(url).create(IDataService::class.java)
    }

}