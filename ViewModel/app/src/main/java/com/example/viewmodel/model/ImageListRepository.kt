package com.example.viewmodel.model

import com.example.viewmodel.Generator

class ImageListRepository  {


    fun addImage(images: List<ImageItem>) : List<ImageItem> {
        return listOf(generateImages(1)[0]) + images
    }

    fun deleteImage(position: Int, images: List<ImageItem>) : List<ImageItem> {
        return images.filterIndexed { index, _ ->  index != position}
    }


    fun generateImages(num: Int) : List<ImageItem> {
        return (1 .. num).map { _ ->
            ImageItem(Generator.nextId(), Generator.getImageItem())
        }
    }
}