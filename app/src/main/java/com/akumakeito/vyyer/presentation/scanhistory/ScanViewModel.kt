package com.akumakeito.vyyer.presentation.scanhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.akumakeito.vyyer.domain.model.Gender
import com.akumakeito.vyyer.domain.repository.ScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanRepository: ScanRepository
) : ViewModel() {

    private val scanPagingList = scanRepository.scanPagingList

    private val identityList = scanRepository.identityList

    val data = scanPagingList.combine(identityList) { scanPagingList, identityList ->
        scanPagingList.map {
            it.copy(
                fullName = identityList.find { identity -> identity.identityId == it.identityId }?.fullName ?: "",
                gender = identityList.find { identity -> identity.identityId == it.identityId }?.gender ?: Gender.OTHER
            )
        }
    }.cachedIn(viewModelScope)



}
