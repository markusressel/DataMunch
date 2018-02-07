package de.markusressel.datamunch.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import de.markusressel.datamunch.data.persistence.entity.MyObjectBox
import io.objectbox.BoxStore
import javax.inject.Singleton

/**
 * Created by Markus on 30.01.2018.
 */
@Module
abstract class PersistenceBindingsModule {

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        internal fun provideBoxStore(context: Context): BoxStore {
            return MyObjectBox
                    .builder()
                    .androidContext(context)
                    .build()
        }

    }

}