package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDecodeTest {

    // 1. Пустая строка → исключение
    @Test
    void testEmptyString() {
        assertThrows(NumberFormatException.class, () -> Integer.decode(""));
    }

    // 2. Первый символ '-'
    @Test
    void testNegativeDecimal() {
        assertEquals(-123, Integer.decode("-123"));
    }

    // 3. Первый символ '+'
    @Test
    void testPositiveWithPlus() {
        assertEquals(456, Integer.decode("+456"));
    }

    // 4. Без знака, десятичное число
    @Test
    void testDecimal() {
        assertEquals(789, Integer.decode("789"));
    }

    // 5. Hex с префиксом "0x"
    @Test
    void testHex0xLower() {
        assertEquals(255, Integer.decode("0xFF"));
    }

    // 6. Hex с префиксом "0X"
    @Test
    void testHex0xUpper() {
        assertEquals(255, Integer.decode("0XFF"));
    }

    // 7. Hex с префиксом "#"
    @Test
    void testHexHash() {
        assertEquals(255, Integer.decode("#FF"));
    }

    // 8. Octal (начинается с '0' и длина > 1)
    @Test
    void testOctal() {
        assertEquals(83, Integer.decode("0123"));  // 83 = 1*64 + 2*8 + 3
    }

    // 9. Отрицательное octal
    @Test
    void testNegativeOctal() {
        assertEquals(-63, Integer.decode("-077"));
    }

    // 10. Ноль (крайний случай)
    @Test
    void testZero() {
        assertEquals(0, Integer.decode("0"));
    }

    // 11. Некорректный octal → исключение
    @Test
    void testInvalidOctal() {
        assertThrows(NumberFormatException.class, () -> Integer.decode("09"));
    }

    // 12. Некорректный hex → исключение
    @Test
    void testInvalidHex() {
        assertThrows(NumberFormatException.class, () -> Integer.decode("0xZZ"));
    }

    // 13. Исключение: знак в неправильной позиции
    @Test
    void testSignCharacterWrongPosition() {
        assertThrows(NumberFormatException.class, () -> Integer.decode("123-45"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0x-FF"));
    }
}
