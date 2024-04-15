package ui.dialog.taoHoaDonDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.*;
import entity.*;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;

public class TaoHoaDonDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlKhachHang;
    private JPanel pnlButtom;

    private ButtonCustom tiepTucBtn, btnHuyBo, timBtn, xoaRongBtn;
    private InputForm tenKhachHangTextField;
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
    private List<Integer> dsCho;
    private KhachHang khachHang;

    private TuyenDao tuyenDao;
    private SlotDao slotDao;
    private LoaiKhoangDao loaiKhoangDao;

    public TaoHoaDonDialog() {
        dsCho = new ArrayList<>();

        slotDao = new SlotDao();
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


        // -----------TAM TINH---------

        soTienTamTinhTextField = new InputForm("Số tiền tạm tính", 400, 70);
        soTienTamTinhTextField
                .getTxtForm()
                .setEnabled(false);

        vatTextField = new InputForm("Thuế VAT", 400, 70);
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
                .setEnabled(false);

        Box tienBox = Box.createVerticalBox();
        tienBox.setBackground(Color.white);
        tienBox.setBorder(new TitledBorder("Thông tin thanh toán"));
        tienBox.add(soTienTamTinhTextField);
        tienBox.add(vatTextField);
        tienBox.add(tongTienTextField);
        tienBox.add(soTienKhachDua);

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

        JPanel maHoTenKHPanel = new JPanel(new FlowLayout());
        maHoTenKHPanel.setBackground(Color.white);
        maHoTenKHPanel.add(maKhachHangTextField);
        maHoTenKHPanel.add(tenKhachHangTextField);


        JPanel khachHangPanel = new JPanel(new FlowLayout());
        khachHangPanel.setPreferredSize(tienBox.getPreferredSize());
        khachHangPanel.setBackground(Color.white);
        khachHangPanel.setBorder(new TitledBorder("Thông tin khách hàng"));
        khachHangPanel.add(maHoTenKHPanel);
        khachHangPanel.add(soDienThoaiTextField);

        JPanel khVaTien = new JPanel(new FlowLayout());
        khVaTien.setBackground(Color.white);
        khVaTien.add(khachHangPanel);
        khVaTien.add(tienBox);

        veModel = new DefaultTableModel("Mã vé;Chỗ ngồi;Tên toa;Giá vé;Mô tả".split(";"), 0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        veTable = new JTable(veModel);
        veTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        veTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        veTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane veTablePanel = new JScrollPane(veTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        veTablePanel.setBorder(new TitledBorder("Thông tin vé"));

        pnlMain.add(khVaTien, BorderLayout.NORTH);
        pnlMain.add(veTablePanel, BorderLayout.CENTER);
        pnlMain.add(veTablePanel, BorderLayout.CENTER);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);

        xoaRongBtn = new ButtonCustom("Xóa rỗng", "danger", 14, 100, 40);
        timBtn = new ButtonCustom("Tìm", "success", 14, 100, 40);
        tiepTucBtn = new ButtonCustom("Tiếp tục", "success", 14, 100, 40);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14, 100, 40);

        // timBtn.addActionListener(this);
        // xoaRongBtn.addActionListener(this);
        // tiepTucBtn.addActionListener(this);
        // btnHuyBo.addActionListener(this);

        tiepTucBtn.setEnabled(false);

        pnlButtom.add(timBtn);
        pnlButtom.add(tiepTucBtn);
        pnlButtom.add(xoaRongBtn);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    public void setData(
            String tenGaDi,
            String tenGaDen,
            Tau tau,
            ToaTau toaTau,
            KhachHang khachHang,
            List<Integer> dsCho) {

        this.dsCho.addAll(dsCho);
        this.khachHang = khachHang;

        maKhachHangTextField.getTxtForm().setText(String.valueOf(khachHang.getMaKhachHang()));
        tenKhachHangTextField.getTxtForm().setText(khachHang.getHoTen());
        soDienThoaiTextField.getTxtForm().setText(khachHang.getSoDienThoai());

        Tuyen tuyen = tuyenDao.timTuyenTheoTenGa(tenGaDi, tenGaDen);
        while (veModel.getRowCount() > 0)
            veModel.removeRow(0);

        List<Ve> danhSachVe = new ArrayList<>();
        List<Slot> dsSlot = slotDao.laySlotTheoMaToaTauVaDsChoNgoi(toaTau.getMaToa(), dsCho);

        for (Slot slot : dsSlot) {

            String maGaDi = tuyen.getGaDi().getMaGa();
            String maGaDen = tuyen.getGaDen().getMaGa();
            String maVe = maGaDi + "-" + maGaDen + "-" + slot.getMaSlot();
            Ve ve = new Ve(maVe, slot, khachHang, tuyen, tau);
            LoaiKhoang loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(toaTau.getMaToa());
            double giaVe = ve.tinhGiaBanVe(loaiKhoang.getMaLoaiKhoang());
            danhSachVe.add(ve);
            System.out.println(slot.getSoSlot());

             veModel.addRow(new String[]{
                     maVe,
                     String.valueOf(slot.getSoSlot()),
                     toaTau.getTenToa(),
                     String.valueOf(giaVe),
                     ""
             });
        }
    }


    private void xoaDuLieu() {
        // danhSachVe.clear();
        khachHang = null;

        maKhachHangTextField.getTxtForm().setText("");
        tenKhachHangTextField.getTxtForm().setText("");
        soDienThoaiTextField.getTxtForm().setText("");
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


    // @Override
    // public void actionPerformed(ActionEvent e) {
    // Object eSource = e.getSource();

    // if (eSource.equals(timBtn) || eSource.equals(soDienThoaiTextField)) {

    // maKhachHangTextField.setText("");
    // tenKhachHangTextField.setText("");
    // tiepTucBtn.setEnabled(false);

    // String soDienThoai = soDienThoaiTextField.getText().trim();
    // if (Validation.isEmpty(soDienThoai)
    // || !Validation.kiemTraSoDienThoai(soDienThoai) && soDienThoai.length() != 10)
    // {

    // JOptionPane.showMessageDialog(
    // this,
    // "Số điện thoại không được rỗng và phải là 10 ký tự số",
    // "Cảnh báo !",
    // JOptionPane.WARNING_MESSAGE);
    // return;
    // }

    // khachHang = khachHangDao.timTheoSDT(soDienThoai);

    // if (khachHang == null) {

    // JOptionPane.showMessageDialog(
    // this,
    // "Không tìm thấy khách hàng. Tạo khách hàng mới ?",
    // "Cảnh báo !",
    // JOptionPane.WARNING_MESSAGE);
    // if (timKhachHangListener != null)
    // timKhachHangListener.khongTimThayKhachHang(soDienThoai);

    // dispose();
    // return;
    // }

    // soDienThoaiTextField.setText(khachHang.getSoDienThoai());
    // maKhachHangTextField.setText(String.valueOf(khachHang.getMaKhachHang()));
    // tenKhachHangTextField.setText(khachHang.getHoTen());
    // tiepTucBtn.setEnabled(true);
    // }

    // if (eSource.equals(xoaRongBtn)) {
    // xoaDuLieu();
    // }

    // if (eSource.equals(btnHuyBo)) {
    // xoaDuLieu();
    // dispose();
    // }

    // if (eSource.equals(tiepTucBtn)) {
    // if (timKhachHangListener != null && khachHang != null)
    // timKhachHangListener.timThayhachhang(khachHang);
    // dispose();
    // }
    // }

}
