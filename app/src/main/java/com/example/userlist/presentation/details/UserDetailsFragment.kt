package com.example.userlist.presentation.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.userlist.ApplicationDelegate
import com.example.userlist.R
import com.example.userlist.databinding.FragmentUserDetailsBinding
import com.example.userlist.domain.model.User
import com.example.userlist.presentation.model.UserItem
import com.example.userlist.presentation.recycler.UserAdapter
import com.example.userlist.presentation.viewmodel.ViewModelFactory
import com.example.userlist.util.Constants.dateTimePattern
import com.example.userlist.util.getErrorMessage
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() : FragmentUserDetailsBinding = _binding!!

    private lateinit var adapter: UserAdapter
    private lateinit var currentUser: User

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: UserDetailsViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ApplicationDelegate.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory
        ).get(UserDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onStart() {
        super.onStart()
        initLiveDataListeners()
        initClickListeners()
    }

    private fun initClickListeners() {
        with(binding) {
            tvEmail.setOnClickListener {
                sendEmailIntent(currentUser.email)
            }
            tvPhone.setOnClickListener {
                sendPhoneIntent(currentUser.phone)
            }
            tvLocation.setOnClickListener {
                sendLocationIntent(currentUser.latitude, currentUser.longitude)
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun sendEmailIntent(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        sendImplicitIntent(intent)
    }

    private fun sendPhoneIntent(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        sendImplicitIntent(intent)
    }

    private fun sendLocationIntent(latitude: Double, longitude: Double) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("geo:$latitude,$longitude")
        }
        sendImplicitIntent(intent)
    }

    private fun initLiveDataListeners() {
        val userId: Int = arguments?.let {
            UserDetailsFragmentArgs.fromBundle(it).userId
        } ?: throw IllegalStateException(getString(R.string.no_internet_message))
        viewModel.getUserById(userId).observe(viewLifecycleOwner) {
            it?.let { user ->
                bindUser(user)
            } ?: run {
                showSnackbar(getString(R.string.user_not_fount))
                findNavController().navigateUp()
            }
        }
        viewModel.navigationId().observe(viewLifecycleOwner) {
            navigateToUserDetails(it)
        }
        viewModel.getError().observe(viewLifecycleOwner) {
            showSnackbar(it.getErrorMessage(resources))
        }
    }

    private fun bindUser(user: User) {
        currentUser = user
        with(binding) {
            tvEmail.text = user.email
            tvName.text = user.name
            (getString(R.string.age_prefix) + " ${user.age}").also { tvAge.text = it }
            tvAbout.text = user.about
            (getString(R.string.company_prefix) + " ${user.company}").also { tvCompany.text = it }
            (getString(R.string.phone_prefix) + " ${user.phone}").also { tvPhone.text = it }
            (getString(R.string.location_prefix) + " ${user.latitude} ${user.longitude}").also {
                tvLocation.text = it
            }
            (getString(R.string.registered_prefix) + " ${user.registered.toString(dateTimePattern)}").also {
                tvRegistered.text = it
            }
            (getString(R.string.address_prefix) + " ${user.address}").also { tvAddress.text = it }
            bindColor(user.eyeColor)
            bindFruit(user.favoriteFruit)
            submitList(user.friends)
        }
    }

    private fun bindFruit(fruit: String) {
        val imageId = when (fruit) {
            getString(R.string.strawberry) -> R.drawable.strawberry
            getString(R.string.apple) -> R.drawable.apple
            getString(R.string.banana) -> R.drawable.banana
            else -> throw IllegalStateException(getString(R.string.unknown_fruit))
        }
        binding.ivFruit.setImageResource(imageId)
    }

    private fun bindColor(string: String) {
        val color = when (string) {
            getString(R.string.brown) -> ContextCompat.getColor(requireContext(), R.color.brown)
            getString(R.string.blue) -> ContextCompat.getColor(requireContext(), R.color.blue)
            getString(R.string.green) -> ContextCompat.getColor(requireContext(), R.color.green)
            else -> throw IllegalStateException(getString(R.string.unknown_color))
        }
        binding.cvEyeColor.setCardBackgroundColor(color)
    }

    private fun submitList(users: List<User>?) {
        adapter.submitList(users?.map { user ->
            UserItem(
                user.id,
                user.email,
                user.name,
                user.isActive
            )
        })
    }

    private fun navigateToUserDetails(id: Int) {
        val action = UserDetailsFragmentDirections.actionUserDetailsFragmentSelf()
        action.userId = id
        findNavController().navigate(action)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendImplicitIntent(intent: Intent) {
        activity?.let {
            if (intent.resolveActivity(it.packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }
}