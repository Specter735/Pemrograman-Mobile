package com.example.zennfit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.zennfit.databinding.ListItemBinding

class AlatGymAdapter(
    private var alatList: List<AlatGym>,
    private val onClick: (AlatGym) -> Unit
) : RecyclerView.Adapter<AlatGymAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alat = alatList[position]
        holder.binding.namaAlat.text = alat.nama
        holder.binding.alatImageList.setImageResource(alat.imageResId)
        holder.binding.descDetail.text = holder.itemView.context.getString(alat.deskripsiResId)
        holder.binding.viewButton.setOnClickListener {
            onClick(alat)
        }

        holder.binding.browserButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/search?q=${Uri.encode(alat.nama)}")
            )
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = alatList.size

    fun updateData(newList: List<AlatGym>) {
        alatList = newList
        notifyDataSetChanged()
    }
}
