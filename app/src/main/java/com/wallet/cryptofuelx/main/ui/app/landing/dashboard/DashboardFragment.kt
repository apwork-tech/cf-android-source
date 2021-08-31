package com.wallet.cryptofuelx.main.ui.app.landing.dashboard

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.wallet.cryptofuelx.R
import com.wallet.cryptofuelx.databinding.FragmentDashboardBinding
import com.wallet.cryptofuelx.main.data.remote.response.home_data.HomeData
import com.wallet.cryptofuelx.main.data.remote.response.home_data.SixmonthDipositeWithdrawal
import com.wallet.cryptofuelx.main.ui.base.component.BaseFragment
import com.wallet.cryptofuelx.main.ui.base.helper.ProgressDialogUtils
import com.wallet.cryptofuelx.utils.helper.Constants
import com.wallet.cryptofuelx.utils.libs.ToastUtils
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlin.collections.ArrayList


class DashboardFragment : BaseFragment<DashboardMvpView, DashboardPresenter>(),
        DashboardMvpView {


    private lateinit var mBinding: FragmentDashboardBinding

    override val layoutId: Int
        get() = R.layout.fragment_dashboard

    override fun getFragmentPresenter(): DashboardPresenter {
        return DashboardPresenter()
    }

    override fun startUI() {
        mBinding = viewDataBinding as FragmentDashboardBinding
        setListeners()
        initialize()
    }


    private fun loadData() {
        if (mContext != null) {
            presenter.getHomeData(Constants.TIME_PREIOD.WEEK, Constants.ChartType.TYPE_WEEK)
        }
    }

    private fun setListeners() {
        tv_this_month.setOnClickListener {
            presenter.getHomeData(Constants.TIME_PREIOD.MONTH,Constants.ChartType.TYPE_MONTH)
            tv_this_month.setTextColor(ContextCompat.getColor(activity!!,
                    R.color.colorPrimaryDark
            ))
            tv_this_week.setTextColor(ContextCompat.getColor(activity!!,
                    R.color.colorTextTitle
            ))
        }

        tv_this_week.setOnClickListener {
            presenter.getHomeData(Constants.TIME_PREIOD.WEEK, Constants.ChartType.TYPE_WEEK)
            tv_this_month.setTextColor(ContextCompat.getColor(activity!!,
                    R.color.colorTextTitle
            ))
            tv_this_week.setTextColor(ContextCompat.getColor(activity!!,
                    R.color.colorPrimaryDark
            ))
        }
    }

    private fun initializeDepositWithdrawChart(type: Int, label: List<String>, deposite: List<String>, withdraw: List<String>) {
        //yAxis value formatter to set $sign before value
        val yAxis = chart_deposit_withdraw.axisLeft
        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%.2f", value)
            }
        }
        //handle chart zoom
        if (type == Constants.ChartType.TYPE_MONTH) {
            chart_deposit_withdraw.viewPortHandler.setMaximumScaleY(1f)
            chart_deposit_withdraw.viewPortHandler.setMaximumScaleX(3.6f)
        } else if (type == Constants.ChartType.TYPE_WEEK) {
            chart_deposit_withdraw.viewPortHandler.setMaximumScaleY(1f)
            chart_deposit_withdraw.viewPortHandler.setMaximumScaleX(1f)
        }
        //disable vertical line of chart
        chart_deposit_withdraw.xAxis.setDrawGridLines(false)
        //hide right side data of chart
        chart_deposit_withdraw.axisRight.isEnabled = false
        //hide bottom label of chart
        chart_deposit_withdraw.legend.isEnabled = false
        //set animation to chart
        chart_deposit_withdraw.animateX(1000, Easing.EaseInBack)
        //hide description label of chart
        chart_deposit_withdraw.description.isEnabled = false
        //place xAxis value on the bottom
        chart_deposit_withdraw.xAxis.position = XAxis.XAxisPosition.BOTTOM
        //disable pinch zoom
        chart_deposit_withdraw.setPinchZoom(false)
        val lineData = LineData()
        lineData.addDataSet(getDepositLineDataSet(type, label, deposite))
        lineData.addDataSet(getWithdrawLineDataSet(type, label, withdraw))
        chart_deposit_withdraw.data = lineData
        chart_deposit_withdraw.isHighlightPerTapEnabled = true
        chart_deposit_withdraw.marker = CustomChartMarkerView(activity!!,
                R.layout.layout_chart_marker
        )
        chart_deposit_withdraw.invalidate()
    }

    private fun getDepositLineDataSet(type: Int, label: List<String>, deposite: List<String>): LineDataSet {
        val entryList = ArrayList<Entry>()
        val monthList = ArrayList<Entry>()
        var lineDataSet: LineDataSet? = null
        //set xAxisValue formatter for
        if (type == Constants.ChartType.TYPE_WEEK) {
            var i = 0
            deposite.forEach {
                entryList.add(Entry(i.toFloat(), it.toFloat()))
                i++
            }
            // Your List / array with String Values For X-axis Labels
            var weekdays: MutableList<String> = ArrayList()
            label.forEach {
                weekdays.add(it.substring(0, 3))
            }
            // Set the value formatter
            val xAxis = chart_deposit_withdraw.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(weekdays)
            lineDataSet = LineDataSet(entryList, null)
        } else {
            lineDataSet = LineDataSet(monthList, null)
            //if chart is for this total month
            var i = 0
            deposite.forEach {
                monthList.add(Entry(i.toFloat(), it.toFloat()))
                i++
            }
            // Your List / array with String Values For X-axis Labels
            var weekdays: MutableList<String> = ArrayList()
            label.forEach {
                weekdays.add(it)
            }
            // Set the value formatter
            val xAxis = chart_deposit_withdraw.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(weekdays)
        }

        //set line width
        lineDataSet.lineWidth = 3f
        //hide value on line
        lineDataSet.setDrawValues(false)
        //disable hole of line
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawCircles(false)
        //make line cubic
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        //disable dashes
        lineDataSet.disableDashedLine()
        //set line color
        lineDataSet.color = resources.getColor(com.wallet.cryptofuelx.R.color.deposit_color)
        lineDataSet.getEntryForIndex(3).icon = resources
                .getDrawable(R.drawable.circle_chart_indicator)
        //disable highlight
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        lineDataSet.setDrawVerticalHighlightIndicator(false)
        return lineDataSet
    }

    private fun getWithdrawLineDataSet(type: Int, label: List<String>, withdraw: List<String>): LineDataSet {
        val entryList = ArrayList<Entry>()
        val monthList = ArrayList<Entry>()
        var lineDataSet: LineDataSet? = null
        //set xAxisValue formatter for
        if (type == Constants.ChartType.TYPE_WEEK) {
            var i = 0
            withdraw.forEach {
                entryList.add(Entry(i.toFloat(), it.toFloat()))
                i++
            }
            // Your List / array with String Values For X-axis Labels
            var weekdays: MutableList<String> = ArrayList()
            label.forEach {
                weekdays.add(it.substring(0, 3))
            }
            // Set the value formatter
            val xAxis = chart_deposit_withdraw.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(weekdays)
            lineDataSet = LineDataSet(entryList, null)
        } else {
            var i = 0
            withdraw.forEach {
                monthList.add(Entry(i.toFloat(), it.toFloat()))
                i++
            }
            //if chart is for this total month
            // Your List / array with String Values For X-axis Labels
            var weekdays: MutableList<String> = ArrayList()
            label.forEach {
                weekdays.add(it)
            }
            // Set the value formatter
            val xAxis = chart_deposit_withdraw.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(weekdays)
            lineDataSet = LineDataSet(monthList, null)
        }
        //set line width
        lineDataSet.lineWidth = 3f
        //hide value on line
        lineDataSet.setDrawValues(false)
        //disable hole of line
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawCircles(false)
        //make line cubic
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        //disable dashes
        lineDataSet.disableDashedLine()
        //set line color
        lineDataSet.color = resources.getColor(com.wallet.cryptofuelx.R.color.withdraw_color)
        lineDataSet.getEntryForIndex(3).icon = resources.getDrawable(R.drawable.circle_chart_indicator)
        //disable highlight
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        lineDataSet.setDrawVerticalHighlightIndicator(false)
        return lineDataSet
    }


    private fun initializeHistoryChart(list: List<SixmonthDipositeWithdrawal>) {
        val colors = mutableListOf<Int>()
        colors.add(com.wallet.cryptofuelx.R.color.color_history_deposit)
        colors.add(com.wallet.cryptofuelx.R.color.color_history_withdraw)
        val entries = ArrayList<BarEntry>()
        var weekdays: MutableList<String> = ArrayList()
        var l = 0
        list.forEach {
            weekdays.add(it.month.substring(0, 3))
            entries.add(BarEntry(l.toFloat(), floatArrayOf(it.sixDeposit.toFloat(), it.sixWithdrawal.toFloat())))
            l++
        }
        val set = BarDataSet(entries, "")
        //set color to chart
        set.colors = getColors().toMutableList()
        set.setDrawValues(true)
        //format value of bar
        set.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%.1f", value)
            }
        }
        //yAxis value formatter to set $sign before value
        val yAxis = chart_dashboard_history.axisLeft
        yAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%.2f", value)
            }
        }
        val barData = BarData(set)
        // Your List / array with String Values For X-axis Labels


        // Set the value formatter
        val xAxis = chart_dashboard_history.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(weekdays)
        //default zoom level
        chart_dashboard_history.zoomAndCenterAnimated(
                2f,
                1f,
                0f,
                2f,
                YAxis.AxisDependency.LEFT,
                1000)
        chart_dashboard_history.setFitBars(true)

        chart_dashboard_history.setMaxVisibleValueCount(6)

        //set bar width
        barData.barWidth = 0.9f
        //set animation to chart
        chart_dashboard_history.animateY(1000, Easing.EaseInOutBack)
        //set color of bar
        //disable vertical line of chart
        chart_dashboard_history.xAxis.setDrawGridLines(false)
        //hide right side data of chart
        chart_dashboard_history.axisRight.isEnabled = false
        //hide bottom label of chart
        chart_dashboard_history.legend.isEnabled = false
        //hide description label of chart
        chart_dashboard_history.description.isEnabled = false
        //place xAxis value on the bottom
        chart_dashboard_history.xAxis.position = XAxis.XAxisPosition.BOTTOM
        //disable pinch zoom
        chart_dashboard_history.setPinchZoom(false)
        chart_dashboard_history.viewPortHandler.setMaximumScaleY(1f)
        chart_dashboard_history.viewPortHandler.setMaximumScaleX(2f)
        chart_dashboard_history.data = barData
        chart_dashboard_history.invalidate()
    }

    private fun getColors(): IntArray {
        // have as many colors as stack-values per entry
        val LIBERTY_COLORS = intArrayOf(Color.rgb(94, 85, 230),
                Color.rgb(223, 243, 254))
        val colors = IntArray(2)
        System.arraycopy(LIBERTY_COLORS, 0, colors, 0, 2)
        return colors
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    private fun initialize() {

    }

    override fun stopUI() {

    }

    override fun onHomeDataSuccess(homeData: HomeData, type:Int) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.success(homeData.message)
        initializeHistoryChart(homeData.sixmonthDipositeWithdrawal)
        initializeDepositWithdrawChart(type, homeData.label, homeData.deposite, homeData.withdraw)
    }

    override fun onHomeDataError(message: String) {
        ProgressDialogUtils.on().hideProgressDialog()
        ToastUtils.error(message)
    }
}