package ru.ivadimn.di

import android.os.Handler
import android.os.Looper
import android.util.Log

import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(noinline binder: (View) -> T) =
    ViewBindingDelegate(this, binder)

class ViewBindingDelegate<T : ViewBinding>(
   private val fragment: Fragment,
   private val binder: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var _value: T? = null
    private val observer = ViewBindingObserver()

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
       // Log.d("Fragment", "getValue" )

        this._value?.let { return it }

        val view = thisRef.requireView()
        thisRef.viewLifecycleOwner.lifecycle.addObserver(observer)
        _value = binder(view)
        return _value!!
    }

    inner class ViewBindingObserver : LifecycleObserver {
        private val mainHandler = Handler(Looper.getMainLooper())

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        @Suppress("UNUSED")
        @MainThread
        fun onDestroy() {
            //Log.d("Fragment", "Lifecycle OnDestroy" )
            fragment.viewLifecycleOwner.lifecycle.removeObserver(observer)
            mainHandler.post { _value = null }
        }
    }
}