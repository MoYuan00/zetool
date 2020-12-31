package com.zetool.beancopy.field;

import com.zetool.beancopy.field.cloner.TypeCloner;
import com.zetool.beancopy.field.cloner.TypeClonerFactory;

import java.lang.reflect.Field;

/**
 * 内部使用TypeCloner拷贝字段值
 * @see TypeCloner
 * @see TypeClonerFactory
 */
public class TypeCloneFieldHelper extends SimpleFieldHelper {

    public TypeCloneFieldHelper(Field field) {
        super(field);
    }

    public TypeCloneFieldHelper(Field field, Object obj) {
        super(field, obj);
    }

    @Override
    public Object cloneValue() {
        return TypeClonerFactory.getTypeCloner(this.field.getType()).cloneValue(this.getValue());
    }
}
