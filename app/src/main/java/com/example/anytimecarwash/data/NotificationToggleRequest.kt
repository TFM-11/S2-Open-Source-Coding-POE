package com.example.anytimecarwash.data

data class NotificationToggleRequest(
    val userId: Int,
    val notificationsEnabled: Boolean,
    val bookingReminders: Boolean,
    val promotionalOffers: Boolean,
    val serviceUpdates: Boolean
)