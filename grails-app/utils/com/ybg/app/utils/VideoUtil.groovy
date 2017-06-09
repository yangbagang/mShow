package com.ybg.app.utils

/**
 * Created by yangbagang on 2017/1/19.
 */
class VideoUtil {

    static Set<String> container = new HashSet<String>()

    static checkIsRunning(String key) {
        return container.contains(key)
    }

    static addRunningKey(String key) {
        return container.add(key)
    }

    static removeRunningKey(String key) {
        return container.remove(key)
    }
}
