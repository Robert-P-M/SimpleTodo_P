package at.robthered.simpletodo.features.homeScreen.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import at.robthered.simpletodo.features.core.domain.Resource
import at.robthered.simpletodo.features.core.domain.state.expandedItemsState.ExpandedItemsState
import at.robthered.simpletodo.features.core.presentation.components.lazyColumn.lazyColumnItemResourceError
import at.robthered.simpletodo.features.core.presentation.components.lazyColumn.lazyColumnItemResourceLoading
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.homeScreen.domain.model.ScreenItemsModel
import at.robthered.simpletodo.features.homeScreen.presentation.state.HomeStateHandler

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.homeItems(
    homeItemsResource: Resource<List<ScreenItemsModel>>,
    expandedTasks: ExpandedItemsState,
    expandedSections: ExpandedItemsState,
    stateHandler: HomeStateHandler
) {
    when (homeItemsResource) {
        is Resource.Stale -> Unit
        is Resource.Error -> lazyColumnItemResourceError(error = homeItemsResource.error)
        is Resource.Loading -> {
            // TODO: show loading info?
            lazyColumnItemResourceLoading()
        }
        is Resource.Success -> {


            homeItemsResource.data.mapIndexed { index: Int, screenItemsModel: ScreenItemsModel ->
                if(screenItemsModel.section != null) {
                    stickyHeader {
                        SectionItemHeader(
                            modifier = Modifier.animateItem(
                                fadeInSpec = tween(
                                    delayMillis = 40 * index,
                                    easing = EaseInOut

                                ),
                                fadeOutSpec = tween(
                                    delayMillis = 40 * index,
                                    easing = EaseInOut
                                ),
                                placementSpec = tween(
                                    delayMillis = 40 * index,
                                    easing = EaseInOut
                                ),
                            ),
                            item = screenItemsModel.section,
                            taskCount = screenItemsModel.tasks.size,
                            stateHandler = stateHandler,
                            isChildrenVisible = { sectionId: Long? ->
                                expandedSections.expandedItems.contains(
                                    sectionId
                                )
                            },
                        )
                    }
                }

                itemsIndexed(
                    items = screenItemsModel.tasks,
                    key = { _: Int, taskWithDetailsAndChildrenModel: TaskWithDetailsAndChildrenModel -> "task-${taskWithDetailsAndChildrenModel.task.taskId}" }
                ) { taskIndex: Int, taskWithDetailsAndChildrenModel ->

                    AnimatedVisibility(
                        visible = if(screenItemsModel.section != null) expandedSections.expandedItems.contains(screenItemsModel.section.sectionId) else true,
                        enter = fadeIn(
                            animationSpec = tween(
                                delayMillis = 40 * taskIndex,
                                easing = EaseInOut
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                delayMillis = 40 * taskIndex,
                                easing = EaseInOut
                            )
                        )
                    ) {

                        TaskCard(
                            modifier = Modifier,
                            stateHandler = stateHandler,
                            depth = 1,
                            item = taskWithDetailsAndChildrenModel,
                            isTaskExpanded = { taskId: Long? ->
                                expandedTasks.expandedItems.contains(taskId)
                            },
                        )
                    }
                }
            }
        }
    }
}