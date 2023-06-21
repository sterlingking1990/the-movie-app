package com.business.the_movie_app.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.business.the_movie_app.BuildConfig
import com.business.the_movie_app.R
import com.business.the_movie_app.databinding.FragmentMovieListingBinding
import com.business.the_movie_app.model.response.Movie
import com.business.the_movie_app.presentation.adapter.MovieListAdapter
import com.business.the_movie_app.presentation.viewmodel.GetMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



@AndroidEntryPoint
class MovieListingFragment : Fragment() {
    private lateinit var binding: FragmentMovieListingBinding
    private val getMoviesViewModel:GetMoviesViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter
    var originalMovies: List<Movie> = listOf()
    val filteredMovies: MutableList<Movie> = ArrayList(originalMovies)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieListingBinding.inflate(layoutInflater,container,false)

        adapter = MovieListAdapter(emptyList()) { movie ->
            openMovieDetails(movie)
        }
        binding.rvMovieListing.adapter = adapter
        binding.rvMovieListing.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.etSearchMovie.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val filterPattern = s.toString().toLowerCase().trim()

                filteredMovies.clear()
                filteredMovies.addAll(originalMovies.filter { movie ->
                    movie.title.toLowerCase().contains(filterPattern)
                })

                adapter = MovieListAdapter(filteredMovies) { movie ->
                    openMovieDetails(movie)
                }
                binding.rvMovieListing.adapter = adapter

                adapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
                if(s?.length!! <1){
                    CoroutineScope(Dispatchers.Main).launch { loadMovies() }
                }
            }
        })

        getMoviesViewModel.movies.observe(viewLifecycleOwner) { movies ->
            Log.d("Movies", movies.toString())
            originalMovies= movies
            adapter = MovieListAdapter(movies) { movie ->
                openMovieDetails(movie)
            }
            binding.rvMovieListing.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        CoroutineScope(Dispatchers.Main).launch { loadMovies() }

    }

    private fun loadMovies(){
        //getMoviesViewModel.fetchCachedMovies(BuildConfig.MOVIE_API_KEY)
        getMoviesViewModel.fetchMovies(BuildConfig.MOVIE_API_KEY)


    }

    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable("MovieDetail",movie)
        findNavController().navigate(R.id.action_movieListingFragment_to_movieDetailPage,bundle)

    }
}