package com.lefarmico.moviesfinder.injection.module

import com.lefarmico.moviesfinder.data.entity.CategoryData
import com.lefarmico.moviesfinder.data.entity.MenuItem
import com.lefarmico.moviesfinder.data.http.request.TmdbApi
import com.lefarmico.moviesfinder.data.http.response.State
import com.lefarmico.moviesfinder.data.manager.useCase.GetNowPlayingMovieBriefList
import com.lefarmico.moviesfinder.data.manager.useCase.GetPopularMovieBriefList
import com.lefarmico.moviesfinder.data.manager.useCase.GetTopRatedMovieBriefList
import com.lefarmico.moviesfinder.data.manager.useCase.GetUpcomingMovieBriefList
import com.lefarmico.moviesfinder.private.Private.API_KEY
import com.lefarmico.moviesfinder.utils.mapper.Converter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetPopularMovieBriefListCategory(
        tmdb: TmdbApi
    ): GetPopularMovieBriefList =
        GetPopularMovieBriefList { pageNumber ->
            flow {
                emit(State.Loading)
                try {
                    val category = CategoryData.PopularCategory
                    val call =
                        tmdb.getMovies(category.categoryRequestTitle, API_KEY, "en-US", pageNumber)
                    val tmdbMovieListResult = call.awaitResponse().body()
                    if (tmdbMovieListResult != null) {
                        emit(
                            State.Success(
                                MenuItem.Movies(
                                    movieCategoryData = category,
                                    movieBriefDataList = Converter.convertApiListToDTOList(
                                        tmdbMovieListResult.tmdbMovie
                                    )
                                )
                            )
                        )
                    } else {
                        emit(State.Error(NullPointerException("Request executed with failure")))
                    }
                } catch (e: Exception) {
                    emit(State.Error(e))
                }
            }
        }

    @Singleton
    @Provides
    fun provideGetUpcomingMovieBriefListCategory(
        tmdb: TmdbApi
    ): GetUpcomingMovieBriefList =
        GetUpcomingMovieBriefList { pageNumber ->
            flow {
                emit(State.Loading)
                try {
                    val category = CategoryData.UpcomingCategory
                    val call =
                        tmdb.getMovies(
                            category.categoryRequestTitle,
                            API_KEY,
                            "en-US",
                            pageNumber
                        )
                    val tmdbMovieListResult = call.awaitResponse().body()
                    if (tmdbMovieListResult != null) {
                        emit(
                            State.Success(
                                MenuItem.Movies(
                                    movieCategoryData = category,
                                    movieBriefDataList = Converter.convertApiListToDTOList(
                                        tmdbMovieListResult.tmdbMovie
                                    )
                                )
                            )
                        )
                    } else {
                        emit(State.Error(NullPointerException("Request executed with failure")))
                    }
                } catch (e: Exception) {
                    emit(State.Error(e))
                }
            }
        }

    @Singleton
    @Provides
    fun provideGetNowPlayingMovieBriefListCategory(
        tmdb: TmdbApi
    ): GetNowPlayingMovieBriefList =
        GetNowPlayingMovieBriefList { pageNumber ->
            flow {
                emit(State.Loading)
                try {
                    val category = CategoryData.NowPlayingCategory
                    val call =
                        tmdb.getMovies(
                            category.categoryRequestTitle,
                            API_KEY,
                            "en-US",
                            pageNumber
                        )
                    val tmdbMovieListResult = call.awaitResponse().body()
                    if (tmdbMovieListResult != null) {
                        emit(
                            State.Success(
                                MenuItem.Movies(
                                    movieCategoryData = category,
                                    movieBriefDataList = Converter.convertApiListToDTOList(
                                        tmdbMovieListResult.tmdbMovie
                                    )
                                )
                            )
                        )
                    } else {
                        emit(State.Error(NullPointerException("Request executed with failure")))
                    }
                } catch (e: Exception) {
                    emit(State.Error(e))
                }
            }
        }

    @Singleton
    @Provides
    fun provideGetTopRatedMovieBriefListCategory(
        tmdb: TmdbApi
    ): GetTopRatedMovieBriefList =
        GetTopRatedMovieBriefList { pageNumber ->
            flow {
                emit(State.Loading)
                try {
                    val category = CategoryData.TopRatedCategory
                    val call =
                        tmdb.getMovies(
                            category.categoryRequestTitle,
                            API_KEY,
                            "en-US",
                            pageNumber
                        )
                    val tmdbMovieListResult = call.awaitResponse().body()
                    if (tmdbMovieListResult != null) {
                        emit(
                            State.Success(
                                MenuItem.Movies(
                                    movieCategoryData = category,
                                    movieBriefDataList = Converter.convertApiListToDTOList(
                                        tmdbMovieListResult.tmdbMovie
                                    )
                                )
                            )
                        )
                    } else {
                        emit(State.Error(NullPointerException("Request executed with failure")))
                    }
                } catch (e: Exception) {
                    emit(State.Error(e))
                }
            }
        }
}
