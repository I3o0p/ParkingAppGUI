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

    private Color defaultButtonColor = Color.decode("#1b1b1b");
    private Color hoverButtonColor = Color.decode("#2d2d2d");

    public ParkingGUI(ParkingLot parkingLot) {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.parkingLot = parkingLot;

        setLookAndFeel();

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
        ImageIcon parkview = resizeIcon("src/Images/parkview.png", 30, 30);

        // Create buttons with icons
        JButton parkButton = createStyledButton("Припарковать автомобиль", parkIcon);
        JButton leaveButton = createStyledButton("Покинуть парковку", leaveIcon);
        JButton infoButton = createStyledButton("Информация о свободных местах", infoIcon);
        JButton searchButton = createStyledButton("Поиск автомобиля", searchIcon);
        JButton reserveButton = createStyledButton("Забронировать парковочное место", reserveIcon);
        JButton statusButton = createStyledButton("Показать состояние парковки", statusIcon);
        JButton resetButton = createStyledButton("Обнулить парковку", resetIcon);
        JButton exitButton = createStyledButton("Выйти", exitIcon);
        JButton parkNowButton = createStyledButton("Парковка сейчас", parkview);

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
        buttonPanel.add(parkNowButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between buttons
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.WEST);

        // Create output text area
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.decode("#1b1b1b"));
        outputTextArea.setForeground(Color.WHITE);
        outputTextArea.setBorder(BorderFactory.createEmptyBorder());

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
        messagePanel.add(new JLabel("Сообщения:"), BorderLayout.NORTH);
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

        parkNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openParkingManager();
            }
        });

        setVisible(true);

        getRootPane().setBackground(new Color(27, 27, 27));
        getRootPane().setForeground(Color.WHITE);
    }

    private void setLookAndFeel() {
        UIManager.put("OptionPane.background", new ColorUIResource(27, 27, 27));
        UIManager.put("Panel.background", new ColorUIResource(27, 27, 27));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(255, 255, 255));
        UIManager.put("Slider.track", new ColorUIResource(27, 27, 27));
        UIManager.put("Slider.thumb", new ColorUIResource(27, 27, 27));
        UIManager.put("Button.background", defaultButtonColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.border", BorderFactory.createLineBorder(defaultButtonColor, 1));
    }

    private JButton createStyledButton(String text, Icon icon) {
        JButton button = new JButton(text);
        setCommonButtonProperties(button, icon);
        return button;
    }

    private void setCommonButtonProperties(JButton button, Icon icon) {
        button.setBackground(defaultButtonColor);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIcon(icon);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverButtonColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultButtonColor);
            }
        });
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

    private void openParkingManager() {
        ParkingManager parkingManager = new ParkingManager(parkingLot);
    }

    private ImageIcon resizeIcon(String filePath, int width, int height) {
        try {
            // Use absolute file path
            File imageFile = new File(filePath);
            BufferedImage image = ImageIO.read(imageFile);
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void appendToOutput(String message) {
        outputTextArea.append(message + "\n\n");
    }

    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(null, "Введите количество мест на парковке (по умолчанию 10):", "Количество мест", JOptionPane.QUESTION_MESSAGE);
        int defaultCapacity = 10;

        int tempCapacity;

        try {
            tempCapacity = (input == null || input.isEmpty()) ? defaultCapacity : Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка при вводе количества мест. Установлено значение по умолчанию: " + defaultCapacity);
            tempCapacity = defaultCapacity;
        }

        ParkingLot parkingLotObj = new ParkingLot(tempCapacity);
        ParkingGUI parkingGUI = new ParkingGUI(parkingLotObj);
    }



}
