package com.dd;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Arrays;
import java.net.URL;

class Profile {

    private static String API_subscribes = "https://api.vk.com/method/users.getSubscriptions?user_id=";
    private static String API_user_info = "https://api.vk.com/method/users.get?user_id=";
    private static String API_friends = "https://api.vk.com/method/friends.get?user_id=";
    private Integer subscribes_groups_count = 0;
    private Integer subscribes_users_count = 0;
    private String[] subscribes_groups;
    private static Integer counter = 0;
    private String[] subscribes_users;
    private static double avg_speed;
    private Integer friends_count;
    private static String API_ver = "&v=5.52"; // Без этого запросы могут иметь немного другие ключи, например вместо id будет uid.
    private String first_name;
    private boolean isDeleted;
    private String last_name;
    private String[] friends;
    private String id;

    Profile(String id) throws IOException {

        this.id = id; // параметр, переданный конструктору при создании, становиться полем класса. Не удалять, иначе id = null!

        parse_user_info(get_JSON_object(API_user_info, id));

        parse_friends(get_JSON_object(API_friends, id));

        parse_subscribes(get_JSON_object(API_subscribes, id));

        counter++; // Увеличиваем счетчик, считающий сколько раз проходил парсинг

        avg_speed = counter/(0.001*(System.currentTimeMillis() - Main.timestart));

        log();

    }

    private void parse_user_info(JSONObject jsonObj) throws IOException {

        // Разбор объекта вида {"response":[{"id":...,"first_name":"...","last_name":"...","hidden":1}]}

        // ВАЖНО! Т.к. это объект вида {"response" : [массив объктов JSON из 1 элемента]},
        // из-за того что там массив из одного элемента (в квадратных скобках), приходиться добавлять .getJSONObject(0) перед .getString("first_name");

        first_name =  jsonObj.getJSONArray("response").getJSONObject(0).getString("first_name");

        last_name =  jsonObj.getJSONArray("response").getJSONObject(0).getString("last_name");

    }

    private void parse_friends(JSONObject jsonObj) throws IOException {

        // Разбор объекта вида {"response":{"count":NUMBER,"items":[...,...,...]}}

        // try-catch - на случай если страница удалена, тогда приходит сообщение об ошибке,число и список друзей установить невозможно
        try {

            friends_count = jsonObj.getJSONObject("response").getInt("count"); // получаем число друзей

            friends = new String[friends_count];

            for (int i = 0; i < friends_count; i++) {

                // Получаем объект JSON в виде {"response":{"count":NUMBER,"items":[...,...,...]}}
                // В объекте JSON ищем вложенный объект JSON по ключу response --> в нем массив по ключу items --> получаем i-тый элемент в формате int --> переводим в String
                // используем для этого String.valueOf(), т.к у int нету метода .toString();
                friends[i] = String.valueOf(jsonObj.getJSONObject("response").getJSONArray("items").getInt(i));


                /* АЛЬТЕРНАТИВНЫЙ ВАРИАНТ с использованием временной переменной tmp:

                // Получаем id в виде Integer в переменную tmp, затем преобразуем в String.
                // В одну строчку сделать не удается: добавление в конец .toString() не сработает, т.к. getInt(i) дает int а не Integer

                Integer tmp;
                tmp = jsonObj.getJSONObject("response").getJSONArray("items").getInt(i);
                friends[i] = tmp.toString();

                */

            }

        } catch(Exception e){

            isDeleted = true;

        }

    }

    private void parse_subscribes(JSONObject jsonObj) throws IOException {

        // Разбор объекта вида {"response": {"users": {"count":NUMBER,"items":[...,...,...]}, "groups":{"count":NUMBER,"items":[...,...,...,]}}}

        subscribes_users_count = jsonObj.getJSONObject("response").getJSONObject("users").getInt("count");

        subscribes_users = new String[subscribes_users_count];

        subscribes_groups_count = jsonObj.getJSONObject("response").getJSONObject("groups").getInt("count");

        subscribes_groups = new String[subscribes_groups_count];

        // эти два цикла надо в один объединить как-то...ну или вызывать метод два раза, что бы без дублирования кода, или лямбда выражение. или мб ну нах

        for (int i = 0; i < subscribes_users_count; i++) {

            // Получаем id в виде Integer в переменную tmp, затем преобразуем в String.
            // В одну строчку сделать не удается: добавление в конец .toString() не сработает, т.к. getInt(i) дает int а не Integer

            Integer tmp;

            tmp = jsonObj.getJSONObject("response").getJSONObject("users").getJSONArray("items").getInt(i);

            subscribes_users[i] = tmp.toString();
        }

        for (int i = 0; i < subscribes_groups_count; i++) {

            // Получаем id в виде Integer в переменную tmp, затем преобразуем в String.
            // В одну строчку сделать не удается: добавление в конец .toString() не сработает, т.к. getInt(i) дает int а не Integer

            Integer tmp;

            tmp = jsonObj.getJSONObject("response").getJSONObject("groups").getJSONArray("items").getInt(i);

            subscribes_groups[i] = tmp.toString();

        }

    }

    private JSONObject get_JSON_object (String API, String id) {

        String address = API + id + API_ver; // Создаем запрос

        JSONObject jsonObj = null; // Нельзя объявить в блоке try-catch, она тогда не будет видима за пределами блока
                    // важно, проверь, если тут не писать = null, то будет ли она при 2+ срабатывании метода создаваться заного и быть нулл? если нет, то оставь как есть, что бы обнулялась. если тогда ошибка в трай-кетч, то что бы был нулл а не прошлое её значение, так легче будет ошибку найти

        try {

            URL url = new URL(address);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            jsonObj = new JSONObject(reader.readLine());

        } catch (IOException e) {

            e.printStackTrace();

        }

        return jsonObj;

    }

    private void log(){

        System.out.println("Добавлен профиль: id " + id + "; " + first_name + " " + last_name + "; Чило друзей: " + friends_count + " Подписки (группы/люди): " + subscribes_groups_count + "/" + subscribes_users_count);
        System.out.println("Список друзей: " + Arrays.toString(friends));
        System.out.println("Подписки (люди): " + Arrays.toString(subscribes_users));
        System.out.println("Подписки (группы): " + Arrays.toString(subscribes_groups));
        System.out.println("Обработано профилей " + counter + "; Прошло времени "+ 0.001*(System.currentTimeMillis() - Main.timestart) + "; Скорость обработки: " + avg_speed + " профилей в секунду.");
        System.out.println();

    }

    public static double getAvg_speed() {
        return avg_speed;
    }

    public Integer getFriends_count() {
        return friends_count;
    }

    public String[] getFriends() {
        return friends;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public static Integer getCounter() {
        return counter;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

}
