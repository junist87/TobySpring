package Toby.MainBuild.ApplicationContext;

import Toby.MainBuild.Advisor.TransactionAdvice;
import Toby.MainBuild.Dao.UserDao;
import Toby.MainBuild.Dao.UserDaoJdbc;
import Toby.MainBuild.Service.TestUserServiceImpl;
import Toby.MainBuild.Service.UserLevelUpgradePolicy.EventPeriod;
import Toby.MainBuild.Service.UserLevelUpgradePolicy.UserLevelUpgradePolicy;
import Toby.MainBuild.Service.UserService;
import Toby.MainBuild.Service.UserServiceImpl;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TestApplicationContext {

    // +---- DB Part ----+
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource =
                new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/spring");
        dataSource.setPassword("1234");
        dataSource.setUsername("root");
        return dataSource;
    }


    // +---- DB Control Part ----+
    @Bean
    public UserDao userDao() {
        UserDaoJdbc userDao = new UserDaoJdbc();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    // +---- UserService Part ----+
    @Bean
    public UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUpgradePolicy(upgradePolicy());
        userService.setUserDao(userDao());
        return userService;
    }

    @Bean
    public UserLevelUpgradePolicy upgradePolicy() {
        EventPeriod upgradePolicy =
                new EventPeriod();
        upgradePolicy.setUserDao(userDao());
        return upgradePolicy;
    }

    @Bean
    public UserService testUserService() {
        TestUserServiceImpl userService =
                new TestUserServiceImpl();
        userService.setUpgradePolicy(upgradePolicy());
        userService.setUserDao(userDao());
        return userService;
    }


    // +---- Transaction Part ----+


    // 후처리기 로딩
    @Bean
    public DefaultAdvisorAutoProxyCreator proxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    // 트랙젝션 메니져
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    // 포인트컷 ( 클래스필터 + 메소드멧쳐 )
    // 연습을 위해 만들어본 포인트컷, 실제로 사용되지 않는다
//    @Bean
//    public NameMatchClassMethodPointcut transactionPointcut() {
//        NameMatchClassMethodPointcut pointcut =
//                new NameMatchClassMethodPointcut();
//        pointcut.setMappedClassName("*ServiceImpl");
//        pointcut.setMappedName("upgrade*");
//        return pointcut;
//    }


    // AspectJExpressionPointcut 의 expression 을 사용한 포인트컷
    @Bean
    public AspectJExpressionPointcut transactionPointcut() {
        AspectJExpressionPointcut pointcut =
                new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* *..*ServiceImpl.upgrade*(..))");
        return pointcut;
    }

    // 트랜젝션 어드바이스
    @Bean
    public TransactionAdvice transactionAdvice() {
        TransactionAdvice advice = new TransactionAdvice();
        advice.setTransactionManager(transactionManager());
        return advice;
    }


    // 기본어드바이져 (포인트컷 + 어드바이스)
    @Bean
    public DefaultPointcutAdvisor transactionAdvisor() {
        DefaultPointcutAdvisor advisor =
                new DefaultPointcutAdvisor();
        advisor.setAdvice(transactionAdvice());
        advisor.setPointcut(transactionPointcut());
        return advisor;
    }

}
