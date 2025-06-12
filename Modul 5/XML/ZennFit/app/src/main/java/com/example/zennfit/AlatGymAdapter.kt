package com.example.zennfit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.zennfit.databinding.ListItemBinding
import com.example.zennfit.model.ExerciseInfo
import com.bumptech.glide.Glide
import com.example.zennfit.model.getLocalizedDescription
import com.example.zennfit.model.getLocalizedName
import com.example.zennfit.model.getMainImageUrl
import android.util.Log

class AlatGymAdapter(
    private var alatList: List<ExerciseInfo>,
    private val onClick: (ExerciseInfo) -> Unit
) : RecyclerView.Adapter<AlatGymAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alat = alatList[position]

        holder.binding.namaAlat.text = alat.getLocalizedName()

        val description = alat.getLocalizedDescription()
        if (description == "Deskripsi tidak tersedia." || description.isBlank()) {
            holder.binding.descDetail.visibility = View.GONE
        } else {
            holder.binding.descDetail.visibility = View.VISIBLE
            holder.binding.descDetail.text = description
        }

        val imageUrl = alat.getMainImageUrl()
        if (!imageUrl.isNullOrEmpty()) {
            holder.binding.alatImageList.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.binding.alatImageList)
        } else {
            holder.binding.alatImageList.visibility = View.GONE
        }

        holder.binding.viewButton.setOnClickListener {
            onClick(alat)
        }

        holder.binding.browserButton.setOnClickListener {
            val context = holder.itemView.context
            val wgerUrl = "https://wger.de/id/exercise/${alat.id}/view"

            Log.d("BrowserUrlDebug", "Final URL sent to browser: $wgerUrl")

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(wgerUrl)
            )

            try {
                context.startActivity(intent)
                Log.d("BrowserUrlDebug", "URL opened successfully.")
            } catch (e: Exception) {
                Log.e("BrowserUrlDebug", "Could not open URL: $wgerUrl. Error: ${e.message}")
            }
        }
    }

    override fun getItemCount(): Int {
        val count = alatList.size
        Log.d("AlatGymAdapter", "getItemCount() called. Returning: $count")
        return count
    }

    fun updateData(newList: List<ExerciseInfo>) {
        Log.d("AlatGymAdapter", "updateData() called with ${newList.size} items.")
        alatList = newList
        notifyDataSetChanged()
        Log.d("AlatGymAdapter", "notifyDataSetChanged() called.")
    }
}