package com.example.androidtvdemo.ui.detail

import android.os.Bundle
import android.view.View
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import com.example.androidtvdemo.FullDetailsMoviePresenter
import com.example.androidtvdemo.ui.main.Movie

class DetailFragment: DetailsSupportFragment() {

    private lateinit var rowsAdapter: ArrayObjectAdapter
    private val detailsBackgroundState = DetailsBackgroundState(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireActivity().intent.getParcelableExtra<Movie>(DetailActivity.MOVIE_EXTRA)
            ?: throw IllegalStateException("Movie not found")

        val presenter = FullDetailsMoviePresenter(requireActivity())

        rowsAdapter = ArrayObjectAdapter(presenter)

        val row = DetailsOverviewRow(movie)
//        row.(requireContext(), movie.poster)
        row.actionsAdapter = presenter.buildActions()

        rowsAdapter.add(row)

        adapter = rowsAdapter

        detailsBackgroundState.loadUrl(movie.poster)
    }
}