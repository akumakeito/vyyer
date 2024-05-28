package com.akumakeito.vyyer.presentation.scanhistory

import androidx.lifecycle.ViewModel
import com.akumakeito.vyyer.domain.repository.ScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val scanRepository: ScanRepository
) : ViewModel() {

     val data = scanRepository.scanPagingList




}
