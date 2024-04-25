package ui.dialog.thanhToanDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import ui.component.SlotBtn;
import ui.dialog.chiTietVeDialog.CapNhatVeListener;
import ui.dialog.chiTietVeDialog.ChiTietVeDialog;

public class ThanhToanDialog extends JDialog {
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
    private NhanVien nhanVien;
    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    private ThanhToanListener thanhToanListener;
    private ToaTau toaTau;
    private ChiTietVeDialog chiTietVeDialog;
    private int soVeDaCapNhat = 0;

    public ThanhToanDialog() {
        chiTietVeDialog = new ChiTietVeDialog();
        chiTietVeDialog.setCapNhatVeListener(new CapNhatVeListener() {
            @Override
            public void capNhatVe(Ve ve) {
                int row = veTable.getSelectedRow();
                if (row >= 0) {
                    soVeDaCapNhat++;
                    danhSachVe.set(row, ve);
                    veModel.setValueAt(ve.getHoTenNguoiDi(), row, 1);
                    veModel.setValueAt(ve.getCccdNguoiDi(), row, 2);
                }
            }
        });

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
        khachHangPanel.add(cmndKhachHangTextField);

        JPanel khVaTien = new JPanel(new FlowLayout());
        khVaTien.setBackground(Color.white);
        khVaTien.add(khachHangPanel);
        khVaTien.add(tienBox);

        veModel = new DefaultTableModel("Mã vé;Tên người đi;CCCD người đi;Chỗ ngồi;Tên toa;Giá vé".split(";"), 0);

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

        tiepTucBtn = new ButtonCustom("Tiếp tục", "success", 14, 100, 40);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14, 100, 40);

        tiepTucBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taoHoaDonTuVe();
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

    public void setData(
            NhanVien nhanVien,
            String tenGaDi,
            String tenGaDen,
            Tau tau,
            ToaTau toaTau,
            KhachHang khachHang,
            List<SlotBtn> dsCho) {
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.toaTau = toaTau;

        maKhachHangTextField.getTxtForm().setText(String.valueOf(khachHang.getMaKhachHang()));
        tenKhachHangTextField.getTxtForm().setText(khachHang.getHoTen());
        soDienThoaiTextField.getTxtForm().setText(khachHang.getSoDienThoai());
        cmndKhachHangTextField.getTxtForm().setText(khachHang.getCMND());
        Tuyen tuyen = tuyenDao.timTuyenTheoTenGa(tenGaDi, tenGaDen);
        while (veModel.getRowCount() > 0)
            veModel.removeRow(0);

        tongTienTamTinh = 0.0;
        dsChoDaChon = dsCho
                .stream()
                .map(slotBtn -> slotBtn.getSlot())
                .toList();

        for (Slot slot : dsChoDaChon) {
            Date date= new Date();
            String maGaDi = tuyen.getGaDi().getMaGa();
            String maGaDen = tuyen.getGaDen().getMaGa();
            String maVe = maGaDi +"-"+ maGaDen + "-" + slot.getMaSlot() + "-" + new Timestamp(date.getTime());
            Ve ve = new Ve(maVe, slot, khachHang, tuyen, tau, null, null, 0, 1);
            LoaiKhoang loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(toaTau.getMaToa());
            double giaVe = ve.tinhGiaBanVe(loaiKhoang.getMaLoaiKhoang());
            tongTienTamTinh += giaVe;
            danhSachVe.add(ve);
            veTable.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = veTable.getSelectedRow();
                    if (row >= 0) {


                        chiTietVeDialog.setVe(loaiKhoang, toaTau, danhSachVe.get(row));
                        chiTietVeDialog.setVisible(true);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            veModel.addRow(new String[]{
                    maVe,
                    ve.getHoTenNguoiDi() != null ? ve.getHoTenNguoiDi() : "Chưa có",
                    ve.getCccdNguoiDi() != null ? ve.getCccdNguoiDi() : "Chưa có",
                    String.valueOf(slot.getSoSlot()),
                    toaTau.getTenToa(),
                    String.valueOf(giaVe)
            });
        }


        soTienTamTinhTextField.setText(currencyFormatter.format(tongTienTamTinh));
        tinhTongTien();
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

    private void tinhTongTien() {
        tongTienGiam = 0;
        tongTien = tongTienTamTinh + (tongTienTamTinh * 0.1);
        tongTienTextField.setText(currencyFormatter.format(tongTien));
    }

    private void taoHoaDonTuVe()  {

        if (soVeDaCapNhat < danhSachVe.size()) {
            JOptionPane.showMessageDialog(this, "Vui lòng cập nhật đủ vé trước khi thanh toán");
            return;
        }

        try {
            Date date= new Date();

            hoaDon = new HoaDon(
                    "HD"+"-KH"+khachHang.getMaKhachHang() +"-" + new Timestamp(date.getTime()),
                    LocalDateTime.now(),
                    "",
                    0.1,
                    tongTien,
                    tongTienTamTinh,
                    tongTienGiam,
                    khachHang,
                    nhanVien,
                    null);
            hoaDonDao.them(hoaDon);


            List<ChiTietHoaDon> dsChiTietHoaDon = new ArrayList<>();
            for (Ve ve: danhSachVe) {
                LoaiKhoang loaiKhoang = loaiKhoangDao.layLoaiKhoangTheoMaToa(toaTau.getMaToa());
                double giaVe = ve.tinhGiaBanVe(loaiKhoang.getMaLoaiKhoang());
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, ve, giaVe);
                dsChiTietHoaDon.add(chiTietHoaDon);
            }

            if (hoaDonDao.taoHoaDon(dsChoDaChon, hoaDon, dsChiTietHoaDon, danhSachVe)) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                if (thanhToanListener != null){
                    thanhToanListener.thanhToanThanhCong(hoaDon);
                }
                xoaDuLieu();
                dispose();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setThanhToanListener(ThanhToanListener thanhToanListener) {
        this.thanhToanListener = thanhToanListener;
    }
}
