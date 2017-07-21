//этот класс должен быть напи сан ОТДЕЛЬНЫМ файлом, а не размещен внутри Main, иначе ошибка

package com.dd;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

 /*
    Собираемая инфа:
    users.get - Имя, Фамилия, Дата рождения (она мб скрыта)
    users.get - много инфы, но нужно сделать access_token
    groups.get - список сообщества, либо ошибка 260 если ограничено приватностью
 */

class Friend{
    private Integer id;
    private String first_name;
    private String last_name;
    private JSONObject jsonObj;

    Friend(String address) throws IOException {
        jsonObj = parse(address);
        this.id = jsonObj.getInt("id");
        this.first_name = jsonObj.getString("first_name");
        this.last_name = jsonObj.getString("last_name");
    }

    private JSONObject parse(String adress) throws IOException {

        String inputData = null;

        URL url = new URL(adress);

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

    public Integer getId() {
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
