package ui.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

public class Seat extends JCheckBox {
    private int trangThai;
    private int soChoNgoi;

    public Seat(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
        this.setText(String.valueOf(soChoNgoi)); // Đưa số vào trong checkbox
        this.setHorizontalAlignment(SwingConstants.CENTER); // Canh giữa số trong checkbox
        this.setOpaque(false); // Xóa nền để lại viền ngoài
        this.setFocusPainted(false); // Không vẽ viền xung quanh khi focus
        this.setBorderPainted(true); // Vẽ viền ngoài cho checkbox
        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK)); // Tạo viền đen cho checkbox
        this.setPreferredSize(new Dimension(50, 50)); // Đặt kích thước cho checkbox
        this.setFont(new Font("Arial", Font.PLAIN, 20)); // Đặt font cho số trong checkbox
        this.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setBackground(java.awt.Color.CYAN); // Đổi màu nền khi được chọn
                } else {
                    setBackground(null); // Xóa màu nền khi không được chọn
                }
            }
        });
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
        setEnabled(trangThai == 0);
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Seat Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 2));

        // Tạo các ô chọn chỗ ngồi và thêm vào frame
        for (int i = 1; i <= 9; i++) {
            Seat seat = new Seat(i);
            seat.setTrangThai(i % 2 == 0 ? 0 : 1); // Mỗi ô chẵn có trạng thái 0 (đã đặt), ô lẻ có trạng thái 1 (chưa
                                                   // đặt)
            frame.add(seat);
        }

        frame.pack();
        frame.setLocationRelativeTo(null); // Hiển thị frame ở giữa màn hình
        frame.setVisible(true);
    }
}
