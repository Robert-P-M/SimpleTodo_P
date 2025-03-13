package at.robthered.simpletodo.features.core.utils.ext

fun Set<Long>.toggle(id: Long): Set<Long> = if (contains(id)) minus(id) else plus(id)