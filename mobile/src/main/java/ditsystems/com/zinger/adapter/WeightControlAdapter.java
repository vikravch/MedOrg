package ditsystems.com.zinger.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ditsystems.com.zinger.R;
import ditsystems.com.zinger.util.Const;

/**
 * Created by Android on 06.05.2016.
 */
public class WeightControlAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Cursor mCursor;

    public WeightControlAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_weight_control,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;

        Log.d(Const.TAG_LOG_PROJECT,"Count - "+mCursor.getCount());

        mCursor.moveToPosition(position);
        myHolder.tvNameDiet.setText(mCursor.getString(mCursor.getColumnIndex(Const.FIELD_NAME_DIETS)));
        myHolder.tvAboutDiet.setText(mCursor.getString(mCursor.getColumnIndex(Const.FIELD_ABOUT_DIETS)));
        myHolder.tvDescription.setText(mCursor.getString(mCursor.getColumnIndex(Const.FIELD_DESCRIPTION_DIETS)));

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNameDiet;
        public TextView tvAboutDiet;
        public TextView tvDescription;

        public MyViewHolder(View itemView) {

            super(itemView);
            tvNameDiet = (TextView) itemView.findViewById(R.id.tvNameDietRecycler);
            tvAboutDiet = (TextView) itemView.findViewById(R.id.tvAboutDietRecycler);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescriptionDietRecycler);

        }
    }
}
