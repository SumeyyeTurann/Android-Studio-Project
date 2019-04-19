package application.sumeyyeturan.com.kentsimgeleriapp;

import android.graphics.Bitmap;

public class Globals {
    private static Globals instance;

    private Bitmap chooseImage;
    private Globals(){

    }
    public void setData(Bitmap chooseImage){
        this.chooseImage=chooseImage;
    }
    public Bitmap getData(){
        return this.chooseImage;
    }

    public static Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
