package ui.component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Carriages {
    private List<Seat> seats;

    // Khởi tạo một danh sách chỗ ngồi với số lượng chỗ được chỉ định
    public Carriages(int numberOfSeats) {
        this.seats = new java.util.ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            Seat seat = new Seat(i);
            seats.add(seat);
        }
    }

    // Tạo một khoang với 4 chỗ ngồi
    public static Carriages createFourSeaterCarriage() {
        return new Carriages(4);
    }

    // Tạo một khoang với 6 chỗ ngồi
    public static Carriages createSixSeaterCarriage() {
        return new Carriages(6);
    }

    // Tạo một khoang với 50 chỗ ngồi
    public static Carriages createFiftySeaterCarriage() {
        return new Carriages(48);
    }

    // Trả về danh sách các chỗ ngồi trong khoang
    public List<Seat> getSeats() {
        return seats;
    }

    // Trả về số lượng chỗ ngồi trong khoang
    public int getNumberOfSeats() {
        return seats.size();
    }

    // Trả về chỗ ngồi tại một vị trí cụ thể trong khoang
    public Seat getSeat(int seatNumber) {
        if (seatNumber > 0 && seatNumber <= seats.size()) {
            return seats.get(seatNumber - 1);
        }
        return null;
    }

    // Hàm main để minh họa
    public static void main(String[] args) {
        // Chọn loại khoang
        Object[] options = {"4 chỗ", "6 chỗ", "50 chỗ"};
        int choice = JOptionPane.showOptionDialog(null, "Chọn loại khoang:",
                "Chọn loại khoang", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        Carriages carriage;
        switch (choice) {
            case 0:
                carriage = Carriages.createFourSeaterCarriage();
                break;
            case 1:
                carriage = Carriages.createSixSeaterCarriage();
                break;
            case 2:
                carriage = Carriages.createFiftySeaterCarriage();
                break;
            default:
                return;
        }

        // Lấy danh sách các chỗ ngồi trong khoang
        List<Seat> seats = carriage.getSeats();

        // Hiển thị số lượng chỗ ngồi và thông tin của từng chỗ ngồi
        System.out.println("Số lượng chỗ ngồi trong khoang: " + carriage.getNumberOfSeats());
        for (Seat seat : seats) {
            System.out.println("Chỗ ngồi số " + seat.getSoChoNgoi());
        }

        // Hiển thị giao diện đồ họa minh họa
        JFrame frame = new JFrame("Carriage Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Thêm các chỗ ngồi vào frame
        for (Seat seat : seats) {
            frame.add(seat);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
