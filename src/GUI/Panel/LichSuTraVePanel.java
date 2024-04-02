package GUI.Panel;

import DTO.PhieuNhapDTO;
import GUI.Component.*;
import GUI.Main;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.*;
import helper.JTableExporter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.text.PlainDocument;

public final class LichSuTraVePanel extends JPanel implements KeyListener, PropertyChangeListener, ItemListener {

    PanelBorderRadius main, functionBar, box;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableLichSuTraVe;
    JScrollPane scrollTableLichSuTraVe;

    private DefaultTableModel tblModel;
    private SelectForm gaDenComboBox, gaDiCombox;
    private InputDate thoiGianTraVe;
    private InputForm soDienThoaiInputForm;
    private List<LichSuTraVe> danhSachLichSuTraVe;


    public LichSuTraVePanel() {
        initComponent();
        layDanhSachNhaGa();
        layDanhSachLichSu();
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

        tableLichSuTraVe = new JTable();
        scrollTableLichSuTraVe = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] headerCols = "Mã vé;Tên khách hàng;Chỗ ngồi;Thời gian trả vé;Ghi chú".split(";");
        tblModel.setColumnIdentifiers(headerCols);
        tableLichSuTraVe.setModel(tblModel);
        tableLichSuTraVe.setDefaultEditor(Object.class, null);
        scrollTableLichSuTraVe.setViewportView(tableLichSuTraVe);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableLichSuTraVe.setDefaultRenderer(Object.class, centerRenderer);
        tableLichSuTraVe.setFocusable(false);
        tableLichSuTraVe.getColumnModel().getColumn(0).setPreferredWidth(10);
        tableLichSuTraVe.getColumnModel().getColumn(1).setPreferredWidth(10);
        tableLichSuTraVe.getColumnModel().getColumn(2).setPreferredWidth(10);

        tableLichSuTraVe.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableLichSuTraVe, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableLichSuTraVe, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableLichSuTraVe, 4, TableSorter.VND_CURRENCY_COMPARATOR);

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

        ChucNangChinh chucNangChinh = new ChucNangChinh(new String[]{"chi-tiet", "nhap-excel", "xuat-excel"});
        functionBar.add(chucNangChinh);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        box = new PanelBorderRadius();
        box.setPreferredSize(new Dimension(250, 0));
        box.setLayout(new GridLayout(6, 1, 10, 0));
        box.setBorder(new EmptyBorder(0, 5, 150, 5));
        contentCenter.add(box, BorderLayout.WEST);

        gaDenComboBox = new SelectForm("Ga đi");
        gaDiCombox = new SelectForm("Ga đến");

        thoiGianTraVe = new InputDate("Thời gian trả vé");

        soDienThoaiInputForm = new InputForm("Số điện thoại khách hàng");
        soDienThoaiInputForm.setEditable(true);
        soDienThoaiInputForm.requestFocus();


        //PlainDocument doc_min = (PlainDocument) moneyMin.getTxtForm().getDocument();
        //doc_min.setDocumentFilter(new NumericDocumentFilter());


//        dateStart.getDateChooser().addPropertyChangeListener(this);
//        dateEnd.getDateChooser().addPropertyChangeListener(this);
        //moneyMin.getTxtForm().addKeyListener(this);
