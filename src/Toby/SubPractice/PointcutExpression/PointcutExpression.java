package Toby.SubPractice.PointcutExpression;

import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PointcutExpression {

    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        //pointcut.setExpression("execution(public int " + "Toby.SubPractice.PointcutExpression.Target.minus(int, int) " + "throws java.lang.RuntimeException)");

        pointcut.setExpression("execution(int minus(int, int))");
        assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null), is(true));


        assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null), is(false));

        assertThat(pointcut.getClassFilter().matches(Target.class) &&
                pointcut.getMethodMatcher().matches(Target.class.getMethod("method"), null), is(false));
    }


    @Test
    public void pointCut() throws Exception {
        targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
    }

    public void targetClassPointcutMatches(String expression, boolean... expected) throws Exception{
        pointcutMatches(expression, expected[0], Target.class, "hello");
        pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
        pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
        pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
        pointcutMatches(expression, expected[4], Target.class, "method");
        pointcutMatches(expression, expected[5], Bean.class, "method");

    }


    // 헬퍼메소드라고 한다
    public void pointcutMatches(String expression, Boolean expected, Class<?> aClass, String methodName, Class<?> ...args) throws Exception {
        AspectJExpressionPointcut pointcut =
                new AspectJExpressionPointcut();

        pointcut.setExpression(expression);

        assertThat(pointcut.getClassFilter().matches(aClass) &&
        pointcut.getMethodMatcher().matches(aClass.getMethod(methodName, args), null), is(expected));
    }
}
