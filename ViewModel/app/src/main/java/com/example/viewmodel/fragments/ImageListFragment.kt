package com.example.viewmodel.fragments


import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodel.*
import com.example.viewmodel.adapters.ImageListAdapter
import com.example.viewmodel.model.ImageItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.animators.*

class ImageListFragment(
) : Fragment(R.layout.fragment_entity_list) {

    private val imageListViewModel : ImageListViewModel by viewModels()

    private lateinit var imageList : RecyclerView
    private lateinit var fab : FloatingActionButton

    private var imageAdapter : ImageListAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageList = requireView().findViewById(R.id.entityList)
        fab = requireView().findViewById(R.id.addFab)
        fab.setOnClickListener { addImage() }
        imageAdapter = ImageListAdapter() { position -> deleteImage(position) }
        initList()
        imageAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                imageList.scrollToPosition(0)
            }
        })
        observeViewModelState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageAdapter = null
    }

    private fun initList() {
        with(imageList) {
            setHasFixedSize (true)
            addItemDecoration(ImageItemDecoration(10, requireContext()))
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            itemAnimator = FadeInDownAnimator()
        }
    }

    private fun addImage() {
        imageListViewModel.addImage()
    }

    private fun deleteImage(position : Int) {
        imageListViewModel.deleteImage(position)
    }

    private fun observeViewModelState() {
        imageListViewModel.images
            .observe(viewLifecycleOwner) {newImages -> imageAdapter?.submitList(newImages)}

        imageListViewModel.showToast
            .observe(viewLifecycleOwner) {toast("Item was added")}
    }

    private fun toast(msg : String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}