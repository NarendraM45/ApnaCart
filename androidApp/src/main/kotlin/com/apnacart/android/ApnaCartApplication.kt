package com.apnacart.android

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.di.getSharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ApnaCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        val androidModule = module {
            single<SqlDriver> { AndroidSqliteDriver(ApnaCartDatabase.Schema, get(), "apnacart.db") }
        }
        
        startKoin {
            androidContext(this@ApnaCartApplication)
            modules(androidModule, getSharedModule())
        }
    }
}
