package com.example.viewmodel

import com.example.viewmodel.model.Entity

interface DialogListener {
    fun onOkButtonClick(entity: Entity)
}