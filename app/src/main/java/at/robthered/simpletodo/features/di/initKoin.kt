package at.robthered.simpletodo.features.di

import at.robthered.simpletodo.features.addSectionDialog.di.addSectionDialogModule
import at.robthered.simpletodo.features.addTaskDialog.di.addTaskDialogModule
import at.robthered.simpletodo.features.alarm.di.alarmManagerModule
import at.robthered.simpletodo.features.core.di.coreModule
import at.robthered.simpletodo.features.dataSource.di.dataSourceModule
import at.robthered.simpletodo.features.dueDialog.di.dueDialogModule
import at.robthered.simpletodo.features.homeScreen.di.homeModule
import at.robthered.simpletodo.features.network.di.networkModule
import at.robthered.simpletodo.features.priorityDialogScreen.di.priorityPickerDialogModule
import at.robthered.simpletodo.features.shared.di.sharedModule
import at.robthered.simpletodo.features.sharedUrlProcessorDialogScreen.di.sharedUrlProcessorDialogModule
import at.robthered.simpletodo.features.taskActivityProtocolDialog.di.taskActivityDialogModule
import at.robthered.simpletodo.features.taskDetailsDialog.di.taskDetailsDialogModule
import at.robthered.simpletodo.features.updatePriorityDialog.di.updatePriorityDialogModule
import at.robthered.simpletodo.features.updateSectionDialog.di.updateSectionDialogModule
import at.robthered.simpletodo.features.updateTaskDialog.di.updateTaskDialogModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        koinApplication(config)
        androidLogger(Level.DEBUG)
        config?.invoke(this)

        modules(
            coreModule,
            sharedModule,
            dataSourceModule,
            homeModule,
            taskDetailsDialogModule,
            addTaskDialogModule,
            updateTaskDialogModule,
            addSectionDialogModule,
            updateSectionDialogModule,
            updatePriorityDialogModule,
            dueDialogModule,
            taskActivityDialogModule,
            networkModule,
            sharedUrlProcessorDialogModule,
            priorityPickerDialogModule,
            alarmManagerModule,
        )
    }
}