package com.example.adapterlib.converter;

import com.example.adapterlib.holder.Holder;
import com.example.adapterlib.holder.RViewHolder;

/**
 * Created by Think on 2016/10/19.
 */

public interface RVConverter<H> {
    void convert(Holder holder, H item,int position);

}
