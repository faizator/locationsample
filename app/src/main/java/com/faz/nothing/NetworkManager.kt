package com.faz.nothing

class NetworkManager {

    fun requestLocationsAround(location: Location): Set<Location> {
        Logger.log("Request around $location")
        Thread.sleep(2000)
        return setOf(
            Location(1.0, 1.2),
            Location(2.0, 1.0)
        )
    }
}