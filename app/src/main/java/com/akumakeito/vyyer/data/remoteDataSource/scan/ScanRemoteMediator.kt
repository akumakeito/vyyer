package com.akumakeito.vyyer.data.remoteDataSource.scan

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.akumakeito.vyyer.data.database.AppDb
import com.akumakeito.vyyer.data.database.dao.IdentityInfoDao
import com.akumakeito.vyyer.data.database.dao.ScanInfoDao
import com.akumakeito.vyyer.data.database.dao.ScanPageRemoteKeyDao
import com.akumakeito.vyyer.data.database.entity.ScanInfoEntity
import com.akumakeito.vyyer.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ScanRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val scanInfoDao: ScanInfoDao,
    private val identityInfoDao: IdentityInfoDao,
    private val remoteKeyDao: ScanPageRemoteKeyDao,
    private val appDb: AppDb
) : RemoteMediator<Int, ScanInfoEntity>() {

    override suspend fun initialize(): InitializeAction = if (scanInfoDao.isEmpty()) {
        InitializeAction.LAUNCH_INITIAL_REFRESH
    } else {
        InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ScanInfoEntity>
    ): MediatorResult {

        return try {
            val data = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("ScanRemoteMediator", "Loading page: LoadType.REFRESH")
                    return MediatorResult.Success(true)
                }

                LoadType.PREPEND -> {
                    Log.d("ScanRemoteMediator", "Loading page: LoadType.PREPEND")
                    return MediatorResult.Success(true)
                }

                LoadType.APPEND -> {
                    Log.d("ScanRemoteMediator", "Loading page: LoadType.APPEND")
//                    state.pages.lastOrNull() ?: MediatorResult.Success(true)
                    state.lastItemOrNull()?.id ?: return MediatorResult.Success(true)
//                    remoteKeyDao.getNextPageNumber() ?: return MediatorResult.Success(true)
                }
            }

            Log.d("ScanRemoteMediator", "Loading page: $data")


//            val scanResponse = apiService.getScanInfoList(ScanRequestPaging(loadPageKey))
//
//            if (!scanResponse.isSuccessful) {
//                Log.d("ScanRemoteMediator", "!scanResponse.isSuccessful")
//
//                return MediatorResult.Error(Exception(scanResponse.message()))
//            }
//
//            val scanBody = scanResponse.body()
//                ?: return MediatorResult.Error(Exception(scanResponse.message()))
//
//            if (scanBody.data.isEmpty()) {
//
//                Log.d("ScanRemoteMediator", "scanBody.data.isEmpty")
//                return MediatorResult.Success(true)
//            }
//
//
            withContext(Dispatchers.IO) {
                appDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        scanInfoDao.clearAll()
                        identityInfoDao.clearAll()
                    }

//                    val keys =
//                        ScanPageRemoteKeyEntity(prevPage = loadPageKey, nextPage = loadPageKey + 1)
//                    remoteKeyDao.insert(keys)

//                    scanInfoDao.insertAll()

                    }
                }

//                Log.d("+ScanRemoteMediator", "scanBody.data.isEmpty() ${scanBody.data.isEmpty()}")
                MediatorResult.Success(
                    endOfPaginationReached = state.pages.isEmpty()
                )

            } catch (e: Exception) {
                MediatorResult.Error(e)
            }
        }
    }
