package com.othebe.flowmortardemo.ui.screens.timer

import android.os.Bundle
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import mortar.ViewPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimerScreenPresenter @Inject constructor() : ViewPresenter<TimerView>() {
    private var disposable: Disposable? = null
    private var timeState = 0L

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)

        view?.setTime(timeState)
    }

    override fun onExitScope() {
        super.onExitScope()
        disposable?.dispose()
    }

    fun start() {
        if (disposable == null) {
            disposable = getIntervalDisposable()
        }
    }

    private fun getIntervalDisposable(): Disposable {
        return Flowable
                .interval(1L, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: Long -> run {
                    timeState = t
                    view?.setTime(t)
                }})
    }
}