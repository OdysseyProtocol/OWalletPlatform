package com.coinwallet.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
public class ObjectUtils<T> {


    public String toString(Object object) {

        StringBuffer str = new StringBuffer("");

        T pojo = (T) object;

        try {
            Class clazz = pojo.getClass();
            Field[] fields = pojo.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object value = getMethod.invoke(pojo);

                if (value == null) {
                    continue;
                }
                if (value instanceof BigDecimal) {
                    str.append(field.getName() + " : " + value + " " + '\r');

                } else if (value instanceof Double) {

                    str.append(field.getName() + " : " + value + " " + '\r');

                } else {

                    str.append(field.getType() + " " + field.getName() + " : " + value + " " + '\r');

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
