package ui.dialog;

import ui.component.Carriage;
import ui.component.ButtonCustom;
import ui.component.Cabin;
import ui.component.Seat;
import ui.dialog.timKhachHangDialog.TimKhachHangDialog;
import javax.swing.*;
import dao.LoaiKhoangDao;
import dao.ToaDao;
import entity.LoaiKhoang;
import entity.Tau;
import entity.ToaTau;
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
    private Tau tau;
    private JPanel seatPanel;
    private ToaDao toaDao;
    private LoaiKhoangDao loaiKhoangDao;
    private List<ToaTau> toaTaus;
    private LoaiKhoang loaiKhoang;
	private int seatsPerCabin = 10;
	private int numberOfCabins = 8;
    private static List<Integer> soChoNgoi;

    public ChonChoDialog(Tau tau) {
        this.tau = tau;
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

    private void initializeComponents() {
        this.toaTaus = this.toaDao.layToaTheoMaTau(this.tau.getMaTau());
        loaiKhoang = this.loaiKhoangDao.layLoaiKhoangTheoMaToa(this.toaTaus.get(0).getMaToa());
        JPanel mainPanel = new JPanel(new BorderLayout());
        // Panel chọn loại toa và khoang
        this.btgToa = new ButtonGroup();
        topPanel = new JPanel();
        doToaTauLenUI();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        // Panel chọn chỗ
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 4));
        updateSeatPanel();
        JScrollPane scrollPane = new JScrollPane(seatPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new ButtonCustom("Xác nhận", "success", 14);
        JButton cancelButton = new ButtonCustom("Hủy bỏ", "danger", 14);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChonChoDialog.soChoNgoi = layToanBoChoDangChon();
                new TimKhachHangDialog();
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


    // Thêm các chỗ ngồi vào panel
    private void addSeatsToPanel(List<Seat> seats) {
        for (Seat seat : seats) {
            seatPanel.add(seat);
        }
    }

    public List<Integer> layToanBoChoDangChon() {
        List<Seat> dsChoNgoi = new ArrayList<>();
        for (Cabin cabin : carriage.getCabins()) {
            dsChoNgoi = cabin.getSeats();
        }
        List<Integer> dsSoChoNgoi = new ArrayList<>();
        for (Seat seat : dsChoNgoi) {
            if (seat.isSelected()) {
                dsSoChoNgoi.add(seat.getSeatNumber());
            }
        }
        return dsSoChoNgoi;
    }

    public static List<Integer> getSoChoNgoi() {
        return soChoNgoi;
    }

    public static void setSoChoNgoi(List<Integer> soChoNgoi) {
        ChonChoDialog.soChoNgoi = soChoNgoi;
    }
}
