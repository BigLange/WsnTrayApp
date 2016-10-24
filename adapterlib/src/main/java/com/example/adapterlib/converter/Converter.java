package com.example.adapterlib.converter;

import com.example.adapterlib.holder.Holder;

/**
 * Created by Think on 2016/10/19.
 */

public interface Converter<H> {
    void convert(Holder holder,H item);
}
