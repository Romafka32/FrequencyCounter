package com.example.frequencycounter.service;

import org.junit.jupiter.api.Test;

import com.example.frequencycounter.exceptions.EmptyInputException;
import com.example.frequencycounter.exceptions.StringTooLongException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FrequencyServiceImplTest {

    @InjectMocks
    private FrequencyServiceImpl frequencyService;

    @Test
    void countTest() {
        String testString = "aaaabbbcc";
        String expectedResult = "“a”:4,“b”:3,“c”:2";

        String actualResult = frequencyService.count(testString);

        assertEquals(actualResult, expectedResult);
    }

    @Test
    void countEmptyInputExceptionTest() {
        assertThrows(EmptyInputException.class, () -> frequencyService.count(null));
        assertThrows(EmptyInputException.class, () -> frequencyService.count(""));
    }

    @Test
    void countStringTooLongException() {
        String tooLongInput = "a".repeat(1024 * 1024 + 1);

        assertThrows(StringTooLongException.class, () -> frequencyService.count(tooLongInput));
    }
}