import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ParkingManager extends JFrame {

    private ParkingLot parkingLot;

    public ParkingManager(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;

        setTitle("Парковка");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Calculate the number of rows needed
        int numRows = (int) Math.ceil((double) parkingLot.getCapacity() / 10);

        // Создаем панель для отображения информации о парковке
        JPanel panel = new JPanel(new GridLayout(numRows, 10));

        for (int i = 0; i < parkingLot.getCapacity(); i++) {
            JLabel spotLabel = new JLabel("Место " + (i + 1));
            spotLabel.setHorizontalAlignment(SwingConstants.CENTER);
            spotLabel.setVerticalAlignment(SwingConstants.CENTER);

            // Устанавливаем границу для создания прямоугольного вида
            Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
            spotLabel.setBorder(BorderFactory.createCompoundBorder(border,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));

            // Устанавливаем размеры шрифта в зависимости от количества мест
            int fontSize = Math.max(10, 14 - parkingLot.getCapacity() / 8);
            spotLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));

            // Determine if the spot is reserved for disabled
            boolean isReservedForDisabled = parkingLot.getParkingSpotByNumber(i).isReservedForDisabled();

            if (isReservedForDisabled) {
                // Display an icon for reserved disabled spot
                ImageIcon disabledIcon = resizeIcon("src/Images/disability.png", 30, 30);
                spotLabel.setIcon(disabledIcon);
                spotLabel.setText(""); // Clear text
            }

            if (parkingLot.isSpotOccupied(i)) {
                spotLabel.setForeground(Color.RED);
                spotLabel.setText("<html>Занято: " + parkingLot.getCarBySpot(i).getLicensePlate() + "<br>Место: " + (i + 1) + "</html>");
            } else {
                String reservedPlate = parkingLot.isSpotReserved(i);
                if (reservedPlate != null && !reservedPlate.isEmpty()) {
                    spotLabel.setForeground(Color.ORANGE);
                    spotLabel.setText("<html>Забронировано: " + reservedPlate + "<br>Место: " + (i + 1) + "</html>");
                } else {
                    spotLabel.setForeground(Color.GREEN);
                    spotLabel.setText("<html>Свободно<br>Место: " + (i + 1) + "</html>");
                }
            }

            panel.add(spotLabel);
        }

        // Установим цвета, соответствующие основному окну
        panel.setBackground(Color.decode("#1b1b1b"));
        panel.setForeground(Color.WHITE);

        // Add the panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Добавляем панель с поддержкой прокрутки на форму
        add(scrollPane);

        // Делаем форму видимой
        setVisible(true);
    }

    private ImageIcon resizeIcon(String filePath, int width, int height) {
        try {
            File imageFile = new File(filePath);
            BufferedImage image = ImageIO.read(imageFile);
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Запрашиваем у пользователя количество мест на парковке
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
        new ParkingManager(parkingLotObj);
    }
}
