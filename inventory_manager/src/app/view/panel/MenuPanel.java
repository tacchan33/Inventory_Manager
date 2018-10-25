package app.view.panel;

import javax.swing.*;
import java.awt.*;

import app.controller.Controller;
import app.system.Config;

public class MenuPanel extends JPanel{

    /* コントローラ */
    private Controller controller = null;

    /* シングルトン */
    private static MenuPanel instance = null;
    public static MenuPanel getInstance(Controller ctrl){
        if(instance==null){
            instance = new MenuPanel(ctrl);
        }
        return instance;
    }
    private MenuPanel(Controller ctrl){
        System.out.println("[START]new MenuPanel");
        controller = ctrl;
        setPanel();
        createButton();
        System.out.println("[OK]new MenuPanel");
    }

    /* フィールド */
    private JButton[] ModeButton = new JButton[6];
    private String[] ModeButtonName = {"ShowButton","StockButton","FlowButton","NewButton","DeleteButton","ExitButton"};

    /* メソッド */
    private void setPanel(){
        this.setName("MenuPanel");
        this.setLayout(null);
        this.setSize(Config.MenuPanelWidth,Config.MenuPanelHeight);
        this.setLocation(0,0);
        this.setBackground(Color.LIGHT_GRAY);
        this.setVisible(true);
    }

    private void createButton(){
        String[] ModeButtonText = {"表示モード","補充モード","開封モード","新規追加モード","削除モード","終了"};
        for(int i=0;i<ModeButton.length;i++){
            ModeButton[i] = new JButton();
            ModeButton[i].setName(ModeButtonName[i]);
            ModeButton[i].setActionCommand(ModeButtonName[i]);
            ModeButton[i].addActionListener(controller.MenuPanelAction);
            ModeButton[i].setText(ModeButtonText[i]);
            ModeButton[i].setBounds(10,(i*70)+10,Config.ModeButtonWidth,Config.ModeButtonHeight);
            ModeButton[i].setVisible(true);
            add(ModeButton[i]);
        }
    }

    public String getModeButtonName(int i){
        return ModeButton[i].getName();
    }

    public String getModeButtonNameLast(){
        int last = ModeButtonName.length;
        return ModeButton[last-1].getName();
    }

}