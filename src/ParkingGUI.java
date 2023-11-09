import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

public class ParkingGUI extends JFrame {
    private ParkingLot parkingLot;
    private JTextArea outputTextArea;

    public ParkingGUI(ParkingLot parkingLot) {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.parkingLot = parkingLot;

        Color defaultButtonColor = Color.decode("#1b1b1b");
        Color hoverButtonColor = Color.decode("#2d2d2d");

        UIManager.put("OptionPane.background", new ColorUIResource(27, 27, 27));
        UIManager.put("Panel.background", new ColorUIResource(27, 27, 27));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(255, 255, 255));
        UIManager.put("Slider.track", new ColorUIResource(27, 27, 27));
        UIManager.put("Slider.thumb", new ColorUIResource(27, 27, 27));
        UIManager.put("Button.background", defaultButtonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.border", BorderFactory.createLineBorder(defaultButtonColor, 1));

        setTitle("Parking Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#1b1b1b"));
        setLayout(new BorderLayout());

        // Load images for buttons
        ImageIcon parkIcon = resizeIcon("src/Images/Parkthecar.png", 30, 30);
        ImageIcon leaveIcon = resizeIcon("src/Images/Leave.png", 30, 30);
        ImageIcon infoIcon = resizeIcon("src/Images/information-button.png", 30, 30);
        ImageIcon searchIcon = resizeIcon("src/Images/search.png", 30, 30);
        ImageIcon reserveIcon = resizeIcon("src/Images/reserve.png", 30, 30);
        ImageIcon statusIcon = resizeIcon("src/Images/status.png", 30, 30);
        ImageIcon resetIcon = resizeIcon("src/Images/reset.png", 30, 30);
        ImageIcon exitIcon = resizeIcon("src/Images/logout.png", 30, 30);
        // Create buttons with icons
        JButton parkButton = createStyledButton("Припарковать автомобиль", parkIcon, defaultButtonColor, hoverButtonColor);
        JButton leaveButton = createStyledButton("Покинуть парковку", leaveIcon, defaultButtonColor, hoverButtonColor);
        JButton infoButton = createStyledButton("Информация о свободных местах", infoIcon, defaultButtonColor, hoverButtonColor);
        JButton searchButton = createStyledButton("Поиск автомобиля", searchIcon, defaultButtonColor, hoverButtonColor);
        JButton reserveButton = createStyledButton("Забронировать парковочное место", reserveIcon, defaultButtonColor, hoverButtonColor);
        JButton statusButton = createStyledButton("Показать состояние парковки", statusIcon, defaultButtonColor, hoverButtonColor);
        JButton resetButton = createStyledButton("Обнулить парковку", resetIcon, defaultButtonColor, hoverButtonColor);
        JButton exitButton = createStyledButton("Выйти", exitIcon, defaultButtonColor, hoverButtonColor);

// Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

// Add buttons with space between them
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add initial space

        buttonPanel.add(parkButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(leaveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(infoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(reserveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(statusButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.WEST);




// Create output text area
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.decode("#1b1b1b"));
        outputTextArea.setForeground(Color.WHITE);
        outputTextArea.setBorder(BorderFactory.createEmptyBorder()); // Remove the border

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        scrollPane.getVerticalScrollBar().setBackground(Color.decode("#1b1b1b")); // Set the background color of the vertical scrollbar
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#2d2d2d"); // Set the color of the thumb (the draggable part of the scrollbar)
            }
        });


        // Create output panel
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(Color.decode("#1b1b1b"));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        messagePanel.add(outputPanel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.CENTER);

