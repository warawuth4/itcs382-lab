package bikeshare;

public class BikeRecord {
    public String bikeId;
    public int memberType; // 1 = student, 2 = staff
    public String startStation;

    public BikeRecord(String bikeId, int memberType, String startStation) {
        this.bikeId = bikeId;
        this.memberType = memberType;
        this.startStation = startStation;
    }
}
