/*
    VKparser version 1.4 

    HTTPS запрос -> VK API -> JSON -> parse JSON -> export to Gephi

    Нужно использовать user_ID в виде цифв, узнавать через адрес аватарки например

    JSoup не нужен, т.к. итак выдается чистый запрос без HTML лабуды

    Пример запроса: https://api.vk.com/method/users.get?user_id=210700286&v=5.52 вместо users.get ставить нужную команду

    Парсить JSON через добавление сторонней библиотеки json org.json.JSONObject class http://theoryapp.com/parse-json-in-java/

    НАДО ДОБАВИТЬ ОБРАБОТКУ ОШИБОК, КАК КОДОВ ОШИБКИ ОТ ВК ТАК И ТОГО ЧТО В throws IOException И Т.П.
    Сделать свой аналг консоли в окне

    суммарно друзей у твоих друзей - 29783 ... это будет пол часа обрабатываться. перед тем сделать - точно убедись что сохраняешь в том формате, какой нужен!!! и все без ошибок
 */

package com.dd;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static long timestart=System.currentTimeMillis();

    public static void main(String[] args) throws IOException {

        Integer counter = 0; // сколько раз программа провела парсинг
        Integer total_friends = 0;

        String friend_list[];



        String starting_user_id = "7000763"; // с этого юзера начинаем парсинг

        Profile fr = new Profile(starting_user_id);



        //копия - работающий код: просто список друзей одного человека
//        for (String s : fr.getFriends()) {
//
//            Profile tmp = new Profile(s); //вот этот прием выпиши, что в цикле под одним и тем же именем разные классы создаешь. как бы еще сделать что бы имена у них разные были...
//
//        }


        //сохрани инфу оффлайн так, что бы не тратить по 30 мин на парсинг. что бы если что - в проге делать что-то с этой инфой. лучше через сохранение объекта ну или просто
        // отдельно сохранение в файле именно исходных кодов запроса в формате "ссылка на API" - "ответ в том виде как он был с сайта", тогда можно парсить этот файл и это быстрее


        //работающий код: просто список друзей одного человека + его друзей
        for (String s : fr.getFriends()) {

            Profile tmp = new Profile(s); //вот этот прием выпиши, что в цикле под одним и тем же именем разные классы создаешь. как бы еще сделать что бы имена у них разные были...

            if (tmp.getFriends_count() != null){
                total_friends += tmp.getFriends_count();
            }


//            if ( tmp.getFriends() != null) {
//                for (String s2 : tmp.getFriends()) {
//                    Profile tmp2 = new Profile(s2);
//
//                }
//            }
        }



        System.out.println("Всего обнаружено друзей у друзей: " + total_friends);
        System.out.println("Обработка займет: " + total_friends/Profile.getAvg_speed()/60 + " минут.");

    }

    // первая страница с которой начинаешь - задавать именно id её не адрес, и метод должен потом хавать айдишники и парсить по куче API!






    //dont use
    public static void debug(Profile fr){
        System.out.println(
                "id = " + fr.getId() + "\n" +
                        "first_name = " + fr.getFirst_name() + "\n" +
                        "last_name = " + fr.getLast_name()
        );

        System.out.println("Number of friends: " + fr.getFriends_count());
        System.out.println("Frinds list : " + Arrays.toString(fr.getFriends()));
    }

}
