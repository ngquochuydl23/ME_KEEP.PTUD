package ui.dialog;

import ui.component.Carriages;
import ui.component.Seat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChonChoDialog extends JDialog {
    private Carriages selectedCarriage;
    private JComboBox<String> carriageTypeComboBox;
    private JPanel seatPanel;

    public ChonChoDialog(Frame parent) {
        super(parent, "Chọn Chỗ", true);
        initializeComponents();
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel chọn loại khoang
        JPanel topPanel = new JPanel();
        JLabel label = new JLabel("Chọn loại khoang:");
        String[] carriageTypes = {"4 giường nằm", "6 giường nằm", "Chỗ ngồi"};
        carriageTypeComboBox = new JComboBox<>(carriageTypes);
        carriageTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSeatPanel();
            }
        });
        topPanel.add(label);
        topPanel.add(carriageTypeComboBox);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel chọn chỗ
        seatPanel = new JPanel(); // Panel chứa các chỗ ngồi
        seatPanel.setLayout(new GridLayout(0, 4)); // GridLayout với số hàng là 0 để tự động điều chỉnh số hàng
        selectedCarriage = Carriages.createFourSeaterCarriage(); // Mặc định là 4 giường nằm
        updateSeatPanel();

        mainPanel.add(seatPanel, BorderLayout.CENTER);
        mainPanel.setPreferredSize(new Dimension(1000, 800)); // Đặt kích thước ưu tiên cho panel chọn chỗ
        add(mainPanel);
    }

    // Cập nhật panel chọn chỗ dựa trên loại khoang đã chọn
    private void updateSeatPanel() {
        int selectedIndex = carriageTypeComboBox.getSelectedIndex();
        switch (selectedIndex) {
            case 0:
                selectedCarriage = Carriages.createFourSeaterCarriage();
                addSeatsToPanel(selectedCarriage.getSeats(), true);
                break;
            case 1:
                selectedCarriage = Carriages.createSixSeaterCarriage();
                addSeatsToPanel(selectedCarriage.getSeats(), true);
                break;
            case 2:
                selectedCarriage = Carriages.createFiftySeaterCarriage();
                addSeatsToPanel(selectedCarriage.getSeats(), false);
                break;
            default:
                return;
        }

        // Cập nhật giao diện
        revalidate();
        repaint();
    }

    // Thêm các chỗ ngồi vào panel và tùy chỉnh việc thêm chỗ trống giữa các ghế
    private void addSeatsToPanel(List<Seat> seats, boolean addSpaceBetweenSeats) {
        seatPanel.removeAll();
        for (Seat seat : seats) {
            seatPanel.add(seat);
            if (addSpaceBetweenSeats) {
                seatPanel.add(new JLabel()); // Thêm một khoảng trống (JLabel không có văn bản) vào giữa các chỗ ngồi
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() -> {
            ChonChoDialog dialog = new ChonChoDialog(frame);
            dialog.setVisible(true);
        });
    }
}
