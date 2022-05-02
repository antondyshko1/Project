package api.kuCoinApi;
/**
 * Класс который сортирует ответ, чтобы каждый раз не переопределять метод compare
 */

import java.util.Comparator;

public class TickerComparatorLow implements Comparator<TickerData> { // Имплиментируем интерфейс и указываем калсс который хотим сравнить, имплементируем метод compare()
    @Override
    public int compare(TickerData o1, TickerData o2) {
        float result = Float.compare(Float.parseFloat(o1.getChangeRate()),Float.parseFloat(o2.getChangeRate())); //Получили из объекта ChangeRate, преобразуем строку в тип float(в ответе он String) c помощью parseFloat. Методом compare() сравниваем какой из объектов больше или меньше.
        return (int) result; // Приводим ответ к типу int, так как методе указан возвращаемый тип int
    }
}
