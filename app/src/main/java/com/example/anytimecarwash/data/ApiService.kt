package com.example.anytimecarwash.data

import com.example.anytimecarwash.Booking
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: ProfileUpdateRequest): Response<LoginResponse>

    @POST("bookings")
    suspend fun createBooking(@Body request: BookingRequest): Response<BookingResponse>

    @GET("bookings/user/{userId}")
    suspend fun getBookings(@Path("userId") userId: Int): Response<List<Booking>>

    @PUT("profile/{userId}")
    suspend fun updateProfile(@Path("userId") userId: Int, @Body request: ProfileUpdateRequest): Response<LoginResponse>

    @PUT("notifications/{userId}")
    suspend fun toggleNotifications(@Path("userId") userId: Int, @Body request: NotificationToggleRequest): Response<LoginResponse>
}