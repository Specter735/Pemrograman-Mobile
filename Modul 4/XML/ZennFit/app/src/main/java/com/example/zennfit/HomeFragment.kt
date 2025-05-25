package com.example.zennfit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zennfit.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlatGymViewModel by viewModels {
        AlatGymViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AlatGymAdapter(emptyList()) { alat ->
            viewModel.onItemClicked(alat)
            Log.d("HomeFragment", "Tombol item diklik: ${alat.nama}")
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.alatList.collectLatest { alatList ->
                Log.d("HomeFragment", "Data alat diambil dari ViewModel: ${alatList.size} item")
                adapter.updateData(alatList)
            }
        }

        lifecycleScope.launch {
            viewModel.selectedItem.collectLatest { selected ->
                selected?.let { alat ->
                    val fragment = DetailFragment().apply {
                        arguments = Bundle().apply {
                            putString("nama", alat.nama)
                            putInt("deskripsi", alat.deskripsiResId)
                            putInt("gambar", alat.imageResId)
                            putInt("gif", alat.gifResId)
                        }
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()

                    Log.d("HomeFragment", "Berpindah ke DetailFragment: ${alat.nama}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
