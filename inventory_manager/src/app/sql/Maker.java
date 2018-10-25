package app.sql;

public class Maker{

    /* フィールド */
    private static int row = 0;
    private static int[] id = null;
    public static String[] name = null;

    public static void setRow(int r){
        row = r;
        id = new int[r];
        name = new String[r];
    }

    public static void setData(int r,int i,String n){
        id[r] = i;
        name[r] = n;
    }

    public static int getRow(){ return row; }

    public static int getId(int r){ return id[r]; }

    public static String getName(int r){ return name[r]; }

    public static boolean isOverlapName(String arg){
        boolean r = false;
        for(int i=0;i<row;i++){
            if( arg.equals(name[i]) ){
                r = true;
            }
        }
        return r;
    }

}