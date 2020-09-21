package org.mics.lang.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;

/**
 * 对象转化
 * @author mics
 * @date 2020年6月11日
 * @version  1.0
 */
public class ObjectConverter implements Converter {
    /**
     * 注意数组需要有序
     */
    private static String[] responseObjectArray = {};

    private List<String> ignoreList = new ArrayList<>();

    public ObjectConverter() {
    }

    public ObjectConverter(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Object convert(Object value, @SuppressWarnings("rawtypes") Class target, Object context) {

        if (value == null || ignoreList.contains(getPropertyName(String.valueOf(context)))) {
            //return BeanMap.create(target).get(getPropertyName(String.valueOf(context)));
        	return null;
        } else if (Arrays.binarySearch(responseObjectArray, target.getName()) >= 0) {
            return BeanConvertUtil.convert(value, target);
        } else if (!value.getClass().equals(target) && !target.isAssignableFrom(value.getClass())) {
            return BeanMap.create(target).get(getPropertyName(String.valueOf(context)));
        }
        return value;
    }

    private static String getPropertyName(String methodName) {//setAge ---> age
        char[] newChar = new char[methodName.length() - 3];
        System.arraycopy(methodName.toCharArray(), 3, newChar, 0, methodName.length() - 3);
        newChar[0] = Character.toLowerCase(newChar[0]);
        return String.valueOf(newChar);
    }

}
