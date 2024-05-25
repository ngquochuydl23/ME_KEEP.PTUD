package ui.panel;

import dao.VeDao;
import entity.ToaTau;
import entity.Ve;
import ui.component.ChucNangChinh;
import ui.component.IntegratedSearch;
import ui.component.PanelBorderRadius;
import ui.component.TableSorter;
import ui.dialog.chiTietVeDialog.CapNhatVeListener;
import ui.dialog.veDialog.VeDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

/**
 * VePanel
 */
public class VePanel extends JPanel {
    private PanelBorderRadius main, functionBar;
    private JPanel pnlBorder1, pnlBorder2, pnlBorder3, pnlBorder4, contentCenter;
    private JTable tableVe;
    private JScrollPane scrolltableVe;
    private ChucNangChinh chucNangChinh;
    private IntegratedSearch search;
    private DefaultTableModel tblModel;
    private VeDialog veDialog;
    Color BackgroundColor = new Color(240, 247, 250);

    private VeDao veDao;
    private List<Map<String, Object>> listMapVe;
    private List<Ve> ves;

    public VePanel() {
        this.veDao = new VeDao();
        initComponent();

        loadDataTable();
    }

    private void initComponent() {
        setBackground(BackgroundColor);
        setLayout(new BorderLayout(0, 0));
        setOpaque(true);

        this.veDialog = new VeDialog();
        this.veDialog.addCapNhatVeListener(new CapNhatVeListener() {
            @Override
            public void capNhatVe(Ve ve) {
                loadDataTable();
            }
        });

        tableVe = new JTable();
        tableVe.setDefaultEditor(Object.class, null);
        scrolltableVe = new JScrollPane();
        tblModel = new DefaultTableModel();
        tblModel.setColumnIdentifiers(
                "Mã vé;Tuyến;Tàu;Toa;Chỗ ngồi;Tình trạng;Tên hành khách;CCCD hành khách;".split(";"));
        tableVe.setModel(tblModel);
        tableVe.setFocusable(false);
        scrolltableVe.setViewportView(tableVe);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableVe.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableVe.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableVe.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableVe.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableVe.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tableVe.setAutoCreateRowSorter(true);
        TableSorter.configureTableColumnSorter(tableVe, 0, TableSorter.INTEGER_COMPARATOR);

        pnlBorder1 = new JPanel();
        pnlBorder1.setPreferredSize(new Dimension(0, 10));
        pnlBorder1.setBackground(BackgroundColor);
        this.add(pnlBorder1, BorderLayout.NORTH);

        pnlBorder2 = new JPanel();
        pnlBorder2.setPreferredSize(new Dimension(0, 10));
        pnlBorder2.setBackground(BackgroundColor);
        this.add(pnlBorder2, BorderLayout.SOUTH);

        pnlBorder3 = new JPanel();
        pnlBorder3.setPreferredSize(new Dimension(10, 0));
        pnlBorder3.setBackground(BackgroundColor);
        this.add(pnlBorder3, BorderLayout.EAST);

        pnlBorder4 = new JPanel();
        pnlBorder4.setPreferredSize(new Dimension(10, 0));
        pnlBorder4.setBackground(BackgroundColor);
        this.add(pnlBorder4, BorderLayout.WEST);

        contentCenter = new JPanel();
        contentCenter.setPreferredSize(new Dimension(1100, 600));
        contentCenter.setBackground(BackgroundColor);
        contentCenter.setLayout(new BorderLayout(10, 10));
        this.add(contentCenter, BorderLayout.CENTER);

        functionBar = new PanelBorderRadius();
        functionBar.setPreferredSize(new Dimension(0, 100));
        functionBar.setLayout(new GridLayout(1, 2, 50, 0));
        functionBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        chucNangChinh = new ChucNangChinh(
                new String[]{"sua", "chi-tiet", "xuat-excel"});

        chucNangChinh
                .getToolbar("sua")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Ve ve = layVeDangChon();
                        veDialog.setVisible(true);
                        veDialog.setVe(ve);
                    }
                });

        chucNangChinh
                .getToolbar("chi-tiet")
                .addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Ve ve = layVeDangChon();
                        veDialog.xemVe(ve);
                        veDialog.setVisible(true);
                    }
                });

        functionBar.add(chucNangChinh);

        search = new IntegratedSearch(
                new String[]{"Tất cả", "Mã khuyến mãi", "Tỉ lệ giảm", "Khách hàng thân thiết", "Số điện thoại"});
        search.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataTable();
            }
        });

        search.txtSearchForm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyWord = search.txtSearchForm.getText();
                String type = (String) search.cbxChoose.getSelectedItem();
                // if (keyWord.isEmpty())
                // loadDataTable();
                // else
                // timKhachHang(keyWord, type);

            }
        });
        search.cbxChoose.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) search.cbxChoose.getSelectedItem();
                    if (selectedOption.equals("Khách hàng thân thiết")) {

                        // timKhachHang("", selectedOption);
                        loadDataTable();
                    } else {
                        loadDataTable();
                    }
                }
            }
        });

        functionBar.add(search);
        contentCenter.add(functionBar, BorderLayout.NORTH);

        main = new PanelBorderRadius();
        BoxLayout boxly = new BoxLayout(main, BoxLayout.Y_AXIS);
        main.setLayout(boxly);
        contentCenter.add(main, BorderLayout.CENTER);

        main.add(scrolltableVe);

    }

    private void loadDataTable() {
        this.tblModel.setRowCount(0);

        this.listMapVe = this.veDao.layHetCoToaTau();
        this.ves = this.veDao.layHet();
        for (Map<String, Object> map : listMapVe) {
            ToaTau toaTau = (ToaTau) map.get("toa");
            Ve ve = (Ve) map.get("ve");

            Boolean daHuyVe = ve.getTinhTrangVe() == 0;
            String tinhTrang = ve.getTinhTrangVe() == 1 ? "<html><font color='green'>Đã bán</font></html>" : "<html><font color='red'>Đã hủy</font></html>";

            String cccd = "-1".equals(ve.getCccdNguoiDi()) ? "Không có CCCD" : ve.getCccdNguoiDi();

            this.tblModel.addRow(new Object[]{
                    ve.getMaVe(),
                    ve.getTuyen().getMaTuyen(),
                    ve.getTau().getTenTau(),
                    toaTau.getTenToa(),
                    ve.getSlot().getSoSlot(),
                    tinhTrang,
                    daHuyVe ? "" : ve.getHoTenNguoiDi(),
                    daHuyVe ? "" : cccd
            });
        }
    }

    public Ve layVeDangChon() {
        int index = tableVe.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi");
        }
        return ves.get(index);
    }
}