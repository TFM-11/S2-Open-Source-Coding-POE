package com.example.anytimecarwash

data class Booking(
    val id: Int,
    val userEmail: String,
    val service: String,
    val date: String,
    val time: String,
    val notes: String,
    val status: String
)