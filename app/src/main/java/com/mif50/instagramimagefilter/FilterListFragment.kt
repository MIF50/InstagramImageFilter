package com.mif50.instagramimagefilter

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mif50.instagramimagefilter.Interface.FilterListFragmentListener
import com.mif50.instagramimagefilter.adapter.ThumbnailAdapter
import com.mif50.instagramimagefilter.utlis.BitmapUtils
import com.mif50.instagramimagefilter.utlis.SpaceItemDecoration
import com.zomato.photofilters.FilterPack
import com.zomato.photofilters.imageprocessors.Filter
import com.zomato.photofilters.utils.ThumbnailItem
import com.zomato.photofilters.utils.ThumbnailsManager
import kotlinx.android.synthetic.main.fragment_filter_list.*


class FilterListFragment : Fragment(), FilterListFragmentListener {

    private var listener: FilterListFragmentListener? = null
    lateinit var  adapter: ThumbnailAdapter
    lateinit var  thumbnailItemList: MutableList<ThumbnailItem>

    fun setListener(listener : FilterListFragmentListener){
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thumbnailItemList = ArrayList()
        adapter = ThumbnailAdapter(activity!!, thumbnailItemList,this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
            val space = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, this.resources.displayMetrics).toInt()
            addItemDecoration(SpaceItemDecoration(space))
        }

        displayImage(null)

    }

    private fun displayImage(bitmap: Bitmap?){
        val r = Runnable {
            val thumbImage: Bitmap = (if (bitmap == null) {
                BitmapUtils.getBitmapFromAssets(activity!!,MainActivity.IMAGE_NAME, 100,100)

            } else {
                Bitmap.createScaledBitmap(bitmap,100,100,false)
            })
                ?: return@Runnable

            ThumbnailsManager.clearThumbs()
            thumbnailItemList.clear()

            // add normal bitmap first
            val thumbnailItem = ThumbnailItem()
            thumbnailItem.image = thumbImage
            thumbnailItem.filterName = "Normal"
            ThumbnailsManager.addThumb(thumbnailItem)

            // add Filter pack
            val filters = FilterPack.getFilterPack(activity!!)
            for (filter in filters){
                val item = ThumbnailItem()
                item.image = thumbImage
                item.filter = filter
                item.filterName = filter.name
                ThumbnailsManager.addThumb(item)
            }

            thumbnailItemList.addAll(ThumbnailsManager.processThumbs(activity!!))
            activity?.runOnUiThread{
                adapter.notifyDataSetChanged()
            }

        }
        Thread(r).start()
    }

    override fun onFilterSelected(filter: Filter) {
        listener?.onFilterSelected(filter)
    }

}
