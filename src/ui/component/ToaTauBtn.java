package ui.component;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entity.LoaiKhoang;
import entity.ToaTau;

public class ToaTauBtn extends JButton {
    private List<KhoangBtn> cabins;
    private static ToaTau toaTau;

    public ToaTauBtn(ToaTau toaTau) {
        ToaTauBtn.toaTau = toaTau;
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

    public static ToaTauBtn createCarriageWith50Seats(Map<Integer, Integer> dsChoNgoi) {
        ToaTauBtn carriage = new ToaTauBtn(toaTau);
        KhoangBtn cabin = (KhoangBtn) KhoangBtn.createFiftySeaterCabin(dsChoNgoi);
        carriage.addCabin(cabin);
        return carriage;
    }

    // Tạo một toa với 8 khoang, mỗi khoang có 6 chỗ
    public static ToaTauBtn createCarriageWith8Cabins6Seats(Map<Integer, Integer> dsChoNgoi) {
        ToaTauBtn carriage = new ToaTauBtn(toaTau);

        Map<Integer, Integer> seatsOfEachCabin = new TreeMap<>();

        for(Map.Entry<Integer, Integer> entry : dsChoNgoi.entrySet()) {
            Integer soSlot = entry.getKey();
            Integer tinhTrang = entry.getValue();

            seatsOfEachCabin.put(soSlot, tinhTrang);

            if (soSlot  % 4 == 0) {
                carriage.addCabin(KhoangBtn.createFourSeaterCabin(seatsOfEachCabin));
                seatsOfEachCabin.clear();
            }
        }
        return carriage;
    }

    // Tạo một toa với 4 khoang, mỗi khoang có 4 chỗ
    public static ToaTauBtn createCarriageWith8Cabins4Seats(Map<Integer, Integer> dsChoNgoi) {
        ToaTauBtn carriage = new ToaTauBtn(toaTau);
        Map<Integer, Integer> seatsOfEachCabin = new TreeMap<>();
        for(Map.Entry<Integer, Integer> entry : dsChoNgoi.entrySet()) {
            Integer soSlot = entry.getKey();
            Integer tinhTrang = entry.getValue();

            seatsOfEachCabin.put(soSlot, tinhTrang);

            if (soSlot  % 4 == 0) {
                carriage.addCabin(KhoangBtn.createFourSeaterCabin(seatsOfEachCabin));
                seatsOfEachCabin.clear();
            }
        }
        return carriage;
    }

    public void addCabin(KhoangBtn cabin) {
        cabins.add(cabin);
    }

    public List<KhoangBtn> getCabins() {
        return cabins;
    }

    public int getTotalNumberOfSeats() {
        int totalSeats = 0;
        for (KhoangBtn cabin : cabins) {
            totalSeats += cabin.getNumberOfSeats();
        }
        return totalSeats;
    }

    public void displayCarriageInfo() {
        List<KhoangBtn> cabins = getCabins();
        int totalSeats = getTotalNumberOfSeats();
        System.out.println("Total number of seats: " + totalSeats);
        System.out.println("Number of cabins: " + cabins.size());
        for (int i = 0; i < cabins.size(); i++) {
            KhoangBtn cabin = cabins.get(i);
            int cabinNumber = i + 1;
            System.out.println("Cabin " + cabinNumber + ":");
            System.out.println("   Number of seats: " + cabin.getNumberOfSeats());
        }
    }

    public ToaTau getToaTau() {
        return toaTau;
    }

    public void setToaTau(ToaTau toaTau) {
        ToaTauBtn.toaTau = toaTau;
    }  
}