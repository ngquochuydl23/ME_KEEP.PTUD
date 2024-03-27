package GUI.Panel;


import GUI.Component.ChucNangChinh;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import GUI.Component.PanelBorderRadius;
import GUI.Dialog.nhanVienDialog.NhanVienDialog;
import GUI.Main;
import dao1.NhanVienDao;
import entity.NhanVien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class NhanVienPanel extends JPanel {


    private NhanVienDao nhanVienDao;

    public JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableNhanVien;
    private JScrollPane scrollTableSanPham;
    private ChucNangChinh chucNangChinh;
    private IntegratedSearch search;
    private Main m;

    private List<NhanVien> dsNhanVien;

    private Color BackgroundColor = new Color(240, 247, 250);
    private DefaultTableModel tblModel;

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        nhanVienDao = new NhanVienDao();

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở giữa mà contentCenter không bị dính sát vào các thành phần khác
        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentCenter.add(functionBar, BorderLayout.NORTH);

        chucNangChinh = new ChucNangChinh(new String[]{"them", "xoa", "sua", "chi-tiet", "nhap-excel", "xuat-excel"});
        functionBar.add(chucNangChinh);
        search = new IntegratedSearch(new String[]{"Tất cả", "Họ tên", "Số điện thoại"});
        functionBar.add(search);

        chucNangChinh
                .getToolbar("them")
                .addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NhanVienDialog();
            }
        });

        search.setActionReset(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reset");
                layDsNhanVien();
            }
        });
        search.txtSearchForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = search.txtSearchForm.getText().trim();
                String luaChon = search.cbxChoose.getSelectedItem().toString();
                System.out.println(searchText);
                if (!searchText.isEmpty()) {
                    switch (luaChon) {
                        case "Tất cả" -> {
                            System.out.println("Tất cả");
                        }
                        case "Họ tên" -> layDsNhanVienTheoTen(searchText);
                        case "Số điện thoại" -> {
                            System.out.println("Số điện thoại");
                        }
                    }
                }
            }
        });
        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
//        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);

        tableNhanVien = new JTable();
        scrollTableSanPham = new JScrollPane();
        tableNhanVien = new JTable();
        tblModel = new DefaultTableModel();
        String[] headerCols = "Mã nhân viên;Họ tên;Số Điện thoại;Giới tính;Ngày sinh;Email".split(";");

        tblModel.setColumnIdentifiers(headerCols);
        tableNhanVien.setModel(tblModel);
        tableNhanVien.setFocusable(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        tableNhanVien.setDefaultRenderer(Object.class, centerRenderer);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableNhanVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableNhanVien.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        scrollTableSanPham.setViewportView(tableNhanVien);
        main.add(scrollTableSanPham);
    }

    public NhanVienPanel(Main m) {
        this.m = m;
        initComponent();
        tableNhanVien.setDefaultEditor(Object.class, null);
        layDsNhanVien();
    }

    public int getRow() {
        return tableNhanVien.getSelectedRow();
    }

    public void layDsNhanVien() {
        while (tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        dsNhanVien = nhanVienDao.layHet();
        for (entity.NhanVien nhanVien : dsNhanVien) {
            tblModel.addRow(new Object[]{
                    nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(), nhanVien.getGioitinh() == 1 ? "Nam" : "Nữ", nhanVien.getNgaysinh(), nhanVien.getEmail()
            });
        }
    }

    public void layDsNhanVienTheoTen(String ten) {
        while (tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        dsNhanVien = new ArrayList<>();
        NhanVien nhanVien = nhanVienDao.layDsNhanVienTheoTen(ten);
        if (nhanVien != null) {
            dsNhanVien.add(nhanVien);

            tblModel.addRow(new Object[]{
                    nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(), nhanVien.getGioitinh() == 1 ? "Nam" : "Nữ", nhanVien.getNgaysinh(), nhanVien.getEmail()
            });
        }
    }
}
