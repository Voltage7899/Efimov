package com.company.efimov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.efimov.databinding.CompanyElementBinding

class AdapterCompany: RecyclerView.Adapter<AdapterCompany.ViewHolder>() {

    private var ListInAdapter = ArrayList<CompanyModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCompany.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.company_element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterCompany.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position])
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CompanyElementBinding.bind(itemView)
        fun bind(com: CompanyModel) {
            binding.companyName.text = com.name
            binding.tarif.text = com.tarif




        }
    }

    fun loadListToAdapter(productList: ArrayList<CompanyModel>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(kino: CompanyModel) {

        }
    }

    fun deleteItem(i: Int): String? {
        var id = ListInAdapter.get(i).name

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}