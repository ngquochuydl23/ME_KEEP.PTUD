package ui.component;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import entity.LoaiKhoang;
import entity.ToaTau;

public class Carriage extends JButton {
    private List<Cabin> cabins;
    private static ToaTau toaTau;

    public Carriage(ToaTau toaTau) {
        Carriage.toaTau = toaTau;
        cabins = new ArrayList<>();
        initComponent(toaTau.getTenToa());
    }

    private void initComponent(String tenToa) {
        // Tạo biểu tượng và văn bản
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/icon/railway-carriage.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(scaledIcon);
        JLabel textLabel = new JLabel(tenToa);

        // Tạo một panel để chứa biểu tượng và văn bản
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Đặt layout là FlowLayout với canh giữa và không có
                                                                 // khoảng cách

        // Thêm biểu tượng và văn bản vào panel
        panel.add(iconLabel);
        panel.add(textLabel);

        // Đặt nút trong suốt và các thuộc tính khác
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Đặt panel làm nội dung của JButton
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

    }

    private LoaiKhoang layLoaiKhoang() {

        return null;
    }

    // Tạo một toa với 50 chỗ
    public static Carriage createCarriageWith50Seats() {
        Carriage carriage = new Carriage(toaTau);
        Cabin cabin = (Cabin) Cabin.createFiftySeaterCabin();
        carriage.addCabin(cabin);

        return carriage;
    }

    // Tạo một toa với 8 khoang, mỗi khoang có 6 chỗ
    public static Carriage createCarriageWith8Cabins6Seats() {
        Carriage carriage = new Carriage(toaTau);
        for (int i = 0; i < 8; i++) {
            List<Seat> seats = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                seats.add(new Seat(j + 1)); // Tạo mới một đối tượng Seat cho mỗi ghế
            }
            Cabin cabin = new Cabin(seats);
            cabin.updateSeatNumbers(i * 6 + 1); // Cập nhật số trên ghế cho mỗi cabin
            carriage.addCabin(cabin);
        }
        return carriage;
    }

    // Tạo một toa với 8 khoang, mỗi khoang có 4 chỗ
    public static Carriage createCarriageWith8Cabins4Seats() {
        Carriage carriage = new Carriage(toaTau);
        for (int i = 0; i < 8; i++) {
            List<Seat> seats = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                seats.add(new Seat(j + 1)); // Tạo mới một đối tượng Seat cho mỗi ghế
            }
            Cabin cabin = new Cabin(seats);
            cabin.updateSeatNumbers(i * 4 + 1); // Cập nhật số trên ghế cho mỗi cabin
            carriage.addCabin(cabin);
        }
        return carriage;
    }
    
    public void addCabin(Cabin cabin) {
        cabins.add(cabin);
    }

    public List<Cabin> getCabins() {
        return cabins;
    }

    public int getTotalNumberOfSeats() {
        int totalSeats = 0;
        for (Cabin cabin : cabins) {
            totalSeats += cabin.getNumberOfSeats();
        }
        return totalSeats;
    }

    public static void displayCarriageInfo(Carriage carriage) {
        List<Cabin> cabins = carriage.getCabins();
        int totalSeats = carriage.getTotalNumberOfSeats();
        System.out.println("Total number of seats: " + totalSeats);
        System.out.println("Number of cabins: " + cabins.size());
        for (int i = 0; i < cabins.size(); i++) {
            Cabin cabin = cabins.get(i);
            int cabinNumber = i + 1;
            System.out.println("Cabin " + cabinNumber + ":");
            System.out.println("   Number of seats: " + cabin.getNumberOfSeats());
        }
    }

    public ToaTau getToaTau() {
        return toaTau;
    }

    public void setToaTau(ToaTau toaTau) {
        Carriage.toaTau = toaTau;
    }  
}