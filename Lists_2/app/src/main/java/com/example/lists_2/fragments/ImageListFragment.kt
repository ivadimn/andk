package com.example.lists_2.fragments


import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.lists_2.*
import com.example.lists_2.adapters.ImageListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.animators.*

class ImageListFragment(
) : Fragment(R.layout.fragment_entity_list) {

    companion object {

        private const val LAYOUT_RES_KEY = "LayoutRes"
        private const val KEY_IMAGES = "ImagesList"

        fun newInstance(@LayoutRes layoutRes : Int) : ImageListFragment {
            return ImageListFragment().withArguments {
                putInt(LAYOUT_RES_KEY, layoutRes)
            }
        }
    }
    private lateinit var imageList : RecyclerView
    private lateinit var fab : FloatingActionButton

    private var imageAdapter by AutoClearedValue<ImageListAdapter>()
    private var images : List<ImageItem> = emptyList()

    @LayoutRes private var layoutRes : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState != null) {
            images = savedInstanceState.getParcelableArrayList<ImageItem>(KEY_IMAGES)
                ?: emptyList()
        }
        else {
            images = generateImages()
        }
        layoutRes = requireArguments().getInt(LAYOUT_RES_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_IMAGES, ArrayList(images))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageList = requireView().findViewById(R.id.entityList)
        fab = requireView().findViewById(R.id.addFab)
        fab.setOnClickListener { addImage() }
        imageAdapter = ImageListAdapter(layoutRes) { position -> deleteImage(position) }
        initList()
        imageAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                imageList.scrollToPosition(0)
            }
        })
        imageAdapter.submitList(images)
    }

    private fun initList() {
        with(imageList) {
            setHasFixedSize (true)
            addItemDecoration(ImageItemDecoration(10, requireContext()))
            adapter = imageAdapter
            when(layoutRes) {
                R.layout.item_image_vertical -> {
                    layoutManager = LinearLayoutManager(requireContext())
                    itemAnimator = ScaleInLeftAnimator()
                }
                R.layout.image_item_horizontal -> {
                    layoutManager = LinearLayoutManager(requireContext()).apply {
                        orientation = LinearLayoutManager.HORIZONTAL
                    }
                    itemAnimator = ScaleInBottomAnimator()
                }
                R.layout.item_image_grid -> {
                    layoutManager = GridLayoutManager(requireContext(), 3)
                    itemAnimator = FadeInDownAnimator()
                }
                R.layout.item_image_grid_horizontal -> {
                    layoutManager = GridLayoutManager(requireContext(), 3).apply {
                        orientation = GridLayoutManager.HORIZONTAL
                        itemAnimator = FlipInRightYAnimator()
                    }
                }
                R.layout.item_image_grid_diff -> {
                    layoutManager = GridLayoutManager(requireContext(), 3).apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (position % 3 == 0) 2 else 1
                            }
                        }
                    }
                    itemAnimator = OvershootInLeftAnimator(0.5f)
                }
                R.layout.item_image_grid_staggered -> {
                    layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                    itemAnimator = SlideInUpAnimator()
                }
            }
        }
    }

    private fun addImage() {
        val image = ImageItem(Generator.nextId(), Generator.getImageItem())
        images = listOf(image) + images
        imageAdapter.submitList(images)
    }

    private fun deleteImage(position : Int) {
        images = images.filterIndexed { index, _ -> index != position}
        imageAdapter.submitList(images)
    }

    private fun generateImages() : List<ImageItem> {
        return (1 .. 50).map { _ ->
            ImageItem(Generator.nextId(), Generator.getImageItem())
        }
    }




}