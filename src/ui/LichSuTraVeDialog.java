package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.text.PlainDocument;

import dao.LichSuTraVeDao;
import entity.KhachHang;
import entity.LichSuTraVe;
import entity.Slot;
import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputDate;
import ui.component.InputForm;
import ui.component.NumericDocumentFilter;


public class LichSuTraVeDialog extends JDialog implements MouseListener{
	private HeaderTitle titlePage;
    private InputForm maVeTextField;
    private InputForm tenKhachHangTextField;
    private InputForm soDienThoaiTextField;
    private InputForm choNgoiTextField;
    private InputForm phiTraVeTextField;
    private InputDate thoiGianTraVeTextField;
    private LichSuTraVeDao lichSuTraVeDao;
    private LichSuTraVe lichSuTraVe;
    private KhachHang khachHang;
    private Slot choNgoi;
    private ButtonCustom btnThoat;
    private JPanel main, bottom;
    
    public LichSuTraVeDialog() {
    	init();
    	lichSuTraVeDao = new LichSuTraVeDao();
    }
    
    public void init() {
 //       addWindowListener(this);
        setSize(new Dimension(600, 600));
        setLayout(new BorderLayout(0, 0));

        titlePage = new HeaderTitle("Chi tiết");
        main = new JPanel(new GridLayout(6, 1, 20, 0));
        main.setBackground(Color.white);
         
         main = new JPanel();
         main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
         main.setBackground(Color.white);
         maVeTextField = new InputForm("Mã vé");
         tenKhachHangTextField = new InputForm("Tên Khách Hàng");
         soDienThoaiTextField = new InputForm("Số điện thoại");
         
         choNgoiTextField = new InputForm("Chỗ ngồi");
         PlainDocument phonex = (PlainDocument) choNgoiTextField.getTxtForm().getDocument();
         phonex.setDocumentFilter((new NumericDocumentFilter()));
         phiTraVeTextField = new InputForm("Phí trả vé");
         thoiGianTraVeTextField = new InputDate("Thời gian trả vé");
         
         main.add(maVeTextField);
         main.add(tenKhachHangTextField);
         main.add(soDienThoaiTextField);
         main.add(choNgoiTextField);
         main.add(phiTraVeTextField);
         main.add(thoiGianTraVeTextField);
         
         bottom = new JPanel(new FlowLayout());
         bottom.setBorder(new EmptyBorder(10, 0, 10, 0));
         bottom.setBackground(Color.white);
         btnThoat = new ButtonCustom("Thoát","danger", 14);
         
         btnThoat.addMouseListener(this);
         
         bottom.add(btnThoat);
         
         add(titlePage, BorderLayout.NORTH);
         add(main, BorderLayout.CENTER);
         add(bottom, BorderLayout.SOUTH);
         setLocationRelativeTo(null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void xemLichSuTraVe() {
        titlePage.setLblTitle("Xem chi tiết");
        tenKhachHangTextField.setText(khachHang.getHoTen());
        soDienThoaiTextField.setText(khachHang.getSoDienThoai());
        //choNgoiTextField.setText(choNgoi.getSoSlot());
	}
}
