package com.olderwold.sliide.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.olderwold.sliide.R
import com.olderwold.sliide.presentation.create.CreateUserDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView
    private lateinit var fab: FloatingActionButton
    private val adapter = UserListAdapter()

    private val viewModel by viewModels<UserListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        configureRecyclerView()
        configureFab()
        bindViewModel()
    }

    private fun configureFab() {
        fab.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            supportFragmentManager.findFragmentByTag(CreateUserDialogFragment.TAG)?.let {
                transaction.remove(it)
            }

            val userDialogFragment = CreateUserDialogFragment()
            userDialogFragment.show(transaction, CreateUserDialogFragment::class.simpleName)
        }
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)
        fab = findViewById(R.id.fab)
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

    private fun bindViewModel() {
        viewModel.load()
        viewModel.state.observe(this) { state ->
            when (state) {
                UserListViewModel.State.Loading -> showLoading()
                is UserListViewModel.State.Loaded -> showLoaded(state)
                is UserListViewModel.State.Error -> showError(state)
            }
        }
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
