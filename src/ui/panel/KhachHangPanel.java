package ui.panel;

import entity.KhachHang;
import ui.component.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.dialog.khachHangDialog.KhachHangDialog;
import dao.KhachHangDao;

import helper.Validation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ui.dialog.khachHangDialog.SuaKhachHangListener;
import ui.dialog.khachHangDialog.TaoKhachHangListener;

public class KhachHangPanel extends JPanel implements ItemListener {

    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableKhachHang;
    private JScrollPane scrollTableKhachHang;
    private ChucNangChinh chucNangChinh;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private KhachHangDao khachHangDAO;
    private List<KhachHang> danhSachKhachHang;
    Color BackgroundColor = new Color(240, 247, 250);

    private KhachHangDialog khachHangDialog;

    public KhachHangPanel() {
        danhSachKhachHang = new ArrayList<>();
        khachHangDAO = new KhachHangDao();
        initComponent();

        loadDataTable();
    }

    private void initComponent() {
        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        khachHangDialog = new KhachHangDialog();
        khachHangDialog.setTaoKhachHangListener(new TaoKhachHangListener() {
            @Override
            public void taoKhachHangThanhCong(KhachHang khachHang) {
                loadDataTable();
            }
        });
        khachHangDialog.setSuaKhachHangListener(new SuaKhachHangListener() {
            @Override
            public void suaKhachHangThanhCong(KhachHang khachHang) {
                loadDataTable();
            }
        });


        tableKhachHang = new JTable();
        tableKhachHang.setDefaultEditor(Object.class, null);
        scrollTableKhachHang = new JScrollPane();
        tblModel = new DefaultTableModel();
        tblModel.setColumnIdentifiers("Mã khách hàng;Tên khách hàng;Số điện thoại;Khách hàng thân thiết;Ngày tham gia; Số CMND".split(";"));
        tableKhachHang.setModel(tblModel);
        tableKhachHang.setFocusable(false);
        scrollTableKhachHang.setViewportView(tableKhachHang);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableKhachHang.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableKhachHang.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tableKhachHang.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableKhachHang, 0, TableSorter.INTEGER_COMPARATOR);


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


        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        chucNangChinh = new ChucNangChinh(new String[]{"them", "sua", "xoa", "chi-tiet", "nhap-excel", "xuat-excel"});
        chucNangChinh
                .getToolbar("them")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	khachHangDialog.xoaDuLieu();
                        khachHangDialog.setVisible(true);
                    }
                });

        chucNangChinh
                .getToolbar("sua")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        KhachHang khachHang = layKhachHangDangChon();
                        if (khachHang != null) {
                        	khachHangDialog.xoaDuLieu();
                            khachHangDialog.setKhachHang(khachHang);
                            khachHangDialog.setVisible(true);
                        }
                    }
                });

        chucNangChinh
                .getToolbar("chi-tiet")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        KhachHang khachHang = layKhachHangDangChon();
                        if (khachHang != null) {
                        	khachHangDialog.xoaDuLieu();
                            khachHangDialog.xemKhachHang(layKhachHangDangChon());
                            khachHangDialog.setVisible(true);
                        }
                    }
                });

        chucNangChinh
                .getToolbar("xoa")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        KhachHang khachHang = layKhachHangDangChon();
                        if (khachHang == null) {
                            return;
                        }
                        if (JOptionPane.showConfirmDialog(
                                null,
                                "Bạn có chắc chắn muốn xóa khách hàng ?",
                                "Xóa khách hàng",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {

                            if (khachHangDAO.xoa(khachHang.getMaKhachHang())) {
                                loadDataTable();
                                Logger.getLogger(KhachHangPanel.class.getName()).log(Level.INFO, "Xóa khách hàng thành công!");
                                JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công!");
                            }
                        }
                    }
                });

        functionBar.add(chucNangChinh);

        search = new IntegratedSearch(new String[]{"Tất cả", "Mã khách hàng", "Tên khách hàng", "Khách hàng thân thiết", "Số điện thoại"});
        search.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataTable();
            }
        });

        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyWord = search.txtSearchForm.getText();
                String type = (String) search.cbxChoose.getSelectedItem();
                if (keyWord.isEmpty())
                    loadDataTable();
                else timKhachHang(keyWord, type);

            }
        });
        search.cbxChoose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) search.cbxChoose.getSelectedItem();
                    if (selectedOption.equals("Khách hàng thân thiết")) {

                        timKhachHang("", selectedOption);
                        loadDataTable();
                    } else {
                        loadDataTable();
                    }
                }
            }
        });

        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        main.add(scrollTableKhachHang);

    }

    public void timKhachHang(String text, String type) {
        List<KhachHang> result = new ArrayList<>();
        text = text.toLowerCase();

        switch (type) {
            case "Tất cả":
                for (entity.KhachHang i : danhSachKhachHang) {
                    if (Integer.toString(i.getMaKhachHang()).toLowerCase().contains(text)
                            || i.getHoTen().toLowerCase().contains(text) || i.laKhachHangThanThiet()
                            || i.getSoDienThoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;

            case "Mã khách hàng":
                for (entity.KhachHang i : danhSachKhachHang) {
                    if (Integer.toString(i.getMaKhachHang()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;

            case "Tên khách hàng":
                for (entity.KhachHang i : danhSachKhachHang) {
                    if (i.getHoTen().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;

            case "Khách hàng thân thiết":
                for (entity.KhachHang i : danhSachKhachHang) {
                    if (i.laKhachHangThanThiet()) {
                        result.add(i);
                    }
                }
                break;

            case "Số điện thoại":
                for (entity.KhachHang i : danhSachKhachHang) {
                    if (i.getSoDienThoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
            case "Số CMND":
                for (entity.KhachHang i : danhSachKhachHang) {
                    if (i.getSoCMND().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
                break;
        }
        while (tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        danhSachKhachHang = result;
        for (KhachHang khachHang : danhSachKhachHang) {
            tblModel.addRow(new Object[]{
                    "KH" + khachHang.getMaKhachHang(),
                    khachHang.getHoTen(),
                    khachHang.getSoDienThoai(),
                    khachHang.laKhachHangThanThiet() ? "Có" : "Không",
                    khachHang.getThoiGianDangKy(),
                    khachHang.getSoCMND()
            });
        }
    }

    public void loadDataTable() {
        while (tblModel.getRowCount() > 0) {
            tblModel.removeRow(0);
        }
        danhSachKhachHang = khachHangDAO.layHet();
        for (KhachHang khachHang : danhSachKhachHang) {
            tblModel.addRow(new Object[]{
                    "KH" + khachHang.getMaKhachHang(),
                    khachHang.getHoTen(),
                    khachHang.getSoDienThoai(),
                    khachHang.laKhachHangThanThiet() ? "Có" : "Không",
                    khachHang.getThoiGianDangKy(),
                    khachHang.getSoCMND()
            });
        }
    }

    public KhachHang layKhachHangDangChon() {
        int index = tableKhachHang.getSelectedRow();
        
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng");
            return null; // Trả về null nếu không có khách hàng nào được chọn
        } else {
            return danhSachKhachHang.get(index);
        }
    }


    public void importExcel() {
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelJTableImport = null;
        JFileChooser jf = new JFileChooser();
        int result = jf.showOpenDialog(null);
        jf.setDialogTitle("Open file");
        Workbook workbook = null;
        int k = 0;
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = jf.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelJTableImport = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    int check = 1;
                    XSSFRow excelRow = excelSheet.getRow(row);

                    String tenkh = excelRow.getCell(0).getStringCellValue();
                    String sdt = excelRow.getCell(1).getStringCellValue();
                    String diachi = excelRow.getCell(2).getStringCellValue();
                    if (Validation.isEmpty(tenkh) || Validation.isEmpty(sdt)
                            || !Validation.kiemTraSoDienThoai(sdt) || sdt.length() != 10 || Validation.isEmpty(diachi)) {
                        check = 0;
                    }
                    if (check == 1) {
                        // khachhangBUS.add(new KhachHangDTO(id, tenkh, sdt, diachi));
                    } else {
                        k += 1;
                    }
                }
                JOptionPane.showMessageDialog(this, "Nhập thành công");
            } catch (FileNotFoundException ex) {
                System.out.println("Lỗi đọc file");
            } catch (IOException ex) {
                System.out.println("Lỗi đọc file");
            }
        }
        if (k != 0) {
            JOptionPane.showMessageDialog(this, "Những dữ liệu không hợp lệ không được thêm vào");
        }
        loadDataTable();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        loadDataTable();
    }
}
