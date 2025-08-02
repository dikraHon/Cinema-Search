package com.example.cinemasearch.di

import androidx.lifecycle.ViewModel
import com.example.cinemasearch.data.repository.RepositoryImpl
import com.example.cinemasearch.domain.Repository
import com.example.cinemasearch.presintation.viewModelPackage.detailsViewModelPack.DetailsViewModel
import com.example.cinemasearch.presintation.viewModelPackage.favoritesScreenViewModel.FavoritesViewModel
import com.example.cinemasearch.presintation.viewModelPackage.mainScreenViewModel.SearchFilmsViewModel
import com.example.cinemasearch.presintation.viewModelPackage.selectionOfFilmsViewModel.CollectionsViewModel
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
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SearchFilmsViewModel::class)
    abstract fun bindSearchFilmsViewModel(viewModel: SearchFilmsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchFilmsViewModel::class)
    abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchFilmsViewModel::class)
    abstract fun bindCollectionsViewModel(viewModel: CollectionsViewModel): ViewModel

    @MustBeDocumented
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val viewModelKey: KClass<out ViewModel>)
}