package ui.dialog.nhanVienDialog;

import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputDate;
import ui.component.InputForm;
import ui.component.NumericDocumentFilter;
import dao.NhanVienDao;
import entity.NhanVien;
import helper.Validation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;


public class NhanVienDialog extends JDialog {
    private HeaderTitle titlePage;
    private ButtonCustom btnSubmit, btnExit;
    private InputForm hoTenInputForm;
    private InputForm sdtInputForm;
    private InputForm emailInputForm;
    private InputForm matKhauInputForm;
    private JComboBox<String> vaiTroCb;
    private ButtonGroup gioiTinhBtnGroup;
    private JRadioButton gioiTinhNamRbtn;
    private JRadioButton gioiTinhNuRbtn;
    private InputDate ngaySinhInputDate;
    private NhanVien nhanVien;
    private boolean dangChinhSua;
    private NhanVienDao nhanVienDao;
    private TaoNhanVienListener taoNhanVienListener;
    private SuaNhanVienListener suaNhanVienListener;
    private JPanel main, bottom;


    public NhanVienDialog() {
        init();
        enableFrom();
        dangChinhSua = false;
        nhanVienDao = new NhanVienDao();
    }

    public void init() {
        setSize(new Dimension(450, 590));
        setLocationRelativeTo(null);


        titlePage = new HeaderTitle("Thêm");

        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.white);
        hoTenInputForm = new InputForm("Họ và tên");
        sdtInputForm = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) sdtInputForm.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        emailInputForm = new InputForm("Email");
        gioiTinhNamRbtn = new JRadioButton("Nam");
        gioiTinhNuRbtn = new JRadioButton("Nữ");
        gioiTinhBtnGroup = new ButtonGroup();
        gioiTinhBtnGroup.add(gioiTinhNamRbtn);
        gioiTinhBtnGroup.add(gioiTinhNuRbtn);
        vaiTroCb = new JComboBox<>(new String[]{"Quản lí", "Nhân viên bán vé"});
        matKhauInputForm = new InputForm("Mật khẩu");


        // Gioi tinh - Field
        JPanel jpanelG = new JPanel(new GridLayout(2, 1, 0, 2));
        jpanelG.setBackground(Color.white);
        jpanelG.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel jgender = new JPanel(new GridLayout(1, 2));
        jgender.setSize(new Dimension(500, 80));
        jgender.setBackground(Color.white);
        jgender.add(gioiTinhNamRbtn);
        jgender.add(gioiTinhNuRbtn);
        JLabel labelGender = new JLabel("Giới tính");
        jpanelG.add(labelGender);
        jpanelG.add(jgender);


        // Ngày sinh field
        JLabel lbBd = new JLabel("Ngày sinh");
        lbBd.setSize(new Dimension(100, 100));

        ngaySinhInputDate = new InputDate("Ngày sinh");
        ngaySinhInputDate.setSize(new Dimension(100, 100));

        JPanel jpaneljd = new JPanel();
        jpaneljd.setBorder(new EmptyBorder(10, 10, 10, 10));
        jpaneljd.setSize(new Dimension(500, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);
        jpaneljd.add(lbBd);
        jpaneljd.add(ngaySinhInputDate);

        // Vai tro combo box
        JLabel vaiTroLabel = new JLabel("Vai Trò");
        vaiTroLabel.setSize(new Dimension(100, 100));

        JPanel vaiTroPanel = new JPanel();
        vaiTroPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        vaiTroPanel.setSize(new Dimension(500, 100));
        vaiTroPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        vaiTroPanel.setBackground(Color.white);
        vaiTroPanel.add(vaiTroLabel);
        vaiTroPanel.add(vaiTroCb);


        main.add(hoTenInputForm);
        main.add(sdtInputForm);
        main.add(emailInputForm);
        main.add(matKhauInputForm);

        main.add(jpanelG);
        main.add(ngaySinhInputDate);
        main.add(vaiTroPanel);


        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnSubmit = new ButtonCustom(dangChinhSua ? "Lưu thông tin" : "Thêm người dùng", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dangChinhSua){
                    suaNhanVien();
                    return;
                }
                themNhanVien();
            }
        });

        bottom.add(btnSubmit);
        bottom.add(btnExit);
        this.add(titlePage, BorderLayout.NORTH);
        this.add(main, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

    }

    boolean validationInput() throws ParseException {
        String hoTen = hoTenInputForm.getText().trim();
        String email = emailInputForm.getText().trim();
        LocalDate ngaySinh = ngaySinhInputDate
                .getDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        String matKhau = matKhauInputForm.getText().trim();
        String sdt = sdtInputForm.getText().trim();


        if (Validation.isEmpty(hoTen)) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (hoTen.length() < 6) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên ít nhất 6 kí tự!");
            return false;
        }
        if (Validation.isEmpty(email) || !Validation.kiemTraEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (Validation.isEmpty(sdt) || !Validation.kiemTraSoDienThoai(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải đúng định dạng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (ngaySinh == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
            return false;
        }
        if (!gioiTinhNamRbtn.isSelected() && !gioiTinhNuRbtn.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return false;
        }
        if (Validation.isEmpty(matKhau)) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
            return false;
        }
        if (vaiTroCb.getSelectedItem() == null || vaiTroCb.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vai trò!");
            return false;
        }
        return true;
    }

    private void themNhanVien() {
        try {
            if (validationInput()) {

                NhanVien nhanVienMoi = layNhanVienTuField();

                if (nhanVienDao.timNhanVienTheoSdt(nhanVienMoi.getSoDienThoai()) != null) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại này đã được sử dụng trong hệ thống!");
                    return;
                }

                if (nhanVienDao.timNhanVienTheoEmail(nhanVienMoi.getEmail()) != null) {
                    JOptionPane.showMessageDialog(this, "Email này đã được sử dụng trong hệ thống!");
                    return;
                }

                if (nhanVienDao.them(nhanVienMoi)) {
                    if (taoNhanVienListener != null) {
                        taoNhanVienListener.taoNhanVienThanhCong();
                    }
                    dispose();
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(NhanVienDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void suaNhanVien() {
        try {
            if (validationInput()) {
                NhanVien thongNVTinMoi = layNhanVienTuField();
                thongNVTinMoi.setMaNhanVien(nhanVien.getMaNhanVien());

                if (!nhanVien.getSoDienThoai().equals(thongNVTinMoi.getSoDienThoai()) && nhanVienDao.timNhanVienTheoSdt(thongNVTinMoi.getSoDienThoai()) != null) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại này đã được sử dụng trong hệ thống!");
                    return;
                }

                if (!nhanVien.getEmail().equals(thongNVTinMoi.getEmail()) && nhanVienDao.timNhanVienTheoEmail(thongNVTinMoi.getEmail()) != null) {
                    JOptionPane.showMessageDialog(this, "Email này đã được sử dụng trong hệ thống!");
                    return;
                }

                if (nhanVienDao.sua(thongNVTinMoi)) {
                    if (suaNhanVienListener != null) {
                        suaNhanVienListener.suaNhanVienThanhCong();
                    }
                    dispose();
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(NhanVienDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTaoNhanVienListener(TaoNhanVienListener taoNhanVienListener) {
        this.taoNhanVienListener = taoNhanVienListener;
    }

    public void setSuaNhanVienListener(SuaNhanVienListener suaNhanVienListener) {
        this.suaNhanVienListener = suaNhanVienListener;
    }

    public void xoaDuLieu() {
        dangChinhSua = false;
        titlePage.setLblTitle("Thêm");


        enableFrom();

        hoTenInputForm.setText("");
        sdtInputForm.setText("");
        emailInputForm.setText("");
        gioiTinhNamRbtn.setSelected(false);
        gioiTinhNuRbtn.setSelected(false);
        vaiTroCb.setSelectedItem(null);
        ngaySinhInputDate.setDate(LocalDate.of(1900, 1, 1));
        btnSubmit.setText("Thêm người dùng");
        btnSubmit.setVisible(true);
        invalidate();
        validate();
        repaint();
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        dangChinhSua = true;
        titlePage.setLblTitle("Sửa");
        enableFrom();
        hoTenInputForm.setText(nhanVien.getHoTen());
        sdtInputForm.setText(nhanVien.getSoDienThoai());
        emailInputForm.setText(nhanVien.getEmail());
        gioiTinhNamRbtn.setSelected(nhanVien.getGioitinh() == 1);
        ngaySinhInputDate.setDate(nhanVien.getNgaysinh());
        matKhauInputForm.setText(nhanVien.getMatKhau());
        vaiTroCb.setSelectedItem(nhanVien.getVaiTro());

        gioiTinhNamRbtn.setSelected(nhanVien.getGioitinh() == 1);
        gioiTinhNuRbtn.setSelected(nhanVien.getGioitinh() == 0);

        btnSubmit.setText("Lưu thông tin");
        btnSubmit.setVisible(true);
        invalidate();
        validate();
        repaint();
    }

    public void xemChiTietNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        setVisible(true);

        dangChinhSua = false;
        titlePage.setLblTitle("Sửa");
        disableForm();
        hoTenInputForm.setText(nhanVien.getHoTen());
        sdtInputForm.setText(nhanVien.getSoDienThoai());
        emailInputForm.setText(nhanVien.getEmail());
        gioiTinhNamRbtn.setSelected(nhanVien.getGioitinh() == 1);
        ngaySinhInputDate.setDate(nhanVien.getNgaysinh());
        matKhauInputForm.setText(nhanVien.getMatKhau());
        vaiTroCb.setSelectedItem(nhanVien.getVaiTro());

        gioiTinhNamRbtn.setSelected(nhanVien.getGioitinh() == 1);
        gioiTinhNuRbtn.setSelected(nhanVien.getGioitinh() == 0);

        btnSubmit.setVisible(false);
        invalidate();
        validate();
        repaint();
    }

    private NhanVien layNhanVienTuField() throws ParseException {
        int gioiTinh = gioiTinhNamRbtn.isSelected() ? 1 : 0;
        String hoTen = hoTenInputForm.getText().trim();
        String email = emailInputForm.getText().trim();
        LocalDate ngaySinh = ngaySinhInputDate
                .getDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        String matKhau = matKhauInputForm.getText().trim();
        String vaiTro = vaiTroCb.getSelectedItem().toString();
        String sdt = sdtInputForm.getText().trim();

        return new NhanVien(hoTen, gioiTinh, sdt, ngaySinh, 1, matKhau, email, vaiTro);
    }

    private void enableFrom() {
        hoTenInputForm.setEditable(true);
        sdtInputForm.setEditable(true);
        emailInputForm.setEditable(true);
        gioiTinhNamRbtn.setEnabled(true);
        gioiTinhNuRbtn.setEnabled(true);
        vaiTroCb.setEditable(true);
        vaiTroCb.setEnabled(true);
        matKhauInputForm.setEditable(true);
        ngaySinhInputDate.getDateChooser().setEnabled(true);
    }

    private void disableForm()  {
        hoTenInputForm.setEditable(false);
        sdtInputForm.setEditable(false);
        emailInputForm.setEditable(false);
        gioiTinhNamRbtn.setEnabled(false);
        gioiTinhNuRbtn.setEnabled(false);
        vaiTroCb.setEditable(false);
        vaiTroCb.setEnabled(false);
        ngaySinhInputDate.getDateChooser().setEnabled(false);
        matKhauInputForm.setEditable(false);
    }
}
