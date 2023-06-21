package com.business.the_movie_app.di

import com.business.the_movie_app.remote.MovieApiMethod
import com.business.the_movie_app.remote.MovieApiMethodImpl
import com.business.the_movie_app.repository.MovieRepository
import com.business.the_movie_app.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkBinding {

    @Binds
    abstract fun bindMovieApiMethod(movieApiMethodImpl: MovieApiMethodImpl): MovieApiMethod

    @Binds
    abstract fun bindMovieRespository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

}

