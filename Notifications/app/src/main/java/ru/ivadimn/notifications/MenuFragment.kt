package ru.ivadimn.notifications


import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ivadimn.notifications.databinding.FragmentMenuBinding
import ru.ivadimn.notifications.recivers.BatteryBroadcastReceiver


class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding : FragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    private val receiver = BatteryBroadcastReceiver()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notificationButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToNotificationFragment()
            findNavController().navigate(action)
        }

        binding.firebaseButton.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToFirebaseFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_LOW))
        requireContext().registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_OKAY))
        Log.d("Notification", "Receiver registered")
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(receiver)
        Log.d("Notification", "Receiver unregistered")
    }

}