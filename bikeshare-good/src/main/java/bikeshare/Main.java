package bikeshare;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PricingPolicy pricingPolicy = new PricingPolicy();
        RideRepository repository = new CsvRideRepository("rides.csv");
        BikeShareService service = new BikeShareService(pricingPolicy, repository);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Commands: rent <memberId> <student|staff> <station>, return <memberId> <station> <minutes>, quit");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("quit")) {
                break;
            }
            String[] parts = line.split("\\s+");
            if (parts[0].equalsIgnoreCase("rent") && parts.length == 4) {
                try {
                    MemberType type = MemberType.fromString(parts[2]);
                    RentalRecord record = service.rentBike(parts[1], type, parts[3]);
                    if (record != null) {
                        System.out.println("Rented " + record.getBikeId() + " to " + record.getMemberId());
                    } else {
                        System.out.println("No bike available at " + parts[3]);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid member type: " + parts[2]);
                }
            } else if (parts[0].equalsIgnoreCase("return") && parts.length == 4) {
                RentalRecord active = service.getActiveRental(parts[1]);
                String bikeId = (active != null) ? active.getBikeId() : "Unknown";
                int minutes = Integer.parseInt(parts[3]);
                Double fee = service.returnBike(parts[1], parts[2], minutes);
                if (fee != null) {
                    System.out.println("--- Receipt ---");
                    System.out.println("Bike: " + bikeId);
                    System.out.println("Minutes: " + minutes);
                    System.out.println("Fee: " + fee + " THB");
                } else {
                    System.out.println("No active rental found for " + parts[1]);
                }
            }
        }
        scanner.close();
    }
}
