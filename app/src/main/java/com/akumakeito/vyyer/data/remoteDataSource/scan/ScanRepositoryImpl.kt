package com.akumakeito.vyyer.data.remoteDataSource.scan

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.akumakeito.vyyer.data.database.dao.IdentityInfoDao
import com.akumakeito.vyyer.data.database.dao.ScanInfoDao
import com.akumakeito.vyyer.data.database.entity.ScanInfoEntity
import com.akumakeito.vyyer.data.database.entity.fromResponseToEntity
import com.akumakeito.vyyer.data.dto.DEFAULT_PER_PAGE
import com.akumakeito.vyyer.data.dto.IdentityRequestBody
import com.akumakeito.vyyer.data.network.ApiService
import com.akumakeito.vyyer.domain.model.IdentityScanInfoModel
import com.akumakeito.vyyer.domain.repository.ScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    scanRemoteMediator: ScanRemoteMediator,
    private val scanInfoDao: ScanInfoDao,
    private val identityInfoDao: IdentityInfoDao,
) : ScanRepository {

    @OptIn(ExperimentalPagingApi::class)
    override val scanPagingList: Flow<PagingData<IdentityScanInfoModel>> = Pager(
        config = PagingConfig(DEFAULT_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = scanInfoDao::pagingSource,
        remoteMediator = scanRemoteMediator
    ).flow
        .map { scanInfoPaging ->

            getIdentityList(scanInfoPaging)

            scanInfoPaging.map { scanInfoResponse ->

                val identity = identityInfoDao.getIdentityById(scanInfoResponse.identityId)

                IdentityScanInfoModel(
                    id = scanInfoResponse.id,
                    identityId = identity.identityId,
                    fullName = identity.fullName,
                    createdAt = scanInfoResponse.createdAt,
                    verdictName = scanInfoResponse.verdictName,
                    gender = identity.gender,
                )
            }
        }


    private suspend fun getIdentityList(scanList: PagingData<ScanInfoEntity>) {
        val ids = mutableListOf<Int>()
        scanList.map { scanResponse ->
            ids.add(scanResponse.id)
        }

        if (ids.isNotEmpty()) {

            val identityIdInDao = identityInfoDao.getAllIdentityId()

            val requestList = ids.filterNot { it in identityIdInDao }

            val identityResponse = apiService.getIdentityInfoList(IdentityRequestBody(requestList))

            if (identityResponse.isSuccessful) {
                identityInfoDao.insertAll(
                    identityResponse.body()?.data?.fromResponseToEntity() ?: emptyList())
            }
        }


    }

}

