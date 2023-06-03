package com.juanmora.square.employeelist.features.home.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanmora.square.employeelist.databinding.ItemEmployeeBinding
import com.juanmora.square.employeelist.features.core.extensions.CornerType
import com.juanmora.square.employeelist.features.core.extensions.download
import com.juanmora.square.employeelist.features.employees.domain.models.Employee

typealias SelectEmployeeCallback = (employee: Employee) -> Unit

class EmployeesAdapter(
    private val items: MutableList<Employee>,
    private val callback: SelectEmployeeCallback
) : RecyclerView.Adapter<EmployeesAdapter.EmployeesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeesViewHolder {
        val view = ItemEmployeeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EmployeesViewHolder(view, callback)
    }

    override fun onBindViewHolder(
        holder: EmployeesViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(employees: List<Employee>) {
        items.run {
            clear()
            addAll(employees)
        }
        notifyDataSetChanged()
    }

    class EmployeesViewHolder(
        private val binding: ItemEmployeeBinding,
        val employeeCallback: SelectEmployeeCallback
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                employeeCallback(it.tag as Employee)
            }
        }

        fun bind(employee: Employee) {
            with(binding) {
                root.tag = employee
                employeeItemImageView.download(
                    item = employee.smallAvatarUrl,
                    cornerType = CornerType.Circle
                )
                employeeItemNameTextView.text = employee.name
                employeeItemTeamNameTextView.text = employee.teamName
            }
        }
    }
}
