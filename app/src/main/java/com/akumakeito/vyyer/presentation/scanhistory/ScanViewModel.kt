package com.akumakeito.vyyer.presentation.scanhistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.akumakeito.vyyer.domain.repository.ScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanRepository: ScanRepository
) : ViewModel() {

//    private val scanPagingList = scanRepository.scanPagingList
//
//    private val identityList = scanRepository.identityList

    val data = scanRepository.scanPagingList.cachedIn(viewModelScope)



}
