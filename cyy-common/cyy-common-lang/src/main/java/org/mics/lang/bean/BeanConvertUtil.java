package org.mics.lang.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.mics.lang.exception.CopyException;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 数据对象转化类
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class BeanConvertUtil {
    /**
     * 锁
     */
    private static Lock INIT_LOCK = new ReentrantLock();

    /**
     * BeanCopier的缓存
     */
    private static ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 初始化 BeanCopier
     *
     * @param source s
     * @param target t
     * @return BeanCopier
     */
    private static BeanCopier initCopier(Class<?> source, Class<?> target) {
        INIT_LOCK.lock();
        BeanCopier find = BEAN_COPIER_CACHE.get(source.getName() + "_" + target.getName());
        if (find != null) {
            INIT_LOCK.unlock();
            return find;
        }
        BeanCopier beanCopier = BeanCopier.create(source, target, true);
        BEAN_COPIER_CACHE.put(source.getName() + "_" + target.getName(), beanCopier);
        INIT_LOCK.unlock();
        return beanCopier;
    }

    /**
     * 获取BeanCopier
     *
     * @param source s
     * @param target t
     * @return BeanCopier
     */
    private static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
        BeanCopier beanCopier = BEAN_COPIER_CACHE.get(source.getClass().getName() + "_" + target.getName());
        if (beanCopier != null) {
            return beanCopier;
        }
        return initCopier(source, target);
    }

    /**
     * 类型转换（浅复制，字段名&类型相同则被复制）
     */
    public static <T> T convert(Object source, Class<T> targetClass, String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        List<String> ignoreList = Arrays.asList(ignoreProperties);
        BeanCopier beanCopier = getBeanCopier(source.getClass(), targetClass);
        T target;
        try {
            target = targetClass.newInstance();

            beanCopier.copy(source, target, new ObjectConverter(ignoreList));

        } catch (Exception e) {
            throw new CopyException("对象拷贝失败" + source + "_" + targetClass);
        }
        return target;
    }

    /**
     * Pojo 类型转换List（浅复制，字段名&类型相同则被复制）
     */
    public static <E> List<E> convertList(Iterable<?> source, Class<E> targetClass, String... ignoreProperties) {
        List<E> result = new ArrayList<>();
        if (source == null) {
            return null;
        }
        try {
            for (Object aSource : source) {
                result.add(convert(aSource, targetClass, ignoreProperties));
            }

            return result;
        } catch (Exception e) {
            throw new CopyException("对象拷贝失败" + source + "_" + targetClass);
        }
    }

}
