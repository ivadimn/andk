package com.example.multithreading.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import com.example.multithreading.R

class DeadlockFragment : Fragment(R.layout.fragment_deadlock) {

    override fun onResume() {
        super.onResume()

        val friend1 = Person("Mike")
        val friend2 = Person("John")

        val thread1 = Thread {
            friend1.throwBallTo(friend2)
        }

        val thread2 = Thread {
            friend2.throwBallTo(friend1)
        }
        thread1.start()
        thread2.start()
    }



    data class Person(
            val name : String
    ) {
        fun throwBallTo(friend : Person) {
            synchronized(this) {
                Log.d("Person", "${name} бросает мяч ${friend.name} на потоке ${Thread.currentThread().name}")
                Thread.sleep(500)
            }
            friend.throwBallTo(this)
        }
    }
}