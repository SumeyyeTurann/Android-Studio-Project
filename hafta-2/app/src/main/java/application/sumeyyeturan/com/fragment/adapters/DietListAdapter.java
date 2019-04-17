package application.sumeyyeturan.com.fragment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import application.sumeyyeturan.com.fragment.R;
import application.sumeyyeturan.com.fragment.models.Diet;

public class DietListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;

    //modelimizi bir liste aktarmak için kullanılır
    private List<Diet> dietList;

    //yapılandırıcı oluşturuldu
    public DietListAdapter(LayoutInflater layoutInflater, List<Diet> dietList) {
        this.layoutInflater = layoutInflater;
        this.dietList = dietList;
    }

    @Override
    public int getCount() {
        return dietList.size();
    }

    @Override
    public Object getItem(int position) {
        return dietList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View dietView = layoutInflater.inflate(R.layout.activity_custom_new_adapter,null);
        //bağlantıları kuruyoruz

        TextView besinKalori = dietView.findViewById(R.id.custom_tv_kalori);
        ImageView besinResmi = dietView.findViewById(R.id.custom_imageview);
        TextView besinIsmi = dietView.findViewById(R.id.custom_tvbaslik);

        Diet diet =dietList.get(position);
        besinResmi.setImageResource(diet.getResim());
        besinIsmi.setText(diet.getBesinAdi());
        besinKalori.setText(diet.getKalori());
        return dietView;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public List<Diet> getDietList() {
        return dietList;
    }

    public void setDietList(List<Diet> dietList) {
        this.dietList = dietList;
    }


}
