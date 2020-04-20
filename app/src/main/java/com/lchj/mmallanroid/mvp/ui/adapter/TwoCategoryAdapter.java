package com.lchj.mmallanroid.mvp.ui.adapter;


import android.content.Context;

import com.lchj.mmallanroid.R;
import com.lchj.mmallanroid.mvp.model.entity.Category;
import com.lchj.mmallanroid.mvp.model.entity.Product;
import com.lchj.mmallanroid.mvp.utils.GlideUtils;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


public class TwoCategoryAdapter extends MultiItemTypeAdapter<Product> {
    TwoCategoryItemDelagate twoCategoryItemDelagate;

    public TwoCategoryAdapter(Context context, List<Product> datas) {
        super(context, datas);
        twoCategoryItemDelagate = new TwoCategoryItemDelagate(context,datas);
        addItemViewDelegate(twoCategoryItemDelagate);

    }

}

class TwoCategoryItemDelagate implements ItemViewDelegate<Product> {
    List<Product> datas;
    Context context;

    public TwoCategoryItemDelagate(Context context,List<Product> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.layout_second_category_item;
    }

    @Override
    public boolean isForViewType(Product item, int position) {
        return datas.size() > 0;
    }

    @Override
    public void convert(ViewHolder holder, Product product, int position) {
        holder.setText(R.id.mSecondCategoryNameTv, product.getName());
        GlideUtils.loadUrlImage(context, product.getImageHost()+product.getMainImage(),  holder.getView(R.id.mCategoryIconIv));
    }
}
