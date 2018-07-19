package com.geesanke.comparators.initialsChinaComparator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class InitialsChinaComparator implements Comparator<Object> {

    public static final int DESC = -1 ; 
    public static final int ASC = 1 ; 
    
    private int order ;
    
    public InitialsChinaComparator() {
        this.order = ASC;
    }

    public InitialsChinaComparator(int order) {
        this.order = order;
    }


    @Override
    public int compare(Object o1, Object o2) {
        try {
            Field[] o1Fields = getFields(o1);
            String source = "";
            String target = "";
            for (Field o1Field : o1Fields) {
                CompareString compareString = o1Field.getAnnotation(CompareString.class);
                if (compareString != null) {
                    source = (String) getValueByFieldNameByGetMethod(o1, o1Field.getName());
                    break;
                }
            }
            Field[] o2Fields = getFields(o2);
            for (Field o2Field : o2Fields) {
                CompareString compareString = o2Field.getAnnotation(CompareString.class);
                if (compareString != null) {
                    target = (String) getValueByFieldNameByGetMethod(o2, o2Field.getName());
                    break;
                }
            }
            
            Collator collator = Collator.getInstance(Locale.CHINA);
            return this.order*collator.compare(source, target);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Field[] getFields(Object obj) {
        Field[] result = new Field[] {};
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            result = superClass.getDeclaredFields();
        }
        return result;
    }

    public Object getValueByFieldNameByGetMethod(Object obj, String fieldName) throws Exception {
        String methodName = "get" + firstCharUpCase(fieldName);
        Method method = obj.getClass().getDeclaredMethod(methodName);
        if (method == null) {
            throw new RuntimeException(methodName + "查找失败");
        }
        return method.invoke(obj);
    }

    public String firstCharUpCase(String str) {
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
    
    
    
}
