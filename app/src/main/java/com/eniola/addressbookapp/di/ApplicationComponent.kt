package com.eniola.addressbookapp.di

import android.content.Context
import com.eniola.addressbookapp.base.AddressBookApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, MainModule::class])
interface ApplicationComponent: AndroidInjector<AddressBookApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}