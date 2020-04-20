package com.lchj.mmallanroid.mvp.ui.adapter;


import android.content.Context;

import com.lchj.mmallanroid.R;
import com.lchj.mmallanroid.mvp.model.entity.Category;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


public class TopCategoryAdapter extends MultiItemTypeAdapter<Category> {
    CategoryItemDelagate categoryItemDelagate;

    public TopCategoryAdapter(Context context, List<Category> datas) {
        super(context, datas);
        categoryItemDelagate = new CategoryItemDelagate(context,datas);
        addItemViewDelegate(categoryItemDelagate);

    }

}

class CategoryItemDelagate implements ItemViewDelegate<Category> {
    List<Category> datas;
    Context context;

    public CategoryItemDelagate(Context context,List<Category> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.layout_top_category_item;
    }

    @Override
    public boolean isForViewType(Category item, int position) {
        return datas.size() > 0;
    }

    @Override
    public void convert(ViewHolder holder, Category category, int position) {
        holder.getView(R.id.mTopCategoryNameTv).setSelected(category.isChecked());
        holder.setText(R.id.mTopCategoryNameTv, category.getName());
    }
}
