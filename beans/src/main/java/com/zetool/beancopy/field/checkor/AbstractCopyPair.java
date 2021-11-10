package com.zetool.beancopy.field.checkor;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.field.handler.FieldPairBuilderFactory;
import com.zetool.beancopy.field.ClassHelper;
import com.zetool.beancopy.field.FieldHelper;

import java.util.List;
import java.util.Map;

/**
 * 映射对 抽象类，实现通用的方法，定义通用的字段
 * @param <S>
 * @param <T>
 * @author loki
 */
public abstract class AbstractCopyPair<S, T> implements CopyPair<S, T> {
    /**
     * 源
     */
    protected ClassHelper<S> sourceHelper;

    /**
     * 目标
     */
    protected ClassHelper<T> targetHelper;

    @Override
    public Map<String, FieldHelper> getSourceFieldMap() {
        return sourceHelper.getFieldContexts();
    }

    @Override
    public Map<String, FieldHelper> getTargetFieldMap(){
        return targetHelper.getFieldContexts();
    }

    @Override
    public List<FieldPair> getFieldContextPairList() {
        return FieldPairBuilderFactory.getBuilder(CopyFrom.MirrorType.DEFAULT).getFieldContextPairs(this);
    }
}
