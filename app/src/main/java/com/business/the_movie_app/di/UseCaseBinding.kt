package com.business.the_movie_app.di

import com.business.the_movie_app.core.BaseUseCase
import com.business.the_movie_app.domain.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseBinding {

    @Binds
    fun providesMovieUseCase(movieUseCase: MovieUseCase):
            BaseUseCase.GetMoviesUseCase
}
