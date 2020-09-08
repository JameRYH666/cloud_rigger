package com.jeerigger.frame.util;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Title : List 工具类
 * @Author : JiaS
 * @Date : 2019/9/24 11:00
 */
public class ListTools {
    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    /**
     * 提取实体集合中的一个字段
     * */
    public static<T,R> List<R> entityListToFieldList(List<T> entityList, Function<T,R> mapper) {
        if (isEmpty(entityList)){
            return new ArrayList<>();
        }
        return entityList.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 排序isDesc
     * */
    public static<T,R extends Comparable<? super R>> void entityListToSort(List<T> entityList, Function<T,? extends R> mapper, boolean isDesc) {
        if (isDesc) {
            entityList.sort(Comparator.comparing(mapper).reversed());
        }else {
            entityList.sort(Comparator.comparing(mapper));
        }
    }

    /**
     * 集合去重
     */
    public static<T> List<T> listToDistinct(List<T> list){
        if(isEmpty(list)){
            return new ArrayList<>();
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 实体集合根据属性去重
     */
    public static<T> List<T> entityListToDistinct(List<T> entityList,Function<? super T, Object> mapper){
        if(isEmpty(entityList)){
            return new ArrayList<>();
        }
        return entityList.stream().filter(distinctByKey(mapper)).collect(Collectors.toList());
    }

    /**
     * distinctByKey
     */
    private static<T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(16);
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
