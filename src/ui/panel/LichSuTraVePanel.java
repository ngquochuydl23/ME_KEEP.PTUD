package ui.panel;


import dao.LichSuTraVeDao;
import helper.Formater;
import ui.component.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import entity.*;
import helper.JTableExporter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public final class LichSuTraVePanel extends JPanel implements KeyListener, PropertyChangeListener, ItemListener {

    PanelBorderRadius main, functionBar, box;
    JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableLichSuTraVe;
    JScrollPane scrollTableLichSuTraVe;

    private DefaultTableModel tblModel;
    private InputDate thoiGianTraVe;
    private InputForm soDienThoaiInputForm;
    private List<LichSuTraVe> danhSachLichSuTraVe;
    private LichSuTraVeDao lichSuTraVeDao;

    public LichSuTraVePanel() {
        lichSuTraVeDao = new LichSuTraVeDao();
        danhSachLichSuTraVe = new ArrayList<>();
        initComponent();
        layDanhSachNhaGa();
        layDanhSachLichSu();
    }

    public void initPadding() {
        Color BackgroundColor = new Color(240, 247, 250);

        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        add(pnlBorder4, BorderLayout.WEST);
    }

    private void initComponent() {
        Color BackgroundColor = new Color(240, 247, 250);

        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        tableLichSuTraVe = new JTable();
        scrollTableLichSuTraVe = new JScrollPane();
        tblModel = new DefaultTableModel();
        String[] headerCols = "Mã vé;Tên người đi;Chỗ ngồi;Phí trả vé;Thời gian trả vé".split(";");
        tblModel.setColumnIdentifiers(headerCols);
        tableLichSuTraVe.setModel(tblModel);
        tableLichSuTraVe.setDefaultEditor(Object.class, null);
        scrollTableLichSuTraVe.setViewportView(tableLichSuTraVe);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableLichSuTraVe.setDefaultRenderer(Object.class, centerRenderer);
        tableLichSuTraVe.setFocusable(false);
        tableLichSuTraVe.getColumnModel().getColumn(0).setPreferredWidth(10);
        tableLichSuTraVe.getColumnModel().getColumn(1).setPreferredWidth(10);
        tableLichSuTraVe.getColumnModel().getColumn(2).setPreferredWidth(10);

        tableLichSuTraVe.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableLichSuTraVe, 0, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableLichSuTraVe, 1, TableSorter.INTEGER_COMPARATOR);
        TableSorter.configureTableColumnSorter(tableLichSuTraVe, 4, TableSorter.VND_CURRENCY_COMPARATOR);

        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        initPadding();

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        ChucNangChinh chucNangChinh = new ChucNangChinh(new String[]{"tim","chi-tiet", "nhap-excel", "xuat-excel"});
        functionBar.add(chucNangChinh);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        box = new PanelBorderRadius();
        box.setPreferredSize(new Dimension(250, 0));
        box.setLayout(new GridLayout(6, 1, 10, 0));
        box.setBorder(new EmptyBorder(0, 5, 150, 5));
        contentCenter.add(box, BorderLayout.WEST);


        thoiGianTraVe = new InputDate("Thời gian trả vé");

        soDienThoaiInputForm = new InputForm("Số điện thoại khách hàng");
        soDienThoaiInputForm.setEditable(true);
        soDienThoaiInputForm.requestFocus();

        chucNangChinh
        		.getToolbar("tim")
        		.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		
		                if (!validation() || !validateSelectDate()) return;
		                
		                clearDanhSachLichSu();
		                try {
							layLichSuTraVeTaiDong();
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
		            }
        		});
        
        chucNangChinh
                .getToolbar("chi-tiet")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!kiemTraChonDong())
                            return;
                    }
                });

        chucNangChinh
                .getToolbar("nhap-excel")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

        chucNangChinh
                .getToolbar("xuat-excel")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        xuatLichSuTraVeExcel();
                    }
                });

        box.add(soDienThoaiInputForm);
        box.add(thoiGianTraVe);

        main = new PanelBorderRadius();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentCenter.add(main, BorderLayout.CENTER);
        main.add(scrollTableLichSuTraVe);
    }



    public void resetForm() {

    }

//    private LichSuTraVe layLichSuTraVeTaiDong() {
//        int row = tableLichSuTraVe.getSelectedRow();
//        int maLichSuTraVe = Integer.parseInt(tblModel.getValueAt(row, 0).toString());
//
//        return danhSachLichSuTraVe.stream()
//                .filter(item -> item.getMaLichSuTraVe() == maLichSuTraVe)
//                .findAny()
//                .orElse(null);
//    }

    private boolean kiemTraChonDong() {
        if (tableLichSuTraVe.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng");
            return false;
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    private void layDanhSachNhaGa() {
        String[] listNcc = {"Vui vẻ nha"};
        listNcc = Stream.concat(Stream.of("Tất cả"), Arrays.stream(listNcc)).toArray(String[]::new);
        String[] listNv = {"Nhân viên thân thiện"};
        listNv = Stream.concat(Stream.of("Tất cả"), Arrays.stream(listNv)).toArray(String[]::new);
    }

    private void xuatLichSuTraVeExcel() {
        try {
            JTableExporter.exportJTableToExcel(tableLichSuTraVe);
            Logger.getLogger(LichSuTraVePanel.class.getName()).log(Level.INFO, "Xuất lịch sửa trả vé thành công");
        } catch (IOException ex) {
            Logger.getLogger(LichSuTraVePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void layDanhSachLichSu() {
        danhSachLichSuTraVe = lichSuTraVeDao.layHet();

        for (LichSuTraVe item : danhSachLichSuTraVe) {
            tblModel.addRow(new String[] {
                    item.getVe().getMaVe(),
                    item.getVe().getHoTenNguoiDi(),
                    String.valueOf(item.getVe().getSlot().getSoSlot()),
                    Formater.FormatVND(item.getPhiTraVe()),
                    Formater.FormatTime(item.getThoiGianTraVe())
            });
        }
    }
    private void layLichSuTraVeTaiDong() throws ParseException {
        danhSachLichSuTraVe = lichSuTraVeDao.layTheoSoDienThoai(soDienThoaiInputForm.getText(), thoiGianTraVe.getDateAsLocalDate());
		tblModel.setRowCount(0);

		for (LichSuTraVe item : danhSachLichSuTraVe) {
		    tblModel.addRow(new String[] {
		            item.getVe().getMaVe(),
		            item.getKhachHang().getHoTen(),
		            String.valueOf(item.getVe().getSlot().getSoSlot()),
		            Formater.FormatVND(item.getPhiTraVe()),
		            Formater.FormatTime(item.getThoiGianTraVe())
		    });
		}
    }

    private boolean validation() {
        String soDienThoai = soDienThoaiInputForm.getText();
        if (soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại khách hàng");
            return false;
        }
        return true;
    }
    
    public void clearDanhSachLichSu() {
        danhSachLichSuTraVe.clear();
        tblModel.setRowCount(0); 
    }
    
    private boolean validateSelectDate() {
        Date selectedDate;
		try {
			selectedDate = thoiGianTraVe.getDate();
	        if (selectedDate == null) {
	            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày thời gian trả vé");
	            return false;
	        }
	        return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
    }
}
