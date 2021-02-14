package com.olderwold.sliide.presentation.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.observeSingleEvents
import com.olderwold.sliide.R
import com.olderwold.sliide.presentation.list.UserListViewModel

internal class CreateUserDialogFragment : DialogFragment(), LifecycleObserver {
    companion object {
        val TAG = CreateUserDialogFragment::class.simpleName
    }

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var cancelButton: Button
    private lateinit var okButton: Button
    private lateinit var form: Group
    private lateinit var progressBar: ProgressBar

    private val viewModel: CreateUserViewModel by lazy {
        requireActivity().viewModels<CreateUserViewModel>().value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameEditText = view.findViewById(R.id.name)
        emailEditText = view.findViewById(R.id.email)
        cancelButton = view.findViewById(R.id.cancel)
        okButton = view.findViewById(R.id.ok)
        form = view.findViewById(R.id.form)
        progressBar = view.findViewById(R.id.progressBar)

        okButton.setOnClickListener {
            val name = nameEditText.text?.toString()
            val email = emailEditText.text?.toString()

            viewModel.create(name, email)
        }
        cancelButton.setOnClickListener {
            dismiss()
        }

        viewModel.state.observeSingleEvents(viewLifecycleOwner, ::bindState)
    }

    override fun onDestroyView() {
        viewModel.reset()
        super.onDestroyView()
    }

    private fun bindState(state: CreateUserViewModel.State) {
        when (state) {
            CreateUserViewModel.State.Sending -> showSending()
            is CreateUserViewModel.State.Invalid -> {
                showForm()
                showInvalid(state)
            }
            CreateUserViewModel.State.Success -> {
                showForm()
                handleSuccess()
            }
            is CreateUserViewModel.State.Error -> {
                showForm()
                handleError(state)
            }
        }
    }

    private fun showSending() {
        form.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showForm() {
        form.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun showInvalid(state: CreateUserViewModel.State.Invalid) {
        state.nameError?.let {
            nameEditText.error = getString(it)
        }
        state.emailError?.let {
            emailEditText.error = getString(it)
        }
    }

    private fun handleSuccess() {
        val userListViewModel by requireActivity().viewModels<UserListViewModel>()
        userListViewModel.reload()
        dismiss()
    }

    private fun handleError(state: CreateUserViewModel.State.Error) {
        Toast.makeText(requireActivity(), state.error.message, Toast.LENGTH_LONG).show()
    }
}
