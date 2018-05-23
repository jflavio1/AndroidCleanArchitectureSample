package com.jflavio1.cleanarchsample.presenter.showmovies

import com.jflavio1.cleanarchsample.model.MovieModel
import com.jflavio1.cleanarchsample.model.mapper.MovieModelMapper
import com.jflavio1.cleanarchsample.presenter.ShowMoviesPresenter
import com.jflavio1.cleanarchsample.view.ShowMoviesView
import com.jflavio1.domain.interactors.ShowMoviesUseCaseImpl
import com.jflavio1.domain.model.Movie
import io.reactivex.observers.DisposableObserver

/**
 * ShowMoviesListPresenterImpl
 *
 * @author Jose Flavio - jflavio90@gmail.com
 * @since  14/3/17
 */
class ShowMoviesListPresenterImpl(var view: ShowMoviesView<MovieModel>, var getMovies: ShowMoviesUseCaseImpl) : ShowMoviesPresenter<MovieModel> {

    private var mapper = MovieModelMapper()

    init {
        this.view.initPresenter(this)
    }

    override fun loadMovieList() {
        this.getMovies.executeUseCase(object: DisposableObserver<List<Movie>>() {
            override fun onComplete() {
                view.hideLoader()
            }

            override fun onNext(t: List<Movie>) {
                view.renderMovieList(mapper.transformListMovie(t))
            }

            override fun onError(e: Throwable) {
                this@ShowMoviesListPresenterImpl.showErrorMessage(e.toString())
            }

        }, null)
    }

    override fun onMoviesListLoaded(movieList: ArrayList<MovieModel>) {
        this.view.renderMovieList(movieList)
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun showErrorMessage(message: String) {
    }

    override fun destroy() {
        this.getMovies.dispose()
    }
}