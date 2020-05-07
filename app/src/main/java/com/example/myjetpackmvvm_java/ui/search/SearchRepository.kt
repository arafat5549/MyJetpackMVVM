package com.example.myjetpackmvvm_java.ui.search

import com.example.myjetpackmvvm_java.app.network.NetworkApi
import com.example.myjetpackmvvm_java.app.util.CacheUtil
import com.example.myjetpackmvvm_java.data.ApiPagerResponse
import com.example.myjetpackmvvm_java.data.ApiResponse
import com.example.myjetpackmvvm_java.data.entity.SearchResponse
import com.example.myjetpackmvvm_java.data.entity.SearchResultResponse


/**
 * 作者　: hegaojian
 * 时间　: 2020/2/29
 * 描述　:
 */
class SearchRepository {

    suspend fun getHotData(): ApiResponse<ArrayList<SearchResponse>> {
        return NetworkApi().service.getSearchData()
    }

    suspend fun getSearchResultData(pageNo:Int,searchKey:String): ApiResponse<ApiPagerResponse<ArrayList<SearchResultResponse>>> {
        return NetworkApi().service.getSearchDataByKey(pageNo,searchKey)
    }

    fun getHistoryData(): ArrayList<String> {
        return CacheUtil.getSearchHistoryData()
    }
}