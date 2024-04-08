package ui.dialog;

import ui.component.HeaderTitle;
import ui.component.InputForm;
import ui.component.ButtonCustom;
import ui.panel.KhachHangPanel;
import dao.KhachHangDao;
import entity.KhachHang;
import ui.component.NumericDocumentFilter;
import helper.Validation;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

public class KhachHangDialog extends JDialog implements MouseListener {

    private KhachHangPanel jpKH;
    private JFrame owner;
    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private ButtonCustom btnThem, btnCapNhat, btnHuyBo, btnKhMoi, btnTim;
    private InputForm tenKH, sdtKH;
    private JTextField maKH;
    private KhachHangDao khachHangDao = new KhachHangDao();
    private entity.KhachHang kh;
    private static KhachHang khResult;

    public KhachHangDialog(KhachHangPanel jpKH, JFrame owner, String title, boolean modal, String type) {
        super(owner, title, modal);
        this.jpKH = jpKH;
        // khResult = null;
        this.owner = (JFrame) SwingUtilities.getWindowAncestor(jpKH);
        tenKH = new InputForm("Tên khách hàng");
        sdtKH = new InputForm("Số điện thoại");
        PlainDocument phonex = (PlainDocument) sdtKH.getTxtForm().getDocument();
        phonex.setDocumentFilter((new NumericDocumentFilter()));
        initComponents(title, type);
    }

    public KhachHangDialog(KhachHangPanel jpKH, JFrame owner, String title, boolean modal, String type,
            entity.KhachHang kh) {
        super(owner, title, modal);
        this.kh = kh;
        maKH = new JTextField("");
        setMaKH(Integer.toString(kh.getMaKhachHang()));
        tenKH = new InputForm("Tên khách hàng");
        setTenKH(kh.getHoTen());
        sdtKH = new InputForm("Số điện thoại");
        setSdtKH(kh.getSoDienThoai());
        this.jpKH = jpKH;
        initComponents(title, type);
    }

    public void initComponents(String title, String type) {
        this.setSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout(0, 0));
        titlePage = new HeaderTitle(title.toUpperCase());
        pnlMain = new JPanel(new GridLayout(3, 1, 20, 0));
        pnlMain.setBackground(Color.white);

        pnlMain.add(sdtKH);
        pnlMain.add(tenKH);

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnThem = new ButtonCustom("Thêm khách hàng", "success", 14);
        btnCapNhat = new ButtonCustom("Lưu thông tin", "success", 14);
        btnHuyBo = new ButtonCustom("Huỷ bỏ", "danger", 14);
        btnKhMoi = new ButtonCustom("Khách hàng mới", "success", 14);
        btnTim = new ButtonCustom("Tìm", "excel", 14);

        // Add MouseListener btn
        btnThem.addMouseListener(this);
        btnCapNhat.addMouseListener(this);
        btnHuyBo.addMouseListener(this);
        btnKhMoi.addMouseListener(this);
        btnTim.addMouseListener(this);

        switch (type) {
            case "create":
                pnlButtom.add(btnThem);
                break;
            case "update":
                pnlButtom.add(btnCapNhat);
                break;
            case "view":
                tenKH.setDisable();
                sdtKH.setDisable();
                break;
            case "find":
                tenKH.setDisable();
                if (khResult != null) {
                    setTenKH(khResult.getHoTen());
                    setSdtKH(khResult.getSoDienThoai());
                }

                pnlButtom.add(btnTim);
                pnlButtom.add(btnKhMoi);
                break;
        }
        pnlButtom.add(btnHuyBo);

        this.add(titlePage, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlButtom, BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setTenKH(String name) {
        tenKH.setText(name);
    }

    public String getTenKH() {
        return tenKH.getText();
    }

    public String getMaKH() {
        return maKH.getText();
    }

    public void setMaKH(String id) {
        this.maKH.setText(id);
    }

    public String getSdtKH() {
        return sdtKH.getText();
    }

    public void setSdtKH(String id) {
        this.sdtKH.setText(id);
    }

    public static KhachHang getKhResult() {
        return khResult;
    }

    public static void setKhResult(KhachHang khResult) {
        KhachHangDialog.khResult = khResult;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean Validation() {
        if (Validation.isEmpty(sdtKH.getText())
                || !Validation.isNumber(sdtKH.getText()) && sdtKH.getText().length() != 10) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được rỗng và phải là 10 ký tự số", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (Validation.isEmpty(tenKH.getText())) {
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được rỗng", "Cảnh báo !",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == btnThem && Validation()) {
            entity.KhachHang khachHang = new entity.KhachHang(getTenKH(), getSdtKH(), LocalDateTime.now(), false);
            this.khachHangDao.them(khachHang);
            setKhResult(this.khachHangDao.timTheoSDT(getSdtKH()));
            jpKH.loadDataTable();
            dispose();
        } else if (e.getSource() == btnHuyBo) {
            dispose();
        } else if (e.getSource() == btnCapNhat && Validation()) {
            this.kh.setHoTen(getTenKH());
            this.kh.setSoDienThoai(getSdtKH());

            this.khachHangDao.sua(this.kh);
            jpKH.loadDataTable();
            dispose();
        } else if (e.getSource() == btnKhMoi) {
            dispose();
            new KhachHangDialog(jpKH, owner, "Thêm khách hàng", true, "create");
        } else if (e.getSource() == btnTim & !this.sdtKH.getText().isEmpty()) {
            setKhResult(this.khachHangDao.timTheoSDT(getSdtKH()));

            if (khResult != null)
                this.setTenKH(getKhResult().getHoTen());
        }
    }

    public static boolean isPhoneNumber(String str) {
        // Loại bỏ khoảng trắng và dấu ngoặc đơn nếu có
        str = str.replaceAll("\\s+", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "");

        // Kiểm tra xem chuỗi có phải là một số điện thoại hợp lệ hay không
        if (str.matches("\\d{10}")) {
            return true;
        } else if (str.matches("\\d{3}-\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu gạch ngang
            return true;
        } else if (str.matches("\\(\\d{3}\\)\\d{3}-\\d{4}")) { // Kiểm tra số điện thoại có dấu ngoặc đơn
            return true;
        } else {
            return false; // Trả về false nếu chuỗi không phải là số điện thoại hợp lệ
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
