package bikeshare;

public interface RideRepository {
    void saveRide(String bikeId, String memberId, MemberType memberType, int minutes, double fee);
}
