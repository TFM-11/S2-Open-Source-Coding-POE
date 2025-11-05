package com.example.anytimecarwash.data

import com.example.anytimecarwash.Booking

data class BookingResponse(
    val success: Boolean,
    val message: String,
    val booking: Booking? = null
)