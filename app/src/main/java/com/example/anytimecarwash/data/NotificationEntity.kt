package com.example.anytimecarwash.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val notificationsEnabled: Boolean = true,
    val bookingReminders: Boolean = true,
    val promotionalOffers: Boolean = false,
    val serviceUpdates: Boolean = true
)