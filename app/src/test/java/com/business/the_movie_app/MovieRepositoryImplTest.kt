package com.business.the_movie_app

import com.business.the_movie_app.model.response.Movie
import com.business.the_movie_app.remote.MovieApiMethod
import com.business.the_movie_app.repository.MovieRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    private lateinit var movieApiMethod: MovieApiMethod
    private lateinit var movieRepository: MovieRepositoryImpl

    @Before
    fun setup() {
        movieApiMethod = mockk()
        movieRepository = MovieRepositoryImpl(movieApiMethod)
    }

    @Test
    fun `getMovies should call MovieApiMethod and return a list of movies`() {
        // Mocked response
        val apiMovies = listOf(
            Movie(1, "Title 1", "This is an overview of the movie 1", "2023-09-01", "path1.jpg", 4.5),
            Movie(2, "Title 2", "This is an overview of the movie 2", "2023-11-02", "path2.jpg", 3.7)
        )
        val apiKey = BuildConfig.MOVIE_API_KEY

        // Mock the behavior of MovieApiMethod
        every { movieApiMethod.getMovies(apiKey) } returns Single.just(apiMovies)

        // Call the method being tested
        val testObserver = movieApiMethod.getMovies(apiKey).test()

        // Verify that MovieApiMethod.getMovies was called with the correct API key
        verify { movieApiMethod.getMovies(apiKey) }

        // Verify the result
        testObserver.assertValue(apiMovies)
        testObserver.assertComplete()
    }
}
