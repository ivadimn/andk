package com.example.lists2

import android.os.Bundle
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*

class ImageListFragment : Fragment(R.layout.fragment_image_list) {

    private val images = listOf(
            "https://images.unsplash.com/photo-1606291587449-6b5d1257293e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
        "https://images.unsplash.com/photo-1522747776116-64ee03be1dad?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80",
        "https://images.unsplash.com/photo-1489493512598-d08130f49bea?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=748&q=80",
        "https://images.unsplash.com/photo-1606291587449-6b5d1257293e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
        "https://images.unsplash.com/photo-1472531881330-7c4124ae6691?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=751&q=80",
        "https://images.unsplash.com/photo-1472114864173-39596323454f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"

    )

    private var imageAdapter : ImageListAdapter ? = null
    private lateinit var imageList : RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageList = requireView().findViewById(R.id.imageList)
        initList()
    }

    override fun onDestroy() {
        super.onDestroy()
        imageAdapter = null
    }

    private fun initList() =
        with(imageList) {
            adapter = ImageListAdapter().apply {
                updateImages(images + images + images)
            }

            addItemDecoration(ItemOffsetDecoration(10, requireContext()))

            layoutManager = StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }

}