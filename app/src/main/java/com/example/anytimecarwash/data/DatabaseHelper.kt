package com.example.anytimecarwash

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "AnytimeCarWash.db"
        private const val DATABASE_VERSION = 1

        // User table and columns
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_FULL_NAME = "full_name"
        const val COLUMN_PHONE = "phone"

        // Bookings table and columns
        const val TABLE_BOOKINGS = "bookings"
        const val COLUMN_BOOKING_ID = "id"
        const val COLUMN_USER_EMAIL = "user_email"
        const val COLUMN_SERVICE = "service"
        const val COLUMN_DATE = "date"
        const val COLUMN_TIME = "time"
        const val COLUMN_NOTES = "notes"
        const val COLUMN_STATUS = "status"
    }

    // Data classes for better data handling
    data class UserProfile(
        val id: Int,
        val email: String,
        val fullName: String,
        val phone: String
    )

    data class Booking(
        val id: Int,
        val userEmail: String,
        val service: String,
        val date: String,
        val time: String,
        val notes: String,
        val status: String
    )

    override fun onCreate(db: SQLiteDatabase) {
        // Create users table
        val createUserTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EMAIL TEXT UNIQUE NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_FULL_NAME TEXT,
                $COLUMN_PHONE TEXT
            )
        """.trimIndent()
        db.execSQL(createUserTable)

        // Create bookings table
        val createBookingsTable = """
            CREATE TABLE $TABLE_BOOKINGS (
                $COLUMN_BOOKING_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_EMAIL TEXT NOT NULL,
                $COLUMN_SERVICE TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_TIME TEXT NOT NULL,
                $COLUMN_NOTES TEXT,
                $COLUMN_STATUS TEXT DEFAULT 'upcoming'
            )
        """.trimIndent()
        db.execSQL(createBookingsTable)

        // Insert a default user for testing
        val defaultUser = ContentValues().apply {
            put(COLUMN_EMAIL, "user@example.com")
            put(COLUMN_PASSWORD, "password123")
            put(COLUMN_FULL_NAME, "Thoriso Maake")
            put(COLUMN_PHONE, "+27 67 456 789")
        }
        val result = db.insert(TABLE_USERS, null, defaultUser)
        Log.d("DatabaseHelper", "Default user inserted with result: $result")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKINGS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // ============ BOOKING METHODS ============

    /**
     * Insert a new booking into the database
     */
    fun insertBooking(userEmail: String, service: String, date: String, time: String, notes: String = ""): Boolean {
        val db = this.writableDatabase

        Log.d("DatabaseHelper", "Attempting to insert booking:")
        Log.d("DatabaseHelper", "Email: $userEmail")
        Log.d("DatabaseHelper", "Service: $service")
        Log.d("DatabaseHelper", "Date: $date")
        Log.d("DatabaseHelper", "Time: $time")
        Log.d("DatabaseHelper", "Notes: $notes")

        try {
            val values = ContentValues().apply {
                put(COLUMN_USER_EMAIL, userEmail)
                put(COLUMN_SERVICE, service)
                put(COLUMN_DATE, date)
                put(COLUMN_TIME, time)
                put(COLUMN_NOTES, notes)
                put(COLUMN_STATUS, "upcoming")
            }

            val result = db.insert(TABLE_BOOKINGS, null, values)
            Log.d("DatabaseHelper", "Insert result: $result")

            if (result == -1L) {
                Log.e("DatabaseHelper", "INSERT FAILED - Check database schema")
                return false
            } else {
                Log.d("DatabaseHelper", "INSERT SUCCESSFUL - Booking ID: $result")
                return true
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error during insert: ${e.message}")
            e.printStackTrace()
            return false
        }
    }

    /**
     * Get all upcoming bookings for a specific user
     */
    fun getUpcomingBookings(userEmail: String): List<Booking> {
        val bookings = mutableListOf<Booking>()
        val db = readableDatabase

        val query = """
            SELECT * FROM $TABLE_BOOKINGS 
            WHERE $COLUMN_USER_EMAIL = ? AND $COLUMN_STATUS = 'upcoming'
            ORDER BY $COLUMN_DATE ASC, $COLUMN_TIME ASC
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userEmail))

        while (cursor.moveToNext()) {
            val booking = Booking(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)),
                userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                service = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            )
            bookings.add(booking)
        }
        cursor.close()
        Log.d("DatabaseHelper", "Found ${bookings.size} upcoming bookings for user: $userEmail")
        return bookings
    }

    /**
     * Get all past bookings for a specific user
     */
    fun getPastBookings(userEmail: String): List<Booking> {
        val bookings = mutableListOf<Booking>()
        val db = readableDatabase

        val query = """
            SELECT * FROM $TABLE_BOOKINGS 
            WHERE $COLUMN_USER_EMAIL = ? AND $COLUMN_STATUS = 'past'
            ORDER BY $COLUMN_DATE DESC, $COLUMN_TIME DESC
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userEmail))

        while (cursor.moveToNext()) {
            val booking = Booking(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)),
                userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                service = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            )
            bookings.add(booking)
        }
        cursor.close()
        Log.d("DatabaseHelper", "Found ${bookings.size} past bookings for user: $userEmail")
        return bookings
    }

    /**
     * Get all bookings for a specific user (both upcoming and past)
     */
    fun getAllBookings(userEmail: String): List<Booking> {
        val bookings = mutableListOf<Booking>()
        val db = readableDatabase

        val query = """
            SELECT * FROM $TABLE_BOOKINGS 
            WHERE $COLUMN_USER_EMAIL = ?
            ORDER BY $COLUMN_DATE DESC, $COLUMN_TIME DESC
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userEmail))

        while (cursor.moveToNext()) {
            val booking = Booking(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)),
                userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                service = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            )
            bookings.add(booking)
        }
        cursor.close()
        Log.d("DatabaseHelper", "Found ${bookings.size} total bookings for user: $userEmail")
        return bookings
    }

    /**
     * Update booking status (upcoming/past)
     */
    fun updateBookingStatus(bookingId: Int, status: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STATUS, status)
        }

        val rowsAffected = db.update(
            TABLE_BOOKINGS,
            values,
            "$COLUMN_BOOKING_ID = ?",
            arrayOf(bookingId.toString())
        )

        val success = rowsAffected > 0
        Log.d("DatabaseHelper", "Update booking status: $success (ID: $bookingId, Status: $status)")
        return success
    }

    /**
     * Delete a booking by ID
     */
    fun deleteBooking(bookingId: Int): Boolean {
        val db = writableDatabase
        val rowsAffected = db.delete(
            TABLE_BOOKINGS,
            "$COLUMN_BOOKING_ID = ?",
            arrayOf(bookingId.toString())
        )

        val success = rowsAffected > 0
        Log.d("DatabaseHelper", "Delete booking: $success (ID: $bookingId)")
        return success
    }

    /**
     * Get a specific booking by ID
     */
    fun getBookingById(bookingId: Int): Booking? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_BOOKINGS WHERE $COLUMN_BOOKING_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(bookingId.toString()))

        return if (cursor.moveToFirst()) {
            val booking = Booking(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)),
                userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                service = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            )
            cursor.close()
            booking
        } else {
            cursor.close()
            null
        }
    }

    /**
     * Update an existing booking
     */
    fun updateBooking(bookingId: Int, service: String, date: String, time: String, notes: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SERVICE, service)
            put(COLUMN_DATE, date)
            put(COLUMN_TIME, time)
            put(COLUMN_NOTES, notes)
        }

        val rowsAffected = db.update(
            TABLE_BOOKINGS,
            values,
            "$COLUMN_BOOKING_ID = ?",
            arrayOf(bookingId.toString())
        )

        val success = rowsAffected > 0
        Log.d("DatabaseHelper", "Update booking: $success (ID: $bookingId)")
        return success
    }

    // ============ USER METHODS ============

    /**
     * Check if a user exists in the database
     */
    fun checkUserExists(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        Log.d("DatabaseHelper", "User $email exists: $exists")
        return exists
    }

    /**
     * Get user profile by email
     */
    fun getUserProfile(email: String): UserProfile? {
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_EMAIL, COLUMN_FULL_NAME, COLUMN_PHONE),
            selection,
            selectionArgs,
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val user = UserProfile(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME)),
                phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    /**
     * Create a new user
     */
    fun createUser(email: String, password: String, fullName: String?, phone: String?): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PHONE, phone)
        }

        val result = db.insert(TABLE_USERS, null, values)
        Log.d("DatabaseHelper", "Create user result: $result for email: $email")
        return result
    }

    /**
     * Verify user login credentials
     */
    fun verifyUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            selection,
            selectionArgs,
            null, null, null
        )

        val isValid = cursor.count > 0
        cursor.close()
        Log.d("DatabaseHelper", "Verify user $email: $isValid")
        return isValid
    }

    /**
     * Update user profile information
     */
    fun updateUserProfile(email: String, fullName: String, phone: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FULL_NAME, fullName)
            put(COLUMN_PHONE, phone)
        }

        val rowsAffected = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_EMAIL = ?",
            arrayOf(email)
        )

        val success = rowsAffected > 0
        Log.d("DatabaseHelper", "Update user profile: $success for email: $email")
        return success
    }

    /**
     * Update user password
     */
    fun updateUserPassword(email: String, currentPassword: String, newPassword: String): Boolean {
        val db = this.writableDatabase

        // First verify current password
        val selection = "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(email, currentPassword)

        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            selection,
            selectionArgs,
            null, null, null
        )

        val userExists = cursor.count > 0
        cursor.close()

        if (userExists) {
            // Update password
            val values = ContentValues()
            values.put(COLUMN_PASSWORD, newPassword)

            val rowsAffected = db.update(
                TABLE_USERS,
                values,
                "$COLUMN_EMAIL = ?",
                arrayOf(email)
            )

            val success = rowsAffected > 0
            Log.d("DatabaseHelper", "Update password: $success for email: $email")
            return success
        }

        Log.d("DatabaseHelper", "Update password failed: current password incorrect for email: $email")
        return false
    }

    /**
     * Get user by ID
     */
    fun getUserById(userId: Int): UserProfile? {
        val db = this.readableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(userId.toString())

        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_EMAIL, COLUMN_FULL_NAME, COLUMN_PHONE),
            selection,
            selectionArgs,
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val user = UserProfile(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME)),
                phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            )
            cursor.close()
            user
        } else {
            cursor.close()
            null
        }
    }

    /**
     * Get user full name by email
     */
    fun getUserFullName(email: String): String? {
        val db = this.readableDatabase
        val selection = "$COLUMN_EMAIL = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_FULL_NAME),
            selection,
            selectionArgs,
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME))
            cursor.close()
            fullName
        } else {
            cursor.close()
            null
        }
    }

    // ============ UTILITY METHODS ============

    /**
     * Test database connection
     */
    fun testDatabase(): String {
        return try {
            val db = readableDatabase
            if (db.isOpen) {
                "Database is open and accessible"
            } else {
                "Database is not open"
            }
        } catch (e: Exception) {
            "Database error: ${e.message}"
        }
    }

    /**
     * Get total number of bookings in database
     */
    fun getBookingsCount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_BOOKINGS", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        Log.d("DatabaseHelper", "Total bookings in database: $count")
        return count
    }

    /**
     * Get bookings count for a specific user
     */
    fun getUserBookingsCount(userEmail: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT COUNT(*) FROM $TABLE_BOOKINGS WHERE $COLUMN_USER_EMAIL = ?",
            arrayOf(userEmail)
        )
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        Log.d("DatabaseHelper", "Total bookings for user $userEmail: $count")
        return count
    }

    /**
     * Check if a user has any bookings
     */
    fun hasUserBookings(userEmail: String): Boolean {
        return getUserBookingsCount(userEmail) > 0
    }

    /**
     * Auto-update booking statuses based on current date
     * This should be called periodically to mark past bookings as "past"
     */
    fun updateBookingStatuses(currentDate: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STATUS, "past")
        }

        val rowsAffected = db.update(
            TABLE_BOOKINGS,
            values,
            "$COLUMN_DATE < ? AND $COLUMN_STATUS = 'upcoming'",
            arrayOf(currentDate)
        )

        if (rowsAffected > 0) {
            Log.d("DatabaseHelper", "Updated $rowsAffected bookings to 'past' status")
        }
    }

    /**
     * Get recent bookings (last 10 bookings)
     */
    fun getRecentBookings(userEmail: String, limit: Int = 10): List<Booking> {
        val bookings = mutableListOf<Booking>()
        val db = readableDatabase

        val query = """
            SELECT * FROM $TABLE_BOOKINGS 
            WHERE $COLUMN_USER_EMAIL = ?
            ORDER BY $COLUMN_DATE DESC, $COLUMN_TIME DESC
            LIMIT $limit
        """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(userEmail))

        while (cursor.moveToNext()) {
            val booking = Booking(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)),
                userEmail = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                service = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERVICE)),
                date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            )
            bookings.add(booking)
        }
        cursor.close()
        return bookings
    }
}

