package network.xyo.base

import android.util.Log

/* We have everyone of these functions returning 'this' to allow for chaining */

open class XYLogging(sourceAny: Any) {
    private val source = classNameFromObject(sourceAny)

    fun error(message: String, reThrow: Boolean): XYLogging {
        Log.e(source, message)
        val stackTrace = Thread.currentThread().stackTrace
        if (!stackTrace.isNullOrEmpty()) {
            Log.e(source, stackTrace.contentDeepToString().replace(", ", ",\r\n"))
        }
        if (reThrow) {
            throw RuntimeException()
        }
        return this
    }

    //Any Handled Exceptions
    fun error(ex: Throwable): XYLogging {
        error(ex, false)
        return this
    }

    fun error(ex: Throwable, reThrow: Boolean): XYLogging {
        Log.e(source, classNameFromObject(ex))
        val stackTrace = ex.stackTrace
        if (!stackTrace.isNullOrEmpty()) {
            Log.e(source, stackTrace.contentDeepToString().replace(", ", ",\r\n"))
        }

        if (hasDebugger) {
            if (reThrow) {
                throw RuntimeException(ex)
            }
        }
        return this
    }

    fun error(message: String): XYLogging {
        Log.e(source, "$message:${currentThreadName}")
        return this
    }

    //Normal information used for debugging.  Items should be less noisy than Extreme items
    fun info(function: String, message: String): XYLogging {
        Log.i(source, "$source:$function:$message [${currentThreadName}]")
        return this
    }

    fun info(message: String): XYLogging {
        Log.i(source, "$message [${currentThreadName}]")
        return this
    }

    //Actions are events that are generated by the user, like pushing a button
    fun action(action: String): XYLogging {
        Log.i(source, action)
        return this
    }

    //Status are Large Scale Events, Such As Startup, or Shutdown,
    //that may or may not be a result of a user action
    fun status(status: String): XYLogging {
        Log.i(source, "App Status: $status")
        return this
    }
}