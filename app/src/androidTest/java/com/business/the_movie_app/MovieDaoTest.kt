package com.business.the_movie_app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.business.the_movie_app.local.dao.MovieDao
import com.business.the_movie_app.local.db.AppDatabase
import com.business.the_movie_app.model.response.Movie
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.runBlocking

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    private lateinit var movieDao: MovieDao
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        movieDao = database.getMovieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getMoviesShouldReturnTheListOfMovies() = runBlocking<Unit>{
        // Prepare test data
        val movies = listOf(
            Movie(1, "Title 1", "Overview 1", "2023-01-01", "path1.jpg", 4.5),
            Movie(2, "Title 2", "Overview 2", "2023-02-02", "path2.jpg", 3.7)
        )

        // Insert movies into the database
        movieDao.insertMovies(movies)

        // Call the method being tested
        val testObserver = movieDao.getMovies().test()

        // Verify the result
        testObserver.assertValue(movies)
        testObserver.assertComplete()
    }

    @Test
    fun insertMoviesShouldInsertMoviesIntoTheDatabase() = runBlocking {
        // Prepare test data
        val movies = listOf(
            Movie(1, "Title 1", "Overview 1", "2023-01-01", "path1.jpg", 4.5),
            Movie(2, "Title 2", "Overview 2", "2023-02-02", "path2.jpg", 3.7)
        )

        // Insert movies into the database
            movieDao.insertMovies(movies)

        // Retrieve movies from the database
        val retrievedMovies = movieDao.getMovies().blockingGet()

        // Verify the result
        assertEquals(movies.size, retrievedMovies.size)
        assertTrue(retrievedMovies.containsAll(movies))
    }
}
