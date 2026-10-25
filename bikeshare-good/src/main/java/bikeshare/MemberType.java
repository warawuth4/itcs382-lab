package bikeshare;

public enum MemberType {
    STUDENT(2.0),
    STAFF(3.0);

    private final double ratePerMinute;

    MemberType(double ratePerMinute) {
        this.ratePerMinute = ratePerMinute;
    }

    public double getRatePerMinute() {
        return ratePerMinute;
    }

    public static MemberType fromString(String text) {
        if ("student".equalsIgnoreCase(text) || "1".equals(text)) {
            return STUDENT;
        } else if ("staff".equalsIgnoreCase(text) || "2".equals(text)) {
            return STAFF;
        }
        throw new IllegalArgumentException("Unknown member type: " + text);
    }
}
