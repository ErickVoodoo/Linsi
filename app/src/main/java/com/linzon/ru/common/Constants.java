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
    public static final String[] OrderStatuses = {"Ожидает проверки", "Ждём оплаты", "Выполняется", "Доставляется", "Доставлен", "Отменён", "Собирается", "Готов к отгрузке", "Перенос заказа", "Поступил на пункт самовывоза", "Поступил в отделение почты"};
    public static final String[] Filter_BC = {"Выбрать", "8.3", "8.4", "8.5", "8.6", "8.7", "8.8", "8.9", "9.0"};
    public static final String[] Filter_COLOR = {"Выбрать", "голубой", "карий", "фиалковый", "зеленый", "фиолетовый", "серый", "синий", "аметист", "бирюзовый", "медовый", "карий", "аква", "ореховый", "бирюзовый", "бриллиантовый"};
    public static final String[] Filter_PWR = {"Выбрать", "-20", "-19.75", "-19.5", "-19.25", "-19", "-18.75", "-18.5", "-18.25", "-18", "-17.75", "-17.5", "-17.25", "-17", "-16.75", "-16.5", "-16.25", "-16", "-15.75", "-15.5", "-15.25", "-15", "-14.75", "-14.5", "-14.25", "-14", "-13.75", "-13.5", "-13.25", "-13", "-12.75", "-12.5", "-12.25", "-12", "-11.75", "-11.5", "-11.25", "-11", "-10.75", "-10.5", "-10.25", "-10", "-9.75", "-9.5", "-9.25", "-9", "-8.75", "-8.5", "-8.25", "-8", "-7.75", "-7.5", "-7.25", "-7", "-6.75", "-6.5", "-6.25", "-6", "-5.75", "-5.5", "-5.25", "-5", "-4.75", "-4.5", "-4.25", "-4", "-3.75", "-3.5", "-3.25", "-3", "-2.75", "-2.5", "-2.25", "-2", "-1.75", "-1.5", "-1.25", "-1", "-0.75", "-0.5", "-0.25", "0", "+0.25", "+0.5", "+0.75", "+1", "+1.25", "+1.5", "+1.75", "+2", "+2.25", "+2.5", "+2.75", "+3", "+3.25", "+3.5", "+3.75", "+4", "+4.25", "+4.5", "+4.75", "+5", "+5.25", "+5.5", "+5.75", "+6", "+6.25", "+6.5", "+6.75", "+7", "+7.25", "+7.5", "+7.75", "+8", "+8.25", "+8.5", "+8.75", "+9", "+9.25", "+9.5", "+9.75", "+10", "+10.25", "+10.5", "+10.75", "+11", "+11.25", "+11.5", "+11.75", "+12", "+12.25", "+12.5", "+12.75", "+13", "+13.25", "+13.5", "+13.75", "+14", "+14.25", "+14.5", "+14.75", "+15", "+15.25", "+15.5", "+15.75", "+16", "+16.25", "+16.5", "+16.75", "+17", "+17.25", "+17.5", "+17.75", "+18", "+18.25", "+18.5", "+18.75", "+19", "+19.25", "+19.5", "+19.75", "+20"};
    public static final String[] Brands = {"ACUVUE", "AIR OPTIX", "PUREVISION", "FRESHLOOK", "SOFLENS", "DAILIES", "BIOMEDICS", "RENU", "OPTIMA", "ЦВЕТНЫЕ", "ОТТЕНОЧНЫЕ"};
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
