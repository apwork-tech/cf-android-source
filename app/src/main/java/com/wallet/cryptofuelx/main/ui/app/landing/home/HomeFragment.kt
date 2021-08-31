package com.wallet.cryptofuelx.main.ui.app.landing.home

import android.content.Context
import android.content.Intent
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.FragmentHomeBinding
import com.wallet.cryptofuelx.main.ui.base.component.BaseActivity.Companion.runCurrentActivity
import com.wallet.cryptofuelx.main.ui.base.component.BaseFragment

class HomeFragment : BaseFragment<HomeMvpView, HomePresenter>() {

    companion object {
        /**
         * This method starts current activity
         *
         * @param context UI context
         * */
        fun startActivity(context: Context) {
            val intent = Intent(context, HomeFragment::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            runCurrentActivity(context, intent)
        }
    }

    private lateinit var mBinding: FragmentHomeBinding

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun getFragmentPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as FragmentHomeBinding
        setListeners()
        initialize()
    }

    override fun stopUI() {

    }

    private fun setListeners() {
        /*setClickListener(mBinding.textViewHome, mBinding.textViewMyAppointments,
                mBinding.textViewBookAppointment, mBinding.fabBookAppointment)*/
    }

    private fun initialize() {
        /*if (arguments != null) {
            if (arguments?.containsKey(ContainerActivity::class.java.simpleName)!!) {
                if (arguments?.getBoolean(ContainerActivity::class.java.simpleName)!!) {
                    visitMyAppointments()
                } else {
                    visitDashboard()
                }
            } else {
                visitDashboard()
            }
        } else {
            visitDashboard()
        }*/
    }

    /*fun visitDashboard() {
        mBinding.textViewHome.setTextColor(ViewUtils.getColor(R.color.colorPrimary))
        mBinding.textViewBookAppointment.setTextColor(ViewUtils.getColor(R.color.white))
        mBinding.textViewMyAppointments.setTextColor(ViewUtils.getColor(R.color.white))

        mBinding.textViewHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_blue, 0, 0)
        mBinding.textViewMyAppointments.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clock_white, 0, 0)

        commitChildFragment(R.id.constraint_layout_fragment_container, DashboardFragment())
    }

    private fun bookAppointment() {
        if (mContext != null) {
            SelectClinicActivity.startActivity(mContext!!)
        }
    }

    fun visitMyAppointments() {
        mBinding.textViewHome.setTextColor(ViewUtils.getColor(R.color.white))
        mBinding.textViewBookAppointment.setTextColor(ViewUtils.getColor(R.color.white))
        mBinding.textViewMyAppointments.setTextColor(ViewUtils.getColor(R.color.colorPrimary))

        mBinding.textViewHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home_white, 0, 0)
        mBinding.textViewMyAppointments.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.clock_blue, 0, 0)

        commitChildFragment(R.id.constraint_layout_fragment_container, MyAppointmentsContainerFragment())
    }*/
}