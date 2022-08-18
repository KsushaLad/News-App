package com.ladoshko.newsapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ladoshko.newsapp.R
import com.ladoshko.newsapp.databinding.FragmentMainBinding
import com.ladoshko.newsapp.ui.adapters.NewsAdapter
import com.ladoshko.newsapp.utils.Resourse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view.findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
        }

        mainObserver()
    }

    private fun mainObserver(){
        mainViewModel.newsLiveData.observe(viewLifecycleOwner){
            when(it){
                is Resourse.Success -> {
                    progress_bar.visibility = View.INVISIBLE
                    it.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resourse.Error -> {
                    progress_bar.visibility = View.INVISIBLE
                }
                is Resourse.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        news_adapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}