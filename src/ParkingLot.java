import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private List<Car> cars;
    private List<ParkingSpot> availableSpots;
    private int capacity;
    private int totalCarsParked;
    private double totalRevenue;
    private List<ParkingSpot> reservedSpots;
    private static final int MAX_WAIT_TIME_SECONDS = 300;

    private ParkingSpot[] spots;


    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.cars = new ArrayList<>();
        this.availableSpots = new ArrayList<>();
        this.reservedSpots = new ArrayList<>();
        this.spots = new ParkingSpot[capacity];

        initializeParkingSpots();
    }

    private void initializeParkingSpots() {
        for (int i = 1; i <= capacity; i++) {
            ParkingSpot spot = new ParkingSpot(i);
            availableSpots.add(spot);
            spots[i - 1] = spot;

            // Reserve 7% of spots for disabled people
            if (i <= Math.ceil(0.1 * capacity)) {
                spot.setReservedForDisabled(true);
                reservedSpots.add(spot);
            }
        }
    }

    public void updateCapacity(int newCapacity) {
        this.capacity = newCapacity;
        this.availableSpots.clear();
        this.reservedSpots.clear();
        this.cars.clear();
        this.spots = new ParkingSpot[capacity];

        initializeParkingSpots();
    }

    public int getAvailableSpots() {
        return availableSpots.size();
    }

    public int getTotalCarsParked() {
        return totalCarsParked;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public List<ParkingSpot> getReservedSpots() {
        return reservedSpots;
    }

    public Car findCarByLicensePlate(String licensePlate) {
        for (Car car : cars) {
            if (car.getLicensePlate().equals(licensePlate)) {
                return car;
            }
        }
        return null;
    }

    public ParkingSpot parkCar(Car car) {
        if (availableSpots.isEmpty()) {
            System.out.println("Парковка заполнена. Невозможно припарковать автомобиль с номером " + car.getLicensePlate() + ".");
            return null;
        }

        for (ParkingSpot spot : availableSpots) {
            if (spot.getReservedCarLicensePlate() == null) {
                spot.reserve(car.getLicensePlate());
                car.setParkingSpot(spot);
                cars.add(car);
                totalCarsParked++;
                availableSpots.remove(spot);
                System.out.println("Автомобиль с номером " + car.getLicensePlate() + " припаркован на месте " + spot.getSpotNumber() + ".");
                return spot;
            }
        }

        System.out.println("Все места уже забронированы.");
        return null;
    }

    public void removeCar(String licensePlate) {
        Car car = findCarByLicensePlate(licensePlate);
        if (car == null) {
            System.out.println("Автомобиль с номером " + licensePlate + " не найден на парковке.");
            return;
        }

        car.exitTime = System.currentTimeMillis() / 1000.0;
        double hourlyRate = 5.0;
        car.calculateParkingFee();
        car.getParkingSpot().unreserve();
        availableSpots.add(car.getParkingSpot());
        cars.remove(car);
        DecimalFormat df = new DecimalFormat("#.#");
        System.out.println("Автомобиль с номером " + licensePlate + " покинул парковку.");
        System.out.println("Продолжительность парковки: " + df.format(car.getExitTime() - car.getEntryTime()) + " секунд.");
        System.out.println("Сумма оплаты: " + df.format(car.getParkingFee()) + " грн.");
        totalRevenue += car.getParkingFee();
    }

    public boolean reserveParkingSpot(String carLicensePlate) {
        if (getAvailableSpots() == 0) {
            System.out.println("Все места уже заняты. Невозможно забронировать место для автомобиля с номером " + carLicensePlate + ".");
            return false;
        }

        Car existingCar = findCarByLicensePlate(carLicensePlate);
        if (existingCar != null) {
            System.out.println("Автомобиль с номером " + carLicensePlate + " уже припаркован на месте " + existingCar.getParkingSpot().getSpotNumber() + " и не может быть забронирован.");
            return false;
        }

        for (ParkingSpot spot : availableSpots) {
            if (spot.getReservedCarLicensePlate() == null) {
                spot.reserve(carLicensePlate);
                reservedSpots.add(spot);
                System.out.println("Место " + spot.getSpotNumber() + " успешно забронировано для автомобиля с номером " + carLicensePlate + ".");
                return true;
            }
        }

        return false;
    }

    public void showAvailableSpots() {
        boolean allSpotsOccupied = true;
        System.out.println("Доступные для бронирования места:");
        for (ParkingSpot spot : availableSpots) {
            if (spot.getReservedCarLicensePlate() == null) {
                System.out.print(spot.getSpotNumber() + " ");
                allSpotsOccupied = false;
            }
        }
        if (allSpotsOccupied) {
            System.out.print("Нет доступных мест");
        }
        System.out.println();
    }

    public void showParkingStatus() {
        int reservedSpots = 0;

        for (ParkingSpot spot : availableSpots) {
            if (spot.isReserved()) {
                reservedSpots++;
            }
        }

        int totalOccupiedSpots = getTotalCarsParked() + reservedSpots;

        System.out.println("Состояние парковки:");
        System.out.println("Свободных мест: " + (getAvailableSpots() - reservedSpots));
        System.out.println("Занятых мест: " + totalOccupiedSpots);
        System.out.println("Забронированных мест: " + reservedSpots);
        System.out.println("Общая выручка: " + new DecimalFormat("#.#").format(getTotalRevenue()) + " грн");
    }

    public void resetParkingLot() {
        cars.clear();
        availableSpots.clear();
        reservedSpots.clear();

        initializeParkingSpots();

        totalCarsParked = 0;
        totalRevenue = 0.0;
        System.out.println("Парковка успешно обнулена.");
    }

    public ParkingSpot getParkingSpotByNumber(int i) {
        if (i >= 0 && i < availableSpots.size()) {
            return availableSpots.get(i);
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<ParkingSpot> getParkingSpots() {
        return null;
    }

    public boolean isSpotOccupied(int spotNumber) {
        for (Car car : cars) {
            ParkingSpot parkingSpot = car.getParkingSpot();
            if (parkingSpot != null && parkingSpot.getSpotNumber() == spotNumber + 1) {
                return true;
            }
        }
        return false;
    }

    public Car getCarBySpot(int spotNumber) {
        for (Car car : cars) {
            ParkingSpot parkingSpot = car.getParkingSpot();
            if (parkingSpot != null && parkingSpot.getSpotNumber() == spotNumber + 1) {
                return car;
            }
        }
        return null;
    }

    public String isSpotReserved(int spotNumber) {
        for (ParkingSpot spot : reservedSpots) {
            if (spot.getSpotNumber() == spotNumber + 1) {
                return spot.getReservedCarLicensePlate();
            }
        }
        return null;
    }

}
