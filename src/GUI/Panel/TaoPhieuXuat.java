//package GUI.Panel;
//
//
//
//
//
//import GUI.Component.*;
//
//import java.awt.*;
//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//
//import GUI.Dialog.QRCode_Dialog;
//import GUI.Main;
//import com.formdev.flatlaf.extras.FlatSVGIcon;
//import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
//import helper.Formater;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemEvent;
//import java.awt.event.KeyAdapter;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Vector;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumnModel;
//
//public final class TaoPhieuXuat extends JPanel {
//
//    JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
//
//    PanelBorderRadius right, left;
//    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter, left_top, main, content_btn;
//    JTable tablePhieuXuat, tableSanPham;
//    JScrollPane scrollTablePhieuNhap, scrollTableSanPham;
//    DefaultTableModel tblModel, tblModelSP;
//    ButtonCustom btnAddSp, btnEditSP, btnDelete, btnImport, btnNhapHang;
//    InputForm txtMaphieu, txtNhanVien, txtMaSp, txtTenSp, txtSoluongTon;
//    SelectForm cbxPhienBan;
//    JTextField txtTimKiem;
//    Color BackgroundColor = new Color(240, 247, 250);
//
//    int sum;
//    int maphieu;
//    int manv;
//    int makh = -1;
//    String type;
//
//    public JTextArea textAreaImei;
//    private JLabel labelImei;
//    private JPanel content_right_bottom_top;
//    private JPanel content_right_bottom_bottom;
//    private Vector v;
//    private CustomComboCheck cbxImei;
//
//    private int mapb;
//    private JLabel lbltongtien;
//    private JTextField txtKh;
//    private Main mainChinh;
//    private ButtonCustom btnQuayLai;
//    private ButtonCustom chonImei;
//    private InputForm txtGiaXuat;
//
//    public TaoPhieuXuat(Main mainChinh,
//                       // TaiKhoanDTO tk,
//                        String type) {
//        this.mainChinh = mainChinh;
//       // this.tk = tk;
//        this.type = type;
//        initComponent(type);
//        //loadDataTalbeSanPham(listSP);
//    }
//
//    public void initPadding() {
//        pnlBorder1 = new JPanel();
//        pnlBorder1.setPreferredSize(new Dimension(0, 5));
//        pnlBorder1.setBackground(BackgroundColor);
//        this.add(pnlBorder1, BorderLayout.NORTH);
//
//        pnlBorder2 = new JPanel();
//        pnlBorder2.setPreferredSize(new Dimension(0, 5));
//        pnlBorder2.setBackground(BackgroundColor);
//        this.add(pnlBorder2, BorderLayout.SOUTH);
//
//        pnlBorder3 = new JPanel();
//        pnlBorder3.setPreferredSize(new Dimension(5, 0));
//        pnlBorder3.setBackground(BackgroundColor);
//        this.add(pnlBorder3, BorderLayout.EAST);
//
//        pnlBorder4 = new JPanel();
//        pnlBorder4.setPreferredSize(new Dimension(5, 0));
//        pnlBorder4.setBackground(BackgroundColor);
//        this.add(pnlBorder4, BorderLayout.WEST);
//    }
//
//    private void initComponent(String type) {
//        this.setBackground(BackgroundColor);
//        this.setLayout(new BorderLayout(0, 0));
//        this.setOpaque(true);
//
//        // Phiếu nhập
//        tablePhieuXuat = new JTable();
//        scrollTablePhieuNhap = new JScrollPane();
//        tblModel = new DefaultTableModel();
//        String[] header = new String[]{"STT", "Mã SP", "Tên sản phẩm", "RAM", "ROM", "Màu sắc", "Đơn giá", "Số lượng"};
//        tblModel.setColumnIdentifiers(header);
//        tablePhieuXuat.setModel(tblModel);
//        scrollTablePhieuNhap.setViewportView(tablePhieuXuat);
//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        TableColumnModel columnModel = tablePhieuXuat.getColumnModel();
//        for (int i = 0; i < 8; i++) {
//            if (i != 2) {
//                columnModel.getColumn(i).setCellRenderer(centerRenderer);
//            }
//        }
//        tablePhieuXuat.getColumnModel().getColumn(2).setPreferredWidth(300);
//        tablePhieuXuat.setFocusable(false);
//        tablePhieuXuat.setDefaultEditor(Object.class, null);
//        scrollTablePhieuNhap.setViewportView(tablePhieuXuat);
//        // Table sản phẩm
//        tableSanPham = new JTable();
//        scrollTableSanPham = new JScrollPane();
//        tblModelSP = new DefaultTableModel();
//        String[] headerSP = new String[]{"Mã SP", "Tên sản phẩm", "Số lượng tồn"};
//        tblModelSP.setColumnIdentifiers(headerSP);
//        tableSanPham.setModel(tblModelSP);
//        scrollTableSanPham.setViewportView(tableSanPham);
//        tableSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
//        tableSanPham.getColumnModel().getColumn(1).setPreferredWidth(300);
//        tableSanPham.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
//
//        tableSanPham.setFocusable(false);
//        scrollTableSanPham.setViewportView(tableSanPham);
//
//        initPadding();
//
//        contentCenter = new JPanel();
//        contentCenter.setPreferredSize(new Dimension(1100, 600));
//        contentCenter.setBackground(BackgroundColor);
//        contentCenter.setLayout(new BorderLayout(5, 5));
//        this.add(contentCenter, BorderLayout.CENTER);
//
//        left = new PanelBorderRadius();
//        left.setLayout(new BorderLayout(0, 5));
//        left.setBackground(Color.white);
//
//        left_top = new JPanel(); // Chứa tất cả phần ở phía trái trên cùng
//        left_top.setLayout(new BorderLayout());
//        left_top.setBorder(new EmptyBorder(5, 5, 10, 10));
//        left_top.setOpaque(false);
//
//        JPanel content_top, content_left, content_right, content_right_top, content_right_bottom;
//        content_top = new JPanel(new GridLayout(1, 2, 5, 5));
//        content_top.setOpaque(false);
//        content_left = new JPanel(new BorderLayout(5, 5));
//        content_left.setOpaque(false);
//        content_left.setPreferredSize(new Dimension(0, 300));
//
//        txtTimKiem = new JTextField();
//        txtTimKiem.putClientProperty("JTextField.placeholderText", "Tên sản phẩm, mã sản phẩm...");
//        txtTimKiem.putClientProperty("JTextField.showClearButton", true);
//        txtTimKiem.putClientProperty("JTextField.leadingIcon", new FlatSVGIcon("./icon/search.svg"));
//
//        txtTimKiem.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyReleased(java.awt.event.KeyEvent evt) {
//                //ArrayList<SanPhamDTO> rs = spBUS.search(txtTimKiem.getText());
//                //loadDataTalbeSanPham(rs);
//            }
//        });
//        txtTimKiem.setPreferredSize(new Dimension(100, 40));
//        content_left.add(txtTimKiem, BorderLayout.NORTH);
//        content_left.add(scrollTableSanPham, BorderLayout.CENTER);
//
//        content_right = new JPanel(new BorderLayout(5, 5));
//        content_right.setOpaque(false);
//        content_right.setBackground(Color.WHITE);
//
//        content_right_top = new JPanel(new BorderLayout());
//        content_right_top.setPreferredSize(new Dimension(100, 165));
//        txtMaSp = new InputForm("Mã SP");
//        txtMaSp.setEditable(false);
//        txtTenSp = new InputForm("Tên sản phẩm");
//        txtTenSp.setEditable(false);
//        String[] arrCauhinh = {"Chọn sản phẩm"};
//        JPanel panlePXGX = new JPanel(new GridLayout(1, 3));
//        panlePXGX.setPreferredSize(new Dimension(100, 90));
//        cbxPhienBan = new SelectForm("Cấu hình", arrCauhinh);
//        txtGiaXuat = new InputForm("Giá xuất");
//        txtSoluongTon = new InputForm("Số lượng tồn");
//        txtSoluongTon.setEditable(false);
//        panlePXGX.add(cbxPhienBan);
//        panlePXGX.add(txtGiaXuat);
//        panlePXGX.add(txtSoluongTon);
//        content_right_top.add(txtMaSp, BorderLayout.WEST);
//        content_right_top.add(txtTenSp, BorderLayout.CENTER);
//        content_right_top.add(panlePXGX, BorderLayout.SOUTH);
//        cbxPhienBan.getCbb().addItemListener((ItemEvent e) -> {
//
//        });
//
//        content_right_bottom = new JPanel(new BorderLayout());
//        content_right_bottom.setBorder(new EmptyBorder(0, 10, 10, 10));
//        content_right_bottom.setBackground(Color.WHITE);
//        labelImei = new JLabel("Mã Imei");
//        labelImei.setPreferredSize(new Dimension(70, 0));
//        ButtonCustom scanImei = new ButtonCustom("Quét imei", "success", 14);
//        scanImei.setPreferredSize(new Dimension(110, 0));
//        JPanel panelScanCenter = new JPanel();
//        panelScanCenter.setBackground(Color.WHITE);
//        JPanel jpanelImei = new JPanel(new BorderLayout());
//        jpanelImei.setPreferredSize(new Dimension(0, 40));
//        jpanelImei.setBackground(Color.WHITE);
//        jpanelImei.setBorder(new EmptyBorder(0, 0, 10, 0));
//        jpanelImei.add(labelImei, BorderLayout.WEST);
//        chonImei = new ButtonCustom("Chọn Imei", "success", 14);
//
//        chonImei.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//        JPanel jPanelChonImei = new JPanel(new GridLayout(1, 2));
//        jPanelChonImei.setPreferredSize(new Dimension(200, 0));
//        jPanelChonImei.setOpaque(false);
//        jPanelChonImei.add(chonImei);
//        jPanelChonImei.add(scanImei);
//
//        jpanelImei.add(panelScanCenter, BorderLayout.CENTER);
//        jpanelImei.add(jPanelChonImei, BorderLayout.EAST);
//
//        scanImei.addActionListener((ActionEvent e) -> {
//
//        });
//        textAreaImei = new JTextArea();
//        textAreaImei.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                String[] arrimei = textAreaImei.getText().split("\n");
//                for (int i = 0; i < arrimei.length; i++) {
//                    boolean check = false;
//
//                    if (!check) {
//                        String txt = textAreaImei.getText().replaceAll("(" + arrimei[i] + ")\n", "");
//                        textAreaImei.setText(txt);
//                    }
//                }
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                // Xử lý sự kiện khi có nội dung bị xóa đi
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                // Xử lý sự kiện khi có thay đổi khác
//            }
//        });
//        textAreaImei.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
//        this.textAreaImei.setEditable(false);
//        content_right_bottom_top = new JPanel(new BorderLayout());
//        content_right_bottom_top.setSize(new Dimension(0, 100));
//        content_right_bottom_top.setBackground(Color.white);
//        content_right_bottom_top.add(jpanelImei, BorderLayout.NORTH);
//        content_right_bottom_top.add(textAreaImei, BorderLayout.CENTER);
//        content_right_bottom_bottom = new JPanel(new BorderLayout());
//        content_right_bottom_bottom.setSize(new Dimension(0, 50));
//        content_right_bottom_bottom.setBorder(new EmptyBorder(20, 0, 0, 0));
//
//        content_right_bottom.add(content_right_bottom_top, BorderLayout.CENTER);
//
//        content_right.add(content_right_top, BorderLayout.NORTH);
//        content_right.add(content_right_bottom, BorderLayout.CENTER);
//
//        content_top.add(content_left);
//        content_top.add(content_right);
//
//        content_btn = new JPanel();
//        content_btn.setPreferredSize(new Dimension(0, 47));
//        content_btn.setLayout(new GridLayout(1, 4, 5, 5));
//        content_btn.setBorder(new EmptyBorder(8, 5, 0, 10));
//        content_btn.setOpaque(false);
//        btnAddSp = new ButtonCustom("Thêm sản phẩm", "success", 14);
//
//        btnEditSP = new ButtonCustom("Sửa sản phẩm", "warning", 14);
//        btnDelete = new ButtonCustom("Xoá sản phẩm", "danger", 14);
//        btnImport = new ButtonCustom("Nhập Excel", "excel", 14);
//        content_btn.add(btnAddSp);
//        content_btn.add(btnImport);
//        content_btn.add(btnEditSP);
//        content_btn.add(btnDelete);
//
//        btnAddSp.addActionListener((ActionEvent e) -> {
//
//        });
//
//        btnDelete.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        btnDelete.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        btnImport.addActionListener((ActionEvent e) -> {
//            JOptionPane.showMessageDialog(this, "Chức năng không khả dụng !", "Thông báo", JOptionPane.WARNING_MESSAGE);
//        });
//
//        left_top.add(content_top, BorderLayout.CENTER);
//        left_top.add(content_btn, BorderLayout.SOUTH);
//
//        main = new JPanel();
//        main.setOpaque(false);
//        main.setPreferredSize(new Dimension(0, 280));
//        main.setBorder(new EmptyBorder(0, 5, 10, 10));
//        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
//        main.setLayout(boxly);
//        main.add(scrollTablePhieuNhap);
//        left.add(left_top, BorderLayout.CENTER);
//        left.add(main, BorderLayout.SOUTH);
//
//        right = new PanelBorderRadius();
//        right.setPreferredSize(new Dimension(320, 0));
//        right.setBorder(new EmptyBorder(5, 5, 5, 5));
//        right.setLayout(new BorderLayout());
//
//        JPanel right_top, right_center, right_bottom, pn_tongtien;
//        right_top = new JPanel(new GridLayout(2, 1, 0, 0));
//        right_top.setPreferredSize(new Dimension(300, 180));
//        txtMaphieu = new InputForm("Mã phiếu xuất");
//        txtMaphieu.setEditable(false);
//        txtNhanVien = new InputForm("Nhân viên xuất");
//        txtNhanVien.setEditable(false);
//
//      //  NhanVienDTO nhanvien = NhanVienDAO.getInstance().selectById(tk.getManv() + "");
//        //txtNhanVien.setText(nhanvien.getHoten());
//
//        right_top.add(txtMaphieu);
//        right_top.add(txtNhanVien);
//
//        right_center = new JPanel(new BorderLayout());
//        JPanel khachJPanel = new JPanel(new BorderLayout());
//        khachJPanel.setPreferredSize(new Dimension(0, 40));
//        khachJPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
//        khachJPanel.setOpaque(false);
//        JPanel kJPanelLeft = new JPanel(new GridLayout(1, 1));
//        kJPanelLeft.setOpaque(false);
//        kJPanelLeft.setPreferredSize(new Dimension(40, 0));
//        ButtonCustom btnKh = new ButtonCustom("Chọn khách hàng", "success", 14);
//        kJPanelLeft.add(btnKh);
//
//
//        txtKh = new JTextField("");
//        txtKh.setEditable(false);
//        khachJPanel.add(kJPanelLeft, BorderLayout.EAST);
//        khachJPanel.add(txtKh, BorderLayout.CENTER);
//        JPanel khPanel = new JPanel(new GridLayout(2, 1, 5, 0));
//        khPanel.setBackground(Color.WHITE);
//        khPanel.setPreferredSize(new Dimension(0, 80));
//        JLabel khachKhangJLabel = new JLabel("Khách hàng");
//        khachKhangJLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
//        khPanel.add(khachKhangJLabel);
//        khPanel.add(khachJPanel);
//
//        right_center.add(khPanel, BorderLayout.NORTH);
//        right_center.setOpaque(false);
//
//        right_bottom = new JPanel(new GridLayout(2, 1));
//        right_bottom.setPreferredSize(new Dimension(300, 100));
//        right_bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
//        right_bottom.setOpaque(false);
//
//        pn_tongtien = new JPanel(new FlowLayout(1, 20, 0));
//        pn_tongtien.setOpaque(false);
//        JLabel lbltien = new JLabel("TỔNG TIỀN: ");
//        lbltien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
//        lbltongtien = new JLabel("0đ");
//        lbltongtien.setFont(new Font(FlatRobotoFont.FAMILY, 1, 18));
//        lbltien.setForeground(new Color(255, 51, 51));
//        pn_tongtien.add(lbltien);
//        pn_tongtien.add(lbltongtien);
//
//        tableSanPham.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                int index = tableSanPham.getSelectedRow();
//                if (index != -1) {
//                 //   setInfoSanPham(listSP.get(index));
//                }
//
//            }
//        });
//
//        tablePhieuXuat.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int index = tablePhieuXuat.getSelectedRow();
//                if (index != -1) {
//                    actionbtn("update");
//
//                }
//            }
//        });
//        btnNhapHang = new ButtonCustom("Xuất hàng", "excel", 14);
//        btnQuayLai = new ButtonCustom("Quay lại", "excel", 14);
//        right_bottom.add(pn_tongtien);
//        if (type.equals("create")) {
//            right_bottom.add(btnNhapHang);
//        } else if (type.equals("detail")) {
//            right_bottom.add(btnQuayLai);
//        }
//
//        btnNhapHang.addActionListener((ActionEvent e) -> {
//
//        });
//
//        btnQuayLai.addActionListener((ActionEvent e) -> {
//
//            //mainChinh.setPanel(phieuXuatPanel);
//        });
//
//        right.add(right_top, BorderLayout.NORTH);
//        right.add(right_center, BorderLayout.CENTER);
//        right.add(right_bottom, BorderLayout.SOUTH);
//
//        contentCenter.add(left, BorderLayout.CENTER);
//        contentCenter.add(right, BorderLayout.EAST);
//        actionbtn("add");
//    }
//
//    public void loadDataTalbeSanPham(ArrayList<DTO.SanPhamDTO> result) {
//        tblModelSP.setRowCount(0);
//        for (DTO.SanPhamDTO sp : result) {
//            tblModelSP.addRow(new Object[]{sp.getMasp(), sp.getTensp(), sp.getSoluongton()});
//        }
//    }
//
//
//    public boolean checkInfo() {
//        boolean check = true;
//        if (txtMaSp.getText().equals("")) {
//            JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm");
//            check = false;
//        } else if (textAreaImei.getText().equals("")) {
//            JOptionPane.showMessageDialog(null, "Vui lòng chọn mã imei");
//            check = false;
//        }
//
//        return check;
//    }
//
//
//
//
//    public void actionbtn(String type) {
//        boolean val_1 = type.equals("add");
//        boolean val_2 = type.equals("update");
//        btnAddSp.setEnabled(val_1);
//        btnImport.setEnabled(val_1);
//        btnEditSP.setEnabled(val_2);
//        btnDelete.setEnabled(val_2);
//        content_btn.revalidate();
//        content_btn.repaint();
//    }
//}
