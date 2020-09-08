package com.jeerigger.core.module.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面设置全局事务，拦截service包下面所有方法
 */
@Aspect
@Component
public class TransactionConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(public * com.*.*.BaseService+.*(..)))";

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {

        // 设置第一个事务管理的模式（适用于“增删改”）
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        // 当抛出设置的对应异常后，进行事务回滚（此处设置为“Exception”级别）
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        // 设置隔离级别（存在事务则加入其中，不存在则新建事务）
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置传播行为（读已提交的数据）
        requiredTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        // 设置第二个事务管理的模式（适用于“查”）
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        // 设置隔离级别（支持当前事务，如果当前没有事务，就以非事务方式执行）
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        // 设置传播行为（读已提交的数据）
        readOnlyTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        // 设置事务是否“只读”（非必需，只是声明该事务中不会进行修改数据库的操作，可减轻由事务造成的数据库压力，属于性能优化的推荐配置）
        readOnlyTx.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*", requiredTx);
        source.addTransactionalMethod("save*", requiredTx);
        source.addTransactionalMethod("delete*", requiredTx);
        source.addTransactionalMethod("update*", requiredTx);
        source.addTransactionalMethod("exec*", requiredTx);
        source.addTransactionalMethod("set*", requiredTx);
        source.addTransactionalMethod("cancel*", requiredTx);

        source.addTransactionalMethod("get*", readOnlyTx);
        source.addTransactionalMethod("query*", readOnlyTx);
        source.addTransactionalMethod("find*", readOnlyTx);
        source.addTransactionalMethod("list*", readOnlyTx);
        source.addTransactionalMethod("count*", readOnlyTx);
        source.addTransactionalMethod("is*", readOnlyTx);
        source.addTransactionalMethod("select*", readOnlyTx);
        source.addTransactionalMethod("*page*", readOnlyTx);

        return new TransactionInterceptor(platformTransactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        // 声明切点要切入的面
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 设置需要被拦截的路径
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        // 设置切面和配置好的事务管理
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
