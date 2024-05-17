package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting(){
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUserForDelivery("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        //планируем начальную встречу
        $("[data-test-id='city'] input").setValue(validUser.getCity());

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);

        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".grid-row button").click();

//        проверка только для начальной встречи. 1 тест = 1 проверка
        $("[data-test-id='success-notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content").
                shouldHave(exactText("Встреча успешно запланирована на "+firstMeetingDate));

    }

    @Test
    @DisplayName("Should successful invite to replan meeting")
    void shouldSuccessfulInviteToReplanMeeting(){
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUserForDelivery("ru");
        var daysToAddForFirstMeeting = 5;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        //планируем начальную встречу
        $("[data-test-id='city'] input").setValue(validUser.getCity());

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);

        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".grid-row button").click();

//      перепланируем встречу для того же пользователя
        var daysToAddForSecondMeeting = 8;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(".grid-row button").click();

//        проверка предложения перепланировать встречу. 1 тест = 1 проверка
        $("[data-test-id='replan-notification']").shouldBe(visible);
        $("[data-test-id='replan-notification'] .notification__content").
                shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
    }

    @Test
    @DisplayName("Should successful replan meeting")
    void shouldSuccessfulReplanMeeting(){
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUserForDelivery("ru");
        var daysToAddForFirstMeeting = 6;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        //планируем начальную встречу
        $("[data-test-id='city'] input").setValue(validUser.getCity());

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);

        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".grid-row button").click();

        //перепланируем встречу для того же пользователя
        var daysToAddForSecondMeeting = 4;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(".grid-row button").click();

        //проверка успешного перепланирования. 1 тест = 1 проверка
        $$("[data-test-id='replan-notification'] button").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldBe(visible);
        $("[data-test-id='success-notification'] .notification__content").
                shouldHave(exactText("Встреча успешно запланирована на "+secondMeetingDate));

        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

    }

    @Test
    @DisplayName("Should deny to plan meeting")
    void shouldDenyPlanMeeting(){
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUserNotForDeliveryByCity("ru");
        var daysToAddForFirstMeeting = 6;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);

        //планируем начальную встречу
        $("[data-test-id='city'] input").setValue(validUser.getCity());

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);

        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".grid-row button").click();

//        проверка только для начальной встречи. 1 тест = 1 проверка
        $("[data-test-id='city'] .input__sub").shouldBe(visible);
        $("[data-test-id='city'] .input__sub").
                shouldHave(exactText("Доставка в выбранный город недоступна"));

    }
}
