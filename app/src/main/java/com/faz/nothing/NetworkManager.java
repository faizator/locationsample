package com.faz.nothing;

import java.util.HashSet;
import java.util.Set;

public final class NetworkManager {

    public Set<Location> requestLocationsAround(Location location) {
        Logger.log("Request around $location");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ignored
        }
        HashSet<Location> data = new HashSet<>();
        data.add(new Location(1.0, 1.2));
        data.add(new Location(2.0, 1.0));
        return data;
    }
}
