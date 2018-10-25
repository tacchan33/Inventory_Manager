package app.view.panel;

import javax.swing.*;
import java.awt.*;

import com.phidget22.*;

import app.controller.Controller;
import app.sql.*;
import app.system.Config;

public class NewPanel extends JPanel{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static NewPanel instance = null;
    public static NewPanel getInstance(Controller ctrl){
        if(instance==null){
            instance = new NewPanel(ctrl);
        }
        return instance;
    }
    private NewPanel(Controller ctrl){
        System.out.println("[START]new NewPanel");
        controller = ctrl;
        setPanel();
        createTypeObject();
        createMakerObject();
        createProductObject();
        addObject();
        System.out.println("[OK]new NewPanel");
    }

    /* フィールド */
    private JButton type_button = new JButton("種別登録");
    private JLabel type_label = new JLabel("種別名");
    private JTextField typeName = new JTextField();

    private JButton maker_button = new JButton("メーカ登録");
    private JLabel maker_label = new JLabel("メーカ名");
    private JTextField makerName = new JTextField();

    private JButton product_button = new JButton("商品登録");
    private JLabel product_label = new JLabel("商品名");
    private JTextField productName = new JTextField();
    private JLabel product_label2 = new JLabel("RFID");
    private JTextField productId = new JTextField();
    private JComboBox<String> productType = new JComboBox<String>();
    private JComboBox<String> productMaker = new JComboBox<String>();

    /* private メソッド */
    private void setPanel(){
        this.setName("NewPanel");
        this.setLayout(null);
        this.setSize(Config.MainPanelWidth,Config.MainPanelHeight);
        this.setLocation(Config.MenuPanelWidth,0);
        this.setVisible(false);

        JLabel title = new JLabel();
        title.setText("新規追加モード");
        title.setSize(200,40);
        title.setLocation(5,0);
        title.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        title.setVisible(true);
        this.add(title);
    }

    private void createTypeObject(){
        type_button.setBounds(10,50,200,40);
        type_button.setActionCommand("type_button");
        type_button.addActionListener(controller.NewPanelAction);
        type_label.setBounds(10,100,200,40);
        typeName.setBounds(10,140,200,30);
    }

    private void createMakerObject(){
        maker_button.setBounds(250,50,200,40);
        maker_button.setActionCommand("maker_button");
        maker_button.addActionListener(controller.NewPanelAction);
        maker_label.setBounds(250,100,200,40);
        makerName.setBounds(250,140,200,30);
    }

    private void createProductObject(){
        product_button.setBounds(490,50,200,40);
        product_button.setActionCommand("product_button");
        product_button.addActionListener(controller.NewPanelAction);
        product_label.setBounds(490,100,200,40);
        productName.setBounds(490,140,200,30);
        product_label2.setBounds(490,200,200,40);
        productId.setBounds(490,240,200,30);
        productType.setBounds(490,280,200,30);
        productMaker.setBounds(490,320,200,30);
        for(int i=0;i<Type.getRow();i++){
            productType.addItem(Type.getName(i));
        }
        for(int i=0;i<Maker.getRow();i++){
            productMaker.addItem(Maker.getName(i));
        }
        
    }

    private void addObject(){
        add(type_button);
        add(type_label);
        add(typeName);
        add(maker_button);
        add(maker_label);
        add(makerName);
        add(product_button);
        add(product_label);
        add(product_label2);
        add(productId);
        add(productName);
        add(productType);
        add(productMaker);   
    }

    /* publicメソッド */
    public void resetText(){
        typeName.setText("");
        makerName.setText("");
        productId.setText("");
        productName.setText("");
    }

    public void changeEnable(){
        for(int i=0;i<Type.getRow();i++){//コンボボックス　項目再生成
            productType.addItem(Type.getName(i));
        }
        for(int i=0;i<Maker.getRow();i++){//コンボボックス　項目再生成
            productMaker.addItem(Maker.getName(i));
        }
        setEnabled(true);
        setVisible(true);
        System.out.println("新規追加モード");
    }

    public void changeDisable(){
        setVisible(false);
        setEnabled(false);
        resetText();//テキストフィールド初期化
        productType.removeAllItems();//コンボボックス初期化
        productMaker.removeAllItems();//コンボボックス初期化
    }

    public String getTextType(){
        return typeName.getText();
    }

    public String getTextMaker(){
        return makerName.getText();
    }

    public String getProductId(){
        return productId.getText();
    }
    public String getProductName(){
        return productName.getText();
    }
    public int getProductType(){
        return productType.getSelectedIndex()+1;
    }
    public int getProductMaker(){
        return productMaker.getSelectedIndex()+1;
    }

    public JTextField getRFIDTextBox(){
        return productId;
    }

    public void setRFIDTextBox(String id){
        productId.setText(id);
    }

}