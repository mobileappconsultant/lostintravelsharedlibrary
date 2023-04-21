package com.arkangel.lostintravelsharedlibrary.util

import platform.Network.*
import platform.NetworkExtension.NWPath
import platform.darwin.*
import platform.posix.intptr_t

actual fun isInternetConnected(): Boolean {
    val monitor = nw_path_monitor_create()
    val queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT as intptr_t, 0)

    var isConnected = false

    val semaphore = dispatch_semaphore_create(0)

    nw_path_monitor_set_update_handler(monitor) { path: nw_path_t ->
        isConnected = nw_path_get_status(path) == nw_path_status_satisfied
        dispatch_semaphore_signal(semaphore)
    }

    nw_path_monitor_set_queue(monitor, queue)
    nw_path_monitor_start(monitor)

    val timeout = dispatch_time(DISPATCH_TIME_NOW, 2_000_000_000) // 2 seconds
    dispatch_semaphore_wait(semaphore, timeout)

    nw_path_monitor_cancel(monitor)

    return isConnected
}