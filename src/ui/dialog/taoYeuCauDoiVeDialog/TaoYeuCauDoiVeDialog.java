package ui.dialog.taoYeuCauDoiVeDialog;

import dao.GaDao;
import dao.KhachHangDao;
import dao.TiepNhanYeuCauDoiVeDao;
import entity.KhachHang;
import entity.NhanVien;
import entity.TiepNhanYeuCauDoiVe;
import helper.Validation;
import singleton.NhanVienSuDungSingleton;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputDate;
import ui.component.InputForm;
import ui.component.SelectForm;
import ui.dialog.khachHangDialog.SuaKhachHangListener;
import ui.dialog.khachHangDialog.TaoKhachHangListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaoYeuCauDoiVeDialog extends JDialog implements MouseListener, WindowListener {
    private HeaderTitle titlePage;
    private JPanel pnlMain, pnlButtom;
    private ButtonCustom btnSubmit, btnHuyBo, btnKhMoi, btnTim;
    private InputForm tenKhachHangTextField;
    private InputForm soDienThoaiTextField;

    private SelectForm diemDiSelectForm;
    private SelectForm diemDenSelectForm;
    private InputDate ngayDiDate;
    private KhachHangDao khachHangDao;
    private TaoKhachHangListener taoKhachHangListener;
    private SuaKhachHangListener suaKhachHangListener;
    private List<String> tenGaList;
    private GaDao gaDao;
    private TiepNhanYeuCauDoiVeDao yeuCauDoiVeDao;

    public TaoYeuCauDoiVeDialog() {
        gaDao = new GaDao();
        tenGaList = new ArrayList<>();
        khachHangDao = new KhachHangDao();
        yeuCauDoiVeDao = new TiepNhanYeuCauDoiVeDao();
        initComponents();

        tenGaList = gaDao.layHetTenGa();

        diemDiSelectForm.setCbItems(tenGaList);
        diemDiSelectForm.setSelectedItem(null);

        diemDenSelectForm.setCbItems(tenGaList);
        diemDenSelectForm.setSelectedItem(null);
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

        titlePage = new HeaderTitle("Ghi nhận yêu cầu đổi vé");
        pnlMain = new JPanel(new GridLayout(5, 1, 20, 0));
        pnlMain.setBackground(Color.white);

        tenKhachHangTextField = new InputForm("Tên khách hàng");
        soDienThoaiTextField = new InputForm("Số điện thoại");

        diemDiSelectForm = new SelectForm("Điểm đi");
        diemDenSelectForm = new SelectForm("Điểm đến");

        ngayDiDate = new InputDate("Ngày đi");

        pnlMain.add(soDienThoaiTextField);
        pnlMain.add(tenKhachHangTextField);
        pnlMain.add(ngayDiDate);
        pnlMain.add(diemDiSelectForm);
        pnlMain.add(diemDenSelectForm);

        diemDiSelectForm.cbb.setEditable(true);
        diemDiSelectForm.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timGa(diemDiSelectForm);
                diemDiSelectForm.getCbb().showPopup();
            }

        });

        diemDenSelectForm.cbb.setEditable(true);
        diemDenSelectForm.getCbb().getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timGa(diemDenSelectForm);
                diemDenSelectForm.getCbb().showPopup();
            }

        });

        pnlButtom = new JPanel(new FlowLayout());
        pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
        pnlButtom.setBackground(Color.white);
        btnSubmit = new ButtonCustom("Tạo yêu cầu", "success", 14);
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
        // String soCMND = soCMNDTextField.getText().trim();

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
        }
        return true;
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    public void mousePressed(MouseEvent e)  {
        Object eSource = e.getSource();

        if (eSource.equals(btnSubmit)) {
            if (!kiemTraField())
                return;

            String soDienThoai = soDienThoaiTextField.getText().trim();
            String tenKhachHang = tenKhachHangTextField.getText().trim();
            String diemDi = (String) diemDiSelectForm.getSelectedItem();
            String diemDen = (String) diemDenSelectForm.getSelectedItem();
            LocalDateTime ngayDi = null;
            try {
                ngayDi = convertToLocalDateTime(ngayDiDate.getDate());
            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            KhachHang khachHang = khachHangDao.timTheoSDT(soDienThoai);
            NhanVien nhanVien = NhanVienSuDungSingleton.layThongTinNhanVienHienTai();

            // Tạo yêu cầu đổi vé
            TiepNhanYeuCauDoiVe yeuCauDoiVe = new TiepNhanYeuCauDoiVe(
                    0, // maYeuCau (auto-generated)
                    LocalDateTime.now(),
                    ngayDi,
                    "Yêu cầu đổi vé từ điểm " + diemDi + " đến " + diemDen,
                    nhanVien, // nhanVien (cần lấy từ session hoặc tương tự)
                    khachHang
            );

            // Lưu yêu cầu đổi vé vào cơ sở dữ liệu
            yeuCauDoiVeDao.them(yeuCauDoiVe);

            JOptionPane.showMessageDialog(
                    this,
                    "Yêu cầu đổi vé đã được tạo thành công",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

            xoaDuLieu();

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


    public void setTaoKhachHangListener(TaoKhachHangListener taoKhachHangListener) {
        this.taoKhachHangListener = taoKhachHangListener;
    }

    public void setSuaKhachHangListener(SuaKhachHangListener suaKhachHangListener) {
        this.suaKhachHangListener = suaKhachHangListener;
    }

    // private KhachHang layThongTinKhachHangTuField() {
    // int maKhacHang = 0;
    // if (!maKhachHangTextField.getText().trim().isEmpty())
    // maKhacHang = Integer.valueOf(maKhachHangTextField.getText().trim());
    //
    // String tenKhachHang = tenKhachHangTextField.getText().trim();
    // String soDienThoai = soDienThoaiTextField.getText().trim();
    // String soCMND = soCMNDTextField.getText().trim();
    //
    // return new KhachHang(maKhacHang, tenKhachHang, soDienThoai, soCMND);
    // }

    public void xoaDuLieu() {
        btnSubmit.setVisible(true);
        titlePage.setLblTitle("Thêm khách hàng");
        btnSubmit.setText("Thêm khách hàng");

        tenKhachHangTextField.setText("");
        tenKhachHangTextField.setEditable(true);

        soDienThoaiTextField.setText("");
        soDienThoaiTextField.setEditable(true);

        btnSubmit.setVisible(true);
    }


    public void taoTaiKhoanVoiSoDienThoai(String soDienThoai) {
        soDienThoaiTextField.setText(soDienThoai);
        tenKhachHangTextField.requestFocus();
        tenKhachHangTextField.getTxtForm().requestFocus();
    }

    @Override
    public void setVisible(boolean b) {
        if (!b) {
            xoaDuLieu();
        }
        super.setVisible(b);
    }

    private void timGa(SelectForm selectForm) {
        JTextField editorComponent = (JTextField) selectForm.getCbb().getEditor().getEditorComponent();
        String previousText = editorComponent.getText();
        String tenGa = previousText.trim().toLowerCase();

        if (tenGa.isEmpty()) {
            selectForm.setCbItems(tenGaList);
            selectForm.getCbb().setSelectedItem(null);
            return;
        }

        List<String> ketQua = tenGaList.stream().filter(item -> item.toLowerCase().contains(tenGa)).toList();

        selectForm.setCbItems(ketQua);
        editorComponent.setText(previousText);
    }
}