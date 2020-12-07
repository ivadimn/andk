package com.example.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class InfoFragment : Fragment(R.layout.fragment_info) {

    companion object {
        private const val KEY_TEXT = "key_text"
        fun newInstance(text : String) : InfoFragment {
            return InfoFragment().withArguments {
                putString(KEY_TEXT, text)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LIFE_CYCLE", "Fragment onAttach, ${hashCode()}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LIFE_CYCLE", "Fragment onCreate, ${hashCode()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("LIFE_CYCLE", "Fragment onCreateView, ${hashCode()}")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("INFO_FRAGMENT", "Fragment onActivityCreated, ${hashCode()}")
        val textView = requireView().findViewById<TextView>(R.id.infoTextView)
        textView.text = arguments?.getString(KEY_TEXT) ?: "Ничего не передали"
    }

    override fun onStart() {
        super.onStart()
        Log.d("LIFE_CYCLE", "Fragment onStart, ${hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFE_CYCLE", "Fragment onResume, ${hashCode()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFE_CYCLE", "Fragment onPause, ${hashCode()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFE_CYCLE", "Fragment onStop, ${hashCode()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LIFE_CYCLE", "Fragment onDestroyView, ${hashCode()}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("LIFE_CYCLE", "Fragment onDetach, ${hashCode()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFE_CYCLE", "Fragment onDestroy, ${hashCode()}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("LIFE_CYCLE", "Fragment onSaveInstanceState, ${hashCode()}")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("LIFE_CYCLE", "Fragment onViewStateRestore, ${hashCode()}")
    }


}