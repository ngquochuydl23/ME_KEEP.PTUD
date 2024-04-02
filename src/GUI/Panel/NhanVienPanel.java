package GUI.Panel;


import GUI.Component.ChucNangChinh;
import GUI.Component.IntegratedSearch;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Dialog.nhanVienDialog.NhanVienDialog;
import GUI.Dialog.nhanVienDialog.SuaNhanVienListener;
import GUI.Dialog.nhanVienDialog.TaoNhanVienListener;
import dao1.NhanVienDao;
import entity.NhanVien;
import helper.JTableExporter;
import helper.Validation;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class NhanVienPanel extends JPanel {

    private NhanVienDao nhanVienDao;
    private List<NhanVien> dsNhanVien;
    private JTable tableNhanVien;
    private DefaultTableModel tblModel;
    private NhanVienDialog nhanVienDialog;


    public NhanVienPanel() {
        initComponent();
        tableNhanVien.setDefaultEditor(Object.class, null);
        layDsNhanVien();
    }

    private void initComponent() {
        IntegratedSearch search;
        Color BackgroundColor = new Color(240, 247, 250);

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
        PanelBorderRadius main, functionBar;
        JScrollPane scrollTableSanPham;


        ChucNangChinh chucNangChinh;
        nhanVienDao = new NhanVienDao();
        nhanVienDialog = new NhanVienDialog();
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
        search = new IntegratedSearch(new String[]{ "Họ tên", "Số điện thoại"});
        functionBar.add(search);

        nhanVienDialog.setTaoNhanVienListener(new TaoNhanVienListener() {
            @Override
            public void taoNhanVienThanhCong() {
                nhanVienDialog.xoaDuLieu();
                layDsNhanVien();
            }
        });

        nhanVienDialog.setSuaNhanVienListener(new SuaNhanVienListener() {
            @Override
            public void suaNhanVienThanhCong() {
                nhanVienDialog.xoaDuLieu();
                layDsNhanVien();
            }
        });

        chucNangChinh
                .getToolbar("them")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nhanVienDialog.xoaDuLieu();
                        nhanVienDialog.setVisible(true);
                    }
                });

        chucNangChinh
                .getToolbar("sua")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!kiemTraChonDong())
                            return;
                        nhanVienDialog.xoaDuLieu();
                        nhanVienDialog.setNhanVien(layThongTinNhanVienTaiDong());
                        nhanVienDialog.setVisible(true);
                    }
                });

        chucNangChinh
                .getToolbar("xoa")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!kiemTraChonDong())
                            return;
                        xoaNhanVienTheoMa(layThongTinNhanVienTaiDong());
                    }
                });

        chucNangChinh
                .getToolbar("chi-tiet")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!kiemTraChonDong())
                            return;
                        nhanVienDialog.xoaDuLieu();
                        nhanVienDialog.xemChiTietNhanVien(layThongTinNhanVienTaiDong());
                    }
                });

        chucNangChinh
                .getToolbar("nhap-excel")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nhapExcel();
                    }
                });

        chucNangChinh
                .getToolbar("xuat-excel")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            JTableExporter.exportJTableToExcel(tableNhanVien);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });


        search.setActionReset(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                        case "Họ tên":
                            layDsNhanVienTheoTen(searchText);
                        case "Số điện thoại":
                            layDsNhanVienTheoSdt(searchText);

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
        String[] headerCols = "Mã nhân viên;Họ tên;Số Điện thoại;Giới tính;Ngày sinh;Email;Vai Trò".split(";");

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
        tableNhanVien.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        scrollTableSanPham.setViewportView(tableNhanVien);
        main.add(scrollTableSanPham);
    }

    public void layDsNhanVien() {
        while (tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        dsNhanVien = nhanVienDao.layHet();
        for (entity.NhanVien nhanVien : dsNhanVien) {
            tblModel.addRow(new Object[]{
                    nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(), nhanVien.getGioitinh() == 1 ? "Nam" : "Nữ", nhanVien.getNgaysinh(), nhanVien.getEmail(),
                    nhanVien.getVaiTro()
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

    public void layDsNhanVienTheoSdt(String sdt) {
        while (tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        dsNhanVien = new ArrayList<>();
        NhanVien nhanVien = nhanVienDao.timNhanVienTheoSdt(sdt);
        if (nhanVien != null) {
            dsNhanVien.add(nhanVien);
            tblModel.addRow(new Object[]{
                    nhanVien.getMaNhanVien(), nhanVien.getHoTen(), nhanVien.getSoDienThoai(), nhanVien.getGioitinh() == 1 ? "Nam" : "Nữ", nhanVien.getNgaysinh(), nhanVien.getEmail()
            });
        }
    }

    private NhanVien layThongTinNhanVienTaiDong() {
        int row = tableNhanVien.getSelectedRow();

        int maNhanVien = Integer.parseInt(tblModel.getValueAt(row, 0).toString());
        return dsNhanVien.stream()
                .filter(nhanVien -> nhanVien.getMaNhanVien() == maNhanVien)
                .findAny()
                .orElse(null);
    }

    private void xoaNhanVienTheoMa(NhanVien nhanVien) {
        if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên " + nhanVien.getHoTen()) == 0) {
            if (nhanVienDao.xoa(nhanVien.getMaNhanVien())) {
                JOptionPane.showMessageDialog(null, "Đã xóa nhân viên thành công");
                layDsNhanVien();
            }
        }
    }

    private boolean kiemTraChonDong() {
        if (tableNhanVien.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên");
            return false;
        }
        return true;
    }

    public void nhapExcel() {
        List<NhanVien> importingNhanVien = new ArrayList<>();
        layDsNhanVien();

        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Chọn đường dẫn lưu file Excel");

        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {

                    XSSFRow excelRow = excelSheet.getRow(row);

                    String hoTen = excelRow.getCell(1).getStringCellValue();
                    String soDienThoai = excelRow.getCell(2).getStringCellValue();
                    int gioiTinh = excelRow.getCell(3).getStringCellValue().equals("Nam") ? 1 : 0;

                    LocalDate ngaySinh = LocalDate.parse(excelRow.getCell(4).getStringCellValue());
                    String email = excelRow.getCell(5).getStringCellValue();
                    String vaiTro = excelRow.getCell(6).getStringCellValue();

                    NhanVien nhanVien = new NhanVien(hoTen, gioiTinh, soDienThoai, ngaySinh, 1, "123456789", email, vaiTro);
                    if (!kiemTraNhanVienHopLe(nhanVien)) {
                        break;
                    }

                    if (dsNhanVien.stream()
                            .filter(item -> item.getEmail().equals(nhanVien.getEmail()) || item.getSoDienThoai().equals(nhanVien.getSoDienThoai()))
                            .findAny()
                            .orElse(null) != null) {
                        break;
                    }
                    importingNhanVien.add(nhanVien);
                }

                if (nhanVienDao.themVoiDanhSach(importingNhanVien)) {
                    JOptionPane.showMessageDialog(this, "Nhập dữ liệu thành công");
                }

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy file");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc file");
            }
        }
        layDsNhanVien();
    }

    private boolean kiemTraNhanVienHopLe(NhanVien nhanVien) {
        if (Validation.isEmpty(nhanVien.getHoTen())) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (nhanVien.getHoTen().length() < 6) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên ít nhất 6 kí tự!");
            return false;
        }
        if (Validation.isEmpty(nhanVien.getEmail()) || !Validation.kiemTraEmail(nhanVien.getEmail())) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Validation.isEmpty(nhanVien.getSoDienThoai()) || !Validation.kiemTraSoDienThoai(nhanVien.getSoDienThoai())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải đúng định dạng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (nhanVien.getNgaysinh() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
            return false;
        }
        if (Validation.isEmpty(nhanVien.getVaiTro())) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vai trò!");
            return false;
        }
        return true;
    }
}
