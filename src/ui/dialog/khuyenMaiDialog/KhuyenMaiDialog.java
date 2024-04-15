package ui.dialog.khuyenMaiDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.json.ParseException;

import dao.KhachHangDao;
import dao.KhuyenMaiDao;
import entity.KhachHang;
import entity.KhuyenMai;
import helper.Validation;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputDate;
import ui.component.InputForm;

public class KhuyenMaiDialog extends JDialog implements WindowListener, MouseListener {
    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private ButtonCustom btnSubmit, btnHuyBo, btnKhMoi, btnTim, btnTaoMa;
    private InputForm maKhuyenMaiTextField;
    private InputForm tiLeGiamTextField;
    private InputForm ghiChuTextField;
    private InputDate ngayBatDauDateField;
    private InputDate ngayKetThucDateField;
    private KhuyenMaiDao khuyenMaiDao;
    private KhuyenMai khuyenMai;
    private TaoKhuyenMaiListener taoKhuyenMaiListener;
    private SuaKhuyenMaiListener suaKhuyenMaiListener;

    public KhuyenMaiDialog() {
        khuyenMaiDao = new KhuyenMaiDao();
        initComponents();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        // xoaDuLieu();
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
        setSize(new Dimension(500, 500));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Thêm khuyến mãi");
        pnlMain = new JPanel(new GridLayout(5, 1, 20, 0));
        pnlMain.setBackground(Color.white);

        maKhuyenMaiTextField = new InputForm("Mã khuyến mãi");
        maKhuyenMaiTextField.getTxtForm().setEditable(false);
        tiLeGiamTextField = new InputForm("Tỉ lệ giảm");
        ghiChuTextField = new InputForm("Ghi chú");
        ngayBatDauDateField = new InputDate("Ngày bắt đầu");
        ngayKetThucDateField = new InputDate("Ngày kết thúc");

        pnlMain.add(maKhuyenMaiTextField);
        pnlMain.add(tiLeGiamTextField);
        pnlMain.add(ghiChuTextField);
        pnlMain.add(ngayBatDauDateField);
        pnlMain.add(ngayKetThucDateField);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnSubmit = new ButtonCustom("Thêm khuyến mãi", "success", 14);
        btnTaoMa = new ButtonCustom("Tạo mã", "excel", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnKhMoi = new ButtonCustom("Khách hàng mới", "success", 14);
        btnTim = new ButtonCustom("Tìm", "excel", 14);

        // Add MouseListener btn
        btnSubmit.addMouseListener(this);
        btnHuyBo.addMouseListener(this);
        btnTaoMa.addMouseListener(this);
        btnKhMoi.addMouseListener(this);
        btnTim.addMouseListener(this);

        pnlButtom.add(btnSubmit);
        pnlButtom.add(btnTaoMa);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    boolean kiemTraField() {
        String maKM = maKhuyenMaiTextField.getText().trim();
        String tiLeGiam = tiLeGiamTextField.getText().trim();

        if (Validation.isEmpty(maKM)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng tạo mã khuyến mãi",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(tiLeGiam)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập tỉ lệ giảm",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void xoaDuLieu() {
        btnSubmit.setVisible(true);
        titlePage.setLblTitle("Thêm khuyến mãi");
        btnSubmit.setText("Thêm khuyến mãi");

        this.maKhuyenMaiTextField.setText("");

        tiLeGiamTextField.setText("");

        ghiChuTextField.setText("");
        khuyenMai = null;
    }

    private KhuyenMai layThongTinKhuyenMaiTuField() {
        String maKhuyenMai = "";
        if (!maKhuyenMaiTextField.getText().trim().isEmpty())
            maKhuyenMai = maKhuyenMaiTextField.getText().trim();

        double tiLeGiam = Double.parseDouble(tiLeGiamTextField.getText().trim());
        String ghiChu = ghiChuTextField.getText().trim();
        LocalDateTime ngayBD = null;
        LocalDateTime ngayKT = null;
        try {
            ngayBD = ngayBatDauDateField.getDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            ngayKT = ngayKetThucDateField.getDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return new entity.KhuyenMai(maKhuyenMai, tiLeGiam, ghiChu, ngayBD, ngayKT);
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;

        titlePage.setLblTitle("Cập nhật thông tin");
        btnSubmit.setText("Cập nhật thông tin");
        maKhuyenMaiTextField.setText(khuyenMai.getMaKhuyenMai());
        ghiChuTextField.setText(khuyenMai.getGhiChu());
        tiLeGiamTextField.setText(String.valueOf(khuyenMai.getPhanTramGiamGia()));
        ngayBatDauDateField
                .setDate(Date.from(khuyenMai.getThoiGianBatDau().atZone(ZoneId.systemDefault()).toInstant()));
        ngayKetThucDateField
                .setDate(Date.from(khuyenMai.getThoiGianKetThuc().atZone(ZoneId.systemDefault()).toInstant()));

        btnTaoMa.setVisible(false);
        invalidate();
        validate();
        repaint();
    }

    public KhuyenMai getKhuyenMai() {
        return this.khuyenMai;
    }

    private String taoMaKhuyenMai() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        String result = sb.toString();

        if (this.khuyenMaiDao.layTheoMa(result) == null) {
            return result;
        } else {
            return taoMaKhuyenMai();
        }
    }

    public void xemKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
        titlePage.setLblTitle("Xem khách hàng");
        btnSubmit.setText("Cập nhật thông tin");
        btnSubmit.setVisible(false);

        maKhuyenMaiTextField.setText(khuyenMai.getMaKhuyenMai());
        maKhuyenMaiTextField.setEditable(false);

        ghiChuTextField.setText(khuyenMai.getGhiChu());
        ghiChuTextField.setEditable(false);

        tiLeGiamTextField.setText(String.valueOf(khuyenMai.getPhanTramGiamGia()));
        tiLeGiamTextField.setEditable(false);

        ngayBatDauDateField
                .setDate(Date.from(khuyenMai.getThoiGianBatDau().atZone(ZoneId.systemDefault()).toInstant()));
        ngayBatDauDateField.setDisable();
        ngayKetThucDateField
                .setDate(Date.from(khuyenMai.getThoiGianKetThuc().atZone(ZoneId.systemDefault()).toInstant()));
        ngayKetThucDateField.setDisable();

        invalidate();
        validate();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object eSource = e.getSource();

        if (eSource.equals(btnSubmit)) {
            if (!kiemTraField())
                return;

            if (khuyenMai == null) {
                KhuyenMai result = khuyenMaiDao.themVoiKieuTraVe(layThongTinKhuyenMaiTuField());
                System.out.println(result);
                if (result != null) {
                    if (taoKhuyenMaiListener != null) {
                        taoKhuyenMaiListener.taoKhuyenMaiThanhCong(layThongTinKhuyenMaiTuField());
                    }
                    Logger.getLogger(KhuyenMaiDialog.class.getName()).log(Level.INFO, "Thêm khách hàng thành công!");
                    JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
                    xoaDuLieu();
                    dispose();
                }
            } else {
                if (khuyenMaiDao.sua(layThongTinKhuyenMaiTuField())) {
                    if (suaKhuyenMaiListener != null) {
                        suaKhuyenMaiListener.suaKhuyenMaiThanhCong(layThongTinKhuyenMaiTuField());
                    }
                    Logger.getLogger(KhuyenMaiDialog.class.getName()).log(Level.INFO,
                            "Cập nhật thông tin khách hàng thành công!");
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin khách hàng thành công!");
                    xoaDuLieu();
                    dispose();
                }
            }

        }
        if (eSource.equals(btnHuyBo)) {
            xoaDuLieu();
            dispose();
        }

        if (eSource.equals(btnTaoMa)) {
            this.maKhuyenMaiTextField.setText(this.taoMaKhuyenMai());
        }
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

    public void addTaoKhuyenMaiListener(TaoKhuyenMaiListener taoKhuyenMaiListener) {
        this.taoKhuyenMaiListener = taoKhuyenMaiListener;
    }

    public void addSuaKhuyenMaiListener(SuaKhuyenMaiListener suaKhuyenMaiListener) {
        this.suaKhuyenMaiListener = suaKhuyenMaiListener;
    }
}
