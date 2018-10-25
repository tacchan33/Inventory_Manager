package app.rfid;

import javax.swing.JTextField;

import com.phidget22.*;

import app.controller.*;

public class RFIDReader{

    private static RFID rfid = null;
    private static Controller controller = null;
    private static JTextField textbox = null;

    public static void init(Controller ctrl){
        System.out.println("[START]RFID");
        controller = ctrl;

        try{
            //Log.enable(LogLevel.INFO,null);
            rfid = new RFID();

            if(rfid!=null){            
                rfid.addAttachListener(new AttachListener() {
                    public void onAttach(AttachEvent ae) {
                        RFID phid = (RFID) ae.getSource();
                        try {
                            if(phid.getDeviceClass() != DeviceClass.VINT){
                                controller.showDialog("info","Phidget RFID\n"+"channel:"+phid.getChannel()+"\n"+"on device:"+phid.getDeviceSerialNumber()+"\n"+"接続されました");
                            }
                            else{
                                controller.showDialog("info","Phidget RFID\n"+"channel:"+phid.getChannel()+"\n"+"on device:"+phid.getDeviceSerialNumber()+"\n"+"hub port:"+phid.getHubPort()+"\n接続されました");
                            }
                        } catch (PhidgetException ex) {
                            System.out.println(ex.getDescription());
                        }
                    }
                });
        
                rfid.addDetachListener(new DetachListener() {
                    public void onDetach(DetachEvent de) {
                        RFID phid = (RFID) de.getSource();
                        try {
                            if (phid.getDeviceClass() != DeviceClass.VINT) {
                                controller.showDialog("info","Phidget RFID\n"+"channel:"+phid.getChannel()+"\n"+"on device:"+phid.getDeviceSerialNumber()+"\n"+"切断されました");
                            } else {
                                controller.showDialog("info","Phidget RFID\n"+"channel:"+phid.getChannel()+"\n"+"on device:"+phid.getDeviceSerialNumber()+"\n"+"hub port:"+phid.getHubPort()+"\n切断されました");
                            }
                        } catch (PhidgetException ex) {
                            System.out.println(ex.getDescription());
                        }
                    }
                });
        
                rfid.addErrorListener(new ErrorListener() {
                    public void onError(ErrorEvent ee) {
                        System.out.println("Error: " + ee.getDescription());
                    }
                });
        
                rfid.addTagListener(new RFIDTagListener() {
                    public void onTag(RFIDTagEvent e) {
                        System.out.println("Tag read: " + e.getTag());
                        textbox.setText(e.getTag());
                    }
                });
        
                rfid.addTagLostListener(new RFIDTagLostListener() {
                    public void onTagLost(RFIDTagLostEvent e) {
                        System.out.println("Tag lost: " + e.getTag());
                    }
                });

                System.out.println("[OK]RFID");
            }            
        }catch(PhidgetException pe){
        }catch(NoClassDefFoundError ex){
            controller.showDialog("error","phidgetドライバ");
        }
    }

    public static void openRFID(){
        try{
            if(rfid!=null){
                rfid.open();                                
            }
        }catch(PhidgetException pe){}
    }

    public static void closeRFID(){
        try{
            if(rfid!=null){
                rfid.close();                                
            }
        }catch(PhidgetException pe){}
    }

    public static void setTextField(JTextField t){
        textbox = t;
    }
    
}