package com.eniola.addressbookapp.di

import androidx.lifecycle.ViewModel
import com.eniola.addressbookapp.ui.ContactListFragment
import com.eniola.addressbookapp.ui.ContactViewModel
import com.eniola.addressbookapp.ui.CreateContactFragment
import com.eniola.addressbookapp.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class MainModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun contactListFragment(): ContactListFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun createContactFragment(): CreateContactFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel::class)
    internal abstract fun contactViewModel(viewModel: ContactViewModel): ViewModel

}