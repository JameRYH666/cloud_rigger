package com.jeerigger.frame.json;

import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.*;

public class JSON<T> {
    private Map<Class, FieldInfo> fields = new HashMap<>();
    private Map<Class, ReNameInfos<ReNameInfo>> rename = new HashMap<>();
    private Object data;

    public JSON<T> in(Attr.Property<T, ?>... properties) {
        for (Attr.Property<T, ?> fun : properties) {
            add(true, new Attr<T>(fun));
        }
        return this;
    }


    public JSON<T> in(Attr... attrs) {
        for (Attr attr : attrs)
            add(true, attr);
        return this;
    }


    private void add(boolean in, Attr attr) {
        Class clzz = attr.getClz();
        String fieldName = attr.getName();
        if (fields.containsKey(clzz)) {
            FieldInfo values = fields.get(clzz);
            if (in)
                values.addIncludes(fieldName);
            else
                values.addExcludes(fieldName);
        } else {
            FieldInfo values = new FieldInfo(clzz);
            if (in)
                values.addIncludes(fieldName);
            else
                values.addExcludes(fieldName);
            fields.put(clzz, values);
        }

    }

    private void add(Attr attr, String newName) {
        Class clzz = attr.getClz();
        String fieldName = attr.getName();
        if (rename.containsKey(clzz)) {
            ReNameInfos values = rename.get(clzz);
            values.addIncludes(new ReNameInfo(fieldName, newName));
        } else {
            ReNameInfos values = new ReNameInfos(clzz);
            values.addIncludes(new ReNameInfo(fieldName, newName));
            rename.put(clzz, values);
        }

    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JSON<T> data(Object data) {
        this.data = data;
        return this;
    }

    @SafeVarargs
    public final JSON<T> ex(Attr.Property<T, ?>... properties) {
        for (Attr.Property<T, ?> fun : properties) {
            add(false, new Attr<T>(fun));
        }
        return this;
    }


    public JSON<T> ex(Attr... attrs) {
        for (Attr fun : attrs) {
            add(false, fun);
        }
        return this;
    }


    public JSON<T> rename(Attr.Property<T, ?> property, String new_name) {
        Attr<T> attr = new Attr<T>(property);
        rename(attr, new_name);
        return this;
    }


    public JSON<T> rename(Attr attr, String new_name) {
        add(true, attr);
        add(attr, new_name);
        return this;
    }


    private <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }


    public String toJSONString() {


        return com.alibaba.fastjson.JSON.toJSONString(this, concat(inexfileter(), reNamefilters()));
    }

    public SerializeFilter[] filters() {
        return concat(inexfileter(), reNamefilters());
    }

    private SerializeFilter[] inexfileter() {


        SerializeFilter[] filters = new SerializeFilter[fields.keySet().size()];
        int i = 0;
        SimplePropertyPreFilter filter;
        for (Class clazz : fields.keySet()) {
            filter = new SimplePropertyPreFilter(clazz);
            filter.getIncludes().addAll(fields.get(clazz).includes);
            filter.getExcludes().addAll(fields.get(clazz).excludes);
            filters[i++] = filter;

        }
        return filters;
    }

    private SerializeFilter[] reNamefilters() {
        SerializeFilter[] filters = new SerializeFilter[rename.keySet().size()];
        int i = 0;
        ReNameFilter filter;
        for (Class clazz : rename.keySet()) {
            filter = newReNameFilter(clazz, rename.get(clazz));
            filters[i++] = filter;

        }
        return filters;
    }

    private ReNameFilter newReNameFilter(Class clzz, ReNameInfos reNameInfos) {
        ReNameInfo reNameInfo = null;
        Iterator<ReNameInfo> iterator = reNameInfos.renames.iterator();
        Map<String, String> map = new HashMap<>();
        while (iterator.hasNext()) {
            reNameInfo = iterator.next();
            map.put(reNameInfo.name, reNameInfo.newName);

        }
        return new ReNameFilter(clzz, map);

    }

    private static class ReNameFilter implements NameFilter {
        private final Class<?> clazz;
        private final Map<String, String> renames;

        public ReNameFilter(Class<?> clazz, Map<String, String> map) {
            super();
            this.clazz = clazz;
            this.renames = (map == null ? new HashMap<>() : map);

        }

        public String process(Object source, String name, Object value) {
            if (name == null || name.length() == 0) {
                return name;
            }
            if (clazz != null && !clazz.isInstance(source)) {
                return name;
            }
            if (renames.containsKey(name)) {
                return renames.get(name);
            }

            return name;
        }
    }

    private static class ReNameInfo {

        private final String name;
        private final String newName;

        public ReNameInfo(String name, String newName) {
            this.name = name;
            this.newName = newName;
        }
    }

    private static class ReNameInfos<ReNameInfo> {
        private Set<ReNameInfo> renames = new HashSet<>();

        private Class clzz;

        ReNameInfos(Class clzz) {
            this.clzz = clzz;
        }

        void addIncludes(ReNameInfo name) {
            renames.add(name);
        }


    }

    private static class FieldInfo {
        // since this class is private we can just use field access
        // to make HotSpot a little less work for inlining
        private Set<String> includes = new HashSet<>();
        private Set<String> excludes = new HashSet<>();
        private Class clzz;

        FieldInfo(Class clzz) {
            this.clzz = clzz;
        }

        void addIncludes(String name) {
            includes.add(name);
        }

        void addExcludes(String name) {
            excludes.add(name);
        }
    }


}
