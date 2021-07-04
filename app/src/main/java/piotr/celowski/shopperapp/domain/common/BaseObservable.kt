package piotr.celowski.shopperapp.domain.common

import java.util.*
import java.util.concurrent.ConcurrentHashMap

open class BaseObservable<LISTENER_CLASS> {

    private val mListeners: MutableSet<LISTENER_CLASS> = Collections.newSetFromMap(
        ConcurrentHashMap<LISTENER_CLASS, Boolean>(1)
    )


    fun registerListener(listener: LISTENER_CLASS) {
        mListeners.add(listener)
    }

    fun unregisterListener(listener: LISTENER_CLASS) {
        mListeners.remove(listener)
    }

    protected fun getListeners(): Set<LISTENER_CLASS> {
        return Collections.unmodifiableSet(mListeners)
    }
}