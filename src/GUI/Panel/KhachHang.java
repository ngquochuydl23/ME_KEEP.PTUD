package GUI.Panel;

import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.TableSorter;
import GUI.Dialog.KhachHangDialog;
import dao1.KhachHangDao;
import GUI.Main;
import java.util.logging.Logger;
import java.util.logging.Level;
import helper.JTableExporter;
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

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KhachHang extends JPanel implements ActionListener, ItemListener {

    protected PanelBorderRadius main, functionBar;
    protected JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    protected JTable tableKhachHang;
    protected JScrollPane scrollTableKhachHang;
    protected MainFunction mainFunction;
    protected JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
    protected IntegratedSearch search;
    protected DefaultTableModel tblModel;
    protected KhachHangDao khachHangDAO = new KhachHangDao();
    protected List<entity.KhachHang> listkh = khachHangDAO.layHet();

    Main m;
    Color BackgroundColor = new Color(240, 247, 250);

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        tableKhachHang = new JTable();
        scrollTableKhachHang = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Khách hàng thân thiết",
                "Ngày tham gia" };
        tblModel.setColumnIdentifiers(header);
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
        tableKhachHang.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableKhachHang, 0, TableSorter.INTEGER_COMPARATOR);

        // pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4 chỉ để thêm contentCenter ở
        // giữa mà contentCenter không bị dính sát vào các thành phần khác
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

        // functionBar là thanh bên trên chứa các nút chức năng như thêm xóa sửa, và tìm
        // kiếm
        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "create", "update", "delete", "detail", "import", "export" };
        mainFunction = new MainFunction("khachhang", action);
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }
        functionBar.add(mainFunction);

        search = new IntegratedSearch(
                new String[] { "Tất cả", "Mã khách hàng", "Tên khách hàng", "Khách hàng thân thiết", "Số điện thoại" });
        search.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listkh = khachHangDAO.layHet();
                loadDataTable();
            }
        });
        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String txt = search.txtSearchForm.getText();
                String type = (String) search.cbxChoose.getSelectedItem();
                if (txt.isEmpty()) {
                    listkh = khachHangDAO.layHet();
                    loadDataTable();
                } else {
                    listkh = search(txt, type);
                    loadDataTable();
                }
            }
        });
        search.cbxChoose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) search.cbxChoose.getSelectedItem();
                    if (selectedOption.equals("Khách hàng thân thiết")) {
                        listkh = search("", selectedOption); // Truyền rỗng cho text và "Khách hàng thân thiết" cho type
                        loadDataTable();
                    } else {
                        listkh = khachHangDAO.layHet();
                        loadDataTable();
                    }
                }
            }
        });

        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        // main là phần ở dưới để thống kê bảng biểu
        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        // main.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentCenter.add(main, BorderLayout.CENTER);

        main.add(scrollTableKhachHang);

    }

    public KhachHang(Main m) {
        this.m = m;
        initComponent();
        tableKhachHang.setDefaultEditor(Object.class, null);
        loadDataTable();
    }

    public List<entity.KhachHang> search(String text, String type) {
        List<entity.KhachHang> result = new ArrayList<>();
        text = text.toLowerCase();

        switch (type) {
            case "Tất cả":
                for (entity.KhachHang i : this.listkh) {
                    if (Integer.toString(i.getMaKhachHang()).toLowerCase().contains(text)
                            || i.getHoTen().toLowerCase().contains(text) || i.laKhachHangThanThiet() == true
                            || i.getSoDienThoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            
            case "Mã khách hàng":
                for (entity.KhachHang i : this.listkh) {
                    if (Integer.toString(i.getMaKhachHang()).toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            
            case "Tên khách hàng":
                for (entity.KhachHang i : this.listkh) {
                    if (i.getHoTen().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            
            case "Khách hàng thân thiết":
                for (entity.KhachHang i : this.listkh) {
                    if (i.laKhachHangThanThiet()) {
                        result.add(i);
                    }
                }
            
            case "Số điện thoại":
                for (entity.KhachHang i : this.listkh) {
                    if (i.getSoDienThoai().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            
        }

        return result;
    }

    public void loadDataTable() {
        tblModel.setRowCount(0);
        tableKhachHang.repaint();
        tableKhachHang.revalidate();

        for (entity.KhachHang khachHang : listkh) {
            tblModel.addRow(new Object[] {
                    "KH" + khachHang.getMaKhachHang(),
                    khachHang.getHoTen(),
                    khachHang.getSoDienThoai(),
                    khachHang.laKhachHangThanThiet() == true ? "Có" : "Không",
                    khachHang.getThoiGianDangKy()
            });
        }
    }

    public int getRowSelected() {
        int index = tableKhachHang.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng");
        }
        return index;
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
                            || !isPhoneNumber(sdt) || sdt.length() != 10 || Validation.isEmpty(diachi)) {
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

    public static boolean isPhoneNumber(String str) {
        // Loại bỏ khoảng trắng và dấu ngoặc đơn nếu có
        str = str.replaceAll("\\s+", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "");

        // Kiểm tra xem chuỗi có phải là một số điện thoại hợp lệ hay không
        if (str.matches("\\d{10}")) { // Kiểm tra số điện thoại 10 chữ số
            return true;
        } else if (str.matches("\\d{3}-\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu gạch ngang
            return true;
        } else if (str.matches("\\(\\d{3}\\)\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu ngoặc đơn
            return true;
        } else {
            return false; // Trả về false nếu chuỗi không phải là số điện thoại hợp lệ
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainFunction.btn.get("create")) {
            new KhachHangDialog(this, owner, "Thêm khách hàng", true, "create");
        } else if (e.getSource() == mainFunction.btn.get("update")) {
            int index = getRowSelected();
            if (index != -1) {
                new KhachHangDialog(
                        this,
                        owner, "Chỉnh sửa khách hàng", true, "update",
                        listkh.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("delete")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa khách hàng ?", "Xóa khách hàng",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == JOptionPane.YES_OPTION) {
                    khachHangDAO.xoa(listkh.get(index).getMaKhachHang());
                    loadDataTable();
                }
            }
        } else if (e.getSource() == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            System.out.println(listkh.get(index).getMaKhachHang());
            if (index != -1) {
                new KhachHangDialog(this, owner, "Xem khách hàng",
                        true, "view", listkh.get(index));
            }
        } else if (e.getSource() == mainFunction.btn.get("import")) {
            importExcel();
        } else if (e.getSource() == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableKhachHang);
            } catch (IOException ex) {
                Logger.getLogger(KhachHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        String type = (String) search.cbxChoose.getSelectedItem();
        String txt = search.txtSearchForm.getText();
        // listkh = khachhangBUS.search(txt, type);
        loadDataTable();
    }
}
