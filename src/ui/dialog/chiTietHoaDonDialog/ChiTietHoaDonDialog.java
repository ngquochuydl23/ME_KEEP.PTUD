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

    private InputForm hoTenNhanVienField;
    private InputForm maNhanVienField;
    private InputForm vatTextField;
    private InputForm soTienKhachDua;
    private HoaDonDao hoaDonDao;
    private JTable veTable;
    private DefaultTableModel veModel;
    private HoaDon hoaDon;
    private java.util.List<Slot> dsChoDaChon;
    private KhachHang khachHang;
    private double tongTien;
    private double tongTienGiam;

    private java.util.List<Ve> danhSachVe;

    public ChiTietHoaDonDialog(String maHoaDon) {
        dsChoDaChon = new ArrayList<>();
        danhSachVe = new ArrayList<>();
        hoaDonDao = new HoaDonDao();
        initComponents();
    }

    public void initComponents() {
        setSize(new Dimension(900, 700));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Tạo hóa đơn");
        pnlMain = new JPanel(new BorderLayout());
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

        soTienKhachDua = new InputForm("Số tiền khách đưa", 400, 70);
        soTienKhachDua.setBorder(new EmptyBorder(0, 5, 10, 5));
        soTienKhachDua
                .getTxtForm()
                .setEnabled(true);

        Box tienBox = Box.createVerticalBox();
        tienBox.setBackground(Color.white);
        tienBox.setBorder(new TitledBorder("Thông tin thanh toán"));
        tienBox.add(soTienTamTinhTextField);
        tienBox.add(vatTextField);
        tienBox.add(tongTienTextField);
        tienBox.add(soTienKhachDua);

        // -----------------KHACH HANG ----------------------
        maKhachHangTextField = new InputForm("Mã khách hàng", 100, 60);
        maKhachHangTextField
                .getTxtForm()
                .setEnabled(false);

        tenKhachHangTextField = new InputForm("Tên khách hàng", 760, 60);
        tenKhachHangTextField
                .getTxtForm()
                .setEnabled(false);

        soDienThoaiTextField = new InputForm("Số điện thoại", 400, 60);
        soDienThoaiTextField
                .getTxtForm()
                .setEnabled(false);

        cmndKhachHangTextField = new InputForm("Chứng minh nhân dân", 400, 60);
        cmndKhachHangTextField
                .getTxtForm()
                .setEnabled(false);

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
                .setEnabled(false);

        hoTenNhanVienField = new InputForm("Tên nhân viên", 760, 60);
        hoTenNhanVienField
                .getTxtForm()
                .setEnabled(false);

        JPanel maHoTenNVPanel = new JPanel(new FlowLayout());
        maHoTenNVPanel.setBackground(Color.white);
        maHoTenNVPanel.add(maNhanVienField);
        maHoTenNVPanel.add(hoTenNhanVienField);
        Box nhanVienPanel = Box.createVerticalBox();
        nhanVienPanel.setBackground(Color.white);
        nhanVienPanel.setBorder(new TitledBorder("Thông tin nhân viên bán hàng"));
        nhanVienPanel.add(maHoTenNVPanel);




        veModel = new DefaultTableModel("Mã vé;Tên người đi;CCCD người đi;Chỗ ngồi;Tên toa;Giá vé".split(";"), 0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        veTable = new JTable(veModel);
        veTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        veTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        veTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane veTablePanel = new JScrollPane(veTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        veTablePanel.setBorder(new TitledBorder("Thông tin vé"));

        Box mainBox = Box.createVerticalBox();
        mainBox.add(khachHangPanel);
        mainBox.add(nhanVienPanel);
        pnlMain.add(mainBox, BorderLayout.NORTH);
        pnlMain.add(veTablePanel, BorderLayout.CENTER);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);

        tiepTucBtn = new ButtonCustom("Tiếp tục", "success", 14, 100, 40);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14, 100, 40);

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

    }
}
