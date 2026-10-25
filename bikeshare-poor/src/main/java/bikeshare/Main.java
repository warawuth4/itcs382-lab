package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Map<String, List<String>> stationBikes = new HashMap<>();
    public static Map<String, BikeRecord> activeRentals = new HashMap<>();

    static {
        stationBikes.put("Library", new ArrayList<>(List.of("Bike-L1", "Bike-L2")));
        stationBikes.put("Canteen", new ArrayList<>(List.of("Bike-C1", "Bike-C2")));
        stationBikes.put("Dorm", new ArrayList<>(List.of("Bike-D1", "Bike-D2")));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Commands: rent <memberId> <type: 1=student, 2=staff> <station>, return <memberId> <station> <minutes>, quit");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) {
                break;
            }
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("rent") && parts.length == 4) {
                rentBike(parts[1], Integer.parseInt(parts[2]), parts[3]);
            } else if (parts[0].equalsIgnoreCase("return") && parts.length == 4) {
                returnBike(parts[1], parts[2], Integer.parseInt(parts[3]), true);
            }
        }
        scanner.close();
    }

    public static void rentBike(String memberId, int memberType, String station) {
        List<String> bikes = stationBikes.get(station);
        if (bikes == null || bikes.isEmpty()) {
            System.out.println("No bike available at " + station);
            return;
        }
        String bike = bikes.remove(0);
        activeRentals.put(memberId, new BikeRecord(bike, memberType, station));
        System.out.println("Rented " + bike + " to " + memberId);
    }

    public static double returnBike(String memberId, String station, int minutes, boolean printReceipt) {
        BikeRecord record = activeRentals.remove(memberId);
        if (record == null) {
            System.out.println("No active rental found for " + memberId);
            return 0.0;
        }
        List<String> bikes = stationBikes.get(station);
        if (bikes != null) {
            bikes.add(record.bikeId);
        }
        double rate = (record.memberType == 1) ? 2.0 : 3.0;
        double fee = minutes * rate;

        if (printReceipt) {
            System.out.println("--- Receipt ---");
            System.out.println("Bike: " + record.bikeId);
            System.out.println("Minutes: " + minutes);
            System.out.println("Fee: " + fee + " THB");
            saveRide(record.bikeId, memberId, record.memberType, minutes, fee);
        }
        return fee;
    }

    public static void saveRide(String bikeId, String memberId, int memberType, int minutes, double fee) {
        try (FileWriter writer = new FileWriter("rides.csv", true)) {
            writer.write(bikeId + "," + memberId + "," + memberType + "," + minutes + "," + fee + "\n");
        } catch (IOException e) {
            System.err.println("Error saving ride: " + e.getMessage());
        }
    }
}
