package ui.dialog.veDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.VeDao;
import entity.KhuyenMai;
import entity.Ve;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;
import ui.dialog.chiTietVeDialog.CapNhatVeListener;
import ui.dialog.khuyenMaiDialog.KhuyenMaiDialog;

public class VeDialog extends JDialog implements MouseListener {
    private InputForm maVeField, tenHanhKhachField,
            cccdHanhKhachField, namSinhHanhKhachField;
    private JPanel pnlMain, pnlButtom;
    private JButton capNhatBtn;
    private JButton huyBoBtn;
    private HeaderTitle titlePage;

    private VeDao veDao;
    private Ve ve;
    private CapNhatVeListener capNhatVeListener;

    public VeDialog() {
        this.veDao = new VeDao();
        init();
    }

    private void init() {
        setSize(new Dimension(500, 500));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Cập nhật vé");
        pnlMain = new JPanel(new GridLayout(5, 1, 20, 0));
        pnlMain.setBackground(java.awt.Color.white);

        maVeField = new InputForm("Mã vé");
        maVeField.getTxtForm().setEditable(false);
        tenHanhKhachField = new InputForm("Tên hành khách");
        cccdHanhKhachField = new InputForm("Căn cước công dân hành khách");
        namSinhHanhKhachField = new InputForm("Ngày bắt đầu");

        pnlMain.add(maVeField);
        pnlMain.add(tenHanhKhachField);
        pnlMain.add(cccdHanhKhachField);
        pnlMain.add(namSinhHanhKhachField);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(java.awt.Color.white);
        this.capNhatBtn = new ButtonCustom("Cập nhật", "excel", 14);
        huyBoBtn = new ButtonCustom("Huỷ bỏ", "danger", 14);

        // Add MouseListener btn
        capNhatBtn.addMouseListener(this);
        huyBoBtn.addMouseListener(this);

        pnlButtom.add(capNhatBtn);
        pnlButtom.add(huyBoBtn);

        add(titlePage, BorderLayout.NORTH);
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private boolean kiemTraDuLieu() {

        try {
            if (tenHanhKhachField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên người đi");
                return false;
            }

            if (cccdHanhKhachField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập cccd người đi");
                return false;
            }

            if (namSinhHanhKhachField.getText().isEmpty()) {
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

    public void setVe(Ve ve) {
        this.ve = ve;
        titlePage.setLblTitle("Cập nhật vé");
        maVeField.setText(ve.getMaVe());
        tenHanhKhachField.setText(ve.getHoTenNguoiDi());
        cccdHanhKhachField.setText(ve.getCccdNguoiDi());
        namSinhHanhKhachField.setText(String.valueOf(ve.getNamSinhNguoiDi()));

        invalidate();
        validate();
        repaint();
    }

    public void xemVe(Ve ve) {
        this.ve = ve;
        titlePage.setLblTitle("Chi tiết vé");
        maVeField.setText(ve.getMaVe());
        tenHanhKhachField.setText(ve.getHoTenNguoiDi());
        cccdHanhKhachField.setText(ve.getCccdNguoiDi());
        namSinhHanhKhachField.setText(String.valueOf(ve.getNamSinhNguoiDi()));

        tenHanhKhachField.setEditable(false);
        cccdHanhKhachField.setEditable(false);
        namSinhHanhKhachField.setEditable(false);

        pnlButtom.remove(capNhatBtn);
        invalidate();
        validate();
        repaint();
    }

    private Ve layThongTinKhuyenMaiTuField() {
        String maVe = maVeField.getText().trim();
        String tenHanhKhach = tenHanhKhachField.getText().trim();
        String cccdHanhKhach = cccdHanhKhachField.getText().trim();
        int namSinhHanhKhach = Integer.parseInt(namSinhHanhKhachField.getText().trim());

        Ve ve = new Ve();
        ve.setMaVe(maVe);
        ve.setHoTenNguoiDi(tenHanhKhach);
        ve.setCccdNguoiDi(cccdHanhKhach);
        ve.setNamSinhNguoiDi(namSinhHanhKhach);

        return ve;
    }

    private void xoaDuLieu() {
        maVeField.setText("");
        tenHanhKhachField.setText("");
        cccdHanhKhachField.setText("");
        namSinhHanhKhachField.setText("");
        this.ve = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object eSource = e.getSource();

        if (eSource.equals(capNhatBtn)) {
            if (!kiemTraDuLieu())
                return;

            if (ve != null) {
                if (veDao.sua(layThongTinKhuyenMaiTuField())) {
                    if (capNhatVeListener != null) {
                        capNhatVeListener.capNhatVe(layThongTinKhuyenMaiTuField());
                    }
                    Logger.getLogger(KhuyenMaiDialog.class.getName()).log(Level.INFO,
                            "Cập nhật thông tin vé thành công!");
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin vé thành công!");
                    xoaDuLieu();
                    dispose();
                }
            }

        }

        if (eSource.equals(huyBoBtn)) {
            xoaDuLieu();
            dispose();
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

    public void addCapNhatVeListener(CapNhatVeListener capNhatVeListener) {
        this.capNhatVeListener = capNhatVeListener;
    }
}
