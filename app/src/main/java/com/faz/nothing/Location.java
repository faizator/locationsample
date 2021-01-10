package com.faz.nothing;

import java.util.Objects;

public final class Location {

    public final double lat;
    public final double lon;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.lat, lat) == 0 &&
                Double.compare(location.lon, lon) == 0;
    }
}
