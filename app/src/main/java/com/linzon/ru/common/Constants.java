package com.linzon.ru.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String STATIC_SERVER = "http://www.linzon.ru";
    public static final String STATIC_APP = "http://www.linzon.ru/json_app.txt";
    public static final String STATIC_PRICE = "http://www.linzon.ru/json_price164.txt";
    public static final String STATIC_SEND_BASKET = "http://www.linzon.ru/apijsn2016/postjson.php";
    public static final String STATIC_PRICE_UPDATE = "http://linzon.ru/market_json3.php";
    public static final String STATIC_OFFERS_UPDATE = "http://linzon.ru/market_json4.php";

    public static final String[] linsCount = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static final Map<String , String> CATEGORIES = new HashMap<String , String>() {{
        put("0", "Популярные");
        put("1", "Однодневные линзы");
        put("14", "Двухнедельные линзы");
        put("2", "Дышащие линзы");
        put("15", "Линзы на месяц");
        put("7", "Цветные линзы");
        put("16", "Квартальные линзы");
        put("13", "Торические линзы");
        put("10", "Раствор для линз");
    }};

    public static final String[] NotALins = {"10", "6"};
    public static final String[] SortArray = { "По названию", "По цене(по возрастанию)", "По цене(по убыванию)" };

    public static final String STATUS_OPEN = "open";
    public static final String STATUS_ARCHIVED = "archived";

    public static final String BROADCAST_UPDATE_COUNT = "linzon.update_count";
    public static final String BROADCAST_UPDATE_PRICE = "linzon.update_price";
    public static final String BROADCAST_REMOVE_OFFER = "linzon.remove_offer";
    public static final String BROADCAST_ADD_TO_ARCHIVE = "linzon.add_to_archive";
}
