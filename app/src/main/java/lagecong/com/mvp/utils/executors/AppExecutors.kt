package lagecong.com.mvp.utils.executors

import android.os.Handler
import android.os.Looper
import android.support.annotation.VisibleForTesting

import java.util.concurrent.Executor

/**
 * Created by Anwar on 6/28/2018.
 */
class AppExecutors @VisibleForTesting
private constructor(
    private val mDiskIO: Executor, /* Executor networkIO,*/
    //    private final Executor mNetworkIO;
    private val mMainThread: Executor
)//        mNetworkIO = networkIO;
{
    constructor() : this(DiskIOThreadExecutor(), /*Executors.newFixedThreadPool(THREAD_COUNT),*/MainThreadExecutor()) {}

    fun diskIO(): Executor {
        return mDiskIO
    }
    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

}
