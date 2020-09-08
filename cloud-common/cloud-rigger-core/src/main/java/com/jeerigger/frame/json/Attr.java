package com.jeerigger.frame.json;


import com.trigersoft.jaque.expression.*;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.List;
import java.util.function.Function;
/**
 * @Func : 分析 lambda 方法
 * @Autor: leeton
 * @Date : 2018-03-27 6:36 PM
 */
public class Attr<T> {

    private Class clz;     // 方法所属的类
    private String name;   // 属性名
    private Member member; //成员或构造函数
    // this interface is required to make the lambda Serializable, which removes a need for
    // jdk.internal.lambda.dumpProxyClasses system property. See below.
    public interface Property<T, R> extends Function<T, R>, Serializable {
    }

    public Attr() {
    }

    public Attr(Property<T, ?> propertyRef) {
        parse(propertyRef);
    }

    /**
     * 传入 lambda 静态方法，返回方法属性名及对应的字段名
     *
     * @param propertyRef
     * @return
     */
    public Attr<T> parse(Property<T, ?> propertyRef) {
        LambdaExpression expression = getExpression(propertyRef);
        List<ParameterExpression> params = expression.getParameters();
        this.member = getMember(expression);
        this.clz = params.get(0).getResultType();
        this.name = attrName(member);

        return this;

    }

    public LambdaExpression getExpression(Property<T, ?> propertyRef) {
        return LambdaExpression.parse(propertyRef);
    }

    public static Member getMember(javax.el.LambdaExpression lambdaExpression) {
        Expression methodCall = lambdaExpression.getBody();
        // remove casts
        while (methodCall instanceof UnaryExpression) {
            methodCall = ((UnaryExpression) methodCall).getFirst();
        }
        // checks are omitted for brevity
        Member member = ((MemberExpression) ((InvocationExpression) methodCall).getTarget()).getMember();
        return member;
    }


    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String attrName(Member member) {
        return getCamelName(member.getName());
    }
    private String getCamelName(String method) {
        method = method.startsWith("get") ? method.substring(3) : method.substring(2);
        return StringKit.toLowerCaseFirstOne(method);
    }
}
