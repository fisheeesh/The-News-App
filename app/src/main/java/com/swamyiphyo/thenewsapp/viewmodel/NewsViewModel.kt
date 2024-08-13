package com.swamyiphyo.thenewsapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.swamyiphyo.thenewsapp.models.Article
import com.swamyiphyo.thenewsapp.models.NewsResponse
import com.swamyiphyo.thenewsapp.repository.NewsRepository
import com.swamyiphyo.thenewsapp.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class NewsViewModel(app : Application, private val repo : NewsRepository) : AndroidViewModel(app) {

    var headLines : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headLinesPage = 1
    var headLinesResponse : NewsResponse? = null

    var searchNews : MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse : NewsResponse? = null
    var newsSearchQuery : String? = null
    var oldSearchQuery : String? = null

    init {
        getHeadLines("mm")
    }

    fun getHeadLines(countryCode : String) =
        viewModelScope.launch {
            headLinesInternet(countryCode)
        }

    fun searchNews(searchQuery : String) =
        viewModelScope.launch {
            searchNewsInternet(searchQuery)
        }

    private fun handleHeadLinesResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                headLinesPage++
                if(headLinesResponse == null){
                    headLinesResponse = resultResponse
                }else{
                    val oldArticle = headLinesResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(headLinesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchResponse(response : Response<NewsResponse>) : Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {
                resultResponse ->
                if(searchNewsResponse == null || newsSearchQuery != oldSearchQuery){
                    searchNewsPage = 1
                    oldSearchQuery = newsSearchQuery
                    searchNewsResponse = resultResponse
                }
                else{
                    searchNewsPage++
                    val oldArticle = searchNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToFavorites(article : Article) =
        viewModelScope.launch {
            repo.upsert(article)
        }

    fun getFavoriteNews() = repo.getFavoriteNews()

    fun deleteArticle(article : Article) =
        viewModelScope.launch {
        repo.deleteArticle(article)
    }

    fun internetConnection(context: Context) : Boolean{
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run{
                when{
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    private suspend fun headLinesInternet(countryCode : String){
        headLines.postValue(Resource.Loading())
        try {
            if(internetConnection(this.getApplication())){
                val response = repo.getHeadLines(countryCode, headLinesPage)
                headLines.postValue(handleHeadLinesResponse(response))
            }
            else{
                headLines.postValue(Resource.Error("No Internet Connection!"))
            }
        }
        catch (t : Throwable){
            when(t){
                is IOException -> headLines.postValue(Resource.Error("Unable to connect."))
                else -> headLines.postValue(Resource.Error("No Signal!"))
            }
        }
    }

    private suspend fun searchNewsInternet(searchQuery : String){
        newsSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())

        try {
            if(internetConnection((this.getApplication()))){
                val response = repo.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchResponse(response))
            }
            else{
                searchNews.postValue(Resource.Error("No Internet Connection!"))
            }
        }
        catch (t : Throwable){
            when(t){
                is IOException -> searchNews.postValue(Resource.Error("Unable to connect."))
                else -> searchNews.postValue(Resource.Error("No Signal!"))
            }
        }
    }
}