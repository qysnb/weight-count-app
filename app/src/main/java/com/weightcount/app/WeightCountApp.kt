package com.weightcount.app

import android.app.Application
import com.weightcount.app.data.datastore.SettingsDataStore
import com.weightcount.app.data.db.AppDatabase
import com.weightcount.app.data.repository.TagRepository
import com.weightcount.app.data.repository.WeightRepository

class WeightCountApp : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var weightRepository: WeightRepository
        private set

    lateinit var tagRepository: TagRepository
        private set

    lateinit var settingsDataStore: SettingsDataStore
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
        weightRepository = WeightRepository(database.weightDao(), database.tagDao())
        tagRepository = TagRepository(database.tagDao())
        settingsDataStore = SettingsDataStore(this)
    }
}
