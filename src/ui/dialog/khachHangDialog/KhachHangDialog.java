package ui.dialog.khachHangDialog;

import dao.KhachHangDao;
import entity.KhachHang;
import helper.Validation;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDialog extends JDialog implements MouseListener, WindowListener {
    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private ButtonCustom btnSubmit, btnHuyBo, btnKhMoi, btnTim;
    private InputForm tenKhachHangTextField;
    private InputForm soDienThoaiTextField;
    private InputForm maKhachHangTextField;
    private InputForm soCMNDTextField;
    private KhachHangDao khachHangDao;
    private KhachHang khachHang;
    private TaoKhachHangListener taoKhachHangListener;
    private SuaKhachHangListener suaKhachHangListener;

    public KhachHangDialog() {
        khachHangDao = new KhachHangDao();
        initComponents();
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
        setSize(new Dimension(600, 600));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Thêm khách hàng");
        pnlMain = new JPanel(new GridLayout(4, 1, 20, 0));
        pnlMain.setBackground(Color.white);

        tenKhachHangTextField = new InputForm("Tên khách hàng");
        soDienThoaiTextField = new InputForm("Số điện thoại");
        maKhachHangTextField = new InputForm("Mã khách hàng");
        soCMNDTextField = new InputForm("Số CMND");
        maKhachHangTextField.getTxtForm().setEnabled(false);

        pnlMain.add(maKhachHangTextField);
        pnlMain.add(soDienThoaiTextField);
        pnlMain.add(tenKhachHangTextField);
        pnlMain.add(soCMNDTextField);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnSubmit = new ButtonCustom("Thêm khách hàng", "success", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnKhMoi = new ButtonCustom("Khách hàng mới", "success", 14);
        btnTim = new ButtonCustom("Tìm", "excel", 14);

        // Add MouseListener btn
        btnSubmit.addMouseListener(this);
        btnHuyBo.addMouseListener(this);
        btnKhMoi.addMouseListener(this);
        btnTim.addMouseListener(this);

        pnlButtom.add(btnSubmit);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    boolean kiemTraField() {
        String soDienThoai = soDienThoaiTextField.getText().trim();
        String tenKhachHang = tenKhachHangTextField.getText().trim();
        String soCMND = soCMNDTextField.getText().trim();

        if (Validation.isEmpty(soDienThoai)
                || !Validation.kiemTraSoDienThoai(soDienThoai) && soDienThoai.length() != 10) {

            JOptionPane.showMessageDialog(
                    this,
                    "Số điện thoại không được rỗng và phải là 10 ký tự số",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(tenKhachHang)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Tên khách hàng không được rỗng",
                    "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(soCMND)) {
        	JOptionPane.showMessageDialog(this, 
        			"Số CMND không được rỗng", 
        			"Cảnh báo !", 
        			JOptionPane.WARNING_MESSAGE);
        }
        return true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object eSource = e.getSource();

        if (eSource.equals(btnSubmit)) {
            if (!kiemTraField())
                return;

            if (khachHang == null) {
                KhachHang result = khachHangDao.themVoiKieuTraVe(layThongTinKhachHangTuField());
                if (result != null) {
                    if (taoKhachHangListener != null) {
                        taoKhachHangListener.taoKhachHangThanhCong(result);
                    }
                    Logger.getLogger(KhachHangDialog.class.getName()).log(Level.INFO, "Thêm khách hàng thành công!");
                    JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                    xoaDuLieu();
                    dispose();
                }
            } else {
                if (khachHangDao.sua(layThongTinKhachHangTuField())) {
                    if (suaKhachHangListener != null) {
                        suaKhachHangListener.suaKhachHangThanhCong(layThongTinKhachHangTuField());
                    }
                    Logger.getLogger(KhachHangDialog.class.getName()).log(Level.INFO,
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

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;

        titlePage.setLblTitle("Cập nhật thông tin");
        btnSubmit.setText("Cập nhật thông tin");
        soCMNDTextField.setText(khachHang.getSoCMND());
        soDienThoaiTextField.setText(khachHang.getSoDienThoai());
        tenKhachHangTextField.setText(khachHang.getHoTen());
        maKhachHangTextField.setText(String.valueOf(khachHang.getMaKhachHang()));

        invalidate();
        validate();
        repaint();
    }

    public void setTaoKhachHangListener(TaoKhachHangListener taoKhachHangListener) {
        this.taoKhachHangListener = taoKhachHangListener;
    }

    public void setSuaKhachHangListener(SuaKhachHangListener suaKhachHangListener) {
        this.suaKhachHangListener = suaKhachHangListener;
    }

    private KhachHang layThongTinKhachHangTuField() {
        int maKhacHang = 0;
        if (!maKhachHangTextField.getText().trim().isEmpty())
            maKhacHang = Integer.valueOf(maKhachHangTextField.getText().trim());

        String tenKhachHang = tenKhachHangTextField.getText().trim();
        String soDienThoai = soDienThoaiTextField.getText().trim();
        String soCMND = soCMNDTextField.getText().trim();

        return new KhachHang(maKhacHang, tenKhachHang, soDienThoai, soCMND);
    }

    public void xoaDuLieu() {
        titlePage.setLblTitle("Thêm khách hàng");
        btnSubmit.setText("Thêm khách hàng");
        
        tenKhachHangTextField.setText("");
        tenKhachHangTextField.setEditable(true);

        soDienThoaiTextField.setText("");
        soDienThoaiTextField.setEditable(true);

        maKhachHangTextField.setText("");
        maKhachHangTextField.setEditable(true);
        
        soCMNDTextField.setText("");
        soCMNDTextField.setEditable(true);
        btnSubmit.setVisible(true);
    }

    public void xemKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
        btnSubmit.setVisible(false); // Ẩn nút "Thêm khách hàng" khi xem chi tiết

        titlePage.setLblTitle("Xem khách hàng");
        soDienThoaiTextField.setText(khachHang.getSoDienThoai());
        soDienThoaiTextField.setEditable(false); // Vô hiệu hóa chỉnh sửa

        tenKhachHangTextField.setText(khachHang.getHoTen());
        tenKhachHangTextField.setEditable(false); // Vô hiệu hóa chỉnh sửa
        
        soCMNDTextField.setText(khachHang.getSoCMND());
        soCMNDTextField.setEditable(false); // Vô hiệu hóa chỉnh sửa

        maKhachHangTextField.setText(String.valueOf(khachHang.getMaKhachHang()));
        maKhachHangTextField.setEditable(false); // Vô hiệu hóa chỉnh sửa

        invalidate();
        validate();
        repaint();
    }

    public void taoTaiKhoanVoiSoDienThoai(String soDienThoai) {
        soDienThoaiTextField.setText(soDienThoai);
        tenKhachHangTextField.requestFocus();
        tenKhachHangTextField.getTxtForm().requestFocus();
        soCMNDTextField.requestFocus();
        soCMNDTextField.getTxtForm().requestFocus();
    }
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}