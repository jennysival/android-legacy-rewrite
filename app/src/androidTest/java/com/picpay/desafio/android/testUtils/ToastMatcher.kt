package com.picpay.desafio.android.testUtils

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description?) {
        description?.appendText("is toast")
    }

    override fun matchesSafely(item: Root?): Boolean {
        val type = item?.windowLayoutParams2?.type
        if (type != WindowManager.LayoutParams.TYPE_TOAST) return false

        val windowToken = item.decorView.windowToken
        val appToken = item.decorView.applicationWindowToken
        return windowToken === appToken
    }
}
