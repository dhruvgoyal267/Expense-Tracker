package `in`.expenses.expensetracker.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import `in`.expenses.expensetracker.R
import `in`.expenses.expensetracker.di.DefaultScope
import `in`.expenses.expensetracker.ui.MainActivity
import `in`.expenses.expensetracker.usecases.CheckForTransactionSmsUseCase
import `in`.expenses.expensetracker.usecases.ExtractAmountFromSmsUseCase
import `in`.expenses.expensetracker.utils.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsBroadCastReceiver : BroadcastReceiver() {

    @Inject
    @DefaultScope
    lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var checkForTransactionSmsUseCase: CheckForTransactionSmsUseCase

    @Inject
    lateinit var extractAmountFromSmsUseCase: ExtractAmountFromSmsUseCase

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            coroutineScope.launch {
                for (message in messages) {
                    val body = message.messageBody
                    if (checkForTransactionSmsUseCase(body)) {
                        extractAmountFromSmsUseCase(body)?.let {
                            showNotification(context, it)
                        }
                    }
                }
            }
        }
    }

    private fun showNotification(context: Context, amount: String) {
        val channelId = "sms_channel"
        val notificationId = 1

        // Create NotificationChannel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "SMS Notifications"
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            manager?.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(AppConstants.AMOUNT, amount)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,  // Request code
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        // Build and display the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_app_logo)
            .setContentTitle("New transaction found")
            .setContentText("Add transaction worth Rs. $amount")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        manager?.notify(notificationId, notification)

    }
}