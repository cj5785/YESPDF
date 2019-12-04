package com.aaron.yespdf.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aaron.base.image.DefaultOption;
import com.aaron.base.image.ImageLoader;
import com.aaron.yespdf.R;
import com.aaron.yespdf.common.bean.Cover;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;

import java.util.List;

/**
 * @author Aaron aaronzzxup@gmail.com
 */
public class GroupingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EMPTY = 1;
    private static final int TYPE_ADD_COLLECTION = 2;

    private Context context;

    private GridLayoutManager layoutManager;
    private List<Cover> coverList;
    private Callback callback;
    private boolean enableAddNew = true;

    public GroupingAdapter(List<Cover> coverList, Callback callback) {
        this.coverList = coverList;
        this.callback = callback;
    }

    public GroupingAdapter(GridLayoutManager lm, List<Cover> coverList, Callback callback, boolean enableAddNew) {
        layoutManager = lm;
        this.coverList = coverList;
        this.callback = callback;
        this.enableAddNew = enableAddNew;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_EMPTY) {
            View itemView = inflater.inflate(R.layout.app_recycler_item_emptyview, parent, false);
            return new EmptyHolder(itemView);
        } else if (enableAddNew && viewType == TYPE_ADD_COLLECTION) {
            View add = inflater.inflate(CoverHolder.DEFAULT_LAYOUT, parent, false);
            CoverHolder holder = new CoverHolder(add);
            holder.tvProgress.setVisibility(View.GONE);
            holder.cb.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(v -> callback.onAddNewGroup());
            return holder;
        }
        View view = inflater.inflate(CollectionHolder.DEFAULT_LAYOUT, parent, false);
        CollectionHolder holder = new CollectionHolder(view);
        holder.itemView.setOnClickListener(v -> callback.onAddToGroup(holder.tvTitle.getText().toString()));
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof EmptyHolder) {
            EmptyHolder holder = (EmptyHolder) viewHolder;
            holder.itvEmpty.setVisibility(View.VISIBLE);
            holder.itvEmpty.setText(R.string.app_have_no_group);
            holder.itvEmpty.setIconTop(R.drawable.app_img_all);

        } else if (viewHolder instanceof CoverHolder) {
            CoverHolder holder = (CoverHolder) viewHolder;
            holder.tvTitle.setText(R.string.app_add_new_group);
            holder.ivCover.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.ivCover.setImageResource(R.drawable.app_img_add_group);
            ViewGroup.LayoutParams lp = holder.ivCover.getLayoutParams();
            lp.height = ConvertUtils.dp2px(152);
            holder.ivCover.requestLayout();
        } else {
            CollectionHolder holder = (CollectionHolder) viewHolder;
            if (!coverList.isEmpty()) {
                Cover c = coverList.get(enableAddNew ? position - 1 : position);
                List<String> coverList = c.coverList;
                int count = c.count;

                holder.tvTitle.setText(c.name);
                holder.tvCount.setText(context.getString(R.string.app_total, count));
                setVisibility(holder, count);
                if (count == 0) return;
                setCover(holder.ivCover1, coverList.get(0));
                if (count == 1) return;
                setCover(holder.ivCover2, coverList.get(1));
                if (count == 2) return;
                setCover(holder.ivCover3, coverList.get(2));
                if (count == 3) return;
                setCover(holder.ivCover4, coverList.get(3));
            }
        }
    }

    private void setVisibility(CollectionHolder holder, int count) {
        holder.ivCover1.setVisibility(count >= 1 ? View.VISIBLE : View.INVISIBLE);
        holder.ivCover2.setVisibility(count >= 2 ? View.VISIBLE : View.INVISIBLE);
        holder.ivCover3.setVisibility(count >= 3 ? View.VISIBLE : View.INVISIBLE);
        holder.ivCover4.setVisibility(count >= 4 ? View.VISIBLE : View.INVISIBLE);
    }

    private void setCover(ImageView ivCover, String path) {
        if (!StringUtils.isEmpty(path)) {
            ImageLoader.load(context, new DefaultOption.Builder(path).into(ivCover));
        } else {
            ivCover.setScaleType(ImageView.ScaleType.FIT_XY);
            ivCover.setImageResource(R.drawable.app_img_none_cover);
        }
    }

    @Override
    public int getItemCount() {
        if (coverList.isEmpty()) {
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (coverList.isEmpty()) {
                        return 3;
                    }
                    return 1;
                }
            });
            return 1;
        } else if (enableAddNew) {
            return 1 + coverList.size();
        }
        return coverList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (coverList.isEmpty()) {
            return TYPE_EMPTY;
        } else if (position == 0) {
            return TYPE_ADD_COLLECTION;
        }
        return super.getItemViewType(position);
    }

    public interface Callback {
        void onAddNewGroup();

        void onAddToGroup(String dir);
    }
}
