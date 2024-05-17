package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        // TODO: добавить логику для объявления переменной date и задания её значения, для генерации строки с датой
        // Вы можете использовать класс LocalDate и его методы для получения и форматирования даты
        LocalDate varDate = LocalDate.now().plusDays(shift);
        String date = varDate.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        return date;
    }

    public static String generateCityUnavailableForDelivery(String locale) {
        String[] arrayOfCities = {
                "Донецк",
                "Луганск",
                "Херсон",
                "Запорожье"
        };
        Random r = new Random();
        String city = arrayOfCities[r.nextInt(3)];
        return city;
    }

    public static String generateCityAvailableForDelivery(String locale) {
        // TODO: добавить логику для объявления переменной city и задания её значения, генерацию можно выполнить
        // с помощью Faker, либо используя массив валидных городов и класс Random
        //Faker faker = new Faker(Locale.forLanguageTag(locale));
        //String city = faker.address().cityName();
        String[] arrayOfCities = {
                "Майкоп",
                "Горно-Алтайск",
                "Уфа",
                "Улан-Удэ",
                "Махачкала",
                "Магас",
                "Нальчик",
                "Элиста",
                "Черкесск",
                "Петрозаводск",
                "Сыктывкар",
                "Симферополь",
                "Йошкар-Ола",
                "Саранск",
                "Якутск",
                "Владикавказ",
                "Казань",
                "Кызыл",
                "Ижевск",
                "Абакан",
                "Грозный",
                "Чебоксары",
                "Барнаул",
                "Чита",
                "Петропавловск-Камчатский",
                "Краснодар",
                "Красноярск",
                "Пермь",
                "Владивосток",
                "Ставрополь",
                "Хабаровск",
                "Благовещенск",
                "Архангельск",
                "Астрахань",
                "Белгород",
                "Брянск",
                "Владимир",
                "Волгоград",
                "Вологда",
                "Воронеж",
                "Иваново",
                "Иркутск",
                "Калининград",
                "Калуга",
                "Кемерово",
                "Киров",
                "Кострома",
                "Курган",
                "Курск",
                "Гатчина",
                "Липецк",
                "Магадан",
                "Москва",
                "Мурманск",
                "Нижний Новгород",
                "Великий Новгород",
                "Новосибирск",
                "Омск",
                "Оренбург",
                "Орёл",
                "Пенза",
                "Псков",
                "Ростов-на-Дону",
                "Рязань",
                "Самара",
                "Саратов",
                "Южно-Сахалинск",
                "Екатеринбург",
                "Смоленск",
                "Тамбов",
                "Тверь",
                "Томск",
                "Тула",
                "Тюмень",
                "Ульяновск",
                "Челябинск",
                "Ярославль",
                "Москва",
                "Санкт-Петербург",
                "Севастополь",
                "Биробиджан",
                "Нарьян-Мар",
                "Ханты-Мансийск",
                "Анадырь",
                "Салехард"
        };
        Random r = new Random();
        String city = arrayOfCities[r.nextInt(84)];
        return city;
    }

    public static String generateName(String locale) {
        // TODO: добавить логику для объявления переменной name и задания её значения, для генерации можно
        // использовать Faker
        Faker faker = new Faker(Locale.forLanguageTag(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        // TODO: добавить логику для объявления переменной phone и задания её значения, для генерации можно
        // использовать Faker
        Faker faker = new Faker(Locale.forLanguageTag(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUserForDelivery(String locale) {
            // TODO: добавить логику для создания пользователя user с использованием методов generateCity(locale),
            // generateName(locale), generatePhone(locale)
            DataGenerator dataGenerator = new DataGenerator();
            UserInfo user = new UserInfo(
                    dataGenerator.generateCityAvailableForDelivery(locale),
                    dataGenerator.generateName(locale),
                    dataGenerator.generatePhone(locale));

            return user;
        }

        public static UserInfo generateUserNotForDeliveryByCity(String locale) {
            DataGenerator dataGenerator = new DataGenerator();
            UserInfo user = new UserInfo(
                    dataGenerator.generateCityUnavailableForDelivery(locale),
                    dataGenerator.generateName(locale),
                    dataGenerator.generatePhone(locale));

            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
