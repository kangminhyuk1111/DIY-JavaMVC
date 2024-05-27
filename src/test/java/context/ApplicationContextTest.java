package context;

import code.*;
import com.djawnstj.mvcframework.context.ApplicationContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.ReflectionTest;

import static org.junit.jupiter.api.Assertions.assertAll;

class ApplicationContextTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("init 테스트")
    void testInit() {
        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        UserService bean = applicationContext.getBean(UserService.class);

        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    @DisplayName("어노테이션 조회 테스트")
    void annotationTest() {
        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        Object bean1 = applicationContext.getBean(UserService.class);
        Object bean2 = applicationContext.getBean(UserRepository.class);

        assertAll("annotationTest",
                () -> Assertions.assertThat(bean1).isNotNull(),
                () -> Assertions.assertThat(bean2).isNotNull());
    }

    @Test
    @DisplayName("빈 등록 및 조회 테스트 - 모든 객체는 싱글톤 패턴으로 구현")
    void addBeanSingletonTest() {
        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        Object bean1 = applicationContext.getBean(UserService.class);
        Object bean2 = applicationContext.getBean(UserService.class);

        assertAll("addBeanSingletonTest",
                () -> Assertions.assertThat(bean1).isNotNull(),
                () -> Assertions.assertThat(bean2).isNotNull(),
                () -> Assertions.assertThat(bean1).isSameAs(bean2));
    }

    @Test
    @DisplayName("빈 등록 테스트 - 싱글톤 테스트 - 1")
    void singletonTest() {
        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        final UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        final UserService userService = applicationContext.getBean(UserService.class);

        assertAll("singletonTest",
                () -> Assertions.assertThat(userService).isNotNull(),
                () -> Assertions.assertThat(userRepository).isNotNull(),
                () -> Assertions.assertThat(userService.getUserRepository()).isEqualTo(userRepository));
    }

    @Test
    @DisplayName("빈 등록 테스트 - 싱글톤 테스트 - 2")
    void singletonTest2() {
        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        final UserController userController = applicationContext.getBean(UserController.class);
        final UserService userService = applicationContext.getBean(UserService.class);
        final UserRepository userRepository = applicationContext.getBean(UserRepository.class);

        assertAll("singletonTest",
                () -> Assertions.assertThat(userService).isNotNull(),
                () -> Assertions.assertThat(userRepository).isNotNull(),
                () -> Assertions.assertThat(userController).isNotNull(),
                () -> Assertions.assertThat(userController.getUserService()).isEqualTo(userService));
    }

    @Test
    @DisplayName("빈 등록 테스트 - Configuration Bean 등록")
    void configurationTest() {
        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        final UserService userService = applicationContext.getBean(UserService.class);
        final UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        final PayService payService = applicationContext.getBean(PayService.class);
        final PayRepository payRepository = applicationContext.getBean(PayRepository.class);

        assertAll("singletonTest",
                () -> Assertions.assertThat(userService).isNotNull(),
                () -> Assertions.assertThat(userRepository).isNotNull(),
                () -> Assertions.assertThat(payService).isNotNull(),
                () -> Assertions.assertThat(payRepository).isNotNull(),
                () -> Assertions.assertThat(userService.userRepository).isEqualTo(userRepository),
                () -> Assertions.assertThat(payService.payRepository).isEqualTo(payRepository));
    }

    @Test
    @DisplayName("빈 등록 테스트 - Configuration Bean 등록 2")
    void configurationTest2() {
        ApplicationContext applicationContext = new ApplicationContext("code");
        applicationContext.init();

        final PayService payService = applicationContext.getBean(PayService.class);
        final PayRepository payRepository1 = applicationContext.getBean(PayRepository.class);
        final PayRepository payRepository2 = applicationContext.getBean(PayRepository.class);

        assertAll("singletonTest2",
                () -> Assertions.assertThat(payRepository1).isNotNull(),
                () -> Assertions.assertThat(payRepository2).isNotNull(),
                () -> Assertions.assertThat(payService).isNotNull(),
                () -> Assertions.assertThat(payRepository1).isEqualTo(payRepository2),
                () -> Assertions.assertThat(payService.payRepository).isEqualTo(payRepository1),
                () -> Assertions.assertThat(payService.payRepository).isEqualTo(payRepository2));
    }
}
