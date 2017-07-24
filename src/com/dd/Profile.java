//этот класс должен быть напи сан ОТДЕЛЬНЫМ файлом, а не размещен внутри Main, иначе ошибка

package com.dd;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.net.URL;
import java.util.Arrays;



 /*
    Собираемая инфа: //и коды ошибок от вконтакта!!!обработать их! в т.ч. обработать случае когда запрос прошел но подписок и друзей просто нет и все, что бы корректно было
    users.get - Имя, Фамилия, Дата рождения (она мб скрыта)
    users.getSubscriptions - список подписок
    friends.get - список друзей
 */

class Profile {
    private Integer friends_count;
    private String id; // id мы всегда знаем, так что получать его парсингом смысла нет, эта переменная исп для задания id для получения инфы
    private String first_name;
    private String last_name;
    private String[] friends;
    private JSONObject jsonObj;

    Profile(String id) throws IOException {


        jsonObj = parse_user_info(id); //вместо адреса должен быть id...и метд парс должен быть составным - там складывается загруженный id и произвольный API, и тогда несколько строк parse(API + ID) запарсят все
        // parse("https://api.vk.com/method/" + API[i] + "?user_id=" + id + "&v=5.52")
        // и таких строк несколько , в цикле, прокуручивая все из списка API
        this.id = id; // параметр, переданный конструктору при создании, тсановиться полем класса. не удалять, иначе id = null!
        this.first_name = jsonObj.getString("first_name");
        this.last_name = jsonObj.getString("last_name");
        //number_of_friends = //заполнить
        //String[][] friends = new String[number_of_friends][2];

        // parse with next API:
        // API = "https://api.vk.com/method/users.getSubscriptions?user_id=";

        jsonObj = parse_friends(id);

        this.friends_count = jsonObj.getJSONObject("response").getInt("count"); // получаем число друзей

        this.friends = new String[friends_count];

        //заполняем массив friends
        for (int i = 0; i < friends_count; i++) {
            Integer tmp;
            tmp = jsonObj.getJSONObject("response").getJSONArray("items").getInt(i); //способа напрямую массив json слить в обычный массив - не нашел, а tmp надо т.к. int в String только так можно...в эту строчку не дает дописать .toString
            friends[i] = tmp.toString();
        }


        System.out.println("Number of friends: " + friends_count);
        System.out.println("Frinds list : " + Arrays.toString(friends));

    }

    private JSONObject parse_user_info(String id) throws IOException {

        String API = "https://api.vk.com/method/users.get?user_id=";

        String API_ver = "&v=5.52"; // Без этой штуки - в браузере работает, а в проге - нет, т.к. в ответе на запрос вместо id написано uid.

        String address = API + id + API_ver; // Создаем запрос

        String inputData = null;

        URL url = new URL(address);

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Первичный объект JSON вида {"response" : [массив объктов JSON из 1 элемента]}, где ключем является response
        JSONObject jsonObj = new JSONObject(reader.readLine());

        // Вырезаем оттуда массив [{json}]. Он не распознается как объект JSON из-за квадратных скобок, т.к. это МАССИВ, пусть даже и из одного элемента.
        JSONArray jsonArr = jsonObj.getJSONArray("response");

        // Вырезаем из этого массива нулевой элемент, [{json}] --> {json},
        // и поскольку там больше нет квадратных скокоб от массива, превращаем этот элемент массива (тип: java.lang.Object) в JSON Object с помощью приведения типов "(JSONObject)"
        // Теперь данные оттуда доступны по ключам типа id, last_name и т.п., например jsonObj.get("id");
        jsonObj = (JSONObject) jsonArr.get(0);

        return jsonObj; //Возвращаем объект JSON

    }

    private JSONObject parse_friends(String id) throws IOException {

        String API = "https://api.vk.com/method/friends.get?user_id=";

        String API_ver = "&v=5.52"; // Без этой штуки - в браузере работает, а в проге - нет, т.к. в ответе на запрос вместо id написано uid.

        String address = API + id + API_ver; // Создаем запрос

        String inputData = null;

        URL url = new URL(address);

        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Первичный объект JSON вида {"response":{"count":NUMBER,"items":[...,...,...]}}
        JSONObject jsonObj = new JSONObject(reader.readLine());


       // String friends  = jsonObj.getJSONObject("response").get("items").toString(); // не удалять: что бы взять массив в квадратных скобках - надо просто юзать .get.toString а потом обрезать скобки или getJSONarray


        return jsonObj; //Возвращаем JSON object
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

    public JSONObject getJsonObj() {
        return jsonObj;
    }
}
