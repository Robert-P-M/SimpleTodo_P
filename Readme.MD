# Todoist Clone - Android App

This project is a Todoist-like task management application for Android.

## Features
- Task and section management (create, update, delete)
- Persistent with Room
- Nested tasks and sections
- Priority for tasks
- Event -> Due date and time setting with custom calendar and time picker
- Trying Clean Architecture and SOLID principles implementation 

## Tech Stack
- Kotlin
- Jetpack Compose for UI
- Room for local database
- Coroutines and Flow for asynchronous programming
- Koin for dependency injection
- Custom Composables
- Navigation component with dialog routes

## Architecture and Design Patterns
- Clean Architecture
- SOLID principles
- Repository pattern
- Use case pattern
- State management using StateFlow and custom state managers
- Custom composable state holders
- EventBus for data between screens
 
## Current Status
- Home Dialog
  - ListView of ScreenItems
    - Section as stickyHeader
    - Task as item
      - TaskCard
        - Swipe to archive / delete
        - Task Menu
          - Navigate to task details screen
          - Navigate to update task screen
          - add sub-task
          - change priority
          - delete task
            - open delete dialog
  - HomeFloatingActionButtonMenu
    - Add Section
    - Add Task
  - Top App Bar
    - ViewType - Toggle (functionality not implemented)
    - New Section
    - Locale - Toggle (not working as expected)
- Add Task Dialog
  - Title / Description
  - Reusable TaskOptionsRow
    - Links (own Route - with eventBus)
    - Priority
    - Date (own Route - with eventBus)
    - Notification Toggle
- Add Section Dialog
  - Title of section
- Task Details Dialog
  - Menu
    - Navigate to activity log
    - Navigate to update task
    - delete task
      - open delete dialog
  - navigate to update task on title/description click
  - Reused TaskOptionsRow
    - handle update priority on the fly
    - handle update task links on the fly
    - handle update date on the fly
  - List of Sub-tasks
- Update priority dialog
  - handle updating priority on the fly
- DueDialog
  - Display current event on top with remove action
  - Display upcoming days
  - Scrollable Calendar with paged months
  - Toggle Buttons
    - Current picked date
    - Time Picker
      - Custom Time Wheel
    - Notification toggle
    - DurationPicker
      - Custom Wheel
  - Working with EventBus
    - send current event to dialog
    - send picked event back
- ShareUrlReceiverActivity
  - Catch "Intent.ACTION_SEND"
    - Navigate to SharedUrlProcessorDialog if sharedUrl is in intent
  - Reuse Dialogs/Routes
    - AddTaskDialog
    - DueDialog
    - SharedUrlProcessorDialog
    - PriorityPickerDialog
- Priority Picker Dialog screen
- Alarm/Notification Feature - not tested
## In progress
- Create tests

## BUGS
- switching appLocale not working and crashes

## Upcoming Features?
- Refactoring Entities/App to use AppDateTime for Entities (overhead) or remove it
- safe "ExpandedItems" via Entity in room
  - Enum for "Task"/"Section"/"Project"
  - @androidx.annotation.Keep
- Create Projects
  - Enable picking project:
    - add Section/Task Dialog 
    - update Section/TaskDialog
- update "EventEntity"
  - use EventStatus enum
    - Active, Disabled, ....
- Task Details Dialog
  - Display parentTask / section / project of "task"
- Priority Dialog Route
  - priorityEventBus
- On New Task
  - Enable picking "Parent Task / Section / Project"
- Toggle between Grid View / List View
- Bottom navigation
  - Home Screen
  - Today Screen
  - Upcoming Screen
- Create Today and Upcoming Page
- dragging items between sections / non section / reorder
- Alarm/Notification Feature
  - permission handling
  - set alarm if "isNotificationEnabled"
  - Broadcast receiver for system reboot & reset alarms
  - reset / clear alarms
- Performance optimization

## Contributing
This is a personal portfolio project and is not open for contributions at this time.

## License
MIT License
Copyright (c) 2025 @Robert-P-M

Permission is hereby granted, free of charge, to any person obtaining a copy of this software...