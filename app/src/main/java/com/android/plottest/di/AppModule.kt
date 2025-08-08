package com.android.plottest.di

import android.content.Context
import com.android.plottest.repository.PlotDao
import com.android.plottest.repository.RoomDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext appContext: Context): PlotDao {
        return  RoomDatabaseBuilder.getDatabase(appContext).plotDao()
    }

}