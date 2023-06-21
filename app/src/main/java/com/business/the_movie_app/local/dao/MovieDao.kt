package com.business.the_movie_app.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.business.the_movie_app.model.response.Movie
import io.reactivex.Single

// db/MovieDao.kt
@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_record")
    fun getMovies(): Single<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)
}
