package com.pradip.profile.home.di

import com.pradip.core.dependencies.base.BaseModule
import com.pradip.profile.home.data.ProfileRepo
import com.pradip.profile.home.data.ProfileLocal
import com.pradip.profile.home.interfaces.FetchProfile
import com.pradip.profile.home.interfaces.FetchProfilesRemote
import com.pradip.profile.home.viewmodel.FetchVideosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

/**
 * Dependency injection for Discover Videos
 */
object VideoListModule : BaseModule {
    override fun load() = loadKoinModules(profileListModule)
    override fun unload() = unloadKoinModules(profileListModule)
}

private val profileListModule = module {

    factory<FetchProfile.Remote> { FetchProfilesRemote(apiService = get()) }
    factory<FetchProfile.Local> { ProfileLocal(dao = get()) }

    factory<FetchProfile.Repository> {
        ProfileRepo(
            remote = get(),
            local = get()
        )
    }
    viewModel { FetchVideosViewModel(dispatcher = get(), discoverRepo = get()) }

}
