package ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Seat extends JPanel {
    private int seatNumber;
    private boolean isSelected;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.setPreferredSize(new Dimension(50, 50)); // Đặt kích thước cho nút
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                isSelected = !isSelected; // Đảo trạng thái chọn khi nhấn nút
                repaint(); // Vẽ lại nút sau khi cập nhật
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Đổi con trỏ chuột thành hình bàn tay
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setCursor(Cursor.getDefaultCursor()); // Trả lại con trỏ chuột mặc định
            }
        });
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (isSelected) {
            g2d.setColor(new Color(199, 18, 190)); // Đổi màu nền khi được chọn
        } else {
            g2d.setColor(Color.WHITE); // Xóa màu nền khi không được chọn
        }
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Vẽ nền của nút

        g2d.setColor(Color.BLACK); // Đặt màu chữ
        FontMetrics fm = g2d.getFontMetrics();
        String text = String.valueOf(seatNumber);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - fm.getDescent();
        g2d.drawString(text, x, y); // Vẽ số lên trung tâm của nút

        g2d.setColor(Color.GRAY); // Đổi màu viền
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Vẽ viền của nút
        g2d.dispose();
    }

    // Tạo danh sách các ghế với số lượng ghế mong muốn
    public static List<Seat> createSeats(int numSeats) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= numSeats; i++) {
            seats.add(new Seat(i));
        }
        return seats;
    }

    public void clearSelection() {
        isSelected = false;
        repaint(); // Vẽ lại nút sau khi cập nhật

    }
}