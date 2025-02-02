package io.gromif.astracrypt.files.model.action

import android.net.Uri
import io.gromif.astracrypt.files.domain.model.Item

internal interface Actions {

    fun backStackClick(index: Int?)
    fun click(item: Item)
    fun longClick(id: Long)

    fun setMoveMode()
    fun closeContextualToolbar()

    fun open(id: Long)
    fun createFolder(name: String)
    fun import(uriList: Array<Uri>, saveSource: Boolean)
    fun scan()
    fun move()
    fun star(state: Boolean, idList: List<Long>)
    fun rename(id: Long, name: String)
    fun delete(idList: List<Long>)


    companion object {
        internal val Default = object : Actions {
            override fun backStackClick(index: Int?) {}
            override fun click(item: Item) {}
            override fun longClick(id: Long) {}
            override fun setMoveMode() {}
            override fun closeContextualToolbar() {}
            override fun open(id: Long) {}
            override fun createFolder(name: String) {}
            override fun import(uriList: Array<Uri>, saveSource: Boolean) {}
            override fun scan() {}
            override fun move() {}
            override fun star(state: Boolean, idList: List<Long>) {}
            override fun rename(id: Long, name: String) {}
            override fun delete(idList: List<Long>) {}
        }
    }

}