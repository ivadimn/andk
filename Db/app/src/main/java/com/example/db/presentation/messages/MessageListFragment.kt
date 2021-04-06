package com.example.db.presentation.messages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.db.database.Db
import com.example.db.database.model.message.Message
import com.example.db.databinding.FragmentMessageListBinding
import kotlinx.coroutines.launch
import org.threeten.bp.Instant

class MessageListFragment : Fragment() {
    private var _binding : FragmentMessageListBinding? = null
    val binding get() = _binding!!

    private val messageDao = Db.instance.messageDao()
    private val userDao = Db.instance.userDao()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            messageDao.insertMessages(
                    listOf(
                            Message(0, 2, 3, "message 4", true,
                                    true, Instant.now()),
                            Message(0, 2, 1, "message 5", true,
                                    true, Instant.now()),
                            Message(0, 2, 3, "message 6", true,
                                    true, Instant.now())
                    )
            )
        }
        Log.d("Add user", "messages inserted")
        testGetUserChat()
    }

    private fun testGetUserChat() {
        lifecycleScope.launch {
            val chat = userDao.getUserChat(2)
            binding.messagesTextView.text =
                    "\n\n${chat.fromUser}\n\n${chat.chat.joinToString("\n")}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}