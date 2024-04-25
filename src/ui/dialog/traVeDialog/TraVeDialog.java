package ui.dialog.traVeDialog;

import dao.*;
import entity.*;
import helper.Formater;
import models.TraVeModel;
import singleton.NhanVienSuDungSingleton;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;
import ui.component.SlotBtn;
import ui.dialog.chiTietVeDialog.CapNhatVeListener;
import ui.dialog.chiTietVeDialog.ChiTietVeDialog;
import ui.dialog.thanhToanDialog.ThanhToanListener;
import ui.panel.KhachHangPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TraVeDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlButtom;
    private ButtonCustom timBtn, btnHuyBo, huyVeDangChon;
    private InputForm tenKhachHangTextField;
    private InputForm cmndKhachHangTextField;
    private InputForm maKhachHangTextField;
    private InputForm soDienThoaiTextField;
    private KhachHangDao khachHangDao;
    private JTable veTable;
    private DefaultTableModel veModel;
    private KhachHang khachHang;
    private LichSuTraVeDao lichSuTraVeDao;
    private VeDao veDao;
    private List<TraVeModel> danhSachTraVe;


    public TraVeDialog() {

        veDao = new VeDao();
        danhSachTraVe = new ArrayList<>();
        khachHangDao = new KhachHangDao();
        lichSuTraVeDao = new LichSuTraVeDao();
        initComponents();
    }

    public void initComponents() {
        setSize(new Dimension(900, 700));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Trả vé");
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
        soDienThoaiTextField.setEnabled(true);
        soDienThoaiTextField.setEditable(true);
        soDienThoaiTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKhachHang();
            }
        });


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
        khachHangPanel.add(soDienThoaiTextField);
        khachHangPanel.add(maHoTenKHPanel);
        khachHangPanel.add(cmndKhachHangTextField);

        veModel = new DefaultTableModel("Mã vé;Tên người đi;Chỗ ngồi;Giá vé;Thời gian khởi hành;Loại trả vé;Phí trả vé".split(";"), 0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        veTable = new JTable(veModel);
        veTable.setRowSelectionAllowed(true);
        veTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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

        huyVeDangChon = new ButtonCustom("Hủy vé đang chọn","success", 14, 100, 40);
        timBtn = new ButtonCustom("Tìm", "success", 14, 100, 40);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14, 100, 40);
        huyVeDangChon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huyVeDangChon();
            }
        });
        timBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKhachHang();
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

        timBtn.setEnabled(true);

        pnlButtom.add(timBtn);
        pnlButtom.add(huyVeDangChon);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private void xoaDuLieu() {
        danhSachTraVe.clear();
        khachHang = null;

        maKhachHangTextField.setText("");
        tenKhachHangTextField.setText("");
        soDienThoaiTextField.setText("");
        soDienThoaiTextField.setEnabled(true);
        soDienThoaiTextField.setEditable(true);
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

    private void timKhachHang() {
        while (veModel.getRowCount() > 0) {
            veModel.removeRow(0);
        }

        String soDienThoai = soDienThoaiTextField.getText().trim();
        khachHang = khachHangDao.timTheoSDT(soDienThoai);
        if (khachHang == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng!");
            return;
        }
        soDienThoaiTextField.setText(khachHang.getSoDienThoai());
        maKhachHangTextField.setText(String.valueOf(khachHang.getMaKhachHang()));
        tenKhachHangTextField.setText(khachHang.getHoTen());
        cmndKhachHangTextField.setText(khachHang.getCMND());

        danhSachTraVe = veDao.layVeTheoSdtKhachHang(soDienThoai);
        if (danhSachTraVe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy vé có thể hủy!");
            return;
        }

        for (TraVeModel duDinhTraVe : danhSachTraVe) {
            Ve ve = duDinhTraVe.getVe();

            veModel.addRow(new String[] {
                    ve.getMaVe(),
                    ve.getHoTenNguoiDi(),
                    String.valueOf(ve.getSlot().getSoSlot()),
                    Formater.FormatVND(duDinhTraVe.getDonGia()),
                    duDinhTraVe.getThoiGianKhoiHanh().toString(),
                    loaiHoanTien(duDinhTraVe.tinhLoaiTraVe()),
                    Formater.FormatVND(duDinhTraVe.tinhPhiTraVe())
            });
        }
    }


    private String loaiHoanTien(String loaiHoanVe) {
        if (loaiHoanVe.equals("tra-truoc-24h-phi-20k")) {
            return "Phí trả vé 20.000đ";
        } else if (loaiHoanVe.equals( "tra-tu-4-24h-phi-20k")) {
            return "Phí trả vé 20% của số tiền mua vé";
        }
        return "Không hoàn tiền";
    }

    private void huyVeDangChon() {
        if (veTable.getSelectedRows().length <= 0)  {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn những vé cần hủy!");
            return;
        }

        if (JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn hủy vé?",
                "Hủy vé",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {

            List<TraVeModel> dsVeDuocChon = new ArrayList<>();

            for (int i = 0; i < veTable.getSelectedRows().length; i++) {
                dsVeDuocChon.add(danhSachTraVe.get(i));
            }

            try {
                NhanVien nhanVienThucHien = NhanVienSuDungSingleton.layThongTinNhanVienHienTai();
                if (!dsVeDuocChon.isEmpty() && lichSuTraVeDao.huyVe(dsVeDuocChon, khachHang, nhanVienThucHien)) {
                    JOptionPane.showMessageDialog(this, "Hủy vé thành công.");
                    xoaDuLieu();
                    dispose();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
