package com.olderwold.sliide.presentation

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.olderwold.sliide.R
import com.olderwold.sliide.data.GoRestClient
import com.olderwold.sliide.rx.RxOperators
import com.olderwold.sliide.rx.Schedulers


class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserListViewModel> {
        val context = applicationContext as Application
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserListViewModel(GoRestClient(), Schedulers, RxOperators(context)) as T
            }
        }
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private val adapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)

        configureRecyclerView()

        viewModel.load()
        viewModel.state.observe(this) { state ->
            when (state) {
                UserListViewModel.State.Loading -> showLoading()
                is UserListViewModel.State.Loaded -> showLoaded(state)
                is UserListViewModel.State.Error -> showError(state)
            }
        }
    }

    private fun configureRecyclerView() {
        ContextCompat.getDrawable(this, R.drawable.padding_vertical)?.let {
            val verticalDivider = DividerItemDecoration(this, RecyclerView.VERTICAL)
                .apply { setDrawable(it) }
            recyclerView.addItemDecoration(verticalDivider)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showError(state: UserListViewModel.State.Error) {
        progressBar.visibility = View.GONE
        errorText.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorText.text = state.error.localizedMessage
    }

    private fun showLoaded(state: UserListViewModel.State.Loaded) {
        progressBar.visibility = View.GONE
        errorText.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE

        adapter.submitList(state.users)
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }
}
