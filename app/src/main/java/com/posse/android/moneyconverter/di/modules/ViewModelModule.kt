package com.posse.android.moneyconverter.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.posse.android.moneyconverter.view.main.viewModel.MainFragmentViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @ViewModelKey(MainFragmentViewModel::class)
    @IntoMap
    fun mainFragmentViewModel(mainFragmentViewModel: MainFragmentViewModel): ViewModel
}

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)