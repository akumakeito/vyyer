package com.akumakeito.vyyer.domain.repository

import androidx.paging.PagingData
import com.akumakeito.vyyer.data.database.entity.IdentityInfoEntity
import com.akumakeito.vyyer.domain.model.IdentityScanInfoModel
import kotlinx.coroutines.flow.Flow

interface ScanRepository {

    val scanPagingList : Flow<PagingData<IdentityScanInfoModel>>
//
//    val identityList : Flow<List<IdentityInfoEntity>>


}