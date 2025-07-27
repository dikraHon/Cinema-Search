package com.example.cinemasearch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinemasearch.data.RepositoryImpl
import com.example.cinemasearch.domain.Repository
import com.example.cinemasearch.presintation.FactoryViewModel
import com.example.cinemasearch.presintation.SearchFilmsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindRepository(impl: RepositoryImpl): Repository

    @Binds
    @IntoMap
    @ViewModelKey(SearchFilmsViewModel::class)
    abstract fun bindSearchFilmsViewModel(viewModel: SearchFilmsViewModel): ViewModel


    @MustBeDocumented
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val viewModelKey: KClass<out ViewModel>)
}