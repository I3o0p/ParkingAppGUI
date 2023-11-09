class Car {
    private String licensePlate;
    private double entryTime;
    double exitTime;
    private ParkingSpot parkingSpot;
    private double parkingFee;

    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
        this.entryTime = System.currentTimeMillis() / 1000.0; // Время в секундах
        this.parkingFee = 0.0;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public double getEntryTime() {
        return entryTime;
    }

    public double getExitTime() {
        return exitTime;
    }

    public double getParkingFee() {
        return parkingFee;
    }

    public void calculateParkingFee(double hourlyRate) {
        double parkingDuration = exitTime - entryTime;
        parkingFee = parkingDuration / 30.0; // 30 секунд = 1 грн
    }
}