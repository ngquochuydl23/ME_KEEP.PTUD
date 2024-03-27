package GUI.Dialog.nhanVienDialog;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import GUI.Component.ButtonCustom;
import GUI.Component.HeaderTitle;
import GUI.Component.InputDate;
import GUI.Component.InputForm;
import GUI.Component.NumericDocumentFilter;
import dao1.NhanVienDao;
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
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;


public class NhanVienDialog extends JDialog {
    private HeaderTitle titlePage;
    private JPanel main, bottom;
    private ButtonCustom btnAdd, btnEdit, btnExit;
    private InputForm hoTenInputForm;
    private InputForm sdtInputForm;
    private InputForm emailInputForm;
    private ButtonGroup gioiTinhBtnGroup;
    private JRadioButton gioiTinhNamRbtn;
    private JRadioButton gioiTinhNuRbtn;
    private InputDate ngaySinhInputDate;
    private NhanVien nhanVien;

    private NhanVienDao nhanVienDao;

    public NhanVienDialog() {
        setLocationRelativeTo(null);
        init();
        nhanVienDao = new NhanVienDao();
    }

    public NhanVienDialog(NhanVien nhanVien) {
        setLocationRelativeTo(null);

        this.nhanVien = nhanVien;
        nhanVienDao = new NhanVienDao();

        hoTenInputForm.setText(nhanVien.getHoTen());
        sdtInputForm.setText(nhanVien.getSoDienThoai());
        emailInputForm.setText(nhanVien.getEmail());
        gioiTinhNamRbtn.setSelected(nhanVien.getGioitinh() == 1);
        //ngaySinhInputDate.setDate(nhanVien.getNgaysinh());

        init();
    }

    public void init() {

        this.setVisible(true);
        this.setSize(new Dimension(450, 590));
        this.setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle(nhanVien != null ? "Thêm" : "Sửa");

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
        JPanel jpaneljd = new JPanel();
        jpaneljd.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lbBd = new JLabel("Ngày sinh");
        lbBd.setSize(new Dimension(100, 100));
        jpaneljd.setSize(new Dimension(500, 100));
        jpaneljd.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpaneljd.setBackground(Color.white);
        ngaySinhInputDate = new InputDate("Ngày sinh");
        ngaySinhInputDate.setSize(new Dimension(100, 100));
        jpaneljd.add(lbBd);
        jpaneljd.add(ngaySinhInputDate);
        main.add(hoTenInputForm);
        main.add(emailInputForm);
        main.add(sdtInputForm);
        main.add(jpanelG);
        main.add(ngaySinhInputDate);

        bottom = new JPanel(new FlowLayout());
        bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottom.setBackground(Color.white);
        btnAdd = new ButtonCustom("Thêm người dùng", "success", 14);
        btnEdit = new ButtonCustom("Lưu thông tin", "success", 14);
        btnExit = new ButtonCustom("Hủy bỏ", "danger", 14);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themNhanVien();
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

//        switch (type) {
//            case "create" -> bottom.add(btnAdd);
//            case "update" -> bottom.add(btnEdit);
//            case "detail" -> {
//                name.setDisable();
//                sdtInputForm.setDisable();
//                email.setDisable();
//                Enumeration<AbstractButton> enumeration = gender.getElements();
//                while (enumeration.hasMoreElements()) {
//                    enumeration.nextElement().setEnabled(false);
//                }
//                jcBd.setDisable();
//            }
//            default -> throw new AssertionError();
//        }

        bottom.add(btnExit);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(main, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

    }

    boolean validationInput() throws ParseException {
        if (Validation.isEmpty(hoTenInputForm.getText())) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được rỗng", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (hoTenInputForm.getText().length() < 6) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên ít nhất 6 kí tự!");
            return false;
        } else if (Validation.isEmpty(emailInputForm.getText()) || !Validation.isEmail(emailInputForm.getText())) {
            JOptionPane.showMessageDialog(this, "Email không được rỗng và phải đúng cú pháp", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(sdtInputForm.getText()) && !Validation.isNumber(sdtInputForm.getText()) && sdtInputForm.getText().length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (ngaySinhInputDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!");
            return false;
        } else if (!gioiTinhNamRbtn.isSelected() && !gioiTinhNuRbtn.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!");
            return false;
        }
        return true;
    }

    private void themNhanVien() {
        try {
            if (validationInput()) {

                String sdt = sdtInputForm.getText().trim();
                if (nhanVienDao.timNhanVienTheoSdt(sdt) == null) {
                    JOptionPane.showMessageDialog(this, "Số điện thoại này đã được sử dụng trong hệ thống!");
                    return;
                }

                int gioiTinh = gioiTinhNamRbtn.isSelected() ? 1 : 0;
                String hoTen = hoTenInputForm.getText();
                String email = emailInputForm.getText();
                LocalDate ngaySinh = ngaySinhInputDate
                        .getDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

              //  nhanVienDao.them(new NhanVien(hoTen, gioiTinh, sdt, ngaySinh, 1, ))
                dispose();
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

            }
        } catch (ParseException ex) {
            Logger.getLogger(NhanVienDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
