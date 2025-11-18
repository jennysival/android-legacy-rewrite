package com.picpay.desafio.android

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.testUtils.ToastMatcher
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.ui.ViewState
import com.picpay.desafio.android.ui.user.model.UserUIModel
import com.picpay.desafio.android.ui.user.viewModel.UserViewModel
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MainActivityErrorTest {

    @Test
    fun shouldHideLoadingAndShowToastOnError() {
        val liveData = MutableLiveData<ViewState<List<UserUIModel>>>()
        liveData.postValue(ViewState.Error(Exception()))

        val userViewModel: UserViewModel = mockk(relaxed = true)
        every { userViewModel.userListState } returns liveData

        loadKoinModules(
            module { viewModel { userViewModel } }
        )

        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.user_list_progress_bar)).check(matches(not(isDisplayed())))

        onView(withText("Ocorreu um erro. Tente novamente."))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }
}
