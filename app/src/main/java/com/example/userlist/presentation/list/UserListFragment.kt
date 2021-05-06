package com.example.userlist.presentation.list

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.userlist.ApplicationDelegate
import com.example.userlist.databinding.FragmentUserListBinding
import com.example.userlist.presentation.recycler.UserAdapter
import com.example.userlist.presentation.viewmodel.ViewModelFactory
import com.example.userlist.util.Constants.IS_FIRST_RUN
import com.example.userlist.util.Constants.SHARED_PREFERENCES_NAME
import com.example.userlist.util.getErrorMessage
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding get() = _binding!!
    private lateinit var adapter: UserAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: UserListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ApplicationDelegate.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(viewModelStore, viewModelFactory).get(UserListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        adapter = UserAdapter() { id, isActive ->
            viewModel.itemClicked(id, isActive)
        }
        binding.rvUsers.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvUsers.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        initLiveDataListeners()
        initClickListeners()
    }

    private fun initClickListeners() {
        with(binding) {
            btnRefresh.setOnClickListener {
                viewModel.refreshUser()
            }
        }
    }

    private fun initLiveDataListeners() {
        if (isFirstRun()) {
            viewModel.refreshUser()
        }
        viewModel.getUsers().observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }
        viewModel.navigationId().observe(viewLifecycleOwner) {
            navigateToUserDetails(it)
        }
        viewModel.getError().observe(viewLifecycleOwner) {
            showSnackbar(it.getErrorMessage(resources))
        }
        viewModel.isLoading().observe(viewLifecycleOwner) {
            binding.pbUsers.isVisible = it
        }
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun isFirstRun(): Boolean {
        val sharedPreferences =
            activity?.getSharedPreferences(SHARED_PREFERENCES_NAME, ContextWrapper.MODE_PRIVATE)
        sharedPreferences?.let {
            val firstRun = sharedPreferences.getBoolean(IS_FIRST_RUN, true)
            if (firstRun) {
                sharedPreferences.edit().putBoolean(IS_FIRST_RUN, false).apply()
            }
            return firstRun
        } ?: throw IllegalStateException("Activity not found")
    }

    private fun navigateToUserDetails(id: Int) {
        val action = UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment()
        action.userId = id
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}