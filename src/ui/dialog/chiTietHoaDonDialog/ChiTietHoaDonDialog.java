package ui.dialog.chiTietHoaDonDialog;

import dao.HoaDonDao;
import dao.LoaiKhoangDao;
import dao.TuyenDao;
import entity.*;
import helper.Formater;
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

public class ChiTietHoaDonDialog extends JDialog {
    private String maHoaDon;
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlButtom;
    private ButtonCustom  dongBtn;
    private InputForm tenKhachHangTextField;
    private InputForm cmndKhachHangTextField;
    private InputForm maKhachHangTextField;
    private InputForm soDienThoaiTextField;
    private InputForm soTienTamTinhTextField;
    private InputForm tongTienTextField;

    private InputForm hoTenNhanVienField;
    private InputForm maNhanVienField;
    private InputForm vatTextField;
    private HoaDonDao hoaDonDao;
    private JTable veTable;
    private DefaultTableModel veModel;


    public ChiTietHoaDonDialog(String maHoaDon) {
        this.maHoaDon = maHoaDon;

        hoaDonDao = new HoaDonDao();
        initComponents();
        getHoaDon(maHoaDon);
    }

    public void initComponents() {
        setSize(new Dimension(900, 750));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Hóa đơn: " + maHoaDon);
        pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBackground(Color.white);


        // -----------TAM TINH---------

        soTienTamTinhTextField = new InputForm("Số tiền tạm tính", 400, 70);
        soTienTamTinhTextField
                .getTxtForm()
                .setEnabled(false);

        vatTextField = new InputForm("Thuế VAT", 400, 70);
        vatTextField.setText("10%");
        vatTextField
                .getTxtForm()
                .setEnabled(false);

        tongTienTextField = new InputForm("Tổng tiền", 400, 70);
        tongTienTextField
                .getTxtForm()
                .setEnabled(false);

        Box tienBox = Box.createVerticalBox();
        tienBox.setBackground(Color.white);
        tienBox.setBorder(new TitledBorder("Thông tin thanh toán"));
        tienBox.add(soTienTamTinhTextField);
        tienBox.add(vatTextField);
        tienBox.add(tongTienTextField);

        // -----------------KHACH HANG ----------------------
        maKhachHangTextField = new InputForm("Mã khách hàng", 100, 60);
        maKhachHangTextField
                .getTxtForm()
                .setEditable(false);

        tenKhachHangTextField = new InputForm("Tên khách hàng", 760, 60);
        tenKhachHangTextField
                .getTxtForm()
                .setEditable(false);

        soDienThoaiTextField = new InputForm("Số điện thoại", 400, 60);
        soDienThoaiTextField
                .getTxtForm()
                .setEditable(false);

        cmndKhachHangTextField = new InputForm("Chứng minh nhân dân", 400, 60);
        cmndKhachHangTextField
                .getTxtForm()
                .setEditable(false);

        JPanel maHoTenKHPanel = new JPanel(new FlowLayout());
        maHoTenKHPanel.setBackground(Color.white);
        maHoTenKHPanel.add(maKhachHangTextField);
        maHoTenKHPanel.add(tenKhachHangTextField);
        Box khachHangPanel = Box.createVerticalBox();
        khachHangPanel.setBackground(Color.white);
        khachHangPanel.setBorder(new TitledBorder("Thông tin khách hàng"));
        khachHangPanel.add(maHoTenKHPanel);
        khachHangPanel.add(soDienThoaiTextField);
        khachHangPanel.add(cmndKhachHangTextField);


        maNhanVienField = new InputForm("Mã nhân viên", 100, 60);
        maNhanVienField
                .getTxtForm()
                .setEditable(false);

        hoTenNhanVienField = new InputForm("Tên nhân viên", 760, 60);
        hoTenNhanVienField
                .getTxtForm()
                .setEditable(false);

        JPanel maHoTenNVPanel = new JPanel(new FlowLayout());
        maHoTenNVPanel.setBackground(Color.white);
        maHoTenNVPanel.add(maNhanVienField);
        maHoTenNVPanel.add(hoTenNhanVienField);
        Box nhanVienPanel = Box.createVerticalBox();
        nhanVienPanel.setBackground(Color.white);
        nhanVienPanel.setBorder(new TitledBorder("Thông tin nhân viên bán hàng"));
        nhanVienPanel.add(maHoTenNVPanel);


        veModel = new DefaultTableModel("Mã vé;Tên người đi;CCCD người đi;Chỗ ngồi;Tên khoang;Giá vé".split(";"), 0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        veTable = new JTable(veModel);

        JPanel veTablePanel = new JPanel(new BorderLayout());
        veTablePanel.setBorder(new TitledBorder("Thông tin vé"));
        veTablePanel.setBackground(Color.white);
        JScrollPane scrollPane = new JScrollPane(veTable);
        scrollPane.setPreferredSize(scrollPane.getPreferredSize());
        veTablePanel.add(scrollPane);

        Box mainBox = Box.createVerticalBox();
        mainBox.add(khachHangPanel);
        mainBox.add(nhanVienPanel);

        pnlMain.add(mainBox);
        pnlMain.add(veTablePanel);
        pnlMain.add(tienBox);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);

        dongBtn = new ButtonCustom("Đóng", "danger", 14, 100, 40);

        dongBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                xoaDuLieu();
                dispose();
            }
        });


        pnlButtom.add(dongBtn);

        add(titlePage, BorderLayout.NORTH);
        add(new JScrollPane(pnlMain, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }


    private void xoaDuLieu() {

        maKhachHangTextField.setText("");
        tenKhachHangTextField.setText("");
        soDienThoaiTextField.setText("");
        cmndKhachHangTextField.setText("");
        soTienTamTinhTextField.setText("");
        tongTienTextField.setText("");
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

    private void getHoaDon(String maHoaDon) {
        HoaDon hoaDon = hoaDonDao.layTheoMa(maHoaDon);

        KhachHang khachHang = hoaDon.getKhachHang();
        maKhachHangTextField.setText(String.valueOf(khachHang.getMaKhachHang()));
        maKhachHangTextField.setEditable(false);
        tenKhachHangTextField.setText(khachHang.getHoTen());
        tenKhachHangTextField.setEditable(false);
        soDienThoaiTextField.setText(khachHang.getSoDienThoai());
        soDienThoaiTextField.setEditable(false);
        cmndKhachHangTextField.setText(khachHang.getCMND());
        cmndKhachHangTextField.setEditable(false);


        NhanVien nhanVienBanHang = hoaDon.getNhanVien();
        maNhanVienField.setText(String.valueOf(nhanVienBanHang.getMaNhanVien()));
        maNhanVienField.setEditable(false);
        hoTenNhanVienField.setText(nhanVienBanHang.getHoTen());
        hoTenNhanVienField.setEditable(false);

        for (ChiTietHoaDon cthd : hoaDonDao.layHetChiTietHoaDonTheoMaHoaDon(maHoaDon)) {
            Ve ve = cthd.getVe();

            veModel.addRow(new String[] {
                ve.getMaVe(),
                    ve.getHoTenNguoiDi(),
                    ve.getCccdNguoiDi(),
                    "Chỗ ngồi " + ve.getSlot().getSoSlot(),
                    ve.getSlot().getKhoang().getTenKhoang(),
                    Formater.FormatVND(cthd.getDonGia())
            });
        }


        soTienTamTinhTextField.setText(Formater.FormatVND(hoaDon.getTamTinh()));
        vatTextField.setText((hoaDon.getVat() * 100) + "%");
        tongTienTextField.setText(Formater.FormatVND(hoaDon.getTongTien()));
    }
}
