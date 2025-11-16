package com.picpay.desafio.android.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.mapper.UserDtoMapper
import com.picpay.desafio.android.domain.model.UserModel
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.ui.user.adapter.UserListAdapter
import com.picpay.desafio.android.ui.user.viewModel.UserViewModel
import com.picpay.desafio.android.ui.user.viewModel.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private val userDtoMapper = UserDtoMapper()
    private val userRepository = UserRepositoryImpl(userMapper = userDtoMapper)
    private val getUsersUseCase = GetUsersUseCase(userRepository = userRepository)

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initObserver()
        setUpRecyclerView()
        userViewModel.getUsers()
    }

    private fun initViewModel() {
        userViewModel =
            ViewModelProvider(
                this,
                UserViewModelFactory(
                    getUsersUseCase = getUsersUseCase
                )
            )[UserViewModel::class.java]
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

    private fun handleSuccessState(data: List<UserModel>) {
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
