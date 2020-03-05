package com.mif50.instagramimagefilter.Interface

import com.zomato.photofilters.imageprocessors.Filter

interface FilterListFragmentListener {
    fun onFilterSelected(filter: Filter)
}