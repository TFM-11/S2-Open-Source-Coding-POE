package com.example.anytimecarwash.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val vehicleType: String,
    val service: String,
    val date: String,
    val time: String,
    val location: String,
    val price: Double
)