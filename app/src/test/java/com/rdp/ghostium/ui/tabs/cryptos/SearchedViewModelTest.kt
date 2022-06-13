package com.rdp.ghostium.ui.tabs.cryptos

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rdp.ghostium.database.room.LocalRepository
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.models.coingecko.search.CoinsSearched
import com.rdp.ghostium.models.errors.mapper.NETWORK_ERROR
import com.rdp.ghostium.models.errors.mapper.NOT_FOUND
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.network.DataRepository
import com.rdp.ghostium.ui.tabs.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = Application::class)
class SearchedViewModelTest {

    private lateinit var searchViewModel: SearchViewModel

    // Use a fake UseCase to be injected into the viewModel
    private val dataRepository : DataRepository = mockk()
    private val localRepository : LocalRepository = mockk()
    private val currencyImpl : CurrencyImpl = mockk()


    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var coinName: String
    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()
    private lateinit var application : Application

    @Before
    fun setUp() {
        // Create class under test
        // We initialise the repository with no tasks
        coinName = testModelsGenerator.getStubSearchTitle()
        application = RuntimeEnvironment.application
    }


    @Test
    fun `get Coins List`() {
        // Let's do an answer for the liveData
        val coinsSearched = testModelsGenerator.generateCoins()

        //1- Mock calls
        coEvery { dataRepository.searchCoins("fantom") } returns flow {
            emit(GenericResponse.Success(coinsSearched))
        }

        //2-Call
        searchViewModel = SearchViewModel(dataRepository, localRepository, currencyImpl, application)
        searchViewModel.searchCoins("fantom")

        searchViewModel.coinsAnswer.observeForever {  }

        val isEmptyList = searchViewModel.coinsAnswer.value?.coinResults.isNullOrEmpty()
        assertEquals(false, searchViewModel.displayMessage.value)
        assertEquals(coinsSearched.coinResults, searchViewModel.coinsAnswer.value?.coinResults)
        assertEquals(false,isEmptyList)
    }

    @Test
    fun `get Recipes Empty List`() {
        // Let's do an answer for the liveData
        val coinsSearched = testModelsGenerator.generateCoinsWithEmptyList()

        //1- Mock calls
        coEvery { dataRepository.searchCoins("fantom")} returns flow {
            emit(GenericResponse.Success(coinsSearched))
        }

        //2-Call
        searchViewModel = SearchViewModel(dataRepository, localRepository, currencyImpl, application)
        searchViewModel.searchCoins("fantom")
        //active observer for livedata
        searchViewModel.coinsAnswer.observeForever {  }
        searchViewModel.displayMessage.observeForever {  }
        searchViewModel.resultNotFound.observeForever {  }

        //3-verify
        val isEmptyList = searchViewModel.coinsAnswer.value?.coinResults.isNullOrEmpty()
        assertEquals(true, searchViewModel.displayMessage.value)
        assertEquals(NOT_FOUND, searchViewModel.resultNotFound.value)
        assertEquals(true, isEmptyList)
    }


    @Test
    fun `get Recipes Error`() {
        // Let's do an answer for the liveData

        val error: GenericResponse<CoinsSearched> = GenericResponse.DataError(NETWORK_ERROR)

        //1- Mock calls
        coEvery { dataRepository.searchCoins("fantom")} returns flow {
            emit(error)
        }

        //2-Call
        searchViewModel = SearchViewModel(dataRepository, localRepository, currencyImpl, application)
        searchViewModel.displayMessage.value = false
        searchViewModel.searchCoins("fantom")
        //active observer for livedata
        searchViewModel.resultNotFound.observeForever { }
        searchViewModel.displayMessage.observeForever {  }

        //3-verify
        assertEquals(NETWORK_ERROR, searchViewModel.resultNotFound.value)
        assertEquals(true, searchViewModel.displayMessage.value)
    }

}