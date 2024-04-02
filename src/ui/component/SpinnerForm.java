/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.accessibility.AccessibleContext;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author Tran Nhat Sinh
 */
public final class SpinnerForm extends JPanel {

    private JLabel lblTitle;
    private JSpinner spinner;

    public SpinnerForm() {
    }

    public SpinnerForm(String title) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        this.setBorder(new EmptyBorder(0, 10, 5, 10));
        this.setPreferredSize(new Dimension(100, 100));
        lblTitle = new JLabel(title);
        spinner = new JSpinner();
        this.add(lblTitle);
        this.add(spinner);
    }

    public SpinnerForm(String title, int w, int h) {
        this.setLayout(new GridLayout(2, 1));
        this.setBackground(Color.white);
        // this.setBorder(new EmptyBorder(0, 10, 5, 10));
        this.setPreferredSize(new Dimension(w, h));
        lblTitle = new JLabel(title);
        spinner = new JSpinner();
        this.add(lblTitle);
        this.add(spinner);
    }

    public void setTitle(String title) {
        this.lblTitle.setText(title);
    }

    public JLabel getLblTitle() {
        return lblTitle;
    }

    public void setLblTitle(JLabel lblTitle) {
        this.lblTitle = lblTitle;
    }

    public JSpinner getSpinnerForm() {
        return spinner;
    }

    public void setTxtForm(JSpinner spinner) {
        this.spinner = spinner;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    @Override
    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }

    public void setAccessibleContext(AccessibleContext accessibleContext) {
        this.accessibleContext = accessibleContext;
    }

    public Object getValue() {
        return spinner.getValue();
    }

    public void setValue(Object content) {
        spinner.setValue(content);
    }

}
