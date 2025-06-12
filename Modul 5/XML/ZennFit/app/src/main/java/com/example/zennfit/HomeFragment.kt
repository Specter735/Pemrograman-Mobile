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

import com.example.zennfit.network.ApiClient
import com.example.zennfit.data.AppDatabase
import com.example.zennfit.data.AlatGymRepository
import com.example.zennfit.model.getLocalizedDescription
import com.example.zennfit.model.getLocalizedName
import com.example.zennfit.model.getMainImageUrl
import com.example.zennfit.model.getMainVideoUrl


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlatGymViewModel by viewModels {
        Log.d("HomeFragmentInit", "Starting ViewModel initialization.")
        try {
            val exerciseInfoDao = AppDatabase.getDatabase(requireContext()).exerciseInfoDao()
            Log.d("HomeFragmentInit", "ExerciseInfoDao initialized.")
            val wgerApiService = ApiClient.wgerApiService
            Log.d("HomeFragmentInit", "WgerApiService initialized.")
            val repository = AlatGymRepository(wgerApiService, exerciseInfoDao)
            Log.d("HomeFragmentInit", "AlatGymRepository initialized.")
            AlatGymViewModelFactory(repository)
        } catch (e: Exception) {
            Log.e("HomeFragmentInit", "Error during ViewModel initialization: ${e.message}", e)
            throw e
        }
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
        Log.d("HomeFragment", "onViewCreated called.")

        val adapter = AlatGymAdapter(emptyList()) { exerciseInfo ->
            viewModel.onItemClicked(exerciseInfo)
            Log.d("HomeFragment", "Tombol item diklik: ${exerciseInfo.getLocalizedName()}")
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.alatList.collectLatest { exerciseInfoList ->
                Log.d("HomeFragment", "Data alat diambil dari ViewModel: ${exerciseInfoList.size} item")
                Log.d("HomeFragment", "Calling adapter.updateData() with ${exerciseInfoList.size} items.")
                adapter.updateData(exerciseInfoList)
            }
        }

        lifecycleScope.launch {
            viewModel.selectedItem.collectLatest { selected ->
                selected?.let { exerciseInfo ->
                    val fragment = DetailFragment().apply {
                        arguments = Bundle().apply {
                            putString("nama", exerciseInfo.getLocalizedName())
                            putString("deskripsi", exerciseInfo.getLocalizedDescription())
                            putString("imageUrl", exerciseInfo.getMainImageUrl())
                            putString("videoUrl", exerciseInfo.getMainVideoUrl())
                        }
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    viewModel.clearSelectedItem()
                    Log.d("HomeFragment", "Berpindah ke DetailFragment: ${exerciseInfo.getLocalizedName()}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}