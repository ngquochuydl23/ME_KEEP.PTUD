package ui.dialog.chiTietVeDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.Khoang;
import entity.LoaiKhoang;
import entity.ToaTau;
import entity.Ve;
import helper.Formater;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChiTietVeDialog extends JDialog implements ActionListener {
    private InputForm maVeField, choNgoiField, toaTauField, giaVeField, hoTenNguoiDiTextField, cccdNguoiDi, namSinhNguoiDi;
    private ButtonCustom luuButton, xoaTrangButton, tatButton;
    private HeaderTitle titlePage;
    private JPanel pnlButtom;
    private Ve ve;

    private CapNhatVeListener capNhatVeListener;

    public ChiTietVeDialog() {
        setVisible(false);
        setSize(new Dimension(500, 700));
        setLayout(new BorderLayout(0, 0));

        // Tạo panel cho tiêu đề
        titlePage = new HeaderTitle("Chi tiết vé");
        // pnlMain = new JPanel(new GridLayout(3, 1, 20, 0));
        Box pnlMain = Box.createVerticalBox();
        pnlMain.setBackground(Color.white);

        // Khởi tạo các components khác
        maVeField = new InputForm("Mã vé", 450, 80);
        maVeField.getTxtForm().setEnabled(false);
        choNgoiField = new InputForm("Chỗ ngồi", 450, 80);
        choNgoiField.getTxtForm().setEnabled(false);
        toaTauField = new InputForm("Toa tàu", 450, 80);
        toaTauField.getTxtForm().setEnabled(false);
        giaVeField = new InputForm("Giá vé", 450, 80);
        giaVeField.getTxtForm().setEnabled(false);

        hoTenNguoiDiTextField = new InputForm("Họ tên người đi", 450, 80);

        cccdNguoiDi = new InputForm("CCCD người đi", 450, 80);

        namSinhNguoiDi = new InputForm("Năm sinh", 450, 80);

        pnlMain.add(maVeField);
        pnlMain.add(choNgoiField);
        pnlMain.add(toaTauField);
        pnlMain.add(giaVeField);
        pnlMain.add(hoTenNguoiDiTextField);
        pnlMain.add(cccdNguoiDi);
        pnlMain.add(namSinhNguoiDi);


        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);

        luuButton = new ButtonCustom("Lưu", "success", 14, 100, 40);
        xoaTrangButton = new ButtonCustom("Xóa Trắng", "danger", 14, 100, 40);
        tatButton = new ButtonCustom("Tắt", "danger", 14, 100, 40);

        luuButton.addActionListener(this);
        xoaTrangButton.addActionListener(this);
        tatButton.addActionListener(this);

        pnlButtom.add(luuButton);
        pnlButtom.add(xoaTrangButton);
        pnlButtom.add(tatButton);

        add(titlePage, BorderLayout.NORTH);
        add(new JScrollPane(pnlMain, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        xoaDuLieu();
    }

    private void xoaDuLieu() {
        ve = null;


        maVeField.setText("");
        choNgoiField.setText("");
        toaTauField.setText("");
        giaVeField.setText("");
        hoTenNguoiDiTextField.setText("");
        cccdNguoiDi.setText("");
        namSinhNguoiDi.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object eSource = e.getSource();

        if (eSource.equals(luuButton)) {
            luuDuLieu();
        }

        if (eSource.equals(xoaTrangButton)) {
            xoaDuLieu();
        }

        if (eSource.equals(tatButton)) {

            xoaDuLieu();
            dispose();
        }
    }

    public void setVe(LoaiKhoang loaiKhoang, ToaTau toaTau, Ve ve) {
        this.ve = ve;

        maVeField.setText(ve.getMaVe());
        choNgoiField.setText(String.valueOf(ve.getSlot().getSoSlot()));
        toaTauField.setText(toaTau.getTenToa());
        giaVeField.setText(Formater.FormatVND(ve.tinhGiaBanVe(loaiKhoang.getMaLoaiKhoang())));


        hoTenNguoiDiTextField.setText(ve.getHoTenNguoiDi() != null ? ve.getHoTenNguoiDi() : "");
        cccdNguoiDi.setText(ve.getCccdNguoiDi() != null ? ve.getCccdNguoiDi() : "");
        namSinhNguoiDi.setText(String.valueOf(ve.getNamSinhNguoiDi()));
    }

    private boolean kiemTraDuLieu() {

        try {
            if (hoTenNguoiDiTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên người đi");
                return false;
            }

            if (cccdNguoiDi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập cccd người đi");
                return false;
            }

            if (namSinhNguoiDi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập năm sinh người đi");
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm sinh kiểu số");
            ex.printStackTrace();
            return false;
        }
    }

    private void luuDuLieu() {
        if (kiemTraDuLieu()) {
            ve.setCccdNguoiDi(cccdNguoiDi.getText().trim());
            ve.setHoTenNguoiDi(hoTenNguoiDiTextField.getText().trim());
            ve.setNamSinhNguoiDi(Integer.parseInt(namSinhNguoiDi.getText().trim()));

            if (capNhatVeListener != null)
                capNhatVeListener.capNhatVe(ve);
            JOptionPane.showMessageDialog(this, "Đã cập nhật vé " + ve.getMaVe());

            xoaDuLieu();
            dispose();
        }
    }

    public void setCapNhatVeListener(CapNhatVeListener capNhatVeListener) {
        this.capNhatVeListener = capNhatVeListener;
    }
}
