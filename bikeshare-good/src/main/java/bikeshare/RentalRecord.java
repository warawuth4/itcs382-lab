package bikeshare;

public class RentalRecord {
    private final String bikeId;
    private final String memberId;
    private final MemberType memberType;
    private final String startStation;

    public RentalRecord(String bikeId, String memberId, MemberType memberType, String startStation) {
        this.bikeId = bikeId;
        this.memberId = memberId;
        this.memberType = memberType;
        this.startStation = startStation;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getMemberId() {
        return memberId;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public String getStartStation() {
        return startStation;
    }
}
