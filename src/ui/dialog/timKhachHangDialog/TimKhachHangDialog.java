package ui.dialog.timKhachHangDialog;

import dao.KhachHangDao;
import entity.KhachHang;
import helper.Validation;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;
import ui.dialog.HoaDonDialog;
import ui.dialog.khachHangDialog.SuaKhachHangListener;
import ui.dialog.khachHangDialog.TaoKhachHangListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimKhachHangDialog extends JDialog implements ActionListener, WindowListener {

    private TimKhachHangListener timKhachHangListener;
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlButtom;

    private ButtonCustom tiepTucBtn, btnHuyBo, timBtn, xoaRongBtn;
    private InputForm tenKhachHangTextField;
    private InputForm soDienThoaiTextField;
    private InputForm maKhachHangTextField;
    private KhachHangDao khachHangDao;
    private KhachHang khachHang;

    public TimKhachHangDialog() {
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
        setSize(new Dimension(500, 700));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Tìm khách hàng");
        // pnlMain = new JPanel(new GridLayout(3, 1, 20, 0));
        pnlMain = new JPanel(new FlowLayout());
        pnlMain.setBackground(Color.white);

        soDienThoaiTextField = new InputForm("Số điện thoại", 450, 80);
        soDienThoaiTextField.addActionListener(this);

        tenKhachHangTextField = new InputForm("Tên khách hàng", 450, 80);
        tenKhachHangTextField
                .getTxtForm()
                .setEnabled(false);

        maKhachHangTextField = new InputForm("Mã khách hàng", 450, 80);
        maKhachHangTextField
                .getTxtForm()
                .setEnabled(false);
        pnlMain.add(soDienThoaiTextField);

        pnlMain.add(maKhachHangTextField);
        pnlMain.add(tenKhachHangTextField);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);

        xoaRongBtn = new ButtonCustom("Xóa rỗng", "danger", 14, 100, 40);
        timBtn = new ButtonCustom("Tìm", "success", 14, 100, 40);
        tiepTucBtn = new ButtonCustom("Tiếp tục", "success", 14, 100, 40);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14, 100, 40);

        timBtn.addActionListener(this);
        xoaRongBtn.addActionListener(this);
        tiepTucBtn.addActionListener(this);
        btnHuyBo.addActionListener(this);

        tiepTucBtn.setEnabled(false);

        pnlButtom.add(timBtn);
        pnlButtom.add(tiepTucBtn);
        pnlButtom.add(xoaRongBtn);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        xoaDuLieu();

        setVisible(true);
    }

    private void xoaDuLieu() {
        tiepTucBtn.setEnabled(false);
        tenKhachHangTextField.setText("");
        tenKhachHangTextField.setEnabled(false);
        soDienThoaiTextField.setText("");
        maKhachHangTextField.setText("");
        maKhachHangTextField.setEnabled(false);
        khachHang = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eSource = e.getSource();

        if (eSource.equals(timBtn) || eSource.equals(soDienThoaiTextField)) {

            maKhachHangTextField.setText("");
            tenKhachHangTextField.setText("");
            tiepTucBtn.setEnabled(false);

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

                JOptionPane.showMessageDialog(
                        this,
                        "Không tìm thấy khách hàng. Tạo khách hàng mới ?",
                        "Cảnh báo !",
                        JOptionPane.WARNING_MESSAGE);
                if (timKhachHangListener != null)
                    timKhachHangListener.khongTimThayKhachHang(soDienThoai);

                dispose();
                return;
            }

            soDienThoaiTextField.setText(khachHang.getSoDienThoai());
            maKhachHangTextField.setText(String.valueOf(khachHang.getMaKhachHang()));
            tenKhachHangTextField.setText(khachHang.getHoTen());
            tiepTucBtn.setEnabled(true);
        }

        if (eSource.equals(xoaRongBtn)) {
            xoaDuLieu();
        }

        if (eSource.equals(btnHuyBo)) {
            xoaDuLieu();
            dispose();
        }

        if (eSource.equals(tiepTucBtn)) {
            if (timKhachHangListener != null && khachHang != null)
                timKhachHangListener.timThayhachhang(khachHang);
            dispose();
            new HoaDonDialog();
        }
    }

    public void setTimKhachHangListener(TimKhachHangListener timKhachHangListener) {
        this.timKhachHangListener = timKhachHangListener;
    }
}
