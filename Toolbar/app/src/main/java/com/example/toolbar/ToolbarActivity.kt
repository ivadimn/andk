package com.example.toolbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar

class ToolbarActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var expandTextView : TextView
    private lateinit var searchResult : TextView
    private val users = listOf(
        "User1",
        "User2",
        "Unknown"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        expandTextView = findViewById(R.id.expandTextView)
        searchResult = findViewById(R.id.searchResult)

        initToolbar()
    }

    private fun toast(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initToolbar() {
        toolbar.title = "New title from code"
        toolbar.setNavigationOnClickListener {
            toast("Navigation Button was clicked")
        }
        toolbar.setOnMenuItemClickListener {menuItem ->
            when(menuItem.itemId) {
                R.id.action1 -> {
                    toast("Clicked Action 1")
                    true
                }
                R.id.action2 -> {
                    toast("Clicked Action 2")
                    true
                }
                else -> false
            }
        }
        val serchItem = toolbar.menu.findItem(R.id.search)
        serchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                expandTextView.text = "Search expanded"
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                expandTextView.text = "Search collapsed"
                return true
            }

        })

        (serchItem.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                users.filter { it.contains(newText ?: "", ignoreCase = true) }
                        .joinToString()
                        .let { searchResult.text = it }
                return true
            }

        } )


    }
}