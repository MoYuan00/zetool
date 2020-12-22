package com.zetool.beancopy.checkor;

import com.zetool.beancopy.helper.ClassHelper;

/**
 * 同类间的映射关系
 * @param <S>
 * @param <T>
 * @author loki
 */
public final class EqualsCopyPair<S, T> extends AbstractCopyPair<S, T> {

    public EqualsCopyPair(ClassHelper<S> sourceHelper, ClassHelper<T> targetHelper) {
        super();
        this.sourceHelper = sourceHelper;
        this.targetHelper = targetHelper;
    }

    @Override
    public T cloneSourceToTarget(S sourceObj) {
        sourceHelper.bindObject(sourceObj);
        try {
            targetHelper.bindThisClassInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException("can not create a new instance by default constructor!");
        }
        getFieldContextPairList().forEach(FieldPair::cloneSourceToTarget);
        return targetHelper.getBindObject();
    }

}
