package ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;

public class ChiTietHoaDonDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InputForm maHoaDonField, tenKhachHangField, tongTienField, thoiGianTaoHoaDonField;
	private ButtonCustom tatButton;
	private HeaderTitle titlePage;
	private JPanel pnlMain;
	private JPanel pnlButtom;
	
	public ChiTietHoaDonDialog() {
		setVisible(false);
		setSize(new Dimension(500, 700));
		setLayout(new BorderLayout(0, 0));
		
		titlePage = new HeaderTitle("Chi tiết hóa đơn");
		pnlMain = new JPanel(new FlowLayout());
		pnlMain.setBackground(Color.white);
		
		maHoaDonField = new InputForm("Mã hóa đơn", 450, 80);
		tenKhachHangField = new InputForm("Tên khách hàng", 450, 80);
		tongTienField = new InputForm("Tổng tiền", 450, 80);
		thoiGianTaoHoaDonField = new InputForm("Thời gian tạo hóa đơn", 450, 80);
		
		pnlMain.add(maHoaDonField);
		pnlMain.add(tenKhachHangField);
		pnlMain.add(tongTienField);
		pnlMain.add(thoiGianTaoHoaDonField);
		
		pnlButtom = new JPanel(new FlowLayout());
		pnlButtom.setBorder(new EmptyBorder(10, 0, 10, 0));
		pnlButtom.setBackground(Color.white);
		
		tatButton = new ButtonCustom("Tắt", "danger", 14,100,40);
		
		tatButton.addActionListener(this);
		
		pnlButtom.add(tatButton);
		
		add(titlePage, BorderLayout.NORTH);
		add(pnlMain, BorderLayout.CENTER);
		add(pnlButtom, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		
		xoaDuLieu();
	}
	
	private void xoaDuLieu() {
		maHoaDonField.setText("");
		tenKhachHangField.setText("");
		tongTienField.setText("");
		thoiGianTaoHoaDonField.setText("");
	}

	public static void main(String[] args) {
		new ChiTietHoaDonDialog().setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object eSource = e.getSource();
		
		if(eSource.equals(tatButton)) {
			xoaDuLieu();
			dispose();
		}
	}
}