        // Add action listeners to buttons
        parkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleParkButtonClick();
            }
        });

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLeaveButtonClick();
            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInfoButtonClick();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearchButtonClick();
            }
        });

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReserveButtonClick();
            }
        });

        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStatusButtonClick();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleResetButtonClick();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExitButtonClick();
            }
        });

        setVisible(true);

        getRootPane().setBackground(new Color(27, 27, 27));
        getRootPane().setForeground(Color.WHITE);
    }

    private ImageIcon resizeIcon(String filePath, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JButton createStyledButton(String text, Icon icon, Color defaultColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setBackground(defaultColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(icon);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor);
            }
        });

        return button;
    }

    private void appendToOutput(String message) {
        outputTextArea.append(message + "\n\n");
    }

    private void handleParkButtonClick() {
        String licensePlate = JOptionPane.showInputDialog(this, "Введите номер автомобиля:");

        if (licensePlate == null || licensePlate.isEmpty()) {
            showError("Номер автомобиля не может быть пустым.");
            return;
        }

        Car car = new Car(licensePlate);
        ParkingSpot spot = parkingLot.parkCar(car);
        if (spot != null) {
            outputTextArea.append("Автомобиль припаркован успешно! Место: " + spot.getSpotNumber() + "\n");
        }
    }

    private void handleLeaveButtonClick() {
        String exitLicensePlate = JOptionPane.showInputDialog(this, "Введите номер автомобиля, который покидает парковку:");
        parkingLot.removeCar(exitLicensePlate);
        outputTextArea.append("Автомобиль покинул парковку.\n");
    }

    private void handleInfoButtonClick() {
        int availableSpots = parkingLot.getAvailableSpots();
        int totalCarsParked = parkingLot.getTotalCarsParked();
        String infoMessage = "Свободных мест на парковке: " + availableSpots + "\nЗанятых мест: " + totalCarsParked;
        appendToOutput(infoMessage);
    }

    private void handleSearchButtonClick() {
        String searchLicensePlate = JOptionPane.showInputDialog(this, "Введите номер автомобиля для поиска:");
        Car foundCar = parkingLot.findCarByLicensePlate(searchLicensePlate);
        if (foundCar != null) {
            String message = "Автомобиль с номером " + foundCar.getLicensePlate() + " находится на парковке.";
            ParkingSpot parkedSpot = foundCar.getParkingSpot();
            if (parkedSpot != null) {
                message += "\nОн припаркован на месте " + parkedSpot.getSpotNumber() + ".";
            } else {
                message += "\nМесто парковки для этого автомобиля не определено.";
            }
            appendToOutput(message);
        } else {
            showError("Автомобиль с номером " + searchLicensePlate + " не найден на парковке.");
        }
    }

    private void handleReserveButtonClick() {
        if (parkingLot.getAvailableSpots() == 0) {
            showError("Все места на парковке уже заняты. Пожалуйста, подождите.");
        } else {
            String reserveLicensePlate = JOptionPane.showInputDialog(this, "Введите номер автомобиля для бронирования места:");
            if (reserveLicensePlate != null && !reserveLicensePlate.trim().isEmpty()) {
                if (parkingLot.reserveParkingSpot(reserveLicensePlate)) {
                    appendToOutput("Место успешно забронировано для автомобиля с номером " + reserveLicensePlate + ".");
                } else {
                    showError("Все места на парковке уже заняты. Пожалуйста, подождите.");
                }
            } else {
                showError("Пожалуйста, введите номер автомобиля для бронирования места.");
            }
        }
    }

    private void handleStatusButtonClick() {
        int reservedSpots = parkingLot.getReservedSpots();
        int totalOccupiedSpots = parkingLot.getTotalCarsParked();

        String statusMessage = "Состояние парковки:\n";
        statusMessage += "Свободных мест: " + parkingLot.getAvailableSpots() + "\n";
        statusMessage += "Занятых мест: " + totalOccupiedSpots + "\n";
        statusMessage += "Забронированных мест: " + reservedSpots + "\n";

        // Форматирование вывода общей выручки с одной цифрой после точки
        DecimalFormat df = new DecimalFormat("#0.0");
        statusMessage += "Общая выручка: " + df.format(parkingLot.getTotalRevenue()) + " грн";

        appendToOutput(statusMessage);
    }

    private void handleResetButtonClick() {
        parkingLot.resetParkingLot();
        appendToOutput("Парковка успешно обнулена.");
    }

    private void handleExitButtonClick() {
        System.exit(0);
    }

    private void showError(String message) {
        appendToOutput("Ошибка: " + message);
    }

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(10);
        new ParkingGUI(parkingLot);
    }
}