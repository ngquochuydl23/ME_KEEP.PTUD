package ui.component;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class VeTau extends JPanel {
    private JLabel lblTieuDe;
    private JLabel lblTau;
    private JLabel lblGioDi;
    private JLabel lblGioDen;
    private JLabel lblDiemDi;
    private JLabel lblDiemDen;
    private JLabel lblNgayDi;
    private JLabel lblNgayDen;
    private JLabel lblThoiGianDi;

    public VeTau(String tieuDe, String tenTau, String gioDi, String gioDen, String diemDi, String diemDen,
            String ngayDi, String ngayDen, String thoiGianDi) {
        this.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridLayout(4, 2));

        lblTieuDe = new JLabel(tieuDe);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 16));
        lblTieuDe.setForeground(Color.WHITE);

        lblTau = new JLabel(tenTau);
        lblGioDi = new JLabel(gioDi);
        lblGioDen = new JLabel(gioDen);
        lblDiemDi = new JLabel(diemDi);
        lblDiemDen = new JLabel(diemDen);
        lblNgayDi = new JLabel(ngayDi);
        lblNgayDen = new JLabel(ngayDen);
        lblThoiGianDi = new JLabel(thoiGianDi);

        contentPanel.add(new JLabel("Tàu:"));
        contentPanel.add(lblTau);
        contentPanel.add(new JLabel("Giờ đi:"));
        contentPanel.add(lblGioDi);
        contentPanel.add(new JLabel("Giờ đến:"));
        contentPanel.add(lblGioDen);
        contentPanel.add(new JLabel("Điểm đi:"));
        contentPanel.add(lblDiemDi);
        contentPanel.add(new JLabel("Điểm đến:"));
        contentPanel.add(lblDiemDen);
        contentPanel.add(new JLabel("Ngày đi:"));
        contentPanel.add(lblNgayDi);
        contentPanel.add(new JLabel("Ngày đến:"));
        contentPanel.add(lblNgayDen);
        contentPanel.add(new JLabel("Thời gian di chuyển:"));
        contentPanel.add(lblThoiGianDi);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 184, 255)); // Màu nền tùy chỉnh
        headerPanel.add(lblTieuDe);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Viền xám

        // Tùy chỉnh kích thước
        this.setPreferredSize(new Dimension(400, 200));
    }

    public static void main(String[] args) {
        // Tạo một JFrame để chứa VeTau
        JFrame frame = new JFrame("VeTau Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        // Tạo một VeTau và thêm vào JFrame
        VeTau veTau = new VeTau("Chuyến đi", "Tàu SE12", "19:25", "09:20", "Ga Sài Gòn", "Ga Hà Nội", "12/04/2024",
                "14/04/2024", "37h55'");
        frame.getContentPane().add(veTau);

        frame.setVisible(true);
    }
}
