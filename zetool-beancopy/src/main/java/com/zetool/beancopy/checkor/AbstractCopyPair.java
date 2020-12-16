package com.zetool.beancopy.checkor;

import com.zetool.beancopy.annotation.CopyFrom;
import com.zetool.beancopy.handler.FieldContentPairBuilderFactory;
import com.zetool.beancopy.helper.ClassHelper;
import com.zetool.beancopy.helper.FieldContent;

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
    public Map<String, FieldContent> getSourceFieldMap() {
        return sourceHelper.getFieldContexts();
    }

    @Override
    public Map<String, FieldContent> getTargetFieldMap(){
        return targetHelper.getFieldContexts();
    }

    @Override
    public List<FieldContentPair> getFieldContextPairList() {
        return (List<FieldContentPair>) FieldContentPairBuilderFactory.getBuilder(CopyFrom.MirrorType.DEFAULT).getFieldContextPairs(this);
    }
}
