package uz.gita.music_player_xr.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.music_player_xr.domain.*
import uz.gita.music_player_xr.domain.impl.*

// Created by Jamshid Isoqov an 10/7/2022
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindLoadMusic(impl: GetAllMusicsUseCaseImpl): GetAllMusicsUseCase

    @Binds
    fun bindMusicsUseCase(impl: MusicsUseCaseImpl): MusicsUseCase

}