package com.example.anytimecarwash.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AppDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId: Int): UserEntity?

    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    @Query("SELECT * FROM bookings WHERE userId = :userId")
    suspend fun getBookings(userId: Int): List<BookingEntity>

    @Insert
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("SELECT * FROM notifications WHERE userId = :userId")
    suspend fun getNotification(userId: Int): NotificationEntity?
}