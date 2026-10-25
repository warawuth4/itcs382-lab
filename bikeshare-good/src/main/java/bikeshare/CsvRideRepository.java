package bikeshare;

import java.io.FileWriter;
import java.io.IOException;

public class CsvRideRepository implements RideRepository {
    private final String filePath;

    public CsvRideRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveRide(String bikeId, String memberId, MemberType memberType, int minutes, double fee) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(bikeId + "," + memberId + "," + memberType + "," + minutes + "," + fee + "\n");
        } catch (IOException e) {
            System.err.println("Failed to save ride: " + e.getMessage());
        }
    }
}
