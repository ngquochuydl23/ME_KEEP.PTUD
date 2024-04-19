package ui.component;

import entity.Slot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class SlotBtn extends JPanel {
    private boolean isSelected;
    private String maToaTau;
    private Slot slot;

    @Override
    public String toString() {
        return "SlotBtn{" +
                "maToaTau='" + maToaTau + '\'' +
                ", slot=" + slot +
                '}' +"\n";
    }

    private MouseListener ml;

    public String getMaToaTau() {
        return maToaTau;
    }

    public void setMaToaTau(String maToaTau) {
        this.maToaTau = maToaTau;
    }

    public SlotBtn(String maToaTau, Slot slot, boolean isSelected) {
        this.maToaTau = maToaTau;
        this.slot = slot;
        this.isSelected = isSelected;

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


    public SlotBtn(String maToaTau, Slot slot) {
        this(maToaTau , slot, false);
    }

    private void changeSelectSeat() {
        isSelected = !isSelected;
        repaint();
    }

    public Slot getSlot() {
        return slot;
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

        if (this.getSlot().getTinhTrang() == 0) {
            this.setEnabled(false);
            g2d.setColor(Color.GRAY);
            this.removeMouseListener(ml);
        }

        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Vẽ nền của nút

        g2d.setColor(Color.BLACK); // Đặt màu chữ
        FontMetrics fm = g2d.getFontMetrics();
        String text = String.valueOf(slot.getSoSlot());
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - fm.getDescent();
        g2d.drawString(text, x, y); // Vẽ số lên trung tâm của nút

        g2d.setColor(Color.GRAY); // Đổi màu viền
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Vẽ viền của nút
        g2d.dispose();
    }


    public static List<SlotBtn> createSeats(String maToaTau, List<Slot> dsChoNgoi) {
        List<SlotBtn> seats = new ArrayList<>();
        for (Slot item : dsChoNgoi) {
            seats.add(new SlotBtn(maToaTau, item));
        }
        return seats;
    }

    public void clearSelection() {
        isSelected = false;
        repaint();
    }
}