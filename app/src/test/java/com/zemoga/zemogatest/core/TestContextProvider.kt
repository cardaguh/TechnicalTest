package com.cyclopsapps.kotlinrecyclerviewwithretrofit.core

import com.zemoga.zemogatest.utils.CoroutineContextProvider
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    val sharedDispatcher = TestCoroutineDispatcher()
    override val MAIN: CoroutineContext by lazy { sharedDispatcher}
    override val IO: CoroutineContext by lazy { sharedDispatcher }
    override val Default: CoroutineContext by lazy { sharedDispatcher }
}