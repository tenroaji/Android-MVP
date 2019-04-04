package lagecong.com.mvp.utils.executors

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Anwar on 6/28/2018.
 */
class DiskIOThreadExecutor : Executor {

    private val mDiskIO: Executor

    init {
        mDiskIO = Executors.newSingleThreadExecutor()
    }

    override fun execute(command: Runnable) {
        mDiskIO.execute(command)
    }
}
