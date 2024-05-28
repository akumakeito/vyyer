package com.akumakeito.vyyer.data.remoteDataSource.scan

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.akumakeito.vyyer.data.dto.DEFAULT_PER_PAGE
import com.akumakeito.vyyer.data.dto.IdentityInfoResponse
import com.akumakeito.vyyer.data.dto.IdentityRequestBody
import com.akumakeito.vyyer.data.dto.ScanInfoResponse
import com.akumakeito.vyyer.data.network.ApiService
import com.akumakeito.vyyer.domain.model.Gender
import com.akumakeito.vyyer.domain.model.IdentityScanInfoModel
import com.akumakeito.vyyer.domain.repository.ScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : ScanRepository {


    @OptIn(ExperimentalPagingApi::class)
    override val scanPagingList: Flow<PagingData<IdentityScanInfoModel>> = Pager(
        config = PagingConfig(DEFAULT_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { ScanInfoPagingSource(apiService) },
    ).flow
        .map { scanInfoPaging ->
            val identityList = getIdentityList(scanInfoPaging).associateBy { it.id }

            scanInfoPaging.map { scanInfoResponse ->

                val identity = identityList[scanInfoResponse.identityID]!!

                IdentityScanInfoModel(
                    id = scanInfoResponse.id,
                    identityId = identity.id,
                    fullName = identityList[scanInfoResponse.identityID]!!.fullName,
                    createdAt = scanInfoResponse.createdAt,
                    verdictName = scanInfoResponse.verdictName,
                    gender = Gender.valueOf(identity.gender.uppercase()),
                )
            }

        }


   private suspend fun getIdentityList(scanList: PagingData<ScanInfoResponse>): List<IdentityInfoResponse> {
        val ids = mutableListOf<Int>()
        scanList.map { scanResponse ->
            ids.add(scanResponse.id)
        }
        val identityResponse =
            apiService.getIdentityInfoList(IdentityRequestBody(ids))
        return identityResponse.body()?.data ?: emptyList()


    }

}







