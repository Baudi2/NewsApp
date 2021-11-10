package com.example.testapp1.di.app

import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationContextModule::class])
interface ApplicationComponent {

    fun applicationContext(): Application

//    @Component.Builder
//    interface Builder{
//        @BindsInstance
//        fun application(app: NewsApplication): Builder
//        fun build(): ApplicationComponent
//    }
}