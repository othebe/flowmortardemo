package com.othebe.flowmortardemo.ui.screens.timer

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.othebe.flowmortardemo.common.SERVICE_PRESENTER
import kotlinx.android.synthetic.main.screen_timer.view.*

class TimerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private val presenter: TimerScreenPresenter by lazy {
        context.getSystemService(SERVICE_PRESENTER) as TimerScreenPresenter
    }

    override fun onAttachedToWindow() {
        presenter.takeView(this)

        timer_start.setOnClickListener({
            v -> presenter.start()
        })

        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        presenter.dropView(this)
        super.onDetachedFromWindow()
    }

    fun setTime(time: Long) {
        timer_content.setText(time.toString())
    }
}