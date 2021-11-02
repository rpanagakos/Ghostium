package com.example.ghostzilla.ui.tabs.trends

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ghostzilla.CoroutineTestRule
import com.example.ghostzilla.models.pricing.PriceVolatility
import com.example.ghostzilla.network.covalent.CovalentRemoteRepository
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@ExperimentalCoroutinesApi
class TrendsViewModelTest {

    private lateinit var trendsViewModel: TrendsViewModel

    @Mock
    private lateinit var covalentRemoteRepository: CovalentRemoteRepository

    @Mock
    private lateinit var ioDispatcher: CoroutineDispatcher

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coRoutineTestRule = CoroutineTestRule()

    private val mockObserverForPrices = mock<Observer<PriceVolatility>>()

    @Before
    fun setUp() {
        trendsViewModel = TrendsViewModel(covalentRemoteRepository)
        trendsViewModel.cryptosPricing.observeForever(mockObserverForPrices)
    }

    @Test
    fun testIfWeCanGetData() {

    }


}