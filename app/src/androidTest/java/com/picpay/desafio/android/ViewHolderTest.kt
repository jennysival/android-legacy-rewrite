package com.picpay.desafio.android

import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.ui.user.adapter.UserListItemViewHolder
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewHolderTest {

    @Test
    fun viewHolderBindShouldDisplayCorrectData() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            val parent = FrameLayout(activity)
            val itemBinding = ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            val viewHolder = UserListItemViewHolder(itemBinding)

            val item = UserModel("img", "name", 1, "username")

            viewHolder.bind(item)

            Assert.assertEquals("name", itemBinding.name.text)
            Assert.assertEquals("username", itemBinding.username.text)
            Assert.assertNotNull(itemBinding.picture)
        }
    }
}
