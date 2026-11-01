/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gui;

/**
 *
 * @author Cristofer
 */
public class main {

    public static void main(String[] args) {
       BookingPanel BP = new BookingPanel();
       BP.setVisible(true);
       
       HistoryPanel HP = new HistoryPanel();
       HP.setVisible(true);
      
       ReportPanel RP = new ReportPanel();
       RP.setVisible(true);
       
       InvoiceFrame IF = new InvoiceFrame();
       IF.setVisible(true);
      
    }
    
}
