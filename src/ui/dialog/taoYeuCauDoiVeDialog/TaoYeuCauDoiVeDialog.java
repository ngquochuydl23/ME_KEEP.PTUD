package ui.dialog.taoYeuCauDoiVeDialog;

import dao.*;
import entity.*;
import helper.Validation;
import singleton.NhanVienSuDungSingleton;
import ui.component.*;
import ui.dialog.khachHangDialog.SuaKhachHangListener;
import ui.dialog.khachHangDialog.TaoKhachHangListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ui.dialog.chonChoDialog.ChonChoDialog;
import ui.dialog.chonChoDialog.ChonChoNgoiListener;
import ui.dialog.thanhToanDialog.ThanhToanDialog;
import ui.dialog.thanhToanDialog.ThanhToanListener;

public class TaoYeuCauDoiVeDialog extends JDialog implements WindowListener, MouseListener {
    private HeaderTitle titlePage;
    private JPanel pnlButtom;
    private ButtonCustom btnSubmit, btnHuyBo, btnTimVe, btnTim;
    private InputForm tenKhachHangTextField;
    private InputForm soDienThoaiTextField;
    private InputForm timMaVeTextField;
    private InputForm maVeTextField;
    private InputForm tuyenTextField;
    private InputForm tauTextField;
    private InputForm toaTextField;
    private InputForm choNgoiTextField;
    private InputForm tenNguoiDiTextField;
    private InputForm cccdNguoiDiTextField;

    private SelectForm diemDiSelectForm;
    private SelectForm diemDenSelectForm;
    private InputDate ngayDiDate;
    private KhachHangDao khachHangDao;
    private TaoKhachHangListener taoKhachHangListener;
    private SuaKhachHangListener suaKhachHangListener;
    private List<String> tenGaList;
    private GaDao gaDao;
    private VeDao veDao;
    private ChuyenDao chuyenDao;
    private TiepNhanYeuCauDoiVeDao yeuCauDoiVeDao;
    private KhachHang khachHang;
    private DefaultTableModel tblModel;
    private JTable chuyenTable;
    private ChonChoDialog chonChoDialog;
    private ThanhToanDialog thanhToanDialog;
    private ToaTau toaTauChon;
    private List<SlotBtn> dsChoDaChon = new ArrayList<>();

    private TauDao tauDao;

