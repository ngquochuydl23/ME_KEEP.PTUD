package ui.dialog.traVeDialog;

import dao.HoaDonDao;
import dao.LoaiKhoangDao;
import dao.TuyenDao;
import entity.*;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;
import ui.component.SlotBtn;
import ui.dialog.chiTietVeDialog.CapNhatVeListener;
import ui.dialog.chiTietVeDialog.ChiTietVeDialog;
import ui.dialog.thanhToanDialog.ThanhToanListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TraVeDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlButtom;
    private ButtonCustom tiepTucBtn, btnHuyBo;
    private InputForm tenKhachHangTextField;
    private InputForm cmndKhachHangTextField;
    private InputForm maKhachHangTextField;
    private InputForm soDienThoaiTextField;
    private InputForm soTienTamTinhTextField;
    private InputForm tongTienTextField;
    private InputForm vatTextField;
    private InputForm soTienKhachDua;
    private HoaDonDao hoaDonDao;
    private JTable veTable;
    private DefaultTableModel veModel;
    private HoaDon hoaDon;
    private List<Slot> dsChoDaChon;
    private KhachHang khachHang;
    private double tongTien;
    private double tongTienGiam;
    private TuyenDao tuyenDao;
    private LoaiKhoangDao loaiKhoangDao;
    private List<Ve> danhSachVe;
    private double tongTienTamTinh;
    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    private ThanhToanListener thanhToanListener;
    private ToaTau toaTau;
    private ChiTietVeDialog chiTietVeDialog;
    private int soVeDaCapNhat = 0;

    public TraVeDialog() {

        dsChoDaChon = new ArrayList<>();
        danhSachVe = new ArrayList<>();
        hoaDonDao = new HoaDonDao();
        tuyenDao = new TuyenDao();
        loaiKhoangDao = new LoaiKhoangDao();
        initComponents();
    }

    public void initComponents() {
        setSize(new Dimension(900, 700));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Tạo hóa đơn");
        pnlMain = new JPanel(new BorderLayout());
        pnlMain.setBackground(Color.white);

        // -----------------KHACH HANG ----------------------
        maKhachHangTextField = new InputForm("Mã khách hàng", 100, 70);
        maKhachHangTextField
                .getTxtForm()
                .setEnabled(false);

        tenKhachHangTextField = new InputForm("Tên khách hàng", 285, 70);
        tenKhachHangTextField
                .getTxtForm()
                .setEnabled(false);

        soDienThoaiTextField = new InputForm("Số điện thoại", 400, 70);
        soDienThoaiTextField
                .getTxtForm()
                .setEnabled(false);


        cmndKhachHangTextField = new InputForm("Chứng minh nhân dân", 400, 70);
        cmndKhachHangTextField
                .getTxtForm()
                .setEnabled(false);

        Box maHoTenKHPanel = Box.createHorizontalBox();
        maHoTenKHPanel.setBackground(Color.white);
        maHoTenKHPanel.add(maKhachHangTextField);
        maHoTenKHPanel.add(tenKhachHangTextField);


        Box khachHangPanel = Box.createVerticalBox();
        khachHangPanel.setBackground(Color.white);
        khachHangPanel.setBorder(new TitledBorder("Thông tin khách hàng"));
        khachHangPanel.add(maHoTenKHPanel);
        khachHangPanel.add(soDienThoaiTextField);
        khachHangPanel.add(cmndKhachHangTextField);

        veModel = new DefaultTableModel("Mã vé;Tên người đi;CCCD người đi;Chỗ ngồi;Tên toa;Giá vé".split(";"), 0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        veTable = new JTable(veModel);
        veTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        veTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        veTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane veTablePanel = new JScrollPane(veTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        veTablePanel.setBorder(new TitledBorder("Thông tin vé"));

        pnlMain.add(khachHangPanel, BorderLayout.NORTH);
        pnlMain.add(Box.createVerticalStrut(20));
        pnlMain.add(veTablePanel, BorderLayout.CENTER);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);

        tiepTucBtn = new ButtonCustom("Tiếp tục", "success", 14, 100, 40);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14, 100, 40);

        tiepTucBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnHuyBo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn hủy thanh toán?") == 0) {
                    xoaDuLieu();
                    dispose();
                }
            }
        });

        tiepTucBtn.setEnabled(true);

        pnlButtom.add(tiepTucBtn);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private void xoaDuLieu() {
        danhSachVe.clear();
        toaTau = null;
        khachHang = null;

        maKhachHangTextField.setText("");
        tenKhachHangTextField.setText("");
        soDienThoaiTextField.setText("");
        cmndKhachHangTextField.setText("");
    }

    @Override
    public void dispose() {
        xoaDuLieu();
        super.dispose();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (!b) {
            xoaDuLieu();
        }
    }

    private void tinhTongTien() {
        tongTienGiam = 0;
        tongTien = tongTienTamTinh + (tongTienTamTinh * 0.1);
        tongTienTextField.setText(currencyFormatter.format(tongTien));
    }
}
