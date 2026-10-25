package bikeshare;

import java.util.ArrayDeque;
import java.util.Deque;

public class Station {
    private final String name;
    private final Deque<String> bikes = new ArrayDeque<>();

    public Station(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addBike(String bikeId) {
        bikes.addLast(bikeId);
    }

    public String borrowBike() {
        return bikes.pollFirst();
    }

    public boolean hasAvailableBike() {
        return !bikes.isEmpty();
    }
}
