package com.uhasoft.gateway.util;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class SpelUtil {

    private static final ExpressionParser parser = new SpelExpressionParser();


    public static boolean eval(String condition, StandardEvaluationContext context){
        Boolean result = parser.parseExpression(condition).getValue(context, Boolean.class);
        return result != null? result : false;
    }
}
