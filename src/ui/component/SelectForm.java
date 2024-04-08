/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.component;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SelectForm extends JPanel {
    private JLabel lblTitle;
    public JComboBox<String> cbb;

    public SelectForm(String title, String[] obj) {
        setLayout(new GridLayout(2, 1));
        setBackground(Color.white);
        setBorder(new EmptyBorder(0, 10, 5, 10));

        lblTitle = new JLabel(title);
        cbb = new JComboBox<String>(obj);
        cbb.setSelectedItem(null);

        add(lblTitle);
        add(cbb);
    }

    public SelectForm(String title) {
        setLayout(new GridLayout(2, 1));
        setBackground(Color.white);
        setBorder(new EmptyBorder(0, 10, 5, 10));

        lblTitle = new JLabel(title);
        cbb = new JComboBox<String>();
        add(lblTitle);
        add(cbb);
    }

    public void setArr(String[] obj) {
        this.cbb.setModel(new DefaultComboBoxModel<String>(obj));
    }

    public void setCbItems(List<String> items) {
        String[] arrItems = new String[items.size()];
        items.toArray(arrItems);
        cbb.setModel(new DefaultComboBoxModel(arrItems));
    }

    public String getValue() {
        return (String) cbb.getSelectedItem();
    }

    public Object getSelectedItem() {
        return cbb.getSelectedItem();
    }

    public int getSelectedIndex() {
        return cbb.getSelectedIndex();
    }

    public void setSelectedIndex(int i) {
        cbb.setSelectedIndex(i);
    }

    public void setSelectedItem(Object a) {
        cbb.setSelectedItem(a);
    }

    public JLabel getLblTitle() {
        return lblTitle;
    }

    public void setLblTitle(JLabel lblTitle) {
        this.lblTitle = lblTitle;
    }

    public JComboBox<String> getCbb() {
        return cbb;
    }

    public void setCbb(JComboBox cbb) {
        this.cbb = cbb;
    }

    public void setDisable() {
        cbb.setEnabled(false);
    }

}
