package application.sumeyyeturan.com.instagramclonefirebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import application.sumeyyeturan.com.instagramclonefirebase.R;

public class PostClass extends ArrayAdapter<String> {

    private final ArrayList<String> UserEmail;
    private final ArrayList<String> UserComment;
    private final ArrayList<String> userImage;
    private final Activity context;


    public PostClass(ArrayList<String> userEmail, ArrayList<String> userComment, ArrayList<String> userImage, Activity context) {

        super(context, R.layout.custom_view, userEmail);

        UserEmail = userEmail;
        UserComment = userComment;
        this.userImage = userImage;
        this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        //customviewı layoutıflater kullanarak bir değişkene atıyoruz
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true);

        TextView userEmailText = customView.findViewById(R.id.UserEmailTextView);
        TextView commentText = convertView.findViewById(R.id.commentTextView);
        ImageView imageView = convertView.findViewById(R.id.imageViewCustomView);

        userEmailText.setText(UserEmail.get(position));
        commentText.setText(UserComment.get(position));
        Picasso.get().load(userImage.get(position)).into(imageView);


        return customView;
    }
}