    public TaoYeuCauDoiVeDialog() {
        thanhToanDialog = new ThanhToanDialog();
        veDao = new VeDao();
        gaDao = new GaDao();
        chuyenDao = new ChuyenDao();
        tenGaList = new ArrayList<>();
        khachHangDao = new KhachHangDao();
        tauDao = new TauDao();
        yeuCauDoiVeDao = new TiepNhanYeuCauDoiVeDao();
        initComponents();

        tenGaList = gaDao.layHetTenGa();

        diemDiSelectForm.setCbItems(tenGaList);
        diemDiSelectForm.setSelectedItem(null);

        diemDenSelectForm.setCbItems(tenGaList);
        diemDenSelectForm.setSelectedItem(null);
        chuyenTable.addMouseListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        xoaDuLieu();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public void initComponents() {
        addWindowListener(this);
        setSize(new Dimension(800, 900));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Ghi nhận yêu cầu đổi vé");

        chonChoDialog = new ChonChoDialog();
        chonChoDialog.setMaxChoNgoiCoTheChon(1);
        chonChoDialog.setChonChoNgoiListener(new ChonChoNgoiListener() {
            @Override
            public void chonChoNgoiThanhCong(ToaTau toaTau, List<SlotBtn> dsCho) {
                toaTauChon = toaTau;
                dsChoDaChon.clear();
                dsChoDaChon.addAll(dsCho);
                chonChoDialog.setVisible(false);

                String tenGaDi = diemDiSelectForm.getSelectedItem().toString();
                String tenGaDen = diemDenSelectForm.getSelectedItem().toString();


                thanhToanDialog.setData(
                        NhanVienSuDungSingleton.layThongTinNhanVienHienTai(),
                        tenGaDi,
                        tenGaDen,
                        new Tau(toaTau.getTau().getMaTau()),
                        toaTau,
                        khachHang,
                        dsChoDaChon
                );
                thanhToanDialog.setVisible(true);
            }
        });

        thanhToanDialog.setThanhToanListener(new ThanhToanListener() {
            @Override
            public void thanhToanThanhCong(HoaDon hoaDon) {

            }
        });

        tenKhachHangTextField = new InputForm("Tên khách hàng");
        tenKhachHangTextField.setEditable(false);
        soDienThoaiTextField = new InputForm("Số điện thoại");
        timMaVeTextField = new InputForm("Nhập mã vé");
        diemDiSelectForm = new SelectForm("Điểm đi");
        diemDenSelectForm = new SelectForm("Điểm đến");
        ngayDiDate = new InputDate("Ngày đi");


        Box phieuYeuCauDoiVeBox = Box.createVerticalBox();
        phieuYeuCauDoiVeBox.setBorder(new TitledBorder("Phiếu yêu cầu đổi vé"));
        phieuYeuCauDoiVeBox.setPreferredSize(new Dimension(300, 300));
        phieuYeuCauDoiVeBox.add(soDienThoaiTextField);
        phieuYeuCauDoiVeBox.add(tenKhachHangTextField);
        phieuYeuCauDoiVeBox.add(ngayDiDate);
        phieuYeuCauDoiVeBox.add(diemDiSelectForm);
        phieuYeuCauDoiVeBox.add(diemDenSelectForm);
        phieuYeuCauDoiVeBox.add(timMaVeTextField);


        maVeTextField = new InputForm("Mã vé");
        maVeTextField.setEditable(false);

        tuyenTextField = new InputForm("Tuyến");
        tuyenTextField.setEditable(false);

        tauTextField = new InputForm("Tàu");
        tauTextField.setEditable(false);

        choNgoiTextField = new InputForm("Chồ ngồi");
        choNgoiTextField.setEditable(false);

        tenNguoiDiTextField = new InputForm("Tên hành khách");
        tenNguoiDiTextField.setEditable(false);

        cccdNguoiDiTextField = new InputForm("CCCD Hành khách");
        cccdNguoiDiTextField.setEditable(false);

        Box thongTinVeBox = Box.createVerticalBox();
        thongTinVeBox.setBorder(new TitledBorder("Thông tin vé"));
        thongTinVeBox.add(maVeTextField);
        thongTinVeBox.add(tuyenTextField);
        thongTinVeBox.add(tauTextField);
        thongTinVeBox.add(choNgoiTextField);
        thongTinVeBox.add(tenNguoiDiTextField);
        thongTinVeBox.add(cccdNguoiDiTextField);


        JPanel pnlThongTin = new JPanel(new BorderLayout());
        pnlThongTin.setPreferredSize(new Dimension(780, 400));
        pnlThongTin.setBackground(Color.white);
        pnlThongTin.add(phieuYeuCauDoiVeBox, BorderLayout.WEST);
        pnlThongTin.add(thongTinVeBox, BorderLayout.CENTER);


        diemDiSelectForm.cbb.setEditable(true);
        diemDiSelectForm.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timGa(diemDiSelectForm);
                diemDiSelectForm.getCbb().showPopup();
            }

        });

        diemDenSelectForm.cbb.setEditable(true);
        diemDenSelectForm.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timGa(diemDenSelectForm);
                diemDenSelectForm.getCbb().showPopup();
            }

        });

        JPanel pnlMain = new JPanel(new BorderLayout());
        pnlMain.add(pnlThongTin, BorderLayout.NORTH);
        String[] header = new String[]{"Mã chuyến", "Tuyến", "Tàu", "Thời gian khởi hành", "Thời gian đến dự kiến"};
        tblModel = new DefaultTableModel(header, 0);
        pnlMain.add(new JScrollPane(chuyenTable = new JTable(tblModel)), BorderLayout.CENTER);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnTimVe = new ButtonCustom("Tìm chuyến", "success", 14);
        btnSubmit = new ButtonCustom("Tạo yêu cầu", "success", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnTim = new ButtonCustom("Tìm khách hàng", "excel", 14);


        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    submit();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnTim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKhachHang();
            }
        });

        soDienThoaiTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKhachHang();
            }
        });


        timMaVeTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timVe();
            }
        });
        btnTimVe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doiVe();
            }
        });

        pnlButtom.add(btnTim);
        pnlButtom.add(btnTimVe);
        pnlButtom.add(btnSubmit);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(new JScrollPane(pnlMain, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private void doiVe() {
        tblModel.setRowCount(0);
        try {
            String tenGaDi = diemDiSelectForm.getSelectedItem().toString();
            String tenGaDen = diemDenSelectForm.getSelectedItem().toString();
            LocalDate ngayDi = ngayDiDate.getDateAsLocalDate();

            List<Chuyen> dsChuyen = chuyenDao.timChuyenTheoGa(tenGaDi, tenGaDen, ngayDi);

            if (dsChuyen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chuyến", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
                return;
            }


            for (Chuyen chuyen : dsChuyen) {
                Ga gaDi = chuyen.getTuyen().getGaDi();
                Ga gaDen = chuyen.getTuyen().getGaDen();
                Tau tau = chuyen.getTau();

                tblModel.addRow(new Object[]{chuyen.getMaChuyen(), gaDi.getTenGa() + "-" + gaDen.getTenGa(), tau.getTenTau(), chuyen.getThoiGianKhoiHanh(), chuyen.getThoiGianDen()});

            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void timVe() {
        String maVe = timMaVeTextField.getText().trim();
        String sdt = soDienThoaiTextField.getText().trim();
        if (maVe.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Mã vé không được rỗng.",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
        }

        Ve ve = veDao.layVeTheoMaVaSdt(maVe, sdt);
        if (ve == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không tìm thấy vé.",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maTau = ve.getTau().getMaTau();
        String maTuyen = ve.getTuyen().getMaTuyen();

        Chuyen chuyen = chuyenDao.layTheoMaTauVaMaTuyen(maTau, maTuyen);
        LocalDateTime thoiGianKhoiHanh = chuyen.getThoiGianKhoiHanh();
        LocalDate ngayKhoiHanh = thoiGianKhoiHanh.toLocalDate();

        maVeTextField.setText(ve.getMaVe());
        tuyenTextField.setText(ve.getTuyen().getGaDi().getTenGa() + " - " + ve.getTuyen().getGaDen().getTenGa());
        tauTextField.setText(ve.getTau().getTenTau());
        choNgoiTextField.setText(String.valueOf(ve.getSlot().getSoSlot()));

        tenNguoiDiTextField.setText(ve.getHoTenNguoiDi());
        cccdNguoiDiTextField.setText(ve.getCccdNguoiDi());

        if (ngayKhoiHanh.isBefore(LocalDate.now()) || ngayKhoiHanh.isEqual(LocalDate.now())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể áp dụng đối với vé đã khởi hành.",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    private void timKhachHang() {
        khachHang = null;
        String soDienThoai = soDienThoaiTextField.getText().trim();

        if (Validation.isEmpty(soDienThoai)
                || !Validation.kiemTraSoDienThoai(soDienThoai) && soDienThoai.length() != 10) {

            JOptionPane.showMessageDialog(
                    this,
                    "Số điện thoại không được rỗng và phải là 10 ký tự số",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        khachHang = khachHangDao.timTheoSDT(soDienThoai);

        if (khachHang == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng!", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return;
        }

        soDienThoaiTextField.setText(khachHang.getSoDienThoai());
        tenKhachHangTextField.setText(khachHang.getHoTen());
        tenKhachHangTextField.setEditable(false);
    }

    boolean kiemTraField() {
        String soDienThoai = soDienThoaiTextField.getText().trim();
        if (Validation.isEmpty(soDienThoai)
                || !Validation.kiemTraSoDienThoai(soDienThoai) && soDienThoai.length() != 10) {

            JOptionPane.showMessageDialog(
                    this,
                    "Số điện thoại không được rỗng và phải là 10 ký tự số",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void submit() throws ParseException {
        if (!kiemTraField())
            return;

        String soDienThoai = soDienThoaiTextField.getText().trim();
        String tenKhachHang = tenKhachHangTextField.getText().trim();
        String diemDi = (String) diemDiSelectForm.getSelectedItem();
        String diemDen = (String) diemDenSelectForm.getSelectedItem();

        LocalDate ngayDi = ngayDiDate.getDateAsLocalDate();

        KhachHang khachHang = khachHangDao.timTheoSDT(soDienThoai);
        NhanVien nhanVien = NhanVienSuDungSingleton.layThongTinNhanVienHienTai();


        // Tạo yêu cầu đổi vé
//        TiepNhanYeuCauDoiVe yeuCauDoiVe = new TiepNhanYeuCauDoiVe(
//                ngayDi,
//                "Yêu cầu đổi vé từ điểm " + diemDi + " đến " + diemDen,
//                nhanVien,
//                khachHang
//        );

        //yeuCauDoiVeDao.them(yeuCauDoiVe);

//        JOptionPane.showMessageDialog(
//                this,
//                "Yêu cầu đổi vé đã được tạo thành công",
//                "Thông báo",
//                JOptionPane.INFORMATION_MESSAGE);

        xoaDuLieu();
    }

//    @Override
//    public void mousePressed(MouseEvent e)  {
//        Object eSource = e.getSource();
//
//        if (eSource.equals(btnSubmit)) {
//
//
//        }
//        if (eSource.equals(btnHuyBo)) {
//            xoaDuLieu();
//            dispose();
//        }
//    }


    public void setTaoKhachHangListener(TaoKhachHangListener taoKhachHangListener) {
        this.taoKhachHangListener = taoKhachHangListener;
    }

    public void setSuaKhachHangListener(SuaKhachHangListener suaKhachHangListener) {
        this.suaKhachHangListener = suaKhachHangListener;
    }


    public void xoaDuLieu() {
        btnSubmit.setVisible(true);
        titlePage.setLblTitle("Thêm khách hàng");
        btnSubmit.setText("Thêm khách hàng");

        tenKhachHangTextField.setText("");
        tenKhachHangTextField.setEditable(true);

        soDienThoaiTextField.setText("");
        soDienThoaiTextField.setEditable(true);

        btnSubmit.setVisible(true);
    }

    @Override
    public void setVisible(boolean b) {
        if (!b) {
            xoaDuLieu();
        }
        super.setVisible(b);
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

    private Tau layTauDuocChon() {
        int row = chuyenTable.getSelectedRow();
        if (row != -1) {
            String tenTau = chuyenTable.getValueAt(row, 2).toString();
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
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked on table");
        Tau tau = layTauDuocChon();
        if (tau == null) {
            return;
        }

        chonChoDialog.setTau(tau);
        chonChoDialog.setVisible(true);
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
}