package ui.panel;

import helper.Formater;
import ui.component.*;
import entity.HoaDon;
import entity.KhachHang;
import entity.LichSuTraVe;
import helper.JTableExporter;
import helper.Validation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.HoaDonDao;
import dao.KhachHangDao;
import ui.dialog.chiTietHoaDonDialog.ChiTietHoaDonDialog;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public final class HoaDonPanel extends JPanel {

    private PanelBorderRadius main, functionBar, box;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableHoaDon;
    private JScrollPane scrollTableHoaDon;
    private DefaultTableModel tblModel;
    private SelectForm trangThaiDonHangComboBox;
    private InputDate thoiGianTaoHoaDonInputDate;
    private InputForm soDienThoaiInputForm;
    private HoaDonDao hoaDonDAO;
    private List<HoaDon> danhSachHoaDon;

    public HoaDonPanel() {
        danhSachHoaDon = new ArrayList<HoaDon>();
        hoaDonDAO = new HoaDonDao();
        initComponent();
        doDuLieuVaoBang();
    }

    public void initPadding() {
        Color BackgroundColor = new Color(240, 247, 250);

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
        Color BackgroundColor = new Color(240, 247, 250);

        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        tableHoaDon = new JTable();
        scrollTableHoaDon = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] headerCols = "Mã hóa đơn;Tên khách hàng;Tổng tiền;Thời gian tạo hóa đơn;Ghi chú".split(";");
        tblModel.setColumnIdentifiers(headerCols);
        tableHoaDon.setModel(tblModel);
        tableHoaDon.setDefaultEditor(Object.class, null);
        scrollTableHoaDon.setViewportView(tableHoaDon);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableHoaDon.setDefaultRenderer(Object.class, centerRenderer);
        tableHoaDon.setFocusable(false);
        tableHoaDon.getColumnModel().getColumn(0).setPreferredWidth(10);
        tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(10);
        tableHoaDon.getColumnModel().getColumn(2).setPreferredWidth(10);

        tableHoaDon.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableHoaDon, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableHoaDon, 4, TableSorter.VND_CURRENCY_COMPARATOR);

        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        ChucNangChinh chucNangChinh = new ChucNangChinh(new String[]{"tim","chi-tiet", "nhap-excel", "xuat-excel"});
        functionBar.add(chucNangChinh);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        box = new PanelBorderRadius();
        box.setPreferredSize(new Dimension(250, 0));
        box.setLayout(new GridLayout(6, 1, 10, 0));
        box.setBorder(new EmptyBorder(0, 5, 150, 5));
        contentCenter.add(box, BorderLayout.WEST);

        thoiGianTaoHoaDonInputDate = new InputDate("Thời gian tạo hóa dơn");
        soDienThoaiInputForm = new InputForm("Số điện thoại khách hàng");
        soDienThoaiInputForm.setEditable(true);
        soDienThoaiInputForm.requestFocus();

        trangThaiDonHangComboBox = new SelectForm("Trạng thái đơn hàng");
        thoiGianTaoHoaDonInputDate
                .getDateChooser()
                .addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        layDanhSachHoaDonTheoTieuChi();
                    }
                });

        soDienThoaiInputForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layDanhSachHoaDonTheoTieuChi();
            }
        });

        chucNangChinh
		.getToolbar("tim")
		.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!validation() || !kiemTraNgayTaoHoaDon()) return;
                
                clearDanhSach();
                try {
					layHoaDonTheoYeuCau();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
            }
		});
        
        chucNangChinh
                .getToolbar("chi-tiet")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!kiemTraChonDong())
                            return;
                        HoaDon hoaDon = layHoaDonDangChon();
                        new ChiTietHoaDonDialog(hoaDon.getMaHoaDon()).setVisible(true);
                    }
                });

        chucNangChinh
                .getToolbar("nhap-excel")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

        chucNangChinh
                .getToolbar("xuat-excel")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        xuatLichSuTraVeExcel();
                    }
                });


        box.add(soDienThoaiInputForm);
        box.add(trangThaiDonHangComboBox);
        box.add(thoiGianTaoHoaDonInputDate);

        main = new PanelBorderRadius();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableHoaDon);
    }

    private void layHoaDonTheoYeuCau() throws ParseException {
        danhSachHoaDon = hoaDonDAO.layTheoSoDienThoaiVaThoiGianTaoHoaDon(soDienThoaiInputForm.getText(), thoiGianTaoHoaDonInputDate.getDateAsLocalDate());
		tblModel.setRowCount(0);

        for (HoaDon hoaDon : danhSachHoaDon) {
            tblModel.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getKhachHang().getHoTen(),
                    Formater.FormatVND(hoaDon.getTongTien()),
                    Formater.FormatTime(hoaDon.getThoiGianTaoHoaDon()),
                    hoaDon.getGhiChu()
            });
        }
    }
    
    public void layDanhSachHoaDonTheoTieuChi() {
        try {
            String soDienThoai = soDienThoaiInputForm.getText().trim();
            if (!soDienThoai.isEmpty() && !Validation.kiemTraSoDienThoai(soDienThoai)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ", "Lỗi !",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (kiemTraNgayTaoHoaDon()) {
                return;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetForm() {

    }

    private HoaDon layHoaDonDangChon() {
        int row = tableHoaDon.getSelectedRow();
        String maHoaDon = tblModel.getValueAt(row, 0).toString();
        System.out.println(danhSachHoaDon);
        return danhSachHoaDon.stream()
                .filter(item -> item.getMaHoaDon().equals(maHoaDon))
                .findAny()
                .orElse(null);
    }

    private boolean kiemTraChonDong() {
        if (tableHoaDon.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng");
            return false;
        }
        return true;
    }

    public boolean kiemTraNgayTaoHoaDon() {
        try {
            LocalDate thoiGianTaoHoaDon = thoiGianTaoHoaDonInputDate.getDateAsLocalDate();
            if (thoiGianTaoHoaDon == null)
                return false;

            if (thoiGianTaoHoaDon.isAfter(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !",
                        JOptionPane.ERROR_MESSAGE);

                thoiGianTaoHoaDonInputDate
                        .getDateChooser()
                        .setCalendar(Calendar.getInstance());
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validation() {
        String soDienThoai = soDienThoaiInputForm.getText();
        if (soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại khách hàng");
            return false;
        }
        return true;
    }
    
    public void clearDanhSach() {
        danhSachHoaDon.clear();
        tblModel.setRowCount(0); 
    }
    
    private void xuatLichSuTraVeExcel() {
        try {
            JTableExporter.exportJTableToExcel(tableHoaDon);
            // Logger.getLogger(PhieuNhap.class.getName()).log(Level.INFO, "Xuất lịch sửa trả vé thành công");
        } catch (IOException ex) {
            //  Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doDuLieuVaoBang() {
        while (tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        danhSachHoaDon = hoaDonDAO.layHet();
        for (HoaDon hoaDon : danhSachHoaDon) {
            tblModel.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getKhachHang().getHoTen(),
                    Formater.FormatVND(hoaDon.getTongTien()),
                    Formater.FormatTime(hoaDon.getThoiGianTaoHoaDon()),
                    hoaDon.getGhiChu()
            });
        }
    }
}
