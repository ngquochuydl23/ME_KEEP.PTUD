package ui.dialog.chonChoDialog;

import dao.KhoangDao;
import entity.*;
import ui.component.*;

import javax.swing.*;

import dao.LoaiKhoangDao;
import dao.ToaTauDao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChonChoDialog extends JDialog {
    private ToaTauBtn toaTauBtn;
    private SelectForm loaiKhoangCbx;
    private SelectForm khoangCbx;
    private ButtonGroup btgToa;
    private JPanel topPanel;
    private Tau tau;
    private JPanel seatPanel;
    private ToaTauDao toaTauDao;
    private KhoangDao khoangDao;
    private LoaiKhoangDao loaiKhoangDao;
    private List<ToaTau> danhSachToaTau;
    private List<LoaiKhoang> danhSachLoaiKhoang;
    private LoaiKhoang loaiKhoang;
    private List<Khoang> dsKhoang;

    private ToaTau toaTauDangChon;
    private ChonChoNgoiListener chonChoNgoiListener;

    private ToaTau toaDangChon;

    public ChonChoDialog() {
        setTitle("Chọn chỗ");
        setModalityType(ModalityType.APPLICATION_MODAL);
        initializeComponents();
        setSize(600, 900);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        toaTauDao = new ToaTauDao();
        loaiKhoangDao = new LoaiKhoangDao();
        khoangDao = new KhoangDao();

        doDuLieuLoaiKhoang();
    }

    public void setTau(Tau tau) {
        this.tau = tau;

        dsKhoang = new ArrayList<>();
        danhSachToaTau = toaTauDao.layToaTheoMaTau(this.tau.getMaTau());
        loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(danhSachToaTau.get(0).getMaToa());

        this.doToaTauLenUI();
        updateSeatPanel();
        pack();
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        btgToa = new ButtonGroup();
        topPanel = new JPanel();

        loaiKhoangCbx = new SelectForm("Loại khoang");
        // topPanel.add(loaiKhoangCbx);
        // topPanel.add(khoangCbx);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel chọn chỗ

        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 4));
        // updateSeatPanel();

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
                // dispose();
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
        for (ToaTau toaTau : danhSachToaTau) {
            final ToaTau currentToa = toaTau;
            ToaTauBtn carriage = new ToaTauBtn(toaTau);
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
            this.toaDangChon = this.danhSachToaTau.get(0);
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
                toaTauBtn = ToaTauBtn.createCarriageWith50Seats();
                break;
            case "giuong-nam-khoang-6":
                toaTauBtn = ToaTauBtn.createCarriageWith8Cabins6Seats();
                break;
            case "giuong-nam-khoang-4":
                toaTauBtn = ToaTauBtn.createCarriageWith8Cabins4Seats();
                break;
            default:
                toaTauBtn = ToaTauBtn.createCarriageWith50Seats();
                return;
        }
        seatPanel.removeAll(); // Xóa tất cả các ghế hiện tại trên panel
        clearSeatSelection(); // Xóa lựa chọn trên các ghế
        List<KhoangBtn> cabins = toaTauBtn.getCabins(); // Lấy danh sách cabins từ carriage

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

        for (KhoangBtn cabin : cabins) { // Lặp qua từng cabin
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

    public List<Integer> layToanBoChoDangChon() {
        List<Seat> dsChoNgoi = new ArrayList<>();
        for (KhoangBtn cabin : toaTauBtn.getCabins()) {
            dsChoNgoi = cabin.getSeats();
        }
        List<Integer> dsCho = new ArrayList<>();
        for (Seat seat : dsChoNgoi) {
            if (seat.isSelected()) {
                dsCho.add(seat.getSeatNumber());
            }
        }
        return dsCho;
    }

    public void setChonChoNgoiListener(ChonChoNgoiListener chonChoNgoiListener) {
        this.chonChoNgoiListener = chonChoNgoiListener;
    }

    @Override
    public void setVisible(boolean b) {
        clearSeatSelection();
        super.setVisible(b);
    }

    private void doDuLieuLoaiKhoang() {
        danhSachLoaiKhoang = loaiKhoangDao.layHet();
        loaiKhoangCbx.setCbItems(danhSachLoaiKhoang.stream().map(item -> (String) item.getTenLoaiKhoang()).toList());
    }

}
