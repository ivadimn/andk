package com.example.db.database.model

import androidx.room.Entity
import org.threeten.bp.Instant

@Entity
data class User(
    val id : Long,
    val name : String,
    val family : String,
    val phone : String,
    val email : String,
    val photo : String,
    val createdAt : Instant,
    val updatedAt : Instant
) {
}