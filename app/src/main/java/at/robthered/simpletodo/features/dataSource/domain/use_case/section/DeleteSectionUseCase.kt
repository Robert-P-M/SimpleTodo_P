package at.robthered.simpletodo.features.dataSource.domain.use_case.section

interface DeleteSectionUseCase {
    suspend operator fun invoke(sectionId: Long?)
}