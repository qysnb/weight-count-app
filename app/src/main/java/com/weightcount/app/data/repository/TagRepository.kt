package com.weightcount.app.data.repository

import com.weightcount.app.data.dao.TagDao
import com.weightcount.app.data.entity.Tag
import kotlinx.coroutines.flow.Flow

class TagRepository(private val tagDao: TagDao) {

    fun getAllTags(): Flow<List<Tag>> = tagDao.getAllTags()

    suspend fun getTagById(id: Long): Tag? = tagDao.getTagById(id)

    suspend fun addTag(name: String): Long {
        val maxOrder = tagDao.getMaxSortOrder() ?: -1
        return tagDao.insertTag(Tag(name = name, sortOrder = maxOrder + 1))
    }

    suspend fun updateTag(tag: Tag) {
        tagDao.updateTag(tag)
    }

    suspend fun updateTag(id: Long, newName: String) {
        val tag = tagDao.getTagById(id) ?: return
        tagDao.updateTag(tag.copy(name = newName))
    }

    suspend fun deleteTag(id: Long) {
        tagDao.deleteTagById(id)
    }
}
