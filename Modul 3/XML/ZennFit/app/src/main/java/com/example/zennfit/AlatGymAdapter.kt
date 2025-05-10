package com.example.zennfit  import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.zennfit.databinding.ListItemBinding

class AlatGymAdapter(
    private val data: List<MainActivity.AlatGym>,
    private val onClick: (MainActivity.AlatGym) -> Unit
) : RecyclerView.Adapter<AlatGymAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alat = data[position]
        holder.binding.namaAlat.text = alat.nama
        holder.binding.alatImageList.setImageResource(alat.gambarResId)
        holder.binding.descSingkat.text = alat.descSingkat
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
    override fun getItemCount(): Int = data.size

}