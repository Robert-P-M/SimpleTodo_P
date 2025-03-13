package at.robthered.simpletodo.features.dataSource.data.repository.sectionWithTasks

import at.robthered.simpletodo.features.dataSource.data.local.dao.sectionWithTasks.SectionWithTasksWithDetailsDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.taskWithDetails.TaskWithDetailsDao
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toArchivedModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toCompletedModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toEventModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toLinkModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toPriorityModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toSectionModel
import at.robthered.simpletodo.features.dataSource.data.local.mapper.toTaskModel
import at.robthered.simpletodo.features.dataSource.data.local.relation.SectionWithTasksWithDetailsRelation
import at.robthered.simpletodo.features.dataSource.data.repository.utils.buildNestedTasksWithDetailsFlow
import at.robthered.simpletodo.features.dataSource.domain.local.relation.SectionWithTasksWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel
import at.robthered.simpletodo.features.dataSource.domain.repository.sectionWithTasks.LocalSectionWithTasksWithDetailRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class LocalSectionWithTasksWithDetailRepositoryImpl(
    private val sectionWithTasksWithDetailsDao: SectionWithTasksWithDetailsDao,
    private val taskWithDetailsDao: TaskWithDetailsDao,
): LocalSectionWithTasksWithDetailRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSectionsWithTasksWithDetails(
        depth: Int,
    ): Flow<List<SectionWithTasksWithDetailsAndChildrenModel>> {
        return sectionWithTasksWithDetailsDao.get()
            .flatMapLatest { sections: List<SectionWithTasksWithDetailsRelation> ->
                flow {
                    val sectionsWithChildren = sections.map { section ->
                        val taskWithChildren = section.tasks.map { task ->
                            val childrenFlow = buildNestedTasksWithDetailsFlow(
                                taskId = task.task.taskId,
                                depth = depth - 1,
                                getTasksOfParentWithDetails = { parentId ->
                                    taskWithDetailsDao.getTasksOfParentWithDetails(parentTaskId = parentId)
                                        .map { entities ->
                                            entities.map { it.toModel() }
                                        }
                                }
                            )
                            val children = childrenFlow.first()
                            TaskWithDetailsAndChildrenModel(
                                task = task.task.toTaskModel(),
                                completed = task.completed.map { entity -> entity.toCompletedModel() },
                                archived = task.archived.map { entity -> entity.toArchivedModel() },
                                priority = task.priority.map { entity -> entity.toPriorityModel() },
                                event = task.event?.toEventModel(),
                                links = task.links.map { entity -> entity.toLinkModel() },
                                tasks = children
                            )
                        }
                        SectionWithTasksWithDetailsAndChildrenModel(
                            section = section.section.toSectionModel(),
                            tasks = taskWithChildren
                        )
                    }
                    emit(sectionsWithChildren)
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSectionWithTasksWithDetails(
        sectionId: Long?,
        depth: Int,
    ): Flow<SectionWithTasksWithDetailsAndChildrenModel?> {
        return sectionWithTasksWithDetailsDao.get(sectionId)
            .flatMapLatest { section: SectionWithTasksWithDetailsRelation? ->
                section?.let {
                    flow {
                        val taskWithChildren = section.tasks.map { task ->
                            val childrenFlow = buildNestedTasksWithDetailsFlow(
                                taskId = task.task.taskId,
                                depth = depth - 1,
                                getTasksOfParentWithDetails = { parentId ->
                                    taskWithDetailsDao.getTasksOfParentWithDetails(parentTaskId = parentId)
                                        .map { entities ->
                                            entities.map { it.toModel() }
                                        }
                                }
                            )
                            val children = childrenFlow.first()
                            TaskWithDetailsAndChildrenModel(
                                task = task.task.toTaskModel(),
                                completed = task.completed.map { entity -> entity.toCompletedModel() },
                                archived = task.archived.map { entity -> entity.toArchivedModel() },
                                priority = task.priority.map { entity -> entity.toPriorityModel() },
                                event = task.event?.toEventModel(),
                                links = task.links.map { entity -> entity.toLinkModel() },
                                tasks = children
                            )
                        }
                        emit(
                            SectionWithTasksWithDetailsAndChildrenModel(
                                section = section.section.toSectionModel(),
                                tasks = taskWithChildren
                            )
                        )
                    }
                } ?: flowOf(null)

            }
    }
}