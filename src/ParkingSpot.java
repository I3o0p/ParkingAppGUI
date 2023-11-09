import java.text.DecimalFormat;

class ParkingSpot {
    private int spotNumber;
    private String reservedCarLicensePlate;

    public ParkingSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.reservedCarLicensePlate = null;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public String getReservedCarLicensePlate() {
        return reservedCarLicensePlate;
    }

    public void reserve(String carLicensePlate) {
        this.reservedCarLicensePlate = carLicensePlate;
    }

    public void unreserve() {
        this.reservedCarLicensePlate = null;
    }

    public boolean isOccupied() {
        return reservedCarLicensePlate != null;
    }

    public boolean isReserved() {
        return reservedCarLicensePlate != null;
    }
}