//        moneyMin.getTxtForm().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent e) {
//                System.out.println("TextField được click");
//            }
//        });

        chucNangChinh
                .getToolbar("chi-tiet")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!kiemTraChonDong())
                            return;


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

        box.add(gaDiCombox);
        box.add(gaDenComboBox);
        box.add(soDienThoaiInputForm);
        box.add(thoiGianTraVe);

        main = new PanelBorderRadius();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableLichSuTraVe);
    }

    public void Fillter() throws ParseException {
//        if (validateSelectDate()) {
//            int type = search.cbxChoose.getSelectedIndex();
//            // int mancc = cbxNhaCungCap.getSelectedIndex() == 0 ? 0
//            // : nccBUS.getByIndex(cbxNhaCungCap.getSelectedIndex() - 1).getMancc();
//            // int manv = cbxNhanVien.getSelectedIndex() == 0 ? 0
//            // : nvBUS.getByIndex(cbxNhanVien.getSelectedIndex() - 1).getManv();
//            String input = search.txtSearchForm.getText() != null ? search.txtSearchForm.getText() : "";
//            Date time_start = dateStart.getDate() != null ? dateStart.getDate() : new Date(0);
//            Date time_end = dateEnd.getDate() != null ? dateEnd.getDate() : new Date(System.currentTimeMillis());
//            String min_price = moneyMin.getText();
//            // this.listPhieu = phieunhapBUS.fillerPhieuNhap(type, input, mancc, manv,
//            // time_start, time_end, min_price,
//            // max_price);
//            loadDataTalbe(listPhieu);
//        }
    }

    public void resetForm() {

    }

    private LichSuTraVe layLichSuTraVeTaiDong() {
        int row = tableLichSuTraVe.getSelectedRow();
        int maLichSuTraVe = Integer.parseInt(tblModel.getValueAt(row, 0).toString());

        return danhSachLichSuTraVe.stream()
                .filter(item -> item.getMaLichSuTraVe() == maLichSuTraVe)
                .findAny()
                .orElse(null);
    }

    private boolean kiemTraChonDong() {
        if (tableLichSuTraVe.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng");
            return false;
        }
        return true;
    }

    public boolean validateSelectDate() throws ParseException {
//        Date time_start = dateStart.getDate();
//        Date time_end = dateEnd.getDate();
//
//        Date current_date = new Date();
//        if (time_start != null && time_start.after(current_date)) {
//            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày hiện tại", "Lỗi !",
//                    JOptionPane.ERROR_MESSAGE);
//            dateStart.getDateChooser().setCalendar(null);
//            return false;
//        }
//        if (time_end != null && time_end.after(current_date)) {
//            JOptionPane.showMessageDialog(this, "Ngày kết thúc không được lớn hơn ngày hiện tại", "Lỗi !",
//                    JOptionPane.ERROR_MESSAGE);
//            dateEnd.getDateChooser().setCalendar(null);
//            return false;
//        }
//        if (time_start != null && time_end != null && time_start.after(time_end)) {
//            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !",
//                    JOptionPane.ERROR_MESSAGE);
//            dateEnd.getDateChooser().setCalendar(null);
//            return false;
//        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(BanVe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(BanVe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(BanVe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void layDanhSachNhaGa() {
        String[] listNcc = {"Vui vẻ nha"};
        listNcc = Stream.concat(Stream.of("Tất cả"), Arrays.stream(listNcc)).toArray(String[]::new);
        String[] listNv = {"Nhân viên thân thiện"};
        listNv = Stream.concat(Stream.of("Tất cả"), Arrays.stream(listNv)).toArray(String[]::new);
    }

    private void xuatLichSuTraVeExcel() {
        try {
            JTableExporter.exportJTableToExcel(tableLichSuTraVe);
            Logger.getLogger(BanVe.class.getName()).log(Level.INFO, "Xuất lịch sửa trả vé thành công");
        } catch (IOException ex) {
            Logger.getLogger(BanVe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void layDanhSachLichSu() {
        danhSachLichSuTraVe = new ArrayList<>();
        Ve ve = new Ve( "tphcm-quynhon",  20,  1000000,  null,  1,  new LoaiVe("giuong-nam-khoang-4"), null);
        entity.KhachHang kh = new entity.KhachHang(1, "Owen Shaw", "0868684961", LocalDateTime.now(), false);
        LichSuTraVe ls = new LichSuTraVe(1, LocalDateTime.of(2023, 03, 23, 12, 30, 50), "Khong co", kh, ve);
        danhSachLichSuTraVe.add(ls);

        for (LichSuTraVe item : danhSachLichSuTraVe) {
            tblModel.addRow(new String[] {
                    item.getVe().getMaVe(),
                    item.getKhachHang().getHoTen(),
                    String.valueOf(item.getVe().getChoNgoi()),
                    item.getThoiGianTraVe().toString(),
                    item.getGhiChu()
            });
        }
    }
}
