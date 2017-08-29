package Toby.SubPractice.HelloAdvisor;

import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HelloTest {

    HelloTarget helloTarget;

    @Before
    public void setUp() {
        helloTarget = new HelloTarget();
    }

    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UppercaseAdvice());

        Hello proxiedHello = (Hello) pfBean.getObject();

        System.out.println(proxiedHello.sayHi("Ciao"));
        System.out.println(proxiedHello.sayHello("Ciao"));
        System.out.println(proxiedHello.sayThankYou("Ciao"));

        assertThat(proxiedHello.sayHello("Ciao"), is("HELLO CIAO"));
        assertThat(proxiedHello.sayHi("Ciao"), is("HI CIAO"));
        assertThat(proxiedHello.sayThankYou("Ciao"), is("THANKYOU CIAO"));
    }

    @Test
    public void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();

        System.out.println(proxiedHello.sayHi("Ciao"));
        System.out.println(proxiedHello.sayHello("Ciao"));
        System.out.println(proxiedHello.sayThankYou("Ciao"));

        assertThat(proxiedHello.sayHello("Ciao"), is("HELLO CIAO"));
        assertThat(proxiedHello.sayHi("Ciao"), is("HI CIAO"));
        assertThat(proxiedHello.sayThankYou("Ciao"), is("ThankYou Ciao"));
    }


    @Test
    public void classNamePointCutAdvisor() {

        class HelloWorld extends HelloTarget{}
        class HelloTrol extends HelloTarget{}

        checkAdviced(new HelloTarget(), getPointcut(), true);
        checkAdviced(new HelloTrol(), getPointcut(), true);
        checkAdviced(new HelloWorld(), getPointcut(), false);
    }

    private NameMatchMethodPointcut getPointcut() {
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            public ClassFilter getClassFilter() {
                return aClass -> aClass.getSimpleName().startsWith("HelloT");
            }
        };

        classMethodPointcut.setMappedName("sayH*");
        return classMethodPointcut;
    }

    private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);

        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();

        if (adviced) {
            assertThat(proxiedHello.sayHello("Ciao"), is("HELLO CIAO"));
            assertThat(proxiedHello.sayHi("Ciao"), is("HI CIAO"));
            assertThat(proxiedHello.sayThankYou("Ciao"), is("ThankYou Ciao"));
        } else {
            assertThat(proxiedHello.sayHello("Ciao"), is("Hello Ciao"));
            assertThat(proxiedHello.sayHi("Ciao"), is("Hi Ciao"));
            assertThat(proxiedHello.sayThankYou("Ciao"), is("ThankYou Ciao"));
        }
    }
}
