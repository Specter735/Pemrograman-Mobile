package com.example.zennfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zennfit.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Gunakan data class dari MainActivity
    private lateinit var alatList: List<MainActivity.AlatGym>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pindahkan pengisian alatList ke onViewCreated, karena sudah ada konteks
        val context = requireContext()
        alatList = listOf(
            MainActivity.AlatGym(
                "BenchPress",
                "Melatih otot dada dengan cara mendorong barbel dari posisi telentang di bangku datar.",
                context.getString(R.string.desc_benchpress),
                R.drawable.benchpress,
                R.drawable.incline_benchpress
            ),
            MainActivity.AlatGym(
                "Row Cable",
                "Melatih otot punggung dengan gerakan mendayung dari posisi duduk.",
                context.getString(R.string.desc_row),
                R.drawable.cablerow,
                R.drawable._seated
            ),
            MainActivity.AlatGym(
                "Shoulderpress Machine",
                "Melatih otot bahu dengan mendorong beban ke atas dari posisi duduk.",
                context.getString(R.string.desc_shoulder),
                R.drawable.shoulderpress,
                R.drawable.shoulderpress_machine
            ),
            MainActivity.AlatGym(
                "Legpress Machine",
                "Melatih otot kaki dengan menekan beban menjauh menggunakan kedua kaki.",
                context.getString(R.string.desc_legpress),
                R.drawable.legpress,
                R.drawable.legpressmachine
            ),
            MainActivity.AlatGym(
                "Fly Machine",
                "Melatih otot dada dengan gerakan merapatkan lengan dari samping ke depan seperti memeluk.",
                context.getString(R.string.desc_fly),
                R.drawable.fly,
                R.drawable.fly_machine
            )
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = AlatGymAdapter(alatList) { alat ->
            // Kirim data ke DetailFragment menggunakan Bundle
            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("nama", alat.nama)
                    putString("deskripsi", alat.deskripsi)
                    putInt("gambar", alat.gambarResId)
                    putInt("gif", alat.gifResId)
                }
            }

            // Navigasi ke DetailFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

