package com.example.zennfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import android.util.Log
import com.example.zennfit.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(nama: String, deskripsi: String, gambar: Int, gif: Int): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle().apply {
                putString("nama", nama)
                putString("deskripsi", deskripsi)
                putInt("gambar", gambar)
                putInt("gif", gif)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nama = arguments?.getString("nama")
        Log.d("DetailFragment", "Menampilkan detail untuk: $nama")
        val deskripsiResId = arguments?.getInt("deskripsi") ?: 0
        val deskripsi = getString(deskripsiResId)
        Log.d("DetailFragment", "Menampilkan detail untuk: $deskripsi")
        val gambar = arguments?.getInt("gambar") ?: 0
        Log.d("DetailFragment", "Menampilkan detail untuk: $gambar")
        val gif = arguments?.getInt("gif") ?: 0
        Log.d("DetailFragment", "Menampilkan detail untuk: $gif")

        activity?.title = nama
        binding.namaAlat.text = nama
        binding.deskripsiText.text = deskripsi
        binding.alatImage2.setImageResource(gambar)
        Glide.with(this).asGif().load(gif).into(binding.Gerakan)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
