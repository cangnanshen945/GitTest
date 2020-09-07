package com.example.githubrespo.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubrespo.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: GitViewModel
    private lateinit var listAdapter: GitListAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, GitViewModelFactory()).get(GitViewModel::class.java)
        listAdapter = GitListAdapter(viewModel.repoList.value ?: emptyList())
        swipe_refresh_layout.setOnRefreshListener(this)
        git_list_view.adapter = listAdapter
        viewModel.repoList.observe(viewLifecycleOwner, Observer {
                swipe_refresh_layout.isRefreshing = false
                listAdapter.refresh(it)
        })
        viewModel.loadDataState.observe(viewLifecycleOwner, Observer {
            when (it) {
                1 -> {

                }
                2 -> {
                    listAdapter.noMoreData()
                }
                else -> {

                }
            }
        })
        viewModel.getRepo()
        git_list_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var hasScroll = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    hasScroll = true
                    listAdapter.onScrolled(recyclerView.layoutManager as LinearLayoutManager)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (hasScroll && listAdapter.touchBottom()) {
                        viewModel.loadMore()
                    }
                    hasScroll = false
                }
            }
        })
    }

    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true
        viewModel.getRepo()
    }

}