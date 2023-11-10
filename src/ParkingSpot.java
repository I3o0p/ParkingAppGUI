public class ParkingSpot {
    private int spotNumber;
    private String reservedCarLicensePlate;
    private boolean reservedForDisabled;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public String getReservedCarLicensePlate() {
        return reservedCarLicensePlate;
    }

    public boolean isReserved() {
        return reservedCarLicensePlate != null;
    }

    public void reserve(String carLicensePlate) {
        reservedCarLicensePlate = carLicensePlate;
    }

    public void unreserve() {
        reservedCarLicensePlate = null;
    }

    public void setReservedForDisabled(boolean reservedForDisabled) {
        this.reservedForDisabled = reservedForDisabled;
    }

    public boolean isReservedForDisabled() {
        return reservedForDisabled;
    }
}
