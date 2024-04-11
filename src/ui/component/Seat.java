package ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Seat extends JPanel {
    private int seatNumber;
    private boolean isSelected;
    private int tinhTrangVe;

    private MouseListener ml;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;

        this.setPreferredSize(new Dimension(50, 50));
        ml = new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeSelectSeat();
            }
        };
        this.addMouseListener(ml);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                setCursor(Cursor.getDefaultCursor()); // Trả lại con trỏ chuột mặc định
            }
        });
    }

    private void changeSelectSeat() {
        isSelected = !isSelected;
        repaint();
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

        if (this.getTinhTrangVe() == 1) {
            this.setEnabled(false);
            g2d.setColor(Color.GRAY);
            System.out.println(this.getMouseListeners());
            this.removeMouseListener(ml);
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

        Seat seat = new Seat(60);
        seat.setTinhTrangVe(1);
        seats.set(5, seat);
        return seats;
    }

    public void clearSelection() {
        isSelected = false;
        repaint(); // Vẽ lại nút sau khi cập nhật

    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getTinhTrangVe() {
        return tinhTrangVe;
    }

    public void setTinhTrangVe(int tinhTrangVe) {
        this.tinhTrangVe = tinhTrangVe;
    }
}