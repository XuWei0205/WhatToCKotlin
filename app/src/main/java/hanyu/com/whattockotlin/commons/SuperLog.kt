package hanyu.com.whattockotlin.commons

import android.text.TextUtils
import android.util.Log

/**
 * Created by HanYu on 2018/8/27.
 */
object SuperLog {
    private const val LOG_FORMAT = "%1\$s\n%2\$s"
    private const val LOG_TAG = "WTCKotlin"

    fun LogD(message: String) {
        if (TextUtils.isEmpty(message)) {
            return
        }
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            Log.println(Log.DEBUG, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message))
        }
    }

    fun LogD(vararg args: Any) {

        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            val message = StringBuffer()
            if (args.size > 0) {
                for (j in args.indices) {
                    message.append(args[j].toString()).append(" ")
                }
            }
            Log.println(Log.DEBUG, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message.toString()))
        }
    }

    fun LogI(message: String) {
        if (TextUtils.isEmpty(message)) {
            return
        }
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            Log.println(Log.INFO, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message))
        }
    }

    fun LogI(vararg args: Any) {
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            val message = StringBuilder()
            if (args.size > 0) {
                for (j in args.indices) {
                    message.append(args[j].toString()).append(" ")
                }
            }
            Log.println(Log.INFO, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message.toString()))
        }
    }

    fun LogW(message: String) {
        if (TextUtils.isEmpty(message)) {
            return
        }
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            Log.println(Log.WARN, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message))
        }
    }

    fun LogW(vararg args: Any) {
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            val message = StringBuilder()
            if (args.size > 0) {
                for (j in args.indices) {
                    message.append(args[j].toString()).append(" ")
                }
            }
            Log.println(Log.WARN, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message.toString()))
        }
    }

    fun LogE(message: String) {
        if (TextUtils.isEmpty(message)) {
            return
        }
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            Log.println(Log.ERROR, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message))
        }
    }

    fun LogE(vararg args: Any) {
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            val message = StringBuilder()
            if (args.size > 0) {
                for (j in args.indices) {
                    message.append(args[j].toString()).append(" ")
                }
            }
            Log.println(Log.ERROR, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, message.toString()))
        }
    }

    fun LogE(ex: Throwable?) {
        if (ex == null) {
            return
        }
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            val logMessage = ex.message
            val logBody = Log.getStackTraceString(ex)
            val log = String.format(LOG_FORMAT, logMessage, logBody)
            Log.println(Log.ERROR, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, log))
        }
    }

    fun LogE(ex: Throwable?, vararg args: Any) {
        if (Debug.DEVELOP_MODE) {
            val stack = Throwable().stackTrace
            val i = 1
            val ste = stack[i]
            val message = StringBuilder()
            if (args.size > 0) {
                for (j in args.indices) {
                    message.append(args[j].toString()).append(" ")
                }
            }
            val log: String
            if (ex == null) {
                log = message.toString()
            } else {
                val logMessage = message.toString()
                val logBody = Log.getStackTraceString(ex)
                log = String.format(LOG_FORMAT, logMessage, logBody)
            }
            Log.println(Log.ERROR, LOG_TAG, String.format("[%s][%s][%s]%s", ste.fileName, ste.methodName, ste.lineNumber, log))
        }
    }



}