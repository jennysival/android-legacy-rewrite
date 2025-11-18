package com.picpay.desafio.android.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.user.adapter.UserListAdapter
import com.picpay.desafio.android.ui.user.model.UserUIModel
import com.picpay.desafio.android.ui.user.viewModel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userViewModel: UserViewModel by viewModel()

    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        setUpRecyclerView()
        userViewModel.getUsers()
    }

    private fun initObserver() {
        userViewModel.userListState.observe(this) {
            when (it) {
                is ViewState.Loading -> showLoading()
                is ViewState.Success -> handleSuccessState(it.data)
                is ViewState.Error -> handleErrorState()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading() {
        binding.userListProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.userListProgressBar.visibility = View.GONE
    }

    private fun handleSuccessState(data: List<UserUIModel>) {
        hideLoading()
        adapter.users = data
    }

    private fun handleErrorState() {
        val message = getString(R.string.error)
        hideLoading()
        binding.recyclerView.visibility = View.GONE
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}
