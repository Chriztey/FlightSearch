package com.chris.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import com.chris.flightsearch.data.AppContainer
import com.chris.flightsearch.data.DefaultAppContainer




class FlightSearchApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

}