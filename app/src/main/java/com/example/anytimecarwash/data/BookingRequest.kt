package com.example.anytimecarwash.data

data class BookingRequest(
    val userId: Int,
    val vehicleType: String,
    val service: String,
    val date: String,
    val time: String,
    val location: String
)