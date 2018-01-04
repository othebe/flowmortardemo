package com.othebe.flowmortardemo.ui.screens

open class ScreenKey(val screenType: ScreenType,
                         val scopeName: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScreenKey

        if (screenType != other.screenType) return false

        return true
    }

    override fun hashCode(): Int {
        return screenType.hashCode()
    }
}