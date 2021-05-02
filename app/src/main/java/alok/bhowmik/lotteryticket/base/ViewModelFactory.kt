package alok.bhowmik.lotteryticket.base

import alok.bhowmik.lotteryticket.lotteryList.repository.LotteryListRepository
import alok.bhowmik.lotteryticket.lotteryList.viewModel.LotteryListViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LotteryListViewModel::class.java) -> LotteryListViewModel(
                repository as LotteryListRepository
            ) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}
