package api.kuCoinApi;

import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

/**
 * Класс для написания тестов.
 */
public class StreamApiExamples {
    /**
     * Методот который возвращает спиок с данными из ответа
     */
    public List<TickerData> getTickers() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.kucoin.com/api/v1/market/allTickers")
                .then().log().body()
                .extract().jsonPath().getList("data.ticker", TickerData.class); // Извлекаем данные из ответа в класс, указав путь что извлекаем и куда
    }

    /**
     * Отфильтровать все криптоволюты, которые можно купить за USDT
     */
    @Test
    public void checkCrypto() { // В спиок usdTickers помещаем все валюты, которые можно обменять на доллар.
        List<TickerData> usdTickers = getTickers().stream().filter(x -> x.getSymbol().endsWith("USDT")).collect(Collectors.toList()); // Вызываем метод, котрый возвращает спиок из ответа, с помощю stream() проходимся по нему, filter() фильтрует, чтобы symbol оканчивался на USDT. collect(Collectors.toList()) добавляет всю отфильтрованную информацию в список usdTickers
        Assert.assertTrue(usdTickers.stream().allMatch(x -> x.getSymbol().endsWith("USDT")));// Проверяем, что в списке usdTickers все symbol оканчиваются на USDT. allMatch() проверяет все совпадения, если хотябы один не совпадёт вернёт false

    }

    /**
     * Отсортировать криптовалюту которая больше всего выросла в цене за сутки.
     * Собрать список топ 10 криптовалют выросших за сутки.
     */
    @Test // Тест без использования отдельного класса для sorted()
    public void sortHighToLow() {
        List<TickerData> highToLow = getTickers().stream().filter(x -> x.getSymbol().endsWith("USDT")).sorted(new Comparator<TickerData>() {// фильтруем список криптоволют, которые оканчиваются на USDT, методом sorted() сортируем полученный список
            @Override
            public int compare(TickerData o1, TickerData o2) { //Метод сортирует два любых объекта( которые выберем) o1 и o2 это объекты из списка
                return o2.getChangeRate().compareTo(o1.getChangeRate()); //Сортируем от дорогого к дешевому, начинаем со 2 объекта
            }
        }).collect(Collectors.toList());//Собираем всё в список
        // Список с топ 10 валют вырасших за сутки
        List<TickerData> top10 = highToLow.stream().limit(10).collect(Collectors.toList());// limit(10) выдаёт 10 значений из списка
        Assert.assertEquals(top10.get(0).getSymbol(), "BRISE-USDT"); // Сравниваем, что у 0 элемента из списка top10 symbol содержит "BRISE-USDT"
    }

    /**
     * Отсортировать криптовалюту, которая хуже всего выросла в цене за сутки.
     * Вы вести топ 10 валют, которые упали в цене
     */
    @Test // Тест с созданием класса TickerComparatorLow в котором переопределяется метод compare
    public void sortLowToHigh() {
        List<TickerData> lowToHigh = getTickers().stream().filter(x -> x.getSymbol().endsWith("USDT"))//Создаем список, фильтруем валюты которые оканчиваются на USDT
                .sorted(new TickerComparatorLow()).limit(10).collect(Collectors.toList());// Методом sorted() сортируем валюты которые сильнее всего упали в цене и выводим топ 10

    }

    /**
     * Создание хэш карты с данными из ответа
     */
    @Test
    public void map() {
        Map<String, Float> usd = new HashMap<>(); // Создали хэш карту для хранения валюты. ключ - название, значение - цена.
        List<String> lowerCases = getTickers().stream().map(x -> x.getSymbol().toLowerCase()).collect(Collectors.toList()); // Метод map() преобразует один тип данных в другой. Достали символ из списка ( getSymbol), преобразовали его toLowerCase()(преобразовали в нижний регистр) и добавили в коллекцию lowerCases
        getTickers().forEach(x -> usd.put(x.getSymbol(), Float.parseFloat(x.getChangeRate())));// Заполняем карту usd с помощью forEach, put() добавляет элементы в карту (ключ, значение)
    }

    /**
     * Создание списка с данными без хэш карты, при помощи дополнительного класса, где хранится ответ
     */
    @Test
    public void usdShort() {
        List<TickerShort> shortList = new ArrayList<>();// Создали список shortList с экземплярами класса TickerShort
        getTickers().forEach(x -> shortList.add(new TickerShort(x.getSymbol(), Float.parseFloat(x.getChangeRate()))));// Заполнение списка данными из ответа, список хранит объект с двумя значениями из класса TickerShort

    }
}