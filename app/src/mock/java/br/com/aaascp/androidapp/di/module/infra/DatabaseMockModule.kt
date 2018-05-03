package br.com.aaascp.androidapp.di.module.infra

import android.app.Application
import dagger.Module

@Module
class DatabaseMockModule(application: Application) : DatabaseModule(application){
    override fun name() = "app-dev-db.db"
}