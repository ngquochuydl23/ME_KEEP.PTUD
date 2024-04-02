/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this main2late
 */
package ui.dialog;


import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;
import dao.NhanVienDao;
import entity.NhanVien;
import helper.Validation;
import singleton.NhanVienSuDungSingleton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class TaiKhoanCuaToiPanel extends JFrame {

    private ButtonCustom saveBtn, cancelBtn;
    private HeaderTitle title;
    private JPanel top, center, topCenter, mainCenter, bottom;
    private InputForm matKhauHienTaiInputForm;
    private InputForm xacNhanInputForm;
    private InputForm soDienThoaiInputForm;
    private InputForm emailInputForm;
    private InputForm matKhauMoiInputForm;
    private JRadioButton[] radioButtons;
    private NhanVienDao nhanVienDao;
    private NhanVien nhanVien;
    private JRadioButton soDienThoaiJRabtn;
    private JRadioButton emailJRabtn;
    private JRadioButton matKhauJRabtn;
    private JPanel soDienThoaiPanel;
    private JPanel emailPanel;
    private JPanel matKhauPanel;


    public TaiKhoanCuaToiPanel() {
        super("Tài khoản");

        nhanVien = NhanVienSuDungSingleton.layThongTinNhanVienHienTai();
        nhanVienDao = new NhanVienDao();

        initComponent();
    }

    public void initComponent() {

        setSize(400, 500);
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);
        setResizable(false);

        setLocationRelativeTo(null);
        title = new HeaderTitle("CHỈNH SỬA THÔNG TIN");

        top = new JPanel();
        top.setBackground(Color.WHITE);
        top.setLayout(new FlowLayout(0, 0, 0));
        top.add(title);

        topCenter = new JPanel(new FlowLayout(1, 40, 0));
        topCenter.setBorder(new EmptyBorder(20, 0, 0, 0));
        topCenter.setBackground(Color.WHITE);

        mainCenter = new JPanel();
        mainCenter.setBorder(new EmptyBorder(0, 20, 0, 20));
        mainCenter.setBackground(Color.WHITE);

        ButtonGroup bg = new ButtonGroup();


        soDienThoaiJRabtn = new JRadioButton("Số điện thoại");
        soDienThoaiJRabtn.setSelected(true);
        soDienThoaiJRabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(new Dimension(400, 500));
                setLocationRelativeTo(null);
                mainCenter.removeAll();
                mainCenter.add(soDienThoaiPanel);
                mainCenter.repaint();
                mainCenter.validate();

            }
        });

        emailJRabtn = new JRadioButton("Email");
        emailJRabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(new Dimension(400, 500));
                setLocationRelativeTo(null);
                mainCenter.removeAll();
                mainCenter.add(emailPanel);
                mainCenter.repaint();
                mainCenter.validate();
            }
        });

        matKhauJRabtn = new JRadioButton("Mật khẩu");
        matKhauJRabtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(new Dimension(400, 500));
                setLocationRelativeTo(null);
                mainCenter.removeAll();
                mainCenter.add(matKhauPanel);
                mainCenter.repaint();
                mainCenter.validate();
            }
        });
        soDienThoaiJRabtn.setSelected(true);

        bg.add(soDienThoaiJRabtn);
        bg.add(emailJRabtn);
        bg.add(matKhauJRabtn);

        topCenter.add(soDienThoaiJRabtn);
        topCenter.add(emailJRabtn);
        topCenter.add(matKhauJRabtn);

        // Số điện thoại
        soDienThoaiInputForm = new InputForm("Số điện thoại");
        soDienThoaiInputForm.setText(nhanVien.getSoDienThoai());

        soDienThoaiPanel = new JPanel(new GridLayout(1, 1));
        soDienThoaiPanel.setPreferredSize(new Dimension(400, 100));
        soDienThoaiPanel.add(soDienThoaiInputForm);


        mainCenter.add(soDienThoaiPanel);
        emailInputForm = new InputForm("Email");
        emailInputForm.setText(nhanVien.getEmail());
        emailPanel = new JPanel(new GridLayout(1, 1));
        emailPanel.setPreferredSize(new Dimension(400, 100));
        emailPanel.add(emailInputForm);

        // Mật khẩu
        matKhauHienTaiInputForm = new InputForm("Mật khẩu hiện tại", "password");
        matKhauMoiInputForm = new InputForm("Mật khẩu mới", "password");
        xacNhanInputForm = new InputForm("Nhập lại mật khẩu mới", "password");

        matKhauPanel = new JPanel(new GridLayout(3, 1));
        matKhauPanel.setPreferredSize(new Dimension(400, 300));
        matKhauPanel.add(matKhauHienTaiInputForm);
        matKhauPanel.add(matKhauMoiInputForm);
        matKhauPanel.add(xacNhanInputForm);


        center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add(topCenter, BorderLayout.NORTH);
        center.add(mainCenter, BorderLayout.CENTER);

        saveBtn = new ButtonCustom("Lưu", "success", 15);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                luuThongTin();
            }
        });

        cancelBtn = new ButtonCustom("Hủy", "danger", 15);
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        bottom = new JPanel(new FlowLayout(1, 20, 10));
        bottom.setBackground(Color.WHITE);
        bottom.add(cancelBtn);
        bottom.add(saveBtn);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void luuThongTin() {
        if (soDienThoaiJRabtn.isSelected()) {

            String soDienThoai = soDienThoaiInputForm.getText().trim();
            if (!Validation.kiemTraSoDienThoai(soDienThoai)) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải có 10 ký tự sô", "Chỉnh sửa số điện thoại", JOptionPane.WARNING_MESSAGE);
                return;
            }

            nhanVien.setSoDienThoai(soDienThoai);
            if (nhanVienDao.sua(nhanVien)) {

                Logger.getLogger(TaiKhoanCuaToiPanel.class.getName()).log(Level.INFO, "Cập nhật số điện thoại thành công");
                NhanVienSuDungSingleton.setThongTinNhanVienHienTai(nhanVien);
                JOptionPane.showMessageDialog(this, "Cập nhật số điện thoại thành công");
                dispose();
                return;
            }
        }

        if (emailJRabtn.isSelected()) {
            String email = emailInputForm.getText().trim();

            if (!Validation.isEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng định dạng", "Chỉnh sửa email", JOptionPane.WARNING_MESSAGE);
                return;
            }

            nhanVien.setEmail(email);
            if (nhanVienDao.sua(nhanVien)) {

                Logger.getLogger(TaiKhoanCuaToiPanel.class.getName()).log(Level.INFO, "Cập nhật email thành công");
                NhanVienSuDungSingleton.setThongTinNhanVienHienTai(nhanVien);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                dispose();
                return;
            }
        }

        if (matKhauJRabtn.isSelected()) {
            String matKhauHienTai = matKhauHienTaiInputForm.getPass().trim();
            String matKhauMoi = matKhauMoiInputForm.getPass().trim();
            String xacNhanMatKhauMoi = xacNhanInputForm.getPass().trim();

            if (!matKhauHienTai.equals(nhanVien.getMatKhau())) {
                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (Validation.isEmpty(matKhauHienTai)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (Validation.isEmpty(matKhauMoi) || matKhauMoi.length() < 6) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới không được rỗng và có ít nhất 6 ký tự", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (Validation.isEmpty(xacNhanMatKhauMoi)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không được rỗng", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!matKhauMoi.equals(xacNhanMatKhauMoi)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp với mật khẩu mới", "Cảnh báo!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            nhanVien.setMatKhau(matKhauMoi);
            if (nhanVienDao.sua(nhanVien)) {

                Logger.getLogger(TaiKhoanCuaToiPanel.class.getName()).log(Level.INFO, "Cập nhật mật khẩu thành công");
                matKhauHienTaiInputForm.setPass("");
                matKhauMoiInputForm.setPass("");
                xacNhanInputForm.setPass("");

                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                dispose();
            }

        }
    }

}
