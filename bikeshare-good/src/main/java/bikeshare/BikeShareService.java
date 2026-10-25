package bikeshare;

import java.util.HashMap;
import java.util.Map;

public class BikeShareService {
    private final Map<String, Station> stations = new HashMap<>();
    private final Map<String, RentalRecord> activeRentals = new HashMap<>();
    private final PricingPolicy pricingPolicy;
    private final RideRepository repository;

    public BikeShareService(PricingPolicy pricingPolicy, RideRepository repository) {
        this.pricingPolicy = pricingPolicy;
        this.repository = repository;
        initStations();
    }

    private void initStations() {
        Station lib = new Station("Library");
        lib.addBike("Bike-L1");
        lib.addBike("Bike-L2");
        stations.put("Library", lib);

        Station canteen = new Station("Canteen");
        canteen.addBike("Bike-C1");
        canteen.addBike("Bike-C2");
        stations.put("Canteen", canteen);

        Station dorm = new Station("Dorm");
        dorm.addBike("Bike-D1");
        dorm.addBike("Bike-D2");
        stations.put("Dorm", dorm);
    }

    public RentalRecord rentBike(String memberId, MemberType memberType, String stationName) {
        Station station = stations.get(stationName);
        if (station == null || !station.hasAvailableBike()) {
            return null;
        }
        String bikeId = station.borrowBike();
        RentalRecord record = new RentalRecord(bikeId, memberId, memberType, stationName);
        activeRentals.put(memberId, record);
        return record;
    }

    public Double returnBike(String memberId, String stationName, int minutes) {
        RentalRecord record = activeRentals.remove(memberId);
        if (record == null) {
            return null;
        }
        Station station = stations.get(stationName);
        if (station != null) {
            station.addBike(record.getBikeId());
        }
        double fee = pricingPolicy.calculateFee(record.getMemberType(), minutes);
        repository.saveRide(record.getBikeId(), memberId, record.getMemberType(), minutes, fee);
        return fee;
    }

    public RentalRecord getActiveRental(String memberId) {
        return activeRentals.get(memberId);
    }
}
