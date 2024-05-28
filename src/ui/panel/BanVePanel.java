package ui.panel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.*;
import singleton.NhanVienSuDungSingleton;
import ui.component.*;
import ui.dialog.chonChoDialog.ChonChoDialog;
import dao.ChuyenDao;
import dao.GaDao;
import dao.TauDao;
import helper.JTableExporter;
import ui.dialog.chonChoDialog.ChonChoNgoiListener;
import ui.dialog.khachHangDialog.KhachHangDialog;
import ui.dialog.khachHangDialog.TaoKhachHangListener;
import ui.dialog.thanhToanDialog.ThanhToanDialog;
import ui.dialog.thanhToanDialog.ThanhToanListener;
import ui.dialog.timKhachHangDialog.TimKhachHangDialog;
import ui.dialog.timKhachHangDialog.TimKhachHangListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BanVePanel extends JPanel implements PropertyChangeListener, ItemListener, MouseListener {

    private PanelBorderRadius main, functionBar, box;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableChuyenTau;
    private JScrollPane scrollTableChuyenTau;
    private ChucNangChinh chucNangChinh;
    private DefaultTableModel tblModel;
    private SelectForm cbxGaDi, cbxGaDen;
    private JCheckBox checkBoxKhuHoi;
    private InputDate dateNgayDi, dateNgayVe;
    private SpinnerForm soLuongHanhKhach;
    private GaDao gaDao;
    private ChuyenDao chuyenDao;
    private TauDao tauDao;
    private List<String> tenGaList;
    private List<Chuyen> chuyenList;
    private ChonChoDialog chonChoDialog;
    private TimKhachHangDialog timKhachHangDialog;
    private KhachHangDialog khachHangDialog;
    Color BackgroundColor = new Color(240, 247, 250);
    private List<SlotBtn> dsChoDaChon = new ArrayList<>();
    private ThanhToanDialog thanhToanDialog;
    private ToaTau toaTauChon;

    public BanVePanel() {
        thanhToanDialog = new ThanhToanDialog();
        thanhToanDialog.setThanhToanListener(new ThanhToanListener() {
            @Override
            public void thanhToanThanhCong(HoaDon hoaDon) {
                resetForm();
            }
        });
        chuyenList = new ArrayList<>();
        tenGaList = new ArrayList<>();
        chuyenDao = new ChuyenDao();
        tauDao = new TauDao();
        gaDao = new GaDao();

        initComponent();
        tenGaList = gaDao.layHetTenGa();

        cbxGaDi.setCbItems(tenGaList);
        cbxGaDen.setCbItems(tenGaList);

        resetForm();
    }

    public void initPadding() {
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent() {
        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        tableChuyenTau = new JTable();
        scrollTableChuyenTau = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[]{"Mã chuyến", "Tuyến", "Tàu", "Thời gian khởi hành", "Thời gian đến dự kiến"};
        tblModel.setColumnIdentifiers(header);
        tableChuyenTau.setModel(tblModel);
        tableChuyenTau.setDefaultEditor(Object.class, null);
        scrollTableChuyenTau.setViewportView(tableChuyenTau);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableChuyenTau.setDefaultRenderer(Object.class, centerRenderer);
        tableChuyenTau.setFocusable(false);
        tableChuyenTau.getColumnModel().getColumn(0).setPreferredWidth(10);
        tableChuyenTau.getColumnModel().getColumn(1).setPreferredWidth(10);

        tableChuyenTau.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableChuyenTau, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableChuyenTau, 4, TableSorter.DATE_COMPARATOR);

        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        add(contentCenter, BorderLayout.CENTER);

        timKhachHangDialog = new TimKhachHangDialog();
        timKhachHangDialog.setTimKhachHangListener(new TimKhachHangListener() {
            @Override
            public void timThayhachhang(KhachHang khachHang) {
                timKhachHangDialog.xoaDuLieu();
                themKhachHangVaoHoaDon(khachHang);
            }

            @Override
            public void khongTimThayKhachHang(String soDienThoai) {
                khachHangDialog.taoTaiKhoanVoiSoDienThoai(soDienThoai);
                khachHangDialog.setVisible(true);
            }
        });

        chonChoDialog = new ChonChoDialog();
        chonChoDialog.setChonChoNgoiListener(new ChonChoNgoiListener() {
            @Override
            public void chonChoNgoiThanhCong(ToaTau toaTau, List<SlotBtn> dsCho) {
                toaTauChon = toaTau;
                dsChoDaChon.clear();
                dsChoDaChon.addAll(dsCho);
                chonChoDialog.setVisible(false);
                timKhachHangDialog.setVisible(true);
            }
        });

        khachHangDialog = new KhachHangDialog();
        khachHangDialog.setTaoKhachHangListener(new TaoKhachHangListener() {
            @Override
            public void taoKhachHangThanhCong(KhachHang khachHang) {
                khachHangDialog.xoaDuLieu();
                themKhachHangVaoHoaDon(khachHang);
            }
        });

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        chucNangChinh = new ChucNangChinh(new String[]{"tim", "chi-tiet", "huy-ve", "xuat-excel", "in"});

        chucNangChinh.getToolbar("tim").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!validation() || !validateSelectDate()) return;

                layDuLieu();
            }
        });

        chucNangChinh.getToolbar("huy-ve").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        chucNangChinh.getToolbar("xuat-excel").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JTableExporter.exportJTableToExcel(tableChuyenTau);
                } catch (IOException ex) {
                    Logger.getLogger(BanVePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        functionBar.add(chucNangChinh);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        box = new PanelBorderRadius();
        box.setPreferredSize(new Dimension(250, 0));
        box.setLayout(new GridLayout(6, 1, 10, 0));
        box.setBorder(new EmptyBorder(0, 5, 150, 5));
        contentCenter.add(box, BorderLayout.WEST);

        cbxGaDi = new SelectForm("Ga đi");
        cbxGaDi.cbb.setEditable(true);
        cbxGaDen = new SelectForm("Ga đến");
        cbxGaDen.cbb.setEditable(true);

        dateNgayDi = new InputDate("Ngày đi");
        checkBoxKhuHoi = new JCheckBox("Khứ hồi");
        dateNgayVe = new InputDate("Ngày về");
        dateNgayVe.setVisible(false);
        soLuongHanhKhach = new SpinnerForm("Số lượng hành khách");
        soLuongHanhKhach.getSpinnerForm().setEnabled(false);

        cbxGaDi.getCbb().addItemListener(this);
        cbxGaDen.getCbb().addItemListener(this);
        dateNgayDi.getDateChooser().addPropertyChangeListener(this);
        dateNgayVe.getDateChooser().addPropertyChangeListener(this);

        cbxGaDi.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timGa(cbxGaDi);
                cbxGaDi.getCbb().showPopup();
            }

        });

        cbxGaDen.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timGa(cbxGaDen);
                cbxGaDen.getCbb().showPopup();
            }
        });

        checkBoxKhuHoi.addActionListener(e -> {
            boolean isKhuHoi = checkBoxKhuHoi.isSelected();
            isKhuHoi = !isKhuHoi;

            dateNgayVe.setVisible(!isKhuHoi);
        });

        box.add(cbxGaDi);
        box.add(cbxGaDen);
        Box box1 = Box.createHorizontalBox();
        box.add(soLuongHanhKhach);
        box1.add(dateNgayDi);
        box1.add(checkBoxKhuHoi);
        box.add(box1);
        //box.add(dateNgayVe);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        tableChuyenTau.addMouseListener(this);
        main.add(scrollTableChuyenTau);
    }

    public void layDuLieu() {
        try {
            tblModel.setRowCount(0);
            tableChuyenTau.repaint();
            tableChuyenTau.revalidate();
            String maGaDi = "";
            String maGaDen = "";
            LocalDate ngayDi = LocalDate.now();
            if (validation()) {
                maGaDi = cbxGaDi.getSelectedItem().toString();
                maGaDen = cbxGaDen.getSelectedItem().toString();
                ngayDi = dateNgayDi.getDateAsLocalDate();
            }

            chuyenList = chuyenDao.timChuyenTheoGa(maGaDi, maGaDen, ngayDi);

            if (chuyenList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy tuyến");
                return;
            }

            for (Chuyen chuyen : chuyenList) {
                Ga gaDi = chuyen.getTuyen().getGaDi();
                Ga gaDen = chuyen.getTuyen().getGaDen();
                Tau tau = chuyen.getTau();

                tblModel.addRow(new Object[]{chuyen.getMaChuyen(), gaDi.getTenGa() + "-" + gaDen.getTenGa(), tau.getTenTau(), chuyen.getThoiGianKhoiHanh(), chuyen.getThoiGianDen()});
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    private void timGa(SelectForm selectForm) {
        JTextField editorComponent = (JTextField) selectForm.getCbb().getEditor().getEditorComponent();
        String previousText = editorComponent.getText();
        String tenGa = previousText.trim().toLowerCase();

        if (tenGa.isEmpty()) {
            selectForm.setCbItems(tenGaList);
            selectForm.getCbb().setSelectedItem(null);
            return;
        }

        List<String> ketQua = tenGaList.stream().filter(item -> item.toLowerCase().contains(tenGa)).toList();

        selectForm.setCbItems(ketQua);
        editorComponent.setText(previousText);
    }

    public void resetForm() {
        cbxGaDi.setSelectedItem(null);
        cbxGaDen.setSelectedItem(null);
        soLuongHanhKhach.setValue(0);
        dateNgayDi.getDateChooser().setCalendar(null);
        dateNgayVe.getDateChooser().setCalendar(null);
    }

    public boolean validateSelectDate() {
        try {
            LocalDate ngayDi = dateNgayDi.getDateAsLocalDate();
            //LocalDate ngayVe = dateNgayVe.getDateAsLocalDate();

            if (!checkBoxKhuHoi.isSelected()) {
                if (ngayDi == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày đi", "Lỗi !", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (ngayDi.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(this, "Ngày đi phải sau hoặc trong ngày hiện tại", "Lỗi !", JOptionPane.ERROR_MESSAGE);
                    dateNgayDi.getDateChooser().setCalendar(null);
                }
                return true;
            }

            if (ngayDi == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày đi", "Lỗi !", JOptionPane.ERROR_MESSAGE);
                return false;
            }

//            if (ngayVe == null) {
//                JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày về", "Lỗi !", JOptionPane.ERROR_MESSAGE);
//                return false;
//            }
//
//            if (ngayDi.isAfter(ngayVe)) {
//                JOptionPane.showMessageDialog(this, "Ngày về phải lớn hơn ngày bắt đầu", "Lỗi !", JOptionPane.ERROR_MESSAGE);
//                dateNgayVe.getDateChooser().setCalendar(null);
//                return false;
//            }
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validation() {
        if (this.cbxGaDi.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ga đi");
            return false;
        }

        if (this.cbxGaDen.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ga đến");
            return false;
        }

        if (this.dateNgayDi == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày đi");
            return false;
        }
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tau tau = layTauDuocChon();
        if (tau == null) {
            return;
        }

        chonChoDialog.setTau(tau);
        chonChoDialog.setVisible(true);
    }

    private Tau layTauDuocChon() {
        int row = tableChuyenTau.getSelectedRow();
        if (row != -1) {
            String tenTau = tableChuyenTau.getValueAt(row, 2).toString();
            Tau tau = tauDao.layTheoTenTau(tenTau);

            if (tau == null) {
                JOptionPane.showMessageDialog(null, "Không thể tìm thấy thông tin về tàu.");
                return null;
            }
            return tau;
        }
        JOptionPane.showMessageDialog(null, "Vui lòng chọn một chuyến tàu.");
        return null;
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

    private void themKhachHangVaoHoaDon(KhachHang khachHang) {
        NhanVien nhanVien = NhanVienSuDungSingleton.layThongTinNhanVienHienTai();

        String maGaDi = cbxGaDi.getSelectedItem().toString();
        String maGaDen = cbxGaDen.getSelectedItem().toString();
        Tau tau = layTauDuocChon();
        thanhToanDialog.setData(
                nhanVien,
                maGaDi,
                maGaDen,
                tau,
                toaTauChon,
                khachHang,
                dsChoDaChon);

        thanhToanDialog.setVisible(true);
    }


}
