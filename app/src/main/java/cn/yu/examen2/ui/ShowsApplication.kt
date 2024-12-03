package cn.yu.examen2.ui

import android.app.Application
import cn.yu.examen2.data.AppContainer
import cn.yu.examen2.data.AppDatabase
import cn.yu.examen2.data.DefaultAppContainer

class ShowsApplication: Application() {
    lateinit var container: AppContainer

    companion object {
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        appDatabase = AppDatabase.getInstance(this)
    }
}