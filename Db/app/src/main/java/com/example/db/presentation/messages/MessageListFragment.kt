package com.example.db.presentation.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.db.database.Db
import com.example.db.database.model.message.Message
import com.example.db.databinding.FragmentMessageListBinding
import org.threeten.bp.Instant

class MessageListFragment : Fragment() {
    private var _binding : FragmentMessageListBinding? = null
    val binding get() = _binding!!

    private val messageDao = Db.instance.messageDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageDao.insertMessages(
                listOf(
                        Message(0, 1, 2, "message 1", true,
                        true, Instant.now()),
                        Message(0, 1, 2, "message 2", true,
                                true, Instant.now())
                )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}