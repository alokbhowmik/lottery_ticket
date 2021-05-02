package alok.bhowmik.lotteryticket.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel, R : BaseRepository> : Fragment() {

    protected var mBinding: B? = null
    protected var mViewModel: VM? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = getBinding(inflater, container)
        val factory = ViewModelFactory(getRepository())
        mViewModel = ViewModelProvider(this, factory).get(getViewModel())
        return mBinding?.root
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): B
    abstract fun getViewModel(): Class<VM>
    abstract fun getRepository(): R
}