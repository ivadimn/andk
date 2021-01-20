package com.example.stateflow

import androidx.fragment.app.Fragment

class SubmitFragment : Fragment(R.layout.fragment_submit)
{
    private val _firstName = MutableStateFlow("")
    private val _password = MutableStateFlow("")
    private val _userID = MutableStateFlow("")

}