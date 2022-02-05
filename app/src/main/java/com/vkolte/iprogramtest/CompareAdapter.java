package com.vkolte.iprogramtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CompareAdapter extends RecyclerView.Adapter<CompareAdapter.ViewHolder>{

    private static final String TAG = "CompareAdapter";
    private Context context;
    private ArrayList<CompareModel> list;
    public DBHelper mydb ;

    public CompareAdapter(Context context, ArrayList<CompareModel> list) {
        this.context = context;
        this.list = list;

//        mydb.dropTableIfExist();
        mydb = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View convertView = layoutInflater.inflate(R.layout.image_compare_view, parent, false);
            ViewHolder compareHolder = new ViewHolder(convertView);
            return compareHolder;

        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{

            CompareModel compareModel = list.get(position);

            try{
                if(compareModel.getThumbnailUrl() != null){
                    try {
                        Picasso.get().load(compareModel.getThumbnailUrl()).into(holder.image);

//                        Log.e(TAG, "onBindViewHolder: " + compareModel.getThumbnailUrl());
                    } catch (Exception e) {
                        Log.e(TAG, "onBindViewHolder: Error "+e.toString() );
                    }
                }

                try {
                    holder.photoId.setText("" + compareModel.getId());
                    holder.photoURL.setText(compareModel.getUrl());
                    holder.photoTitle.setText(compareModel.getTitle());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    holder.btnCompare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (compareModel.isCompare()) {
                                holder.btnCompare.setText("Compare");
                                compareModel.setCompare(false);
                                mydb.deleteRecord("Photo "+compareModel.getId());
                            }else{
                                holder.btnCompare.setText("Remove");
                                compareModel.setCompare(true);
                                mydb.insertRecord("Photo "+compareModel.getId(),""+compareModel.getId(),
                                        compareModel.getUrl(),compareModel.getTitle());
                            }

                            Log.e(TAG, mydb.getTableAsString() );
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView photoId, photoTitle, photoURL;
        ImageView image;
        Button btnCompare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            photoId = itemView.findViewById(R.id.photoId);
            photoTitle = itemView.findViewById(R.id.title);
            photoURL = itemView.findViewById(R.id.photoUrl);

            image = itemView.findViewById(R.id.image);

            btnCompare = itemView.findViewById(R.id.btnCompare);
        }
    }

}
