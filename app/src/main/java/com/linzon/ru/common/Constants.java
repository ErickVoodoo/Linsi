package com.linzon.ru.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String STATIC_SERVER = "http://www.linzon.ru";
    public static final String STATIC_APP = "http://www.linzon.ru/json_app2.txt";
    public static final String STATIC_PRICE = "http://www.linzon.ru/json_price164.txt";
    public static final String STATIC_VERSION = "http://www.linzon.ru/version.txt";
    public static final String STATIC_ORDER_STATE = "http://www.linzon.ru/apijsn2016/jsonout.php?state=";
    public static final String STATIC_SEND_BASKET = "http://www.linzon.ru/apijsn2016/postjson.php";
    public static final String STATIC_PRICE_UPDATE = "http://linzon.ru/market_json3.php";
    public static final String STATIC_OFFERS_UPDATE = "http://linzon.ru/market_json4.php";

    public static final String[] linsCount = { "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static final Map<String , String> CATEGORIES = new HashMap<String , String>() {{
        put("0", "Популярные");
        put("1", "Однодневные линзы");
        put("14", "Двухнедельные линзы");
        put("2", "Дышащие линзы");
        put("15", "Линзы на месяц");
        put("7", "Цветные линзы");
        put("16", "Квартальные линзы");
        put("3", "Традиционные линзы");
        put("13", "Торические линзы");
        put("9", "Карнавальные линзы");
        put("6", "Капли для глаз");
        put("10", "Раствор для линз");
    }};

    public static final String[] NotALins = {"10", "6"};
    public static final String[] OrderStatuses = {"Заказано", "Выполняется", "Доставлено"};
    public static final String[] Month = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
    public static final String[] SortArray = { "По названию", "По цене(по возрастанию)", "По цене(по убыванию)" };

    public static final String STATUS_OPEN = "open";
    public static final String STATUS_ARCHIVED = "archived";

    public static final String BROADCAST_UPDATE_COUNT = "linzon.update_count";
    public static final String BROADCAST_UPDATE_PRICE = "linzon.update_price";
    public static final String BROADCAST_REMOVE_OFFER = "linzon.remove_offer";
    public static final String BROADCAST_ADD_TO_ARCHIVE = "linzon.add_to_archive";
    public static final String BROADCAST_ADD_TO_BASKET_FROM_ARCHIVE = "linzon.add_to_basket_from_archive";

    public static final int DELIVER_PRICE = 150;
}
