package com.adesso.movee.util

import com.adesso.movee.internal.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(
    private val testCoroutineDispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : DispatcherProvider {

    override val main: CoroutineDispatcher get() = testCoroutineDispatcher

    override val io: CoroutineDispatcher get() = testCoroutineDispatcher

    override val default: CoroutineDispatcher get() = testCoroutineDispatcher
}
