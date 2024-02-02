package com.example.imagesearch.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.imagesearch.R
import com.example.imagesearch.data.SearchItem
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.fragment.MyLockerFragment
import com.example.imagesearch.fragment.SearchImageFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val itemRepository = mutableListOf<SearchItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setFragment(SearchImageFragment())
        with(binding) {
            btnSearchImagePage.setOnClickListener { setFragment(SearchImageFragment()) }
            btnMyLockerPage.setOnClickListener { setFragment(MyLockerFragment()) }
        }
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, frag)
            addToBackStack("")
            commit()
        }
    }

    fun addLikeContents(contents: SearchItem) {
        if (!itemRepository.contains(contents))
            itemRepository.add(contents)
    }

    fun removeLikedItem(contents: SearchItem) {
        itemRepository.remove(contents)
    }
}