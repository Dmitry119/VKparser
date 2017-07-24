/*
    VKparser version 1.2

    HTTPS запрос -> VK API -> JSON -> parse JSON -> export to Gephi

    Нужно использовать user_ID в виде цифв, узнавать через адрес аватарки например

    JSoup не нужен, т.к. итак выдается чистый запрос без HTML лабуды

    Пример запроса: https://api.vk.com/method/users.get?user_id=210700286&v=5.52 вместо users.get ставить нужную команду

    Парсить JSON через добавление сторонней библиотеки json org.json.JSONObject class http://theoryapp.com/parse-json-in-java/

    НАДО ДОБАВИТЬ ОБРАБОТКУ ОШИБОК, КАК КОДОВ ОШИБКИ ОТ ВК ТАК И ТОГО ЧТО В throws IOException И Т.П.
    Сделать свой аналг консоли в окне
 */

package com.dd;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {



        String starting_user_id = "7000763"; // с этого юзера начинаем парсинг

        Profile fr = new Profile(starting_user_id);

        // первая страница с которой начинаешь - задавать именно id её не адрес, и метод должен потом хавать айдишники и парсить по куче API!

        System.out.println(
                "id = " + fr.getId() + "\n" +
                "first_name = " + fr.getFirst_name() + "\n" +
                "last_name = " + fr.getLast_name()
        );

    }

}
