package com.acacia.recycler;

import com.acacia.sdk.AbstractTransform;
import com.acacia.sdk.AbstractTransformComposer;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.List;


@AutoService(AbstractTransformComposer.class)
public class RecyclerComposer extends AbstractTransformComposer {

    List<AbstractTransform> transforms = new ArrayList<>();


    public RecyclerComposer(){
        super();
        transforms.add(new com.acacia.recycler.Recycler());

    }

    @Override
    public List<AbstractTransform> getOrderedTransforms() {
        return transforms;
    }
}
