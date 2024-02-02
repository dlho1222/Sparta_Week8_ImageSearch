package com.example.imagesearch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.activity.MainActivity
import com.example.imagesearch.adapter.MyLockerAdapter
import com.example.imagesearch.data.SearchItem
import com.example.imagesearch.databinding.FragmentMyLockerBinding

class MyLockerFragment : Fragment() {
    private var _binding: FragmentMyLockerBinding? = null
    private val binding get() = _binding!!
    private lateinit var lockerAdapter: MyLockerAdapter
    private var likedContents = mutableListOf<SearchItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        likedContents = (activity as MainActivity).itemRepository
        _binding = FragmentMyLockerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        lockerAdapter = MyLockerAdapter(likedContents)
        binding.recyclerView.apply {
            adapter = lockerAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}