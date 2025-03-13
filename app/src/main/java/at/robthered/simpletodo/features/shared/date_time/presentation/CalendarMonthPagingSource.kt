package at.robthered.simpletodo.features.shared.date_time.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import at.robthered.simpletodo.features.shared.date_time.domain.AppCalendarUtils
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppCalendarMonth
import at.robthered.simpletodo.features.shared.date_time.domain.AppDateTimeRepository
import at.robthered.simpletodo.features.shared.date_time.domain.model.AppDate

class CalendarMonthPagingSource(
    private val appCalendarUtils: AppCalendarUtils,
    private val appDateTimeRepository: AppDateTimeRepository,
    private val initialDate: AppDate,
): PagingSource<Int, AppCalendarMonth>() {
    override fun getRefreshKey(state: PagingState<Int, AppCalendarMonth>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AppCalendarMonth> {
        val position = params.key ?: 0
        val currentDate = appDateTimeRepository.plusMonths(initialDate, position * 3)
        val items = (0..2).map { monthOffset ->
            val date = appDateTimeRepository.plusMonths(currentDate, monthOffset)
            appCalendarUtils.getCalendarMonth(
                currentAppDate = date)
        }
        return LoadResult.Page(
            data = items,
            prevKey = if (position > 0) position - 1 else null,
            nextKey = position + 1
        )
    }
}