package at.robthered.simpletodo.features.dueDialog.di

import at.robthered.simpletodo.features.core.domain.eventBus.events.DueEventBus
import at.robthered.simpletodo.features.core.domain.state_manager.dueEvent.DueEventStateManager
import at.robthered.simpletodo.features.dueDialog.presentation.DueDialogViewModel
import at.robthered.simpletodo.features.shared.date_time.domain.AppCalendarUtils
import at.robthered.simpletodo.features.shared.date_time.domain.AppDateTimeRepository
import at.robthered.simpletodo.features.shared.locale.domain.AppLocaleProvider
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dueDialogModule = module {

    viewModel {
        DueDialogViewModel(
            appCalendarUtils = get<AppCalendarUtils>(),
            appDateTimeRepository = get<AppDateTimeRepository>(),
            appLocaleProvider = get<AppLocaleProvider>(),
            dueEventBus = get<DueEventBus>(),
            dueEventStateManager = get<DueEventStateManager>(),
        )
    }


}