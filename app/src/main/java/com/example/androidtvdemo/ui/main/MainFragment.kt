package com.example.androidtvdemo.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.androidtvdemo.ui.detail.DetailActivity
import com.example.androidtvdemo.R
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(val title: String, val year:Int, val poster: String): Parcelable

class MainFragment :BrowseSupportFragment() {

    private val backgroundState = BackgroundState(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = getString(R.string.browse)

        viewLifecycleOwner.lifecycleScope.launch {
            adapter = buildAdapter()
        }

        onItemViewClickedListener =
            OnItemViewClickedListener { vh, movie, _, _ ->
                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    (vh.view as ImageCardView).mainImageView,
                    DetailActivity.SHARED_ELEMENT_NAME
                ).toBundle()

                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.MOVIE_EXTRA, movie as Movie)
                }
                startActivity(intent, bundle)
            }

        onItemViewSelectedListener = OnItemViewSelectedListener { _, movie, _, _ ->
            (movie as? Movie)?.let { backgroundState.loadUrl(movie.poster) }
        }

    }


    private suspend fun buildAdapter(): ArrayObjectAdapter {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val listRowAdapter = ArrayObjectAdapter(CardPresenter())
        listRowAdapter.addAll(0, (1..10).map{
            Movie(
                "Title $it",
                2020,
                "https://loremflickr.com/176/313/cat?lock=$it"
            )
        })
        for(i in 1..10){
            val header = HeaderItem("Categoría $i")
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
        return rowsAdapter
    }

    override fun setHeaderPresenterSelector(headerPresenterSelector: PresenterSelector?) {
        super.setHeaderPresenterSelector(headerPresenterSelector)
    }

}