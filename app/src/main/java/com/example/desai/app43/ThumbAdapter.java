package com.example.desai.app43;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThumbAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Photos> data;

    public ThumbAdapter(Context context, ArrayList data){
        super(context, R.layout.gridview, data);
        this.context = context;
        this.data = data;
    }



    @Override
    public View getView(int position, View currentView, ViewGroup parent){

        if (currentView == null){
            LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = (View) i.inflate(R.layout.gridview, parent, false);
        }

        ImageView iv = (ImageView) currentView.findViewById(R.id.image);
        TextView tv = (TextView) currentView.findViewById(R.id.caption);

        Photos photo = (Photos) data.get(position);
        Log.d("BYTE",photo.getPhotoFile().getAbsolutePath());
//       Bitmap bitmap = BitmapFactory.decodeFile(photo.getPhotoFile().getAbsolutePath());
//
//

        iv.setImageBitmap(photo.getImage());
        tv.setText(photo.getCaption());

        return currentView;

    }

}
