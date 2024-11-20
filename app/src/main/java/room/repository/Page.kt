package room.repository

import io.ktor.websocket.parseWebSocketExtensions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import room.dao.PageDao
import database.Page

class Page(private val pageDao: PageDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun getAll() : Flow<List<Page>> {
        return pageDao.getAll()
    }

    fun getAllbyBook(book: Int) : Flow<List<Page>> {
        return pageDao.loadAllByBook(book)
    }

    fun addPage(page: Page) {
        coroutineScope.launch(Dispatchers.IO) {
            pageDao.insert(page)
        }
    }
    fun getPage(id: Int): Flow<Page>{
        return pageDao.getPage(id)
    }

    fun deletePage(page: Page) {
        coroutineScope.launch(Dispatchers.IO) {
            pageDao.delete(page)
        }
    }
}