package com.dattien.tabmenu.tabview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dattien.tabmenu.R;


/**
 * Created by FRAMGIA\bui.tien.dat on 25/08/2017.
 */
// >=== #123455
public class PedestalTabView extends LinearLayout {

    RecyclerView rcvPedestal;

    public PedestalTabView(Context context) {
        super(context);
        initView(context);
    }

    public PedestalTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PedestalTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void smoothScrollBy(int dx, int dy) {
        rcvPedestal.smoothScrollBy(dx, dy);
    }

    public void smoothScrollToPosition(int position) {
        rcvPedestal.smoothScrollToPosition(position);
    }

    private void initView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.pedestal_tab_view, this, true);
        rcvPedestal = (RecyclerView) view.findViewById(R.id.rcv_pedestal);
        LinearLayoutManager layoutManager
            = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rcvPedestal.setLayoutManager(layoutManager);
        rcvPedestal.setAdapter(new PedestalAdapter());
        layoutManager.scrollToPosition(Integer.MAX_VALUE / 2);
    }

    class PedestalAdapter extends RecyclerView.Adapter<PedestalAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedestal_view, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }

        protected class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
// <=== #123455
