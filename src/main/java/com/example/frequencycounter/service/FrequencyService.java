package com.example.frequencycounter.service;

/**
 * Интерфейс сервисного слоя для реализации логики подсчета количества символов в строке
 */
public interface FrequencyService {

    /**
     * Метод для подсчета количества символов в строке
     *
     * @param string входная строка
     */
    String count(String string);
}
