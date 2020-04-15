package com.mif50.instagramimagefilter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mif50.instagramimagefilter.Interface.FilterListFragmentListener
import com.mif50.instagramimagefilter.R
import com.zomato.photofilters.utils.ThumbnailItem
import kotlinx.android.synthetic.main.thumbnail_list_item.view.*

class ThumbnailAdapter (
    private val context: Context,
    private val thumbnailItemList: List<ThumbnailItem>,
    private val listener: FilterListFragmentListener
) : RecyclerView.Adapter<ThumbnailAdapter.MyViewHolder>(){

    private var selectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.thumbnail_list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return thumbnailItemList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val thumbNailItem = thumbnailItemList[position]
        holder.apply {
            thumbNail.setImageBitmap(thumbNailItem.image)
            thumbNail.setOnClickListener {
                listener.onFilterSelected(thumbNailItem.filter)
                selectedIndex = position
                notifyDataSetChanged()
            }
        }
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var thumbNail: ImageView
        var fileterName: TextView

        init {
            thumbNail = itemView.thumbnail
            fileterName = itemView.filterName
        }

    }


}