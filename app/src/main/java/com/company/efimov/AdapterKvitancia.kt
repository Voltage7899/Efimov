package com.company.efimov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.efimov.databinding.CompanyElementBinding
import com.company.efimov.databinding.KvitanciaElementBinding

class AdapterKvitancia : RecyclerView.Adapter<AdapterKvitancia.ViewHolder>() {

    private var ListInAdapter = ArrayList<KvitanciaModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterKvitancia.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kvitancia_element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterKvitancia.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position])
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = KvitanciaElementBinding.bind(itemView)
        fun bind(kv: KvitanciaModel) {
            binding.yourData.text = kv.data
            binding.yourType.text = kv.type
            binding.yourCompany.text = kv.toPayCompany
            binding.yourPrice.text = kv.price




        }
    }

    fun loadListToAdapter(productList: ArrayList<KvitanciaModel>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(kino: CompanyModel) {

        }
    }

    fun deleteItem(i: Int): String? {
        var id = ListInAdapter.get(i).data

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}