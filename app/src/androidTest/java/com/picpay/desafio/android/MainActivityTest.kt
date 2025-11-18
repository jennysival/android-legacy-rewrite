package com.picpay.desafio.android

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.testUtils.FakeViewModel
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.ui.ViewState
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val fakeViewModel = FakeViewModel()

    @Before
    fun setup() {
        loadKoinModules(
            module {
                viewModel { fakeViewModel }
            }
        )
    }

    @Test
    fun shouldDisplayTitle() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withText(R.string.title)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayLoadingBeforeSuccessDataState() {
        ActivityScenario.launch(MainActivity::class.java)

        fakeViewModel.postStateValue(ViewState.Loading)

        onView(withId(R.id.user_list_progress_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldDisplayRecyclerViewAfterSuccessDataState() {
        ActivityScenario.launch(MainActivity::class.java)

        fakeViewModel.postStateValue(
            ViewState.Success(
                listOf(UserModel("img", "name", 1, "username"))
            )
        )

        onView(withId(R.id.recyclerView))
            .check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
