/*
    VKparser

    HTTPS запрос -> VK API -> JSON -> parse JSON

    API: users.get; users.getSubscriptions; friends.get

    Парсинг JSON через добавление сторонней библиотеки json org.json.JSONObject class http://theoryapp.com/parse-json-in-java/

    Суммарно друзей у твоих друзей - 29783 ... это будет пол часа обрабатываться

    Сделать JTextArea вместо консоли, окно + поле для id и одна кнопка

    Сериализация?

 */

package com.dd;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static final double CURRENT_VERSION = 1.5;
    public static long timestart = System.currentTimeMillis(); // Для расчета времени работы программы и скорости
    public static ArrayList<ArrayList<String>> friends = new ArrayList<ArrayList<String>>(); //массив вида id_1; friend_1_id; friend_2_id;...
    public static void main(String[] args) throws IOException {

        //new mainFrame();

        Integer total_friends = 0; // общее число друзей у обработанных профилей

        String starting_user_id = "7000763"; // с этого юзера начинаем парсинг

        Profile fr = new Profile(starting_user_id);

        for (String s : fr.getFriends()) {

            Profile tmp = new Profile(s);

            //Если tmp.getFriends_count() не существует, то профиль удален и для него друзей не считаем
            if (tmp.getFriends_count() != null) {

                total_friends += tmp.getFriends_count();

            }

        }

        // Статистика после завершения первичного парсинга
        System.out.println("Первичный парсинг завершен. Всего обнаружено друзей у друзей: " + total_friends + "\n" +
                "Полный парсинг займет: " + total_friends / Profile.getAvg_speed() / 60 + " минут.");

    }

}
