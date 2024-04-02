package GUI.Panel;

import DTO.PhieuNhapDTO;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Main;
import GUI.Component.IntegratedSearch;
import GUI.Component.MainFunction;
import GUI.Component.NumericDocumentFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import GUI.Component.PanelBorderRadius;
import GUI.Component.SelectForm;
import GUI.Component.SpinnerForm;
import GUI.Component.TableSorter;
import GUI.Dialog.ChiTietPhieuDialog;
import dao1.ChuyenDao;
import dao1.GaDao;
import dao1.KhachHangDao;
import dao1.TauDao;
import dao1.TuyenDao;
import entity.Chuyen;
import entity.Ga;
import entity.Tau;
import entity.Tuyen;
import helper.JTableExporter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class BanVe extends JPanel implements ActionListener, KeyListener, PropertyChangeListener, ItemListener {

    PanelBorderRadius main, functionBar, box;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    JTable tableChuyenTau;
    JScrollPane scrollTableChuyenTau;
    MainFunction mainFunction;
    IntegratedSearch search;
    DefaultTableModel tblModel;
    SelectForm cbxGaDi, cbxGaDen;
    JCheckBox checkBoxKhuHoi;
    InputDate dateNgayDi, dateNgayVe;
    SpinnerForm soLuongHanhKhach;

    TaoPhieuNhap nhapKho;
    Main m;
    entity.NhanVien nv;

    ArrayList<PhieuNhapDTO> listPhieu;
    private GaDao gaDao = new GaDao();
    private TuyenDao tuyenDao;
    private ChuyenDao chuyenDao;
    private TauDao tauDao;
    private java.util.List<Ga> gaList = gaDao.layHet();
    private List<Chuyen> chuyenList;
    private String[] listTenGa;

    Color BackgroundColor = new Color(240, 247, 250);

    public BanVe(Main m) {
        this.m = m;
        // this.nv = nv;
        this.tuyenDao = new TuyenDao();
        this.chuyenDao = new ChuyenDao();
        this.tauDao = new TauDao();

        initComponent();
    }

    public void initPadding() {
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
    }

    private void initComponent() {
        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        tableChuyenTau = new JTable();
        scrollTableChuyenTau = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] header = new String[] { "Mã chuyến", "Tuyến", "Tàu", "Thời gian khởi hành", "Thời gian đến dự kiến" };
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

        this.setBackground(BackgroundColor);
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] action = { "find", "detail", "cancel", "export" };
        mainFunction = new MainFunction(m.user.getManhomquyen(), "nhaphang", action);

        // Add Event MouseListener
        for (String ac : action) {
            mainFunction.btn.get(ac).addActionListener(this);
        }

        functionBar.add(mainFunction);

        String[] objToSearch = { "Tất cả", "Mã phiếu nhập", "Nhà cung cấp", "Nhân viên nhập" };
        search = new IntegratedSearch(objToSearch);
        search.cbxChoose.addItemListener(this);
        search.txtSearchForm.addKeyListener(this);
        search.btnReset.addActionListener(this);
        functionBar.add(search);

        contentCenter.add(functionBar, BorderLayout.NORTH);

        box = new PanelBorderRadius();
        box.setPreferredSize(new Dimension(250, 0));
        box.setLayout(new GridLayout(6, 1, 10, 0));
        box.setBorder(new EmptyBorder(0, 5, 150, 5));
        contentCenter.add(box, BorderLayout.WEST);

        // Handle
        listTenGa = loadDataGaVaoComboBox(this.gaList);
        // init
        cbxGaDi = new SelectForm("Ga đi", listTenGa);
        cbxGaDi.cbb.setEditable(true);
        cbxGaDen = new SelectForm("Ga đến", listTenGa);
        cbxGaDen.cbb.setEditable(true);
        dateNgayDi = new InputDate("Ngày đi");
        checkBoxKhuHoi = new JCheckBox("Khứ hồi");
        dateNgayVe = new InputDate("Ngày về");
        dateNgayVe.setVisible(false);
        soLuongHanhKhach = new SpinnerForm("Số lượng hành khách");

        // add listener
        cbxGaDi.getCbb().addItemListener(this);
        cbxGaDen.getCbb().addItemListener(this);
        dateNgayDi.getDateChooser().addPropertyChangeListener(this);
        dateNgayVe.getDateChooser().addPropertyChangeListener(this);

        cbxGaDi.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchGa(cbxGaDi);
                cbxGaDi.getCbb().showPopup();
            }

        });

        cbxGaDen.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchGa(cbxGaDen);
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
        box.add(dateNgayVe);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableChuyenTau);
    }

    public void loadDataTalbe() {
        tblModel.setRowCount(0);
        tableChuyenTau.repaint();
        tableChuyenTau.revalidate();

        this.chuyenList = this.timChuyen();
        for (Chuyen chuyen : chuyenList) {
            Tuyen tuyen = this.tuyenDao.layTheoMa(chuyen.getTuyen().getMaTuyen());
            Ga gaDi = this.gaDao.layTheoMa(tuyen.getGaDi().getMaGa());
            Ga gaDen = this.gaDao.layTheoMa(tuyen.getGaDen().getMaGa());
            Tau tau = this.tauDao.layTheoMa(chuyen.getTau().getMaTau());
            this.tblModel.addRow(new Object[] {
                    chuyen.getMaChuyen(),
                    gaDi.getTenGa() + "-" + gaDen.getTenGa(),
                    tau.getTenTau(),
                    chuyen.getThoiGianKhoiHanh(),
                    chuyen.getThoiGianDen()
            });
        }
    }

    public String[] loadDataGaVaoComboBox(List<Ga> gaList) {
        listTenGa = new String[gaList.size()];

        for (int i = 0; i < gaList.size(); i++) {
            Ga ga = gaList.get(i);
            listTenGa[i] = ga.getTenGa();
        }

        return listTenGa;
    }

    private List<Ga> getListGaTheoTen(String text) {
        List<Ga> result = new ArrayList<>();
        text = text.toLowerCase();

        for (Ga ga : gaList) {
            if (ga.getTenGa().toLowerCase().contains(text)) {
                result.add(ga);
            }
        }
        return result;
    }

    private void searchGa(SelectForm selectForm) {
        JTextField editorComponent = (JTextField) selectForm.getCbb().getEditor().getEditorComponent();
        String previousText = editorComponent.getText();
        String text = previousText.trim();

        if (text.isEmpty()) {
            gaList = gaDao.layHet();
            listTenGa = loadDataGaVaoComboBox(gaList);
            selectForm.setArr(listTenGa);
        }

        gaList = getListGaTheoTen(text);
        listTenGa = loadDataGaVaoComboBox(gaList);
        selectForm.setArr(listTenGa);

        editorComponent.setText(previousText);
    }

    public int getRowSelected() {
        int index = tableChuyenTau.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập");
        }
        return index;
    }

    public void Fillter() throws ParseException {
        int type = search.cbxChoose.getSelectedIndex();
        // int mancc = cbxGaDi.getSelectedIndex() == 0 ? 0
        // : nccBUS.getByIndex(cbxGaDi.getSelectedIndex() - 1).getMancc();
        // int manv = cbxGaDen.getSelectedIndex() == 0 ? 0
        // : nvBUS.getByIndex(cbxGaDen.getSelectedIndex() - 1).getManv();
        String input = search.txtSearchForm.getText() != null ? search.txtSearchForm.getText() : "";
        Date ngayDi = dateNgayDi.getDate() != null ? dateNgayDi.getDate() : new Date(0);
        Date ngayVe = dateNgayVe.getDate() != null ? dateNgayVe.getDate() : new Date(System.currentTimeMillis());
        // this.listPhieu = phieunhapBUS.fillerPhieuNhap(type, input, mancc, manv,
        // ngayDi, ngayVe, min_price,
        // max_price);
        // loadDataTalbe();
    }

    public void resetForm() {
        cbxGaDi.setSelectedIndex(0);
        cbxGaDen.setSelectedIndex(0);
        search.cbxChoose.setSelectedIndex(0);
        search.txtSearchForm.setText("");
        soLuongHanhKhach.setValue(0);
        dateNgayDi.getDateChooser().setCalendar(null);
        dateNgayVe.getDateChooser().setCalendar(null);
        // this.listPhieu = phieunhapBUS.getAllList();
        // loadDataTalbe();
    }

    public boolean validateSelectDate() throws ParseException {
        Date ngayDi = dateNgayDi.getDate();
        Date ngayVe = dateNgayVe.getDate();

        Date current_date = new Date();
        if (ngayDi != null && ngayDi.before(current_date)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được nhỏ hơn ngày hiện tại", "Lỗi !",
                    JOptionPane.ERROR_MESSAGE);
            dateNgayDi.getDateChooser().setCalendar(null);
            return false;
        }
        if (ngayVe != null && ngayVe.before(ngayDi)) {
            JOptionPane.showMessageDialog(this, "Ngày về không được nhỏ hơn ngày ngày đi", "Lỗi !",
                    JOptionPane.ERROR_MESSAGE);
            dateNgayVe.getDateChooser().setCalendar(null);
            return false;
        }
        if (ngayDi != null && ngayVe != null && ngayDi.after(ngayVe)) {
            JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn ngày bắt đầu", "Lỗi !",
                    JOptionPane.ERROR_MESSAGE);
            dateNgayVe.getDateChooser().setCalendar(null);
            return false;
        }
        return true;
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

    private List<Chuyen> timChuyen() {
        Tuyen tuyen = null;
        List<Chuyen> chuyens = new ArrayList<>();
        if (this.validation()) {
            String maGaDi = this.cbxGaDi.getSelectedItem().toString();
            String maGaDen = this.cbxGaDen.getSelectedItem().toString();

            try {
                Ga gaDi = this.gaDao.layTheoTen(maGaDi);
                Ga gaDen = this.gaDao.layTheoTen(maGaDen);

                tuyen = this.tuyenDao.timTuyen(gaDi.getMaGa(), gaDen.getMaGa());

                if (tuyen == null) {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy tuyến");
                }

                chuyens = this.chuyenDao.timChuyenTheoTuyen(tuyen.getMaTuyen(), dateNgayDi.getDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return chuyens;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == mainFunction.btn.get("find")) {
            loadDataTalbe();
        } else if (source == mainFunction.btn.get("detail")) {
            int index = getRowSelected();
            if (index != -1) {
                // nhapKho = new TaoPhieuNhap(nv, "view", listPhieu.get(index), m);
                // m.setPanel(nhapKho);
                ChiTietPhieuDialog ctsp = new ChiTietPhieuDialog(m, "Thông tin phiếu nhập", true, listPhieu.get(index));
            }
        } else if (source == mainFunction.btn.get("cancel")) {
            int index = getRowSelected();
            if (index != -1) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn huỷ phiếu ?\nThao tác này không thể hoàn tác nên hãy suy nghĩ kĩ !",
                        "Huỷ phiếu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (input == 0) {
                    PhieuNhapDTO pn = listPhieu.get(index);
                    System.out.println(pn);
                    // if (!phieunhapBUS.checkCancelPn(pn.getMaphieu())) {
                    // JOptionPane.showMessageDialog(null,
                    // "Sản phẩm trong phiếu này đã được xuất đi không thể hủy phiếu này!");
                    // } else {
                    // int c = phieunhapBUS.cancelPhieuNhap(pn.getMaphieu());
                    // if (c == 0) {
                    // JOptionPane.showMessageDialog(null, "Hủy phiếu không thành công!");
                    // } else {
                    // JOptionPane.showMessageDialog(null, "Hủy phiếu thành công!");
                    // loadDataTalbe(phieunhapBUS.getAll());
                    // }
                    // }
                }
            }
        } else if (source == search.btnReset) {
            resetForm();
        } else if (source == mainFunction.btn.get("export")) {
            try {
                JTableExporter.exportJTableToExcel(tableChuyenTau);
            } catch (IOException ex) {
                Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            Fillter();
        } catch (ParseException ex) {
            Logger.getLogger(PhieuNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
