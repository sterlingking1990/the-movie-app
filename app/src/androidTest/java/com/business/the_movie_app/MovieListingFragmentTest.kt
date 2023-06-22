package com.business.the_movie_app

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.business.the_movie_app.model.response.Movie
import com.business.the_movie_app.presentation.adapter.MovieListAdapter
import com.business.the_movie_app.presentation.fragment.MovieListingFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matcher.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.android.material.textfield.TextInputEditText
import org.junit.Assert.*
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class MovieListingFragmentTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testMovieListDisplayed() {
        val movies = listOf(
            Movie(1, "movie 1", "This is another movie 1","2023-09-01","posterpic1.jpg",9.6),
            Movie(2, "movie  2", "This is another movie 2","2023-09-02","posterpic2.jpg",9.2),
            Movie(3, "movie 3", "This is another movie 3","2023-09-03","posterpic3.jpg",9.3)
        )

        activityScenarioRule.scenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.home_nav_host_container)
            if (navHostFragment is NavHostFragment) {
                val fragment = navHostFragment.childFragmentManager.fragments.firstOrNull { it is MovieListingFragment }
                if (fragment is MovieListingFragment) {
                    val recyclerView: RecyclerView = fragment.requireView().findViewById(R.id.rvMovieListing)
                    val adapter = recyclerView.adapter as? MovieListAdapter
                    adapter?.movies = movies
                    recyclerView.adapter = adapter

                    assertNotNull(adapter)
                    assertEquals(movies.size, adapter?.movies?.size)

                    for (movie in movies) {
                        assertTrue(adapter?.movies?.contains(movie) ?: false)
                    }
                } else {
                    fail("MovieListingFragment not found")
                }
            } else {
                fail("NavHostFragment not found")
            }
        }
    }

    @Test
    fun testMovieFiltering() {
        val movies = listOf(
            Movie(1, "Movie 1", "This is another movie 1","2023-09-01","posterpic1.jpg",9.6),
            Movie(2, "movie  2", "This is another movie 2","2023-09-02","posterpic2.jpg",9.2),
            Movie(3, "movie 3", "This is another movie 3","2023-09-03","posterpic3.jpg",9.3)
        )

        activityScenarioRule.scenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.home_nav_host_container)
            if (navHostFragment is NavHostFragment) {
                val fragment = navHostFragment.childFragmentManager.fragments.firstOrNull { it is MovieListingFragment }
                if (fragment is MovieListingFragment) {
                    val searchEditText: TextInputEditText = fragment.requireView().findViewById(R.id.etSearchMovie)
                    val recyclerView: RecyclerView = fragment.requireView().findViewById(R.id.rvMovieListing)
                    val adapter = recyclerView.adapter as? MovieListAdapter
                    adapter?.movies = movies
                    recyclerView.adapter=adapter

                    assertNotNull(adapter)

                    // Filter movies
                    searchEditText.setText("Movie 1")

                    assertEquals(1, adapter?.itemCount)
                    assertTrue(adapter?.movies?.contains(movies[0]) ?: false)
                } else {
                    fail("MovieListingFragment not found")
                }
            } else {
                fail("NavHostFragment not found")
            }
        }
    }

    @Test
    fun testMovieItemClickNavigation() {
        val movies = listOf(
            Movie(1, "Movie 1", "This is another movie 1", "2023-09-01", "posterpic1.jpg", 9.6),
            Movie(2, "Movie 2", "This is another movie 2", "2023-09-02", "posterpic2.jpg", 9.2),
            Movie(3, "Movie 3", "This is another movie 3", "2023-09-03", "posterpic3.jpg", 9.3)
        )

        activityScenarioRule.scenario.onActivity { activity ->
            val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.home_nav_host_container)
            if (navHostFragment is NavHostFragment) {
                val fragment = navHostFragment.childFragmentManager.fragments.firstOrNull { it is MovieListingFragment }
                if (fragment is MovieListingFragment) {
                    val recyclerView: RecyclerView = fragment.requireView().findViewById(R.id.rvMovieListing)
                    val adapter = MovieListAdapter(movies) { movie ->
                        // Assert that the correct movie was clicked
                        assertEquals(movies[0], movie)

                        // Assert that navigation to the movie detail fragment occurs
                        val currentDestination = fragment.findNavController().currentDestination
                        assertEquals(R.id.movieListingFragment, currentDestination?.id)

                        val expectedArgs = Bundle().apply {
                            putParcelable("MovieDetail", movie)
                        }
                        val actualArgs = fragment.findNavController().currentBackStackEntry?.arguments
                        assertEquals(expectedArgs, actualArgs)
                    }
                    recyclerView.adapter = adapter

                    // Simulate clicking on the first movie item
                    recyclerView.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
                } else {
                    fail("MovieListingFragment not found")
                }
            } else {
                fail("NavHostFragment not found")
            }
        }
    }

}



