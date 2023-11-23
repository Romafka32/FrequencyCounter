package com.example.frequencycounter.controller;

import com.example.frequencycounter.service.FrequencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс контроллер для обработки входящей строки.
 * Позволяет подсчитать количество символов, которые входят в строку
 */
@RestController
@RequestMapping("api/count")
@Tag(name = "Контроллер приложения", description = "Обрабатывает строку из POST-запроса " +
        "и возвращает строку с результатами подсчета вхождения символов в строку ")
public class FrequencyController {

    /**
     * Константа для проверки на размер входных данных
     */
    private static final int MAX_STRING = 1024 * 1024;

    /**
     * Поле сервиса для обработки входной строки
     */
    @Autowired
    FrequencyService frequencyService;

    /**
     * Метод для подсчета количества символов во входной строке
     *
     * @param line входная строка
     * @return  в случае успешной отработки возвращает HTTP 200 и строку с результатом подсчета символов.
     * В случае ошибки возвращает HTTP 400 и сообщение об ошибке
     */
    @Operation(
            summary = "Обработка входящей строки",
            description = "Возвращает строку, которая в заданном формате описывает символы их количество во входной строке",
            parameters = @Parameter(name = "line", description = "Входящая строка для подсчета символов. Ограничение по размеру строки — 1 MB")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping
    public ResponseEntity<String> counter(@RequestBody(required = false)
                                          @NotBlank
                                          @Size(max = MAX_STRING)
                                          String line) {
        if (StringUtils.isBlank(line)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The input is empty!");
        }
        return ResponseEntity.ok(frequencyService.count(line));
    }
}
