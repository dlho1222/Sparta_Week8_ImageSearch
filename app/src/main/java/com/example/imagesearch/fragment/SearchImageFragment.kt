package com.example.imagesearch.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.Consts.KEYWORD
import com.example.imagesearch.Consts.SEARCH_WORD
import com.example.imagesearch.adapter.ImageAdapter
import com.example.imagesearch.data.SearchItem
import com.example.imagesearch.databinding.FragmentSearchImageBinding
import com.example.imagesearch.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchImageFragment : Fragment() {
    private var _binding: FragmentSearchImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageAdapter: ImageAdapter
    private var searchItems : MutableList<SearchItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnSearch.setOnClickListener {
                val query = etSearch.text.toString()
                if (query.isEmpty()) return@setOnClickListener
                saveData(query)//검색어 저장

                //Api 연결 시 IO 으로 연결 하고, UI갱신은 withContext로 Main에서 처리
                CoroutineScope(Dispatchers.IO).launch {
                    val responseData = RetrofitInstance.imageSearchApi.getImage(query = query)
                    responseData.documents.forEach{
                        val thumbUrl = it.thumbnailUrl
                        val title = it.displaySiteName
                        val dateTime = it.datetime
                        searchItems.add(SearchItem(thumbUrl = thumbUrl, title = title, dateTime = dateTime))
                    }
                    withContext(Dispatchers.Main) {
                        initRecyclerView()
                    }
                }

                downKeyBoard(requireContext(), etSearch)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            imageAdapter = ImageAdapter(searchItems,requireContext())
            adapter = imageAdapter
            itemAnimator = null
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //키보드 내리기
    private fun downKeyBoard(context: Context, editText: EditText) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        editText.clearFocus()
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    //검색어 저장
    private fun saveData(keyWord: String) {
        activity?.getSharedPreferences(SEARCH_WORD, Context.MODE_PRIVATE)?.edit {
            putString(KEYWORD, keyWord)
            apply()
        }
    }

    //저장된 검색어 가져오기
    private fun getData() {
        val saveKeyWord = activity?.getSharedPreferences(SEARCH_WORD, Context.MODE_PRIVATE)
        binding.etSearch.setText(saveKeyWord?.getString(KEYWORD, ""))
    }

    override fun onResume() {
        getData()
        initRecyclerView()
        super.onResume()
    }

}