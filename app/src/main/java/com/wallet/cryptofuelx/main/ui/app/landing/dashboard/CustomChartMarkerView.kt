package com.wallet.cryptofuelx.main.ui.app.landing.dashboard

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.layout_chart_marker.view.*
import com.wallet.cryptofuelx.utils.helper.DataUtils
import java.lang.NumberFormatException


class CustomChartMarkerView(context: Context?, layoutResource: Int) :
        MarkerView(context, layoutResource) {
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        try {
            if (e!!.y.toString().toDouble() < 1000) {
                tv_text.text = String.format("$%.2f", e!!.y.toDouble())
            } else {
                tv_text.text = String.format("$%s",
                        DataUtils.formatToShortNumber(e!!.y.toDouble()))
            }
        } catch (ex: NumberFormatException) {
            tv_text.text = "N/A"
        }

    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }
}
