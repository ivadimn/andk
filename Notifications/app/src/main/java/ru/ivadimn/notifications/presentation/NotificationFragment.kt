package ru.ivadimn.notifications.presentation

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ivadimn.notifications.R
import ru.ivadimn.notifications.databinding.FragmentNotificationBinding
import ru.ivadimn.notifications.viewBinding

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val binding : FragmentNotificationBinding by viewBinding(FragmentNotificationBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        binding.createSimpleNotificationButton.setOnClickListener {
            showSimpleNotification()
        }

        binding.createMessageNotificationButton.setOnClickListener {
            showMessageNotification()
        }

        binding.createNewsNotificationButton.setOnClickListener {
            showNewsNotification()
        }

        binding.createNotificationGroupButton.setOnClickListener {
            showNotificationGroup()
        }

        binding.createProgressNotificationButton.setOnClickListener {
            showProgressNotification()
        }
    }

    private fun showSimpleNotification() {
        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("My notification title")
            .setContentText("My notification text")
            .setSmallIcon(R.drawable.ic_notifications)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(SIMPLE_NOTIFICATION_ID, notification)
    }

    private fun showMessageNotification() {

        val largeIcon  = BitmapFactory.decodeResource(resources, R.drawable.contact2)

        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("New message")
            .setContentText("New message text at - ${System.currentTimeMillis()}")
            .setSmallIcon(R.drawable.ic_message)
            .setLargeIcon(largeIcon)
            .build()


        NotificationManagerCompat.from(requireContext())
            .notify(MESSAGE_NOTIFICATION_ID, notification)
    }

    private fun showNewsNotification() {
        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.NEWS_CHANNEL_ID)
            .setContentTitle("Application update...")
            .setContentText("Release new version application 1.2.7")
            .setSmallIcon(R.drawable.ic_baseline_new_releases_24)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(NEWS_NOTIFICATION_ID, notification)
    }

    private fun showNotificationGroup() {
        val messageCount = 3
        val groupId = "message_group"
        (0 until messageCount).forEach { messageIndex ->
            val messageNumber = messageIndex + 1
            val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.MESSAGE_CHANNEL_ID)
                .setContentTitle("New message")
                .setContentText("New message text from user - $messageNumber")
                .setSmallIcon(R.drawable.ic_message)
                .setGroup(groupId)
                .build()

            NotificationManagerCompat.from(requireContext())
                .notify(messageNumber, notification)
        }

        val summaryNotification =  NotificationCompat.Builder(requireContext(), NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("Summary")
            .setContentText("New message text from time - ${System.currentTimeMillis()}")
            .setSmallIcon(R.drawable.ic_message)
            .setGroup(groupId)
            .setGroupSummary(true)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(SUMMARY_NOTIFICATION_ID, summaryNotification)
    }

    private fun showProgressNotification() {
        val notificationBuilder = NotificationCompat.Builder(requireContext(),
            NotificationChannels.NEWS_CHANNEL_ID)
            .setContentTitle("Update downloading ...")
            .setContentText("Download in progress")
            .setSmallIcon(R.drawable.ic_notifications)

        val maxProgress = 10
        lifecycleScope.launch {
            (0 until maxProgress).forEach { step ->
                val notification = notificationBuilder
                    .setContentText("Download in progress step - $step")
                    .setProgress(maxProgress, step, false)
                    .build()

                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)
                delay(500)
            }

            val notificationComplete = notificationBuilder
                .setContentText("Download complete")
                .setProgress(0, 0, false)
                .build()

            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, notificationComplete)
            delay(500)

            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)
        }

    }



    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
        findNavController().popBackStack()
        }
    }

    companion object {
        const val SIMPLE_NOTIFICATION_ID = 12345
        const val MESSAGE_NOTIFICATION_ID = 12346
        const val NEWS_NOTIFICATION_ID = 12347
        const val SUMMARY_NOTIFICATION_ID = 12348
        const val PROGRESS_NOTIFICATION_ID = 12350
    }
}