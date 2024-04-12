package ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.HoaDonDao;
import entity.KhachHang;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;

public class HoaDonDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlKhachHang;
    private JPanel pnlButtom;

    private ButtonCustom tiepTucBtn, btnHuyBo, timBtn, xoaRongBtn;
    private InputForm tenKhachHangTextField;
    private InputForm maKhachHangTextField;
    private HoaDonDao hoaDonDao;
    private KhachHang khachHang;

    public HoaDonDialog(KhachHang khachHang) {
        this.khachHang = khachHang;

        hoaDonDao = new HoaDonDao();
        initComponents();
    }

    public void initComponents() {
        setSize(new Dimension(500, 700));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Chi tiết vé tàu");
        // pnlMain = new JPanel(new GridLayout(3, 1, 20, 0));
        pnlMain = new JPanel(new FlowLayout());
        pnlMain.setBackground(Color.white);

        tenKhachHangTextField = new InputForm("Tên khách hàng", 390, 80);
        tenKhachHangTextField
                .getTxtForm()
                .setEnabled(false);
        tenKhachHangTextField.getTxtForm().setText(this.khachHang.getHoTen());

        maKhachHangTextField = new InputForm("Mã khách hàng", 90, 80);
        maKhachHangTextField
                .getTxtForm()
                .setEnabled(false);
        maKhachHangTextField.getTxtForm().setText(String.valueOf(this.khachHang.getMaKhachHang()));

        pnlKhachHang = new JPanel(new BorderLayout());
        pnlKhachHang.setBackground(Color.white);

        pnlKhachHang.add(maKhachHangTextField, BorderLayout.WEST);
        pnlKhachHang.add(tenKhachHangTextField);

        pnlMain.add(pnlKhachHang);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);

        xoaRongBtn = new ButtonCustom("Xóa rỗng", "danger", 14, 100, 40);
        timBtn = new ButtonCustom("Tìm", "success", 14, 100, 40);
        tiepTucBtn = new ButtonCustom("Tiếp tục", "success", 14, 100, 40);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14, 100, 40);

        // timBtn.addActionListener(this);
        // xoaRongBtn.addActionListener(this);
        // tiepTucBtn.addActionListener(this);
        // btnHuyBo.addActionListener(this);

        tiepTucBtn.setEnabled(false);

        pnlButtom.add(timBtn);
        pnlButtom.add(tiepTucBtn);
        pnlButtom.add(xoaRongBtn);
        pnlButtom.add(btnHuyBo);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void xoaDuLieu() {
        tiepTucBtn.setEnabled(false);
        tenKhachHangTextField.setText("");
        tenKhachHangTextField.setEnabled(false);
        maKhachHangTextField.setText("");
        maKhachHangTextField.setEnabled(false);
        khachHang = null;
    }

    // @Override
    // public void actionPerformed(ActionEvent e) {
    // Object eSource = e.getSource();

    // if (eSource.equals(timBtn) || eSource.equals(soDienThoaiTextField)) {

    // maKhachHangTextField.setText("");
    // tenKhachHangTextField.setText("");
    // tiepTucBtn.setEnabled(false);

    // String soDienThoai = soDienThoaiTextField.getText().trim();
    // if (Validation.isEmpty(soDienThoai)
    // || !Validation.kiemTraSoDienThoai(soDienThoai) && soDienThoai.length() != 10)
    // {

    // JOptionPane.showMessageDialog(
    // this,
    // "Số điện thoại không được rỗng và phải là 10 ký tự số",
    // "Cảnh báo !",
    // JOptionPane.WARNING_MESSAGE);
    // return;
    // }

    // khachHang = khachHangDao.timTheoSDT(soDienThoai);

    // if (khachHang == null) {

    // JOptionPane.showMessageDialog(
    // this,
    // "Không tìm thấy khách hàng. Tạo khách hàng mới ?",
    // "Cảnh báo !",
    // JOptionPane.WARNING_MESSAGE);
    // if (timKhachHangListener != null)
    // timKhachHangListener.khongTimThayKhachHang(soDienThoai);

    // dispose();
    // return;
    // }

    // soDienThoaiTextField.setText(khachHang.getSoDienThoai());
    // maKhachHangTextField.setText(String.valueOf(khachHang.getMaKhachHang()));
    // tenKhachHangTextField.setText(khachHang.getHoTen());
    // tiepTucBtn.setEnabled(true);
    // }

    // if (eSource.equals(xoaRongBtn)) {
    // xoaDuLieu();
    // }

    // if (eSource.equals(btnHuyBo)) {
    // xoaDuLieu();
    // dispose();
    // }

    // if (eSource.equals(tiepTucBtn)) {
    // if (timKhachHangListener != null && khachHang != null)
    // timKhachHangListener.timThayhachhang(khachHang);
    // dispose();
    // }
    // }

}
