package com.example.anytimecarwash.utils

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NotificationUtils {
    private const val TAG = "NotificationUtils"

    /**
     * Subscribe to topics for receiving notifications
     */
    fun subscribeToTopics(userId: String?) {
        userId?.let { id ->
            // Subscribe to user-specific topic
            FirebaseMessaging.getInstance().subscribeToTopic("user_$id")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Subscribed to user topic: user_$id")
                    } else {
                        Log.w(TAG, "Subscription to user topic failed")
                    }
                }

            // Subscribe to general topics
            FirebaseMessaging.getInstance().subscribeToTopic("bookings")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Subscribed to bookings topic")
                    } else {
                        Log.w(TAG, "Subscription to bookings topic failed")
                    }
                }

            FirebaseMessaging.getInstance().subscribeToTopic("promotions")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Subscribed to promotions topic")
                    } else {
                        Log.w(TAG, "Subscription to promotions topic failed")
                    }
                }
        }
    }

    /**
     * Unsubscribe from topics
     */
    fun unsubscribeFromTopics(userId: String?) {
        userId?.let { id ->
            FirebaseMessaging.getInstance().unsubscribeFromTopic("user_$id")
            FirebaseMessaging.getInstance().unsubscribeFromTopic("bookings")
            FirebaseMessaging.getInstance().unsubscribeFromTopic("promotions")
        }
    }

    /**
     * Get FCM token
     */
    fun getFCMToken(onComplete: (String?) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                onComplete(null)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d(TAG, "FCM Token: $token")
            onComplete(token)
        })
    }

    /**
     * Initialize notifications for the app
     */
    fun initializeNotifications(context: Context, userId: String?) {
        // Get and store FCM token
        getFCMToken { token ->
            token?.let {
                // Store token in SharedPreferences
                val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                sharedPref.edit().putString("fcm_token", token).apply()

                // Subscribe to topics
                subscribeToTopics(userId)
            }
        }
    }
}