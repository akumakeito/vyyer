package com.akumakeito.vyyer.data.remoteDataSource.scan

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.akumakeito.vyyer.data.database.dao.IdentityInfoDao
import com.akumakeito.vyyer.data.database.dao.ScanInfoDao
import com.akumakeito.vyyer.data.database.entity.IdentityInfoEntity
import com.akumakeito.vyyer.data.database.entity.fromResponseToEntity
import com.akumakeito.vyyer.data.dto.DEFAULT_PER_PAGE
import com.akumakeito.vyyer.data.dto.IdentityRequestBody
import com.akumakeito.vyyer.data.network.ApiService
import com.akumakeito.vyyer.domain.model.IdentityScanInfoModel
import com.akumakeito.vyyer.domain.repository.ScanRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    scanRemoteMediator: ScanRemoteMediator,
    private val scanInfoDao: ScanInfoDao,
    private val identityInfoDao: IdentityInfoDao,
) : ScanRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @OptIn(ExperimentalPagingApi::class)
    override val scanPagingList: Flow<PagingData<IdentityScanInfoModel>> = Pager(
        config = PagingConfig(DEFAULT_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { ScanInfoPagingSource(apiService, scanInfoDao) },
        remoteMediator = scanRemoteMediator
    ).flow
        .map { scanInfoPaging ->
            scanInfoPaging.map {
                it.fromEntityToModel()
            }
        }.cachedIn(coroutineScope)

    private val scanIdentityId = scanInfoDao.getNewScansIdentityID()

    override val identityList: Flow<List<IdentityInfoEntity>> =
        scanIdentityId.distinctUntilChanged().map { scanIdentityId ->
            withContext(Dispatchers.IO) {
                val identityIdInDb = identityInfoDao.getAllIdentityId()
                val responseList = scanIdentityId.filterNot { it in identityIdInDb }

            if (responseList.isNotEmpty()) {

                val identityResponse =
                    apiService.getIdentityInfoList(IdentityRequestBody(responseList))
                val body = identityResponse.body()?.data

                identityInfoDao.insertAll(body?.fromResponseToEntity() ?: emptyList())
                identityInfoDao.getAllIdentityInfo().first()
            } else {
                identityInfoDao.getAllIdentityInfo().first()
            }
            }

        }



}

