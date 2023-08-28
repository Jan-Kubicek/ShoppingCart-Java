package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    //Komponenty
    JLabel lbNorth, lbSouth;
    JButton btnOdstranZbozi, btnOdstranKosik, btnVybrat, btnSubmit, btnVymaz, btnZobraz, btnZaplat;
    JTextField tfNazev, tfCena, tfKusu, tfCisloKarty, tfSchvaleno;
    JPanel jpcenter, jpLeft, jpLeftbottom, jpLeftbottomU, jpLeftbottomB, jpRight, jpRightbottom, jpRightbottomU, jpRightbottomB;
    JTable tZbozi, tKosik;

    //Konstruktor
    public MainFrame() {
        InitGUI();
        setTitle("Košík GUIv2");
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //GUI
    public void InitGUI() {
        //North
        lbNorth = new JLabel("Aplikace - Košík v2.0");
        add(lbNorth, BorderLayout.NORTH);
        //South
        lbSouth = new JLabel("Jan Kubíček @2023");
        add(lbSouth, BorderLayout.SOUTH);
        //Center
        jpcenter = new JPanel(new GridLayout(1, 2));
        //Center Left
        jpLeft = new JPanel(new GridLayout(2, 1));
        DefaultTableModel model = new DefaultTableModel(); //Table
        model.addColumn("ID");
        model.addColumn("Nazev");
        model.addColumn("KS");
        model.addColumn("Cena");
        tZbozi = new JTable(model);
        jpLeft.add(new JScrollPane(tZbozi));            //Scrollpane
        jpLeftbottom = new JPanel(new GridLayout(2, 1));
        jpLeftbottomU = new JPanel(new GridLayout(2, 2));
        tfNazev = new JTextField("Název");
        jpLeftbottomU.add(tfNazev);
        tfCena = new JTextField("Cena");
        jpLeftbottomU.add(tfCena);
        tfKusu = new JTextField("Kusů");
        jpLeftbottomU.add(tfKusu);
        btnSubmit = new JButton("Přidej Zboží");
        jpLeftbottomU.add(btnSubmit);
        btnSubmit.addActionListener(e -> {
            int id = model.getRowCount();
            boolean add = true;
            id++;
            String nazev = tfNazev.getText();
            double cena = Double.parseDouble(tfCena.getText());
            int kusu = Integer.parseInt(tfKusu.getText());
            Zbozi zbozi = new Zbozi(nazev, cena, kusu);
            // pokud už v seznamu něco takového je tak se pouze přičtou kusy
            for (int rows = 0; rows < model.getRowCount(); rows++) {
                String cl1 = model.getValueAt(rows, 1).toString();
                double cl3 = Double.parseDouble(model.getValueAt(rows, 3).toString());
                if (cl1.equals(nazev) && cl3 == cena) {
                    int cl2 = Integer.parseInt(model.getValueAt(rows, 2).toString());
                    cl2 += kusu;
                    model.setValueAt(cl2, rows, 2);
                    add = false;
                }
            }
            if (add) model.addRow(new Object[]{id, nazev, kusu, cena});
        });
        jpLeftbottom.add(jpLeftbottomU);
        jpLeftbottomB = new JPanel(new GridLayout(2, 1));
        btnVybrat = new JButton("Pridej do košíku");
        jpLeftbottomB.add(btnVybrat);
        btnOdstranZbozi = new JButton("Odeber ze zboží");
        btnOdstranZbozi.addActionListener(e -> model.removeRow(tZbozi.getSelectedRow()));
        jpLeftbottomB.add(btnOdstranZbozi);
        jpLeftbottom.add(jpLeftbottomB);
        jpLeft.add(jpLeftbottom);
        jpcenter.add(jpLeft);
        //CenterRight
        jpRight = new JPanel(new GridLayout(2, 1));
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("ID");
        model1.addColumn("Nazev");
        model1.addColumn("KS");
        model1.addColumn("Cena");
        tKosik = new JTable(model1);
        jpRight.add(new JScrollPane(tKosik));
        jpRightbottom = new JPanel(new GridLayout(2, 1));
        jpRightbottomU = new JPanel(new GridLayout(2, 2));
        btnVybrat.addActionListener(e -> {
            int indexMd1 = model1.getRowCount();
            ++indexMd1;
            int indexMdSr = tZbozi.getSelectedRow();
            String nazevMd = model.getValueAt(indexMdSr, 1).toString();
            double cenaMd = Double.parseDouble(model.getValueAt(indexMdSr, 3).toString());
            boolean isThere = false;
            for (int rows = 0; rows < model1.getRowCount(); rows++) {
                if (nazevMd.equals(model1.getValueAt(rows, 1)) && cenaMd == Double.parseDouble(model1.getValueAt(rows, 3).toString())) {
                    int pksMd1 = Integer.parseInt(model1.getValueAt(rows, 2).toString());
                    ++pksMd1;
                    model1.setValueAt(pksMd1, rows, 2);
                    isThere = true;
                    int pksMd = Integer.parseInt(model.getValueAt(indexMdSr, 2).toString());
                    if (pksMd > 0) {
                        --pksMd;
                        model.setValueAt(pksMd, indexMdSr, 2);
                    } else {
                        --pksMd1;
                        model1.setValueAt(pksMd1, rows, 2);
                        model.removeRow(indexMdSr);
                    }
                }
            }
            if (!isThere) {
                {
                    model1.addRow(new Object[]{indexMd1, model.getValueAt(indexMdSr, 1), 1, model.getValueAt(indexMdSr, 3)});
                    int pksMd = Integer.parseInt(model.getValueAt(indexMdSr, 2).toString());
                    if (pksMd != 0) {
                        --pksMd;
                        model.setValueAt(pksMd, indexMdSr, 2);
                    } else {
                        model.removeRow(indexMdSr);
                    }
                }
            }
        });
        btnOdstranKosik = new JButton("Odstraň zboží v košíku");
        jpRightbottomU.add(btnOdstranKosik);
        btnOdstranKosik.addActionListener(e -> {
            //TODO
            int indextKosik = tKosik.getSelectedRow();
            String sNazev = String.valueOf(model1.getValueAt(indextKosik, 1));
            int pKusu = Integer.parseInt(model1.getValueAt(indextKosik, 2).toString());
            double cKusu = Double.parseDouble(model1.getValueAt(indextKosik, 3).toString());
            boolean isThere = false;
            //1. položka se vrátí do tZbozi
            for (int rows = 0; rows < model.getRowCount(); rows++) {
                if (sNazev.equals(model.getValueAt(rows, 1)) && cKusu == Double.parseDouble(model.getValueAt(rows, 3).toString())) {
                    int pKusuZBozi = Integer.parseInt(model.getValueAt(rows, 2).toString());
                    pKusuZBozi += pKusu;
                    model.setValueAt(pKusuZBozi, rows, 2);
                    isThere = true;
                }
            }
            if (!isThere) {
                int index = model.getRowCount();
                index++;
                model.addRow(new Object[]{index, sNazev, pKusu, cKusu});
            }
            model1.removeRow(indextKosik);
        });
        btnVymaz = new JButton("Smaž vše v košíku");
        jpRightbottomU.add(btnVymaz);
        btnVymaz.addActionListener(e -> {
            //TODO
            //1. vše se vrátí do tZbozi
            boolean isThere = false;
            for (int rows = 0; rows < model1.getRowCount(); rows++) {
                String nazev = model1.getValueAt(rows, 1).toString();
                int pKusu = Integer.parseInt(model1.getValueAt(rows, 2).toString());
                double cKusu = Double.parseDouble(model1.getValueAt(rows, 3).toString());
                for (int tZbozirows = 0; tZbozirows < model.getRowCount(); tZbozirows++) {
                    if (nazev.equals(model.getValueAt(tZbozirows, 1)) && cKusu == Double.parseDouble(model.getValueAt(tZbozirows, 3).toString())) {
                        isThere = true;
                        int pKusuVZbozi = Integer.parseInt(model.getValueAt(tZbozirows, 2).toString());
                        pKusuVZbozi += pKusu;
                        model.setValueAt(pKusuVZbozi, tZbozirows, 2);
                    }
                }
                if (!isThere) {
                    int index = model.getRowCount();
                    index++;
                    model.addRow(new Object[]{index, nazev, pKusu, cKusu});
                }
            }
            model1.setNumRows(0);
        });
        btnZobraz = new JButton("Zobraz Účtenku");
        jpRightbottomU.add(btnZobraz);
        btnZobraz.addActionListener(e -> {
            double suma = 0;
            int sumaKusu = 0;
            for (int rows = 0; rows < model1.getRowCount(); rows++) {
                int pKusu = Integer.parseInt(model1.getValueAt(rows, 2).toString());
                double cKusu = Double.parseDouble(model1.getValueAt(rows, 3).toString());
                suma += pKusu * cKusu;
                sumaKusu += pKusu;
            }
            JOptionPane.showMessageDialog(this, "Váš nákup bude stát: " + suma + " Kč\n a celkem jste vložil do košíku: " + sumaKusu + " ks", "Účtenka", JOptionPane.INFORMATION_MESSAGE);
        });
        btnZaplat = new JButton("Zaplať");
        jpRightbottomU.add(btnZaplat);
        btnZaplat.addActionListener(e -> {
            boolean provestPlatbu = model1.getRowCount() != 0;
            if (provestPlatbu) {
                String cisloKarty = tfCisloKarty.getText();
                if (cisloKarty.length() != 16) {
                    JOptionPane.showMessageDialog(this, "Zadali jste špatně číslo karty", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Platba byla provedena", "Schváleno", JOptionPane.INFORMATION_MESSAGE);
                    tfSchvaleno.setText("Platba provedena");
                    tfSchvaleno.setBackground(new Color(0, 255, 0));
                    model1.setNumRows(0);
                }
            }else{
                JOptionPane.showMessageDialog(this,"Nelze provést platbu za prázdný košík","Warning",JOptionPane.WARNING_MESSAGE);
            }
        });
        jpRightbottom.add(jpRightbottomU);
        jpRightbottomB = new JPanel(new GridLayout(2, 1));
        tfCisloKarty = new JTextField("Číslo karty");
        jpRightbottomB.add(tfCisloKarty);
        tfSchvaleno = new JTextField();
        jpRightbottomB.add(tfSchvaleno);
        jpRightbottom.add(jpRightbottomB);
        jpRight.add(jpRightbottom);
        jpcenter.add(jpRight);
        add(jpcenter, BorderLayout.CENTER);
        pack();
    }

    //Main
    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
