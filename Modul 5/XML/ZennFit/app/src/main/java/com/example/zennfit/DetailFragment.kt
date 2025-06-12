package com.example.zennfit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.zennfit.databinding.FragmentDetailBinding
import android.util.Log

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var playbackPosition = 0L

    companion object {
        fun newInstance(nama: String, deskripsi: String, imageUrl: String?, videoUrl: String?): DetailFragment {
            val fragment = DetailFragment()
            val bundle = Bundle().apply {
                putString("nama", nama)
                putString("deskripsi", deskripsi)
                putString("imageUrl", imageUrl)
                putString("videoUrl", videoUrl)
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
        val deskripsi = arguments?.getString("deskripsi") ?: "Deskripsi tidak tersedia."
        val imageUrl = arguments?.getString("imageUrl")
        val videoUrl = arguments?.getString("videoUrl")

        Log.d("DetailFragment", "Menampilkan detail untuk: $nama")
        Log.d("DetailFragment", "Deskripsi: $deskripsi")
        Log.d("DetailFragment", "Image URL: $imageUrl")
        Log.d("DetailFragment", "Video URL: $videoUrl")

        activity?.title = nama
        binding.namaAlat.text = nama
        binding.deskripsiText.text = deskripsi

        if (!imageUrl.isNullOrEmpty()) {
            binding.alatImage2.visibility = View.VISIBLE
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(binding.alatImage2)
        } else {
            binding.alatImage2.visibility = View.GONE
        }

        if (!videoUrl.isNullOrEmpty()) {
            binding.videoPlayerView.visibility = View.VISIBLE
            initializePlayer(videoUrl)
        } else {
            binding.videoPlayerView.visibility = View.GONE
            Log.d("DetailFragment", "No video URL provided, PlayerView hidden.")
        }
    }

    private fun initializePlayer(videoUrl: String) {
        if (player == null) {
            player = ExoPlayer.Builder(requireContext())
                .build()
            binding.videoPlayerView.player = player
        }

        val mediaItem = MediaItem.fromUri(videoUrl)
        player?.setMediaItem(mediaItem)
        player?.seekTo(playbackPosition)
        player?.playWhenReady = playWhenReady
        player?.prepare()
        player?.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        Log.d("DetailFragment", "ExoPlayer initialized and trying to play: $videoUrl")
    }

    override fun onStart() {
        super.onStart()
        val videoUrl = arguments?.getString("videoUrl")
        if (!videoUrl.isNullOrEmpty() && player == null) {
            initializePlayer(videoUrl)
        }
    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
        Log.d("DetailFragment", "ExoPlayer onResume: playWhenReady = true")
    }

    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false

        playbackPosition = player?.currentPosition ?: 0L
        playWhenReady = player?.playWhenReady ?: true

        Log.d("DetailFragment", "ExoPlayer onPause: playWhenReady = $playWhenReady, position = $playbackPosition")
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
        Log.d("DetailFragment", "ExoPlayer onStop: Player released.")
    }

    private fun releasePlayer() {
        if (player != null) {
            player?.release()
            player = null
            Log.d("DetailFragment", "ExoPlayer resources released.")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}