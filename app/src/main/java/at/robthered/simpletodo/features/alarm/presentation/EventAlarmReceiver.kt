package at.robthered.simpletodo.features.alarm.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import at.robthered.simpletodo.features.alarm.data.AlarmSchedulerImpl
import at.robthered.simpletodo.features.alarm.domain.ShowNotificationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventAlarmReceiver : BroadcastReceiver(), KoinComponent {
    private val showNotificationUseCase: ShowNotificationUseCase  by inject<ShowNotificationUseCase>()
    private val coroutineScope: CoroutineScope by inject<CoroutineScope>()
    
    
    override fun onReceive(context: Context?, intent: Intent?) {
        val eventId = intent?.getLongExtra(AlarmSchedulerImpl.EVENT_ID, -1) ?: return
        if(eventId == -1L) return


        val pendingResult = goAsync()
        coroutineScope.launch(Dispatchers.IO) {
            try {
                showNotificationUseCase(eventId = eventId)
            } finally {
                pendingResult.finish()
            }
        }
    }
}