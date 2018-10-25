package app.view.panel;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

import com.phidget22.*;

import app.controller.Controller;
import app.sql.*;
import app.system.Config;

public class ShowPanel extends JPanel{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static ShowPanel instance = null;
    public static ShowPanel getInstance(Controller ctrl){
        if(instance==null){
            instance = new ShowPanel(ctrl);
        }
        return instance;
    }
    private ShowPanel(Controller ctrl){
        System.out.println("[START]new ShowPanel");
        controller = ctrl;
        setPanel();
        createObject();
        addObject();
        System.out.println("[OK]new ShowPanel");
    }

    /* フィールド */
    private String[] column = {"種別","メーカ","商品名","在庫数","最終開封日"};
    int row =Product.getRow();
    String[][] data = new String[row][5];
    private DefaultTableModel tablemodel = new DefaultTableModel(column,0);
    private JTable table = new JTable(tablemodel);
    private JTableHeader header = table.getTableHeader();
    private JScrollPane scroll = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    /* private メソッド */
    private void setPanel(){
        this.setName("ShowPanel");
        this.setLayout(null);
        this.setSize(Config.MainPanelWidth,Config.MainPanelHeight);
        this.setLocation(Config.MenuPanelWidth,0);
        this.setVisible(false);

        JLabel title = new JLabel();
        title.setText("表示モード");
        title.setSize(200,40);
        title.setLocation(5,0);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title.setVisible(true);
        this.add(title);
    }

    private void createObject(){        
        header.setBounds(10,50,710,50);
        header.setVisible(true);
        table.setBounds(10,100,710,400);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setVisible(true);
        scroll.setBounds(720,50,20,450);
        scroll.setVisible(true);
    }


    private void addObject(){
        add(table);
        add(header);
        add(scroll);
    }

    /* publicメソッド */
    public void resetText(){
        for(int i=tablemodel.getRowCount()-1;0<=i;i--){
            tablemodel.removeRow(0);
        }
    }

    public void changeEnable(){
        row = Product.getRow();
        data = new String[row][5];
        System.out.println("表示モード");
        for(int i=0;i<Product.getRow();i++){
            data[i][0] = Type.getName(Product.getType(i)-1);
            data[i][1] = Maker.getName(Product.getMaker(i)-1);
            data[i][2] = Product.getName(i);
            data[i][3] = Integer.toString(Product.getStock(i));
            data[i][4] = Product.getLastuse(i);
        }
        for(int i=0;i<Product.getRow();i++){
            tablemodel.addRow(data[i]);
        }
        setEnabled(true);
        setVisible(true);
    }

    public void changeDisable(){
        setVisible(false);
        setEnabled(false);
        resetText();//テキストフィールド初期化
    }


}