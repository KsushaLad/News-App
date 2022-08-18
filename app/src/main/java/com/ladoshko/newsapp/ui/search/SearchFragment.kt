package com.ladoshko.newsapp.ui.search

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ladoshko.newsapp.databinding.FragmentSearchBinding
import com.ladoshko.newsapp.ui.adapters.NewsAdapter
import com.ladoshko.newsapp.utils.Resourse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val mBinding get() = _binding!!
    private val searchViewModel by viewModels<SearchViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        var job: Job? = null
        et_search.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()){
                        searchViewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }
        searchViewModel.searchMutableLiveData.observe(viewLifecycleOwner){
            when(it){
                is Resourse.Success -> {
                    search_progress_bar.visibility = View.INVISIBLE
                    it.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resourse.Error -> {
                    search_progress_bar.visibility = View.INVISIBLE
                }
                is Resourse.Loading -> {
                    search_progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        search_adapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}