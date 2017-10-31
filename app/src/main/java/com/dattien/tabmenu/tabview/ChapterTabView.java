package com.dattien.tabmenu.tabview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.dattien.tabmenu.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by FRAMGIA\bui.tien.dat on 31/08/2017.
 */
// >=== #123455
public class ChapterTabView extends RelativeLayout implements CircleLayoutManager.OnItemSelectedListener {

    public static final float RADIUS_RATIO = 2f;
    public static final int MAX_ITEM = 5000000;
    public static final int ITEM_COUNT = 19;
    public static final float SCALE = 0.9f;
    private static final Integer IMAGES_CHAPTER[] =
            {
                    R.drawable.one,
                    R.drawable.two,
                    R.drawable.three,
                    R.drawable.four,
                    R.drawable.five,
                    R.drawable.six,
                    R.drawable.seven,
                    R.drawable.eight,
                    R.drawable.nine,
                    R.drawable.ten,
                    R.drawable.one,
                    R.drawable.two,
                    R.drawable.three,
                    R.drawable.four,
                    R.drawable.five,
                    R.drawable.six,
                    R.drawable.seven,
                    R.drawable.eight,
                    R.drawable.nine
            };
    private ViewPager mViewPager;
    private FlingRecycleView recyclerViewManga;
    private PedestalTabView mPedestalViewLeft, mPedestalViewRight;
    private CircleLayoutManager mCircleLayoutManager;
    private int positionOffset = 0;
    private Interpolator interpolator;

    public ChapterTabView(Context context) {
        super(context);
        initView(context);
    }

    public ChapterTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void test(){
        recyclerViewManga.getAdapter().notifyDataSetChanged();
        recyclerViewManga.smoothScrollBy(1, 0);
        recyclerViewManga.smoothScrollBy(-1, 0);
    }

    public ChapterTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (positionOffset < position) {
                        scrollToLeftAndRight(true);
                    } else if (positionOffset > position) {
                        scrollToLeftAndRight(false);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private void initView(Context context) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.chapter_tab_view, this, true);
        recyclerViewManga = (FlingRecycleView) view.findViewById(R.id.rcv_tabbar_manga);
        mPedestalViewLeft = (PedestalTabView) view.findViewById(R.id.pedestal_left);
        mPedestalViewRight = (PedestalTabView) view.findViewById(R.id.pedestal_right);
        ChapterTabAdapter adapter = new ChapterTabAdapter(Arrays.asList(IMAGES_CHAPTER));
        mCircleLayoutManager = new CircleLayoutManager(CircleLayoutManager.HORIZONTAL);
        mCircleLayoutManager.attach(recyclerViewManga, (int) Math.pow(ITEM_COUNT, 5));
        mCircleLayoutManager.setItemTransformer(new ScaleTransformer());
        mCircleLayoutManager.setOnItemSelectedListener(this);
        recyclerViewManga.setAdapter(adapter);
        Log.e("DAT", recyclerViewManga.getLayoutParams().height +"????????");
        recyclerViewManga.setL(mCircleLayoutManager);
        recyclerViewManga.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mPedestalViewLeft.smoothScrollBy(dx, 0);
                mPedestalViewRight.smoothScrollBy(dx, 0);
            }
        });

        recyclerViewManga.smoothScrollBy(1, 0);
        recyclerViewManga.smoothScrollBy(-1, 0);



        interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float t) {
                t = Math.abs(t - 1.0f);
                return (float) (1.0f - Math.pow(t, 2));
            }
        };

    }

    @Override
    public void onItemSelected(RecyclerView recyclerView, View item, final int position) {
        positionOffset = position;
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position, false);
        }
    }


    private void scrollToLeftAndRight(boolean isRight) {
        int width = recyclerViewManga.getWidth();
        for (int i = 0; i < recyclerViewManga.getChildCount(); i++) {
            float dx = recyclerViewManga.getChildAt(i).getX();
            float widthChild = recyclerViewManga.getChildAt(i).getWidth();
            if (dx < width / 2 && dx + widthChild > width / 2) {
                if (i > 0 && isRight) {
                    scrollCenter(recyclerViewManga.getChildAt(i + 1));
                } else if (i < recyclerViewManga.getChildCount() && !isRight) {
                    scrollCenter(recyclerViewManga.getChildAt(i - 1));
                }
            }
        }
    }

    private void scrollCenter(View view) {
        int widthView = view.getWidth();
        int center = recyclerViewManga.getWidth() / 2;
        float X = view.getX();
        float scale = widthView - widthView * SCALE;
        if (X > center + widthView / 2) {
            recyclerViewManga.smoothScrollBy((int) -(center - X - scale / 2), 0);
        } else {
            if (X < center - widthView / 2 - scale / 2) {
                recyclerViewManga.smoothScrollBy((int) -(center - (X + widthView) + scale / 2), 0);
            }
        }
    }

    public void scrollToPosition(int position) {
        int positionCenter = getPositionCenter();
        if (positionCenter + 2 > recyclerViewManga.getChildCount()) {
            return;
        }
        View viewStart = recyclerViewManga.getChildAt(positionCenter + 1);
        View viewEnd = recyclerViewManga.getChildAt(positionCenter + 2);
        float distance = (viewEnd.getX() - viewStart.getX());
        int width = viewStart.getWidth();
        int center = recyclerViewManga.getWidth() / 2;
        float dx = viewStart.getX() + ((position - 1) * distance);
        float scale = width - width * SCALE;
        recyclerViewManga.smoothScrollBy((int) -(center - dx - scale / 2), 0, interpolator);
    }

    private int getPositionCenter() {
        int center = recyclerViewManga.getWidth() / 2;
        for (int i = 0; i < recyclerViewManga.getChildCount(); i++) {
            if (recyclerViewManga.getChildAt(i).getX() < center
                    && recyclerViewManga.getChildAt(i).getX() + recyclerViewManga.getChildAt(i).getWidth() > center) {
                return i;
            }
        }
        return 0;
    }

    class ChapterTabAdapter extends RecyclerView.Adapter<ChapterTabAdapter.ViewHolder> {

        private List<Integer> mData;

        ChapterTabAdapter(List<Integer> data) {
            this.mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_tab, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(mData.get(position % IMAGES_CHAPTER.length));
        }

        @Override
        public int getItemCount() {
            return MAX_ITEM;
        }
        private float YY =0 ;
        protected class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
            private ImageView imageView;

            public ViewHolder(final View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
                itemView.setOnClickListener(this);
                itemView.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        FlingRecycleView.isShow = false;
                        if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
                            //itemView.performClick();
                        }


                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                YY = motionEvent.getY();
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                YY = 0;
                                break;
                            case MotionEvent.ACTION_UP:
                                YY = 0;
                                break;
                            case MotionEvent.ACTION_MOVE:

                                if(motionEvent.getY() > 0 && YY > motionEvent.getY()) {
                                    FlingRecycleView.isScroll = true;
                                    itemView.performClick();
                                    itemView.clearFocus();
                                    itemView.stopNestedScroll();
                                    return false;
                                }
                                break;
                        }

                        return false;
                    }
                });
            }

            void bind(int drawable) {
                Glide.with(getContext())
                        .load(drawable).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontTransform()
                        .into(imageView);
            }

            @Override
            public void onClick(View v) {
                scrollCenter(v);
            }
        }

    }
}
// <=== #123455
