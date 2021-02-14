package androidx.lifecycle

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class SingleEvent<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleEvent<*>

        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        return content?.hashCode() ?: 0
    }
}

fun <T: Any> T.toEvent() = SingleEvent(this)

fun <T> LiveData<SingleEvent<T>?>.observeSingleEvents(owner: LifecycleOwner, observer: (event: T) -> Unit) {
    this.observe(owner, Observer { it?.getContentIfNotHandled()?.let(observer) })
}
