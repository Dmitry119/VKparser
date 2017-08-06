/*
    VKparser

    HTTPS запрос -> VK API -> JSON -> parse JSON

    API: users.get; users.getSubscriptions; friends.get

    Парсинг JSON через добавление сторонней библиотеки json org.json.JSONObject class http://theoryapp.com/parse-json-in-java/

    Суммарно друзей у твоих друзей - 29783 ... это будет пол часа обрабатываться

    Сделать JTextArea вместо консоли, окно + поле для id и одна кнопка

    Сериализация работает

    Поиск по id работает

    добавить JUnit тесты

 */

package com.dd;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static final double CURRENT_VERSION = 1.5;
    public static long timestart = System.currentTimeMillis(); // Для расчета времени работы программы и скорости
    public static ArrayList<ArrayList<String>> friends = new ArrayList<ArrayList<String>>(); //массив вида id_1; friend_1_id; friend_2_id;...
    public static void main(String[] args) throws IOException {

        //new mainFrame();

        ArrayList<Profile> friendList;



        // ----------работает, но пока не нужно. НЕ УДАЛЯТЬ.
        // findProfileById(friendList);
        // saveFriendList(friendList);
        // friendList = parseFriendsForUser("7000763");  // это юзать если получаем друзей через парсинг сайта vk а не сериализацией
        // --------------

        friendList = loadFriendList();
        findProfileById(friendList);



    }

    private static ArrayList<Profile> parseFriendsForUser(String starting_user_id) throws IOException {
        // starting_user_id - с этого юзера начинаем парсить

        ArrayList<Profile> friendList = new ArrayList<>();

        Profile fr = new Profile(starting_user_id);

        Integer total_friends = 0; // общее число друзей у обработанных профилей


        for (String s : fr.getFriends()) {

            Profile tmp = new Profile(s);

            friendList.add(tmp);

            //Если tmp.getFriends_count() не существует, то профиль удален и для него друзей не считаем
            if (tmp.getFriends_count() != null) {

                total_friends += tmp.getFriends_count();

            }

        }

        // Статистика после завершения первичного парсинга
        System.out.println("Первичный парсинг завершен. Всего обнаружено друзей у друзей: " + total_friends + "\n" +
                "Полный парсинг займет: " + total_friends / Profile.getAvg_speed() / 60 + " минут.");
        return friendList;
    }

    private static void saveFriendList(ArrayList<Profile> friendList) throws IOException {

        Date parsingDate = new Date();

        SimpleDateFormat formatForParsingDate = new SimpleDateFormat("yyyy.MM.dd_hh.mm.ss");

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("friendList_" + formatForParsingDate.format(parsingDate)+ ".dat"));

        out.writeObject(friendList);

        out.close();

    }

    private static void findProfileById(ArrayList<Profile> friendList) {

        System.out.println("test, input id for find info");
        Scanner sc = new Scanner(System.in);
        String test = sc.next();

        for (int i = 0; i < friendList.size(); i++) {

            if (test.equals(friendList.get(i).getId())) {
                System.out.println("finded: " + friendList.get(i).getId() + " " + friendList.get(i).getFirst_name() + " " + friendList.get(i).getLast_name());
                break;
            }

        }
    }

    private static ArrayList<Profile> loadFriendList() throws IOException {

        // test on friendList_2017.08.07_01.06.42.dat

        ObjectInputStream in =  new ObjectInputStream (new FileInputStream("friendList_2017.08.07_01.06.42.dat"));

        ArrayList<Profile> returned_data = null;
        try {

            returned_data = (ArrayList<Profile>) in.readObject();
            System.out.println("friendList loaded from file! It contains: " + returned_data.size() + " profiles.");

            for (int i = 0; i < returned_data.size(); i++ ) {
                System.out.println(returned_data.get(i).getId() + " " + returned_data.get(i).getFirst_name() + " " + returned_data.get(i).getLast_name());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return returned_data;
    }

}
