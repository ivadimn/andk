package ru.ivadimn.di.holders

import android.graphics.BitmapFactory
import android.os.Binder
import android.util.DisplayMetrics
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import ru.ivadimn.di.App
import ru.ivadimn.di.data.entities.User
import ru.ivadimn.di.databinding.ItemUserBinding
import ru.ivadimn.di.utils.DefaultAvatar
import java.io.File

class UserItemHolder(
    private val binding : ItemUserBinding,
    private val onCall : (String) -> Unit,
    private val onClick : (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val folder = App.context.getExternalFilesDir(App.MEDIA_EXTERNAL_DIR)

    fun bind(user : User) {

        if (user.photo.isNotEmpty()) {
            binding.avatarImageView.setImageBitmap(
                BitmapFactory.decodeStream(File(folder, user.photo).inputStream())
            )
        }
        else {
            val width = 80F.fromDpToPixels()
            val height = 80F.fromDpToPixels()
            binding.avatarImageView.setImageDrawable(DefaultAvatar(width, height , "${user.name[0]}${user.family[0]}"))
        }

        binding.nameTextView.text = "${user.name} ${user.family}"
        binding.phonesTextView.text = "Phone: ${user.phone}"
        binding.emailTextView.text = "Email: ${user.email}"

        binding.callImageView.setOnClickListener {
            onCall(user.phone)
        }

        binding.root.setOnClickListener {
            onClick(user.id)
        }
    }

    private fun Float.fromDpToPixels() : Float {
        val density = App.context.resources.displayMetrics.densityDpi
        val pixelsInDp = density.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()
        Log.d("DINJ", "density - $density,   pixelinDp - $pixelsInDp")
        return this * pixelsInDp
    }
}