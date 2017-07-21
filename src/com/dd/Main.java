/*
    VKparser version 0.7

    HTTPS запрос -> VK API -> JSON -> parse JSON -> export to Gephi

    Нужно использовать user_ID в виде цифв, узнавать через адрес аватарки например

    JSoup не нужен, т.к. итак выдается чистый запрос без HTML лабуды

    Пример запроса: https://api.vk.com/method/users.get?user_id=210700286&v=5.52 вместо users.get ставить нужную команду

    Парсить JSON через добавление сторонней библиотеки json org.json.JSONObject class http://theoryapp.com/parse-json-in-java/
 */

package com.dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

    public static void main(String[] args) throws IOException {


        //проеряю на себе что парсинг работает - распечатка объекта JSON в консоль
        System.out.println("test   " + parse("https://api.vk.com/method/users.get?user_id=7000763&v=5.52"));

        new friend(parse("https://api.vk.com/method/users.get?user_id=7000763&v=5.52"));

    }

    private static JSONObject parse(String adress) throws IOException {

        String inputData = null;

        URL url = new URL(adress);

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Первичный объект JSON вида {"response" : [массив объктов JSON из 1 элемента]}, где ключем является response
        JSONObject jsonObj = new JSONObject(reader.readLine());

        // Вырезаем оттуда массив [{json}]. Он не распознается как объект JSON из-за квадратных скобок, т.к. это МАССИВ, пусть даже и из одного элемента.
        JSONArray jsonArr = jsonObj.getJSONArray("response");

        // Вырезаем из этого массива нулевой элемент, [{json}] --> {json},
        // и поскольку там больше нет квадратных скокоб от массива, превращаем этот элемент массива (тип: java.lang.Object) в JSON Object с помощью приведения типов "(JSONObject)"
        // Теперь данные оттуда доступны по ключам типа id, lats_name и т.п., например jsonObj.get("id");
        jsonObj = (JSONObject) jsonArr.get(0);

        return jsonObj; //Возвращаем объект JSON

    }


    /*
    Собираемая инфа:
    users.get - Имя, Фамилия, Дата рождения (она мб скрыта)
    users.get - много инфы, но нужно сделать access_token
    groups.get - список сообщества, либо ошибка 260 если ограничено приватностью
     */
    private class friend{
        private Integer id;
        private String first_name;
        private String last_name;

        friend(JSONObject jsonObj){
            this.id = jsonObj.getInt("id");
            this.first_name = jsonObj.getString("first_name");
            this.last_name = jsonObj.getString("lats_name");
        }

    }

}
