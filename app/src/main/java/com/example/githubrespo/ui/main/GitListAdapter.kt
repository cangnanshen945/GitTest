package com.example.githubrespo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrespo.R
import kotlinx.android.synthetic.main.item_footer.view.*
import kotlinx.android.synthetic.main.item_list_git.view.*

class GitListAdapter(private val model: List<ResponseItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var hasNoData = false
    private var lastVisibleItem: Int? = 0
    private var loadMore: Int = 0
    private var dataList = mutableListOf<ResponseItem>().apply {
        addAll(model)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            GitViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_git, parent, false)
            )
        } else if(viewType == 1){
            FootViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_footer, parent, false))
        } else {
            NoDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_nodata, parent, false))
        }
    }

    override fun getItemCount(): Int {
       return dataList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FootViewHolder -> {
                when (loadMore) {
                    1 -> {
                        holder.itemView.visibility = VISIBLE
                        holder.itemView.is_loading.visibility = VISIBLE
                        holder.itemView.finish_loading.visibility = GONE
                    }
                    2 -> {
                        holder.itemView.visibility = VISIBLE
                        holder.itemView.is_loading.visibility = GONE
                        holder.itemView.finish_loading.visibility = VISIBLE
                    }
                    else -> {
                        holder.itemView.visibility = GONE
                    }
                }
            }
            is GitViewHolder -> {
                holder.itemView.text.text = dataList[position].name
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasNoData) {
            2
        } else {
            if (itemCount - 1 == position) {
                1
            } else
                0
        }
    }

    fun refresh(model: List<ResponseItem>) {
        hasNoData = false
        loadMore = 0
        dataList = model.toMutableList()
        notifyDataSetChanged()
    }

    fun noMoreData() {
        loadMore = 2
        notifyItemChanged(itemCount - 1)
    }

    fun noData() {
        dataList.clear()
        hasNoData = true
        notifyDataSetChanged()
    }

    fun onScrolled(layoutManager: LinearLayoutManager?) {
        lastVisibleItem = layoutManager?.findLastVisibleItemPosition()
    }

    fun touchBottom() : Boolean{
        if (lastVisibleItem == itemCount -  1) {
            loadMore = 1
            notifyDataSetChanged()
            return true
        }
        return false
    }

}

class GitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class NoDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

