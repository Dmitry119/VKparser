/*
    VKparser version 1.0

    HTTPS запрос -> VK API -> JSON -> parse JSON -> export to Gephi

    Нужно использовать user_ID в виде цифв, узнавать через адрес аватарки например

    JSoup не нужен, т.к. итак выдается чистый запрос без HTML лабуды

    Пример запроса: https://api.vk.com/method/users.get?user_id=210700286&v=5.52 вместо users.get ставить нужную команду

    Парсить JSON через добавление сторонней библиотеки json org.json.JSONObject class http://theoryapp.com/parse-json-in-java/
 */

package com.dd;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Friend fr = new Friend("https://api.vk.com/method/users.get?user_id=7000763&v=5.52");

        System.out.println(
                "id = " + fr.getId() + "\n" +
                "first_name = " + fr.getFirst_name() + "\n" +
                "last_name = " + fr.getLast_name()
        );

    }

}
