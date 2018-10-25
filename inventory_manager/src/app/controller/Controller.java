package app.controller;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import app.sql.DBManager;
import app.rfid.*;
import app.view.frame.*;
import app.view.panel.*;


public class Controller{

    /* シングルトン */
    private static Controller instance = new Controller();
    public static Controller getInstance(){
        if(instance==null){
            instance = new Controller();
        }
        return instance;
    }
    private Controller(){
        super();
    }

    /* フィールド */
    private DBManager db = null;
    private MainFrame frame = null;
    private MenuPanel menupanel = null;
    private DefaultPanel defaultpanel = null;
    private ShowPanel showpanel = null;
    private StockPanel stockpanel = null;
    private FlowPanel flowpanel = null;
    private NewPanel newpanel = null;
    private DeletePanel deletepanel = null;

    /* メソッド */
    private void loadDBManager(){
        db = DBManager.getInstance(this);
    }

    private void loadRFIDReader(){
        RFIDReader.init(this);
        RFIDReader.openRFID();
    }


    private void loadView(){
        frame = MainFrame.getInstance(this);
        menupanel = MenuPanel.getInstance(this);
        defaultpanel = DefaultPanel.getInstance(this);
        showpanel = ShowPanel.getInstance(this);
        stockpanel = StockPanel.getInstance(this);
        flowpanel = FlowPanel.getInstance(this);
        newpanel = NewPanel.getInstance(this);
        deletepanel = DeletePanel.getInstance(this);
        frame.add(menupanel);
        frame.add(defaultpanel);
        frame.add(showpanel);
        frame.add(stockpanel);
        frame.add(flowpanel);
        frame.add(newpanel);
        frame.add(deletepanel);
        frame.repaint();
    }

    public ActionListener MenuPanelAction = new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            if( ae.getActionCommand().equals( menupanel.getModeButtonNameLast() ) ){
                RFIDReader.closeRFID();
                frame.closeFrame();
            }else{
                if(!defaultpanel.getUser().equals("")&&!defaultpanel.getPassword().equals("") ){
                    defaultpanel.changeDisable();
                    showpanel.changeDisable();
                    stockpanel.changeDisable();
                    flowpanel.changeDisable();
                    newpanel.changeDisable();
                    deletepanel.changeDisable();

                    db.setType();
                    db.setMaker();
                    db.setProduct();

                    if(ae.getActionCommand().equals(menupanel.getModeButtonName(0) )){
                        showpanel.changeEnable();
                    }else if(ae.getActionCommand().equals( menupanel.getModeButtonName(1) )){
                        RFIDReader.setTextField(stockpanel.getRFIDTextBox());
                        stockpanel.changeEnable();
                    }else if(ae.getActionCommand().equals( menupanel.getModeButtonName(2) )){
                        RFIDReader.setTextField(flowpanel.getRFIDTextBox());
                        flowpanel.changeEnable();
                    }else if(ae.getActionCommand().equals( menupanel.getModeButtonName(3) )){
                        RFIDReader.setTextField(newpanel.getRFIDTextBox());
                        newpanel.changeEnable();
                    }else if(ae.getActionCommand().equals( menupanel.getModeButtonName(4) )){
                        deletepanel.changeEnable();
                    }
                }else{
                    showDialog("error","ログイン情報を入力してください。");
                }

            }
        }
    };

    public ActionListener DefaultPanelAction = new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            if( ae.getActionCommand().equals("login_button") ){
                String u = defaultpanel.getUser();
                String p = defaultpanel.getPassword();
                db.setLogin(u,p);
                showDialog("info","ログイン情報を記憶しました。");
            }
        }
    };

    public ActionListener StockPanelAction = new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            try{
                String rfid = stockpanel.getID();
                if( db.increaseProduct(stockpanel.getID(),stockpanel.getNumber()) ){
                    stockpanel.changeDisable();
                    db.setType();
                    db.setMaker();
                    db.setProduct();
                    stockpanel.changeEnable();
                }else{
                    stockpanel.changeDisable();
                    db.setType();
                    db.setMaker();
                    db.setProduct();
                    newpanel.changeEnable();
                    newpanel.setRFIDTextBox(rfid);
                }
            }catch(SQLException e){}
        }
    };

    public ActionListener FlowPanelAction = new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            try{
                db.decreaseProduct(flowpanel.getID());
                flowpanel.changeDisable();
                db.setType();
                db.setMaker();
                db.setProduct();
                flowpanel.changeEnable();
            }catch(SQLException e){}
        }
    };

    public ActionListener NewPanelAction = new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            try{
                if( ae.getActionCommand().equals("type_button") ){
                    db.insertType(newpanel.getTextType());
                }else if( ae.getActionCommand().equals("maker_button") ){ 
                    db.insertMaker(newpanel.getTextMaker());
                }else if( ae.getActionCommand().equals("product_button") ){
                    db.insertProduct(newpanel.getProductId(),newpanel.getProductName(),newpanel.getProductType(),newpanel.getProductMaker());
                }
                newpanel.changeDisable();
                db.setType();
                db.setMaker();
                db.setProduct();
                RFIDReader.setTextField(newpanel.getRFIDTextBox());
                newpanel.changeEnable();
            }catch(SQLException e){}
        }
    };

    public ActionListener DeletePanelAction = new ActionListener(){
        public void actionPerformed(ActionEvent ae){
            try{
                db.deleteProduct(deletepanel.getProductName());
                deletepanel.changeDisable();
                db.setType();
                db.setMaker();
                db.setProduct();
                deletepanel.changeEnable();
            }catch(SQLException e){}
        }
    };

    public void showDialog(String stat,String msg){
        switch (stat){
            case "info":
                JOptionPane.showMessageDialog(frame,msg,"Info",JOptionPane.INFORMATION_MESSAGE);
                break;
            case "error":
                JOptionPane.showMessageDialog(frame,msg,"Error",JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public static void main(String[] args){
        Controller controller = Controller.getInstance();
        controller.loadDBManager();
        controller.loadView();
        controller.loadRFIDReader();
    }

}