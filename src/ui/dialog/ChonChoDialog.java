package ui.dialog;

import ui.component.Carriage;
import ui.component.Cabin;
import ui.component.Seat;

import javax.swing.*;

import dao.LoaiKhoangDao;
import dao.ToaDao;
import entity.LoaiKhoang;
import entity.Tau;
import entity.ToaTau;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChonChoDialog extends JDialog {
    private Carriage carriage;
    private JComboBox<String> carriageTypeComboBox;
    private JComboBox<Integer> cabinNumberComboBox;
    private ButtonGroup btgToa;
    private JPanel topPanel;

    private Tau tau;
    private JPanel seatPanel;
    private ToaDao toaDao;
    private LoaiKhoangDao loaiKhoangDao;
    List<ToaTau> toaTaus;
    LoaiKhoang loaiKhoang;

    public ChonChoDialog(Tau tau) {
        this.tau = tau;
        toaDao = new ToaDao();
        loaiKhoangDao = new LoaiKhoangDao();

        initializeComponents();
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(this);
    }

    private void initializeComponents() {
        this.toaTaus = this.toaDao.layToaTheoMaTau(this.tau.getMaTau());
        loaiKhoang = this.loaiKhoangDao.layLoaiKhoangTheoMaToa(this.toaTaus.get(0).getMaToa());
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel chọn loại toa và khoang
        this.btgToa = new ButtonGroup();
        topPanel = new JPanel();
        doToaTauLenUI();

        // JLabel carriageLabel = new JLabel("Chọn loại toa:");
        // setLayout(new GridLayout(carriageTypes.length, 1)); // Số hàng là độ dài của
        // mảng carriageTypes

        // carriageTypeComboBox.addActionListener(new ActionListener() {
        // @Override
        // public void actionPerformed(ActionEvent e) {
        // updateCabinComboBox();
        // updateSeatPanel();
        // }
        // });
        // topPanel.add(carriageLabel);
        // topPanel.add(carriageButtons);

        JLabel cabinLabel = new JLabel("Chọn số khoang:");
        cabinNumberComboBox = new JComboBox<>();
        topPanel.add(cabinLabel);
        topPanel.add(cabinNumberComboBox);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel chọn chỗ
        seatPanel = new JPanel(); // Panel chứa các chỗ ngồi
        seatPanel.setLayout(new GridLayout(0, 4));
        updateSeatPanel();

        mainPanel.add(seatPanel, BorderLayout.CENTER);
        mainPanel.setPreferredSize(new Dimension(1000, 800)); // Đặt kích thước ưu tiên cho panel chọn chỗ
        add(mainPanel);
    }

    private void doToaTauLenUI() {
        for (ToaTau toaTau : toaTaus) {
            final ToaTau currentToa = toaTau; // Biến cục bộ final để lưu trữ giá trị của toaTau

            Carriage carriage = new Carriage(toaTau);
            carriage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(currentToa.getMaToa());
                    updateSeatPanel();
                }
            });

            this.btgToa.add(carriage);
            this.topPanel.add(carriage);
        }
    }

    // Phương thức để xóa lựa chọn trên các chỗ ngồi
    private void clearSeatSelection() {
        Component[] components = seatPanel.getComponents();
        for (Component component : components) {
            if (component instanceof Seat) {
                ((Seat) component).clearSelection();
            }
        }
    }

    // Cập nhật panel chọn chỗ dựa trên loại toa và khoang đã chọn
    private void updateSeatPanel() {
        String loaiToa = this.loaiKhoang.getMaLoaiKhoang();
        if (loaiToa == null) {
            carriage = Carriage.createCarriageWith8Cabins6Seats();
        }
        switch (loaiToa) {
            case "ghe-ngoi":
                carriage = Carriage.createCarriageWith50Seats();
                break;
            case "giuong-nam-khoang-6":
                carriage = Carriage.createCarriageWith8Cabins6Seats();
                break;
            case "giuong-nam-khoang-4":
                carriage = Carriage.createCarriageWith8Cabins4Seats();
                break;
            default:
                carriage = Carriage.createCarriageWith50Seats();
                return;
        }
        seatPanel.removeAll(); // Xóa tất cả các ghế hiện tại trên panel
        clearSeatSelection(); // Xóa lựa chọn trên các ghế
        // Toa 1 và 2 chỉ có một khoang, nên hiển thị số chỗ của khoang đầu tiên
        addSeatsToPanel(carriage.getCabins().get(0).getSeats());
        // Các toa khác có nhiều khoang, hiển thị số chỗ của khoang được chọn
        List<Cabin> cabins = carriage.getCabins();
        addSeatsToPanel(carriage.getCabins().get(0).getSeats());

        // Cập nhật giao diện
        revalidate();

        repaint();

    }

    // Cập nhật ComboBox số khoang tương ứng với loại toa được chọn
    // Cập nhật ComboBox số khoang tương ứng với loại toa được chọn
    private void updateCabinComboBox() {
        int selectedCarriageIndex = carriageTypeComboBox.getSelectedIndex();
        int numCabins = 1; // Mặc định là một khoang
        switch (selectedCarriageIndex) {
            case 0:
            case 1:
                numCabins = 1; // Toa 1 và 2 chỉ có một khoang
                cabinNumberComboBox.setVisible(false); // Ẩn ComboBox số khoang
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                numCabins = 8; // Các toa khác có 8 khoang
                cabinNumberComboBox.setVisible(true); // Hiện ComboBox số khoang
                break;
        }

        cabinNumberComboBox.removeAllItems();
        for (int i = 1; i <= numCabins; i++) {
            cabinNumberComboBox.addItem(i);
        }

        // Xóa lựa chọn trên các chỗ ngồi
        clearSeatSelection();
    }

    // Thêm các chỗ ngồi vào panel
    private void addSeatsToPanel(List<Seat> seats) {
        for (Seat seat : seats) {
            seatPanel.add(seat);
        }
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(() -> {
    // JFrame frame = new JFrame("Transparent Button Example");
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // ChonChoDialog choDialog = new ChonChoDialog()
    // });
    // }
}