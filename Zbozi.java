package org.example;

import javax.swing.*;

public class Zbozi {
    //Atributy
    public String nazev ;
    public double cena;
    public int kusu;
    //Konstruktor
    public Zbozi(String newNazev, double newCena, int newKusu){
        this.cena = newCena; this.nazev=newNazev; this.kusu=newKusu;
    }
    //Metody
    public void odeberKs(int pocetKs){
        if(pocetKs>this.kusu){
            JOptionPane.showMessageDialog(new JFrame(),"Nelze požadovat více kusů než je na skladě","ERROR",JOptionPane.ERROR_MESSAGE);
        }else {
            this.kusu -= pocetKs;
        }
    }
    public void pridejKs(int pocetKs){
        this.kusu += pocetKs;
    }
    public int getKusu(){return kusu;}
    public double getCena(){return cena;}
    public String getNazev(){return nazev;}
}
