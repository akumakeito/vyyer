package com.akumakeito.vyyer.data.remoteDataSource.scan

//
//@OptIn(ExperimentalPagingApi::class)
//class ScanRemoteMediator @Inject constructor(
//    private val apiService: ApiService,
//    private val scanInfoDao: ScanInfoDao,
//    private val identityInfoDao: IdentityInfoDao,
//    private val remoteKeyDao: ScanPageRemoteKeyDao,
//    private val appDb: AppDb
//) : RemoteMediator<Int, ScanInfoEntity>() {
//
//    override suspend fun initialize(): InitializeAction = if (scanInfoDao.isEmpty()) {
//        InitializeAction.LAUNCH_INITIAL_REFRESH
//    } else {
//        InitializeAction.SKIP_INITIAL_REFRESH
//    }
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ScanInfoEntity>
//    ): MediatorResult {
//
//        return try {
//            val loadPageKey = when (loadType) {
//                LoadType.REFRESH -> {
//                    Log.d("ScanRemoteMediator", "Loading page: LoadType.REFRESH")
//                    1
//                    return MediatorResult.Success(true)
//                }
//
//                LoadType.PREPEND -> {
//                    Log.d("ScanRemoteMediator", "Loading page: LoadType.PREPEND")
//                    remoteKeyDao.getPrevPageNumber() ?: return MediatorResult.Success(true)
//                }
//
//                LoadType.APPEND -> {
//                    Log.d("ScanRemoteMediator", "Loading page: LoadType.APPEND")
//                    state.pages.lastOrNull() ?: return MediatorResult.Success(true)
////                    state.lastItemOrNull()?.id ?: return MediatorResult.Success(true)
////                    remoteKeyDao.getNextPageNumber() ?:  MediatorResult.Success(true)
//                }
//            }
//
//            Log.d("ScanRemoteMediator", "Loading page: ${loadPageKey}")
//
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
////
//            withContext(Dispatchers.IO) {
//                appDb.withTransaction {
//                    if (loadType == LoadType.REFRESH) {
//                        scanInfoDao.clearAll()
//                        identityInfoDao.clearAll()
//                    }
//
//                    val keys =
//                        ScanPageRemoteKeyEntity(
//                            prevPage = if (loadPageKey == 1) null else loadPageKey - 1,
//                            nextPage = if (scanBody.data.isNullOrEmpty()) null else loadPageKey + 1,
//                            lastId = scanBody.data.last().id
//                        )
//                    remoteKeyDao.insert(keys)
//
//                    scanInfoDao.insertAll(scanBody.data.fromResponseToEntity())
//
//                }
//            }
//
//            Log.d("+ScanRemoteMediator", "scanBody.data.isEmpty() ${scanBody.data.isEmpty()}")
//            MediatorResult.Success(
//                endOfPaginationReached = state.pages.isEmpty()
//            )
//
//        } catch (e: Exception) {
//            MediatorResult.Error(e)
//        }
//    }
//}
