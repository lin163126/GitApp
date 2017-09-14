package com.example.mylibrary;

import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     time  : 2016/9/21
 *     desc  : 日志相关工具类
 * </pre>
 */
public class LogUtils {

    private static boolean logSwitch = true;
    private static int logLevel = Log.VERBOSE;
    private static String TAG = "bill99_log";

    private static boolean DEBUG_V;
    private static boolean DEBUG_D;
    private static boolean DEBUG_I;
    private static boolean DEBUG_W;
    private static boolean DEBUG_E;

    /**
     * 初始化函数
     * <p>与{@link #getBuilder()}两者选其一</p>
     *
     * @param logSwitch      日志总开关
     * @param logLevel      日志级别
     * @param tag            标签
     */
    public static void init(boolean logSwitch, int logLevel, String tag) {
        LogUtils.logSwitch = logSwitch;
        LogUtils.logLevel = logLevel;
        LogUtils.TAG = tag;
        initLogLevel();
    }

    /**
     * 初始化日志输出级别
     */
    private static void initLogLevel() {
        DEBUG_V = logLevel <= Log.VERBOSE || Log.isLoggable(TAG, Log.VERBOSE);
        DEBUG_D = logLevel <= Log.DEBUG || Log.isLoggable(TAG, Log.DEBUG);
        DEBUG_I = logLevel <= Log.INFO || Log.isLoggable(TAG, Log.INFO);
        DEBUG_W = logLevel <= Log.WARN || Log.isLoggable(TAG, Log.WARN);
        DEBUG_E = logLevel <= Log.ERROR || Log.isLoggable(TAG, Log.ERROR);
    }

    /**
     * 获取LogUtils建造者
     * <p>与{@link #init(boolean, int, String)}两者选其一</p>
     *
     * @return Builder对象
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * VERBOSE日志
     *
     * @param format 日志信息
     * @param args   日志信息参数
     */
    public static void v(String format, Object... args) {
        log(TAG, buildMessage(format, args), null, 'v');
    }

    /**
     * INFO日志
     *
     * @param format 日志信息
     * @param args   日志信息参数
     */
    public static void i(String format, Object... args) {
        log(TAG, buildMessage(format, args), null, 'i');
    }

    /**
     * DEBUG日志
     *
     * @param format 日志信息
     * @param args   日志信息参数
     */
    public static void d(String format, Object... args) {
        log(TAG, buildMessage(format, args), null, 'd');
    }

    /**
     * WARN日志
     *
     * @param format 日志信息
     * @param args   日志信息参数
     */
    public static void w(String format, Object... args) {
        log(TAG, buildMessage(format, args), null, 'w');
    }

    /**
     * ERROR日志
     *
     * @param format 日志信息
     * @param args   日志信息参数
     */
    public static void e(String format, Object... args) {
        log(TAG, buildMessage(format, args), null, 'e');
    }

    /**
     * ERROR日志
     *
     * @param tr 错误堆栈
     */
    public static void e(Throwable tr) {
        log(TAG, buildMessage("Catch Exception: "), tr, 'e');
    }

    /**
     * ERROR日志
     *
     * @param tr     错误堆栈
     * @param format 日志信息
     * @param args   日志信息参数
     */
    public static void e(Throwable tr, String format, Object... args) {
        log(TAG, buildMessage(format, args), tr, 'e');
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    public static String buildMessage(String format, Object... args) {
        String msg = format;
        try {
            msg = (args == null || args.length == 0) ? format : String.format(Locale.US, format, args);
        } catch (Exception e) {

        }
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtils.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    public static void log(String msg, Throwable tr, char type) {
        log(TAG, msg, tr, type);
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag  标签
     * @param msg  消息
     * @param tr   异常
     * @param type 日志类型
     */
    public static void log(String tag, String msg, Throwable tr, char type) {
        if (logSwitch) {
            if ('e' == type && DEBUG_E) {
                Log.e(tag, msg, tr);
            } else if ('w' == type && DEBUG_W) {
                Log.w(tag, msg, tr);
            } else if ('d' == type && DEBUG_D) {
                Log.d(tag, msg, tr);
            } else if ('i' == type && DEBUG_I) {
                Log.i(tag, msg, tr);
            } else if ('v' == type && DEBUG_V) {
                Log.v(tag, msg, tr);
            }
        }
    }

    public static class Builder {
        private boolean logSwitch = true;
        private int logLevel = Log.VERBOSE;
        private String tag = "TAG";

        public Builder setLogSwitch(boolean logSwitch) {
            this.logSwitch = logSwitch;
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setLogLevel(int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public void create() {
            LogUtils.init(logSwitch, logLevel, tag);
        }
    }

    /**
     * A simple event log with records containing a name, thread ID, and timestamp.
     */
    static class MarkerLog {

        /**
         * Minimum duration from first marker to last in an marker log to warrant logging.
         */
        private static final long MIN_DURATION_FOR_LOGGING_MS = 0;
        private final List<Marker> mMarkers = new ArrayList<Marker>();
        private boolean mFinished = false;

        /**
         * Adds a marker to this log with the specified name.
         */
        public synchronized void add(String name, long threadId) {
            if (mFinished) {
                throw new IllegalStateException("Marker added to finished log");
            }

            mMarkers.add(new Marker(name, threadId, SystemClock.elapsedRealtime()));
        }

        /**
         * Closes the log, dumping it to logcat if the time difference between
         * the first and last markers is greater than {@link #MIN_DURATION_FOR_LOGGING_MS}.
         *
         * @param header Header string to print above the marker log.
         */
        public synchronized void finish(String header) {
            mFinished = true;

            long duration = getTotalDuration();
            if (duration <= MIN_DURATION_FOR_LOGGING_MS) {
                return;
            }

            long prevTime = mMarkers.get(0).time;
            d("(%-4d ms) %s", duration, header);
            for (Marker marker : mMarkers) {
                long thisTime = marker.time;
                d("(+%-4d) [%2d] %s", (thisTime - prevTime), marker.thread, marker.name);
                prevTime = thisTime;
            }
        }

        @Override
        protected void finalize() throws Throwable {
            // Catch requests that have been collected (and hence end-of-lifed)
            // but had no debugging output printed for them.
            if (!mFinished) {
                finish("Request on the loose");
                e("Marker log finalized without finish() - uncaught exit point for request");
            }
        }

        /**
         * Returns the time difference between the first and last events in this log.
         */
        private long getTotalDuration() {
            if (mMarkers.size() == 0) {
                return 0;
            }

            long first = mMarkers.get(0).time;
            long last = mMarkers.get(mMarkers.size() - 1).time;
            return last - first;
        }

        private static class Marker {
            public final String name;
            public final long thread;
            public final long time;

            public Marker(String name, long thread, long time) {
                this.name = name;
                this.thread = thread;
                this.time = time;
            }
        }
    }
}
