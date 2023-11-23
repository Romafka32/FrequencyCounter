package com.example.frequencycounter.service;

import com.example.frequencycounter.exceptions.EmptyInputException;
import com.example.frequencycounter.exceptions.StringTooLongException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Класс сервис для обработки входящей строки.
 * Позволяет подсчитать количество символов, которые входят в строку
 */
@Service
public class FrequencyServiceImpl implements FrequencyService {

    /**
     * Константа для проверки на размер входных данных
     */
    private static final int MAX_STRING = 1024 * 1024;

    /**
     * {@link com.example.frequencycounter.service.FrequencyService#count(String)}
     *
     * @return возвращает строку с результатом подсчета символов в формате "символ":"количество вхождений"
     * @throws com.example.frequencycounter.exceptions.EmptyInputException    если входная строка null или пустая
     * @throws com.example.frequencycounter.exceptions.StringTooLongException если размер входной строки превышает 1MB
     */
    @Override
    public String count(String string) {
        String result;

        if (string == null || string.isEmpty()) {
            throw new EmptyInputException("The input is empty!");
        }

        if (string.length() > MAX_STRING) {
            throw new StringTooLongException("The input string is too long! Max size is 1 MB.");
        }

        if (string.length() == 1) {
            return '“' + string + "“:1";
        }

        Map<String, Integer> map = fillMap(string);

        Map<String, Integer> sortedMap = map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        result = getResultFrequency(sortedMap);
        return result;
    }

    /**
     * Внутренний метод для преобразования мэп с символами в строку
     *
     * @param sortedMap мэп с информацией о символах и их количестве вхождения
     */
    private String getResultFrequency(Map<String, Integer> sortedMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            sb.append('“');
            sb.append(entry.getKey());
            sb.append('”').append(':');
            sb.append(entry.getValue()).append(',');
        }
        if (!(sb.length() == 0)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Внутренний метод для преобразования строки в мэп с символами
     *
     * @param string входная строка для подсчета символов
     */
    private Map<String, Integer> fillMap(String string) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < string.length(); i++) {
            if (map.containsKey(String.valueOf(string.charAt(i)))) {
                int k = map.get(String.valueOf(string.charAt(i)));
                map.put(String.valueOf(string.charAt(i)), k + 1);
            } else {
                map.putIfAbsent(String.valueOf(string.charAt(i)), 1);
            }
        }
        return map;
    }
}