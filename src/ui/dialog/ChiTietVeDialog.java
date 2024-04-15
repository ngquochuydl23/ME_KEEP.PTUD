package ui.dialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.component.ButtonCustom;
import ui.component.HeaderTitle;
import ui.component.InputForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChiTietVeDialog extends JDialog implements ActionListener{
    private InputForm maVeField, choNgoiField, toaTauField, giaVeField, maKhuyenMaiField, moTaField;
    private ButtonCustom luuButton, xoaTrangButton, tatButton;
    private HeaderTitle titlePage;
    private JPanel pnlMain;
    private JPanel pnlButtom;

    public ChiTietVeDialog() {
    	setVisible(false);
        setSize(new Dimension(500, 700));
        setLayout(new BorderLayout(0, 0));

        // Tạo panel cho tiêu đề
        titlePage = new HeaderTitle("Chi tiết vé");
        // pnlMain = new JPanel(new GridLayout(3, 1, 20, 0));
        pnlMain = new JPanel(new FlowLayout());
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
        
        maKhuyenMaiField = new InputForm("Mã khuyến mãi", 450, 80);
        
        moTaField =  new InputForm("Mô tả", 450, 80);
        
        pnlMain.add(maVeField);
        pnlMain.add(choNgoiField);
        pnlMain.add(toaTauField);
        pnlMain.add(giaVeField);
        pnlMain.add(maKhuyenMaiField);
        pnlMain.add(moTaField);

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
        add(pnlMain, BorderLayout.CENTER);
        add(pnlButtom, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        
        xoaDuLieu();
    }

    private void xoaDuLieu() {
    	maVeField.setText("");
        choNgoiField.setText("");
        toaTauField.setText("");
        giaVeField.setText("");
        maKhuyenMaiField.setText("");
        moTaField.setText("");
    }
    
    public static void main(String[] args) {
        new ChiTietVeDialog().setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eSource = e.getSource();
		
		if(eSource.equals(luuButton)) {
			// Lưu dữ liệu
		}

        if (eSource.equals(xoaTrangButton)) {
            xoaDuLieu();
        }
        
        if (eSource.equals(tatButton)) {
            xoaDuLieu();
            dispose();
        }
	}
}
