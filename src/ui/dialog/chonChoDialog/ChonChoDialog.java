package ui.dialog.chonChoDialog;

import ui.component.Carriage;
import ui.component.HeaderTitle;
import ui.component.ButtonCustom;
import ui.component.Cabin;
import ui.component.Seat;
import ui.dialog.khachHangDialog.KhachHangDialog;
import ui.dialog.khachHangDialog.TaoKhachHangListener;
import ui.dialog.timKhachHangDialog.TimKhachHangDialog;
import ui.dialog.timKhachHangDialog.TimKhachHangListener;

import javax.swing.*;

import dao.LoaiKhoangDao;
import dao.ToaDao;
import entity.KhachHang;
import entity.LoaiKhoang;
import entity.Tau;
import entity.ToaTau;
import entity.Ve;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChonChoDialog extends JDialog {
    private Carriage carriage;
    private JComboBox<Integer> cabinNumberComboBox;
    private ButtonGroup btgToa;
    private JPanel topPanel;
    private HeaderTitle headerTitle;

    private Tau tau;
    private JPanel seatPanel;
    private ToaDao toaDao;
    private LoaiKhoangDao loaiKhoangDao;
    private List<ToaTau> toaTaus;
    private LoaiKhoang loaiKhoang;
    private ChonChoNgoiListener chonChoNgoiListener;

    private ToaTau toaDangChon;

    public ChonChoDialog() {
        toaDao = new ToaDao();
        loaiKhoangDao = new LoaiKhoangDao();
        setTitle("Chọn chỗ");
        setModalityType(ModalityType.APPLICATION_MODAL);
        initializeComponents();
        setSize(600, 900);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    public void setTau(Tau tau) {
        this.tau = tau;

        toaTaus = toaDao.layToaTheoMaTau(this.tau.getMaTau());
        loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(toaTaus.get(0).getMaToa());

        doToaTauLenUI();
        updateSeatPanel();
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel chọn loại toa và khoang
        this.btgToa = new ButtonGroup();
        topPanel = new JPanel();
        //doToaTauLenUI();

        JLabel cabinLabel = new JLabel("Chọn số khoang:");
        cabinNumberComboBox = new JComboBox<>();
        topPanel.add(cabinLabel);
        topPanel.add(cabinNumberComboBox);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel chọn chỗ

        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 4));
        //updateSeatPanel();

        JScrollPane scrollPane = new JScrollPane(seatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new ButtonCustom("Xác nhận", "success", 14);
        JButton cancelButton = new ButtonCustom("Hủy bỏ", "danger", 14);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chonChoNgoiListener != null) {
                    System.out.println(toaDangChon.toString());
                    chonChoNgoiListener.chonChoNgoiThanhCong(layToanBoChoDangChon());
                }
                //dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        add(mainPanel);
    }

    private void doToaTauLenUI() {
        for (ToaTau toaTau : toaTaus) {
            final ToaTau currentToa = toaTau;
            Carriage carriage = new Carriage(toaTau);
            carriage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toaDangChon = currentToa;
                    loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(currentToa.getMaToa());
                    layToanBoChoDangChon();
                    updateSeatPanel();
                }
            });
            this.btgToa.add(carriage);
            this.topPanel.add(carriage);
        }

        if (this.toaDangChon == null) {
            this.toaDangChon = this.toaTaus.get(0);
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
        List<Cabin> cabins = carriage.getCabins(); // Lấy danh sách cabins từ carriage

        // Tạo layout với số cột phù hợp
        if (loaiToa.equals("giuong-nam-khoang-6") || loaiToa.equals("giuong-nam-khoang-4")) {
            GridLayout gridLayout = new GridLayout(0, 2);
            gridLayout.setHgap(10); // Khoảng trống giữa các cột
            gridLayout.setVgap(10); // Khoảng trống giữa các hàng
            seatPanel.setLayout(gridLayout);
        } else if (loaiToa.equals("ghe-ngoi")) {
            GridLayout gridLayout = new GridLayout(0, 4);
            gridLayout.setHgap(10); // Khoảng trống giữa các cột
            gridLayout.setVgap(10); // Khoảng trống giữa các hàng
            seatPanel.setLayout(gridLayout);
        }

        for (Cabin cabin : cabins) { // Lặp qua từng cabin
            addSeatsToPanel(cabin.getSeats()); // Thêm tất cả các ghế của cabin vào panel

            seatPanel.add(new JPanel());
            seatPanel.add(new JPanel());
        }
        revalidate();
        repaint();
    }

    private void addSeatsToPanel(List<Seat> seats) {
        for (Seat seat : seats) {
            seatPanel.add(seat);
        }
    }

    public List<Ve> layToanBoChoDangChon() {
        List<Seat> dsChoNgoi = new ArrayList<>();
        for (Cabin cabin : carriage.getCabins()) {
            dsChoNgoi = cabin.getSeats();
        }
        List<Ve> dsVe = new ArrayList<>();
        for (Seat seat : dsChoNgoi) {
            if (seat.isSelected()) {
                dsVe.add(new Ve());
            }
        }
        return dsVe;
    }

    public void setChonChoNgoiListener(ChonChoNgoiListener chonChoNgoiListener) {
        this.chonChoNgoiListener = chonChoNgoiListener;
    }

    @Override
    public void setVisible(boolean b) {
        clearSeatSelection();
        super.setVisible(b);
    }
}
