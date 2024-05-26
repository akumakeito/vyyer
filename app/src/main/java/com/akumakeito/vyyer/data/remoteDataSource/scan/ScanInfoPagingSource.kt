package com.akumakeito.vyyer.data.remoteDataSource.scan

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akumakeito.vyyer.data.database.dao.ScanInfoDao
import com.akumakeito.vyyer.data.database.entity.ScanInfoEntity
import com.akumakeito.vyyer.data.database.entity.fromResponseToEntity
import com.akumakeito.vyyer.data.dto.ScanRequestPaging
import com.akumakeito.vyyer.data.network.ApiService


class ScanInfoPagingSource (private val apiService: ApiService, private val scanInfoDao: ScanInfoDao) : PagingSource<Int, ScanInfoEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScanInfoEntity> {
        val page = params.key ?: 1
        return try {
            val data = apiService.getScanInfoList(ScanRequestPaging(page)).body()?.data?.fromResponseToEntity() ?: emptyList()
            scanInfoDao.insertAll(data)
            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ScanInfoEntity>): Int = 1

}
