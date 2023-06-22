package com.business.the_movie_app.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.business.the_movie_app.R
import com.business.the_movie_app.databinding.FragmentMovieDetailPageBinding
import com.business.the_movie_app.model.response.Movie

class MovieDetailPage : Fragment() {
    private lateinit var binding: FragmentMovieDetailPageBinding
    private lateinit var movie:Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable("MovieDetail")!!
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailPageBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
    }

    private fun setView(){
        binding.tvMovieTitle.text = movie.title

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(binding.ivMovieImage)

        binding.tvMovieOverview.text = movie.overview
        binding.tvMovieDatePublished.text = movie.releaseDate
    }
}