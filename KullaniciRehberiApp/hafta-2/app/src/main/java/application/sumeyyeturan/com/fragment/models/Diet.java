package application.sumeyyeturan.com.fragment.models;

public class Diet {
    String kalori;
    String besinAdi;
    int resim; //resmin id si int döneceği için resimler int tanımlanır

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }

    public String getBesinAdi() {
        return besinAdi;
    }

    public void setBesinAdi(String besinAdi) {
        this.besinAdi = besinAdi;
    }

    public int getResim() {
        return resim;
    }

    public void setResim(int resim) {
        this.resim = resim;
    }



    public Diet(int resim, String besinAdi, String kalori){
        this.besinAdi=besinAdi;
        this.kalori=kalori;
        this.resim=resim;
    }


}
