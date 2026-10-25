package bikeshare;

public class PricingPolicy {
    public double calculateFee(MemberType memberType, int minutes) {
        return minutes * memberType.getRatePerMinute();
    }
}
