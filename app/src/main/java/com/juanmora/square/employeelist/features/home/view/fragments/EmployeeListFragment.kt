package com.juanmora.square.employeelist.features.home.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.juanmora.square.employeelist.R
import com.juanmora.square.employeelist.databinding.FragmentEmployeeListBinding
import com.juanmora.square.employeelist.features.employees.domain.models.Employee
import com.juanmora.square.employeelist.features.home.view.adapters.EmployeesAdapter
import com.juanmora.square.employeelist.features.home.view.adapters.SelectEmployeeCallback
import com.juanmora.square.employeelist.features.home.view.viewmodels.EmployeeListViewModel
import com.juanmora.square.employeelist.features.home.view.viewmodels.EmployeeListViewModel.EmployeeListUIState

class EmployeeListFragment : Fragment(R.layout.fragment_employee_list), SelectEmployeeCallback {

    private val employeesViewModel: EmployeeListViewModel by activityViewModels()
    private val employeesAdapter = EmployeesAdapter(mutableListOf(), this)

    private var _binding: FragmentEmployeeListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeListBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.frEmployeeListRecyclerView.adapter = employeesAdapter
        employeesViewModel.loadEmployeeContent()

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                employeesViewModel.employeeUIStateSharedFlow.collect { value ->
                    handleUIState(value)
                }
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            frEmployeeListRefreshLayout.setOnRefreshListener {
                employeesViewModel.loadEmployeeContent(forceUpdate = true)
            }
        }
    }

    private fun handleUIState(value: EmployeeListUIState) {
        when (value) {
            is EmployeeListUIState.Success -> {
                binding.frEmployeeListRefreshLayout.isRefreshing = false
                employeesAdapter.update(value.elements)
            }
            is EmployeeListUIState.Loading -> toggleLoading(value.show)
            is EmployeeListUIState.Error -> showError(value.errorCode)
        }
    }

    private fun showError(errorCode: EmployeeListUIState.Error.Codes) {
        binding.frEmployeeListRefreshLayout.isRefreshing = false
        val errorMessage = when (errorCode) {
            EmployeeListUIState.Error.Codes.EmptyResults -> R.string.fr_employees_empty_list
            EmployeeListUIState.Error.Codes.InvalidResults -> R.string.fr_employees_error
            EmployeeListUIState.Error.Codes.Unknown -> R.string.fr_employees_unknown
        }

        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun toggleLoading(show: Boolean) {
        with(binding) {
            frEmployeeListRecyclerView.isVisible = show.not()
            frEmployeeListLoadingView.isVisible = show
        }
    }

    override fun invoke(employee: Employee) {
        Snackbar.make(binding.root, "You have selected to ${employee.name}", Snackbar.LENGTH_LONG).show()
    }
}
