import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegerDecodeTest {

    // 1. Пустая строка
    @Test
    void testEmptyString() {
        assertThrows(NumberFormatException.class, () -> Integer.decode(""));
    }

    // 2. Минус
    @Test
    void testNegativeDecimal() {
        assertEquals(-123, Integer.decode("-123"));
    }

    // 3. Плюс
    @Test
    void testPositiveWithPlus() {
        assertEquals(456, Integer.decode("+456"));
    }

    // 4. Hex: 0x
    @Test
    void testHex0x() {
        assertEquals(255, Integer.decode("0xFF"));
    }

    // 5. Hex: #
    @Test
    void testHexHash() {
        assertEquals(255, Integer.decode("#FF"));
    }

    // 6. Octal
    @Test
    void testOctal() {
        assertEquals(83, Integer.decode("0123"));  // 1*64 + 2*8 + 3 = 83
    }

    // 7. Decimal
    @Test
    void testDecimal() {
        assertEquals(789, Integer.decode("789"));
    }

    // 8. Ошибка внутри valueOf() (неверный octal)
    @Test
    void testInvalidOctal() {
        assertThrows(NumberFormatException.class, () -> Integer.decode("09"));
    }

    // 9. Неверный hexadecimal
    @Test
    void testInvalidHex() {
        assertThrows(NumberFormatException.class, () -> Integer.decode("0xZZ"));
    }

    // 10. "0" — отдельный путь (не octal!)
    @Test
    void testZero() {
        assertEquals(0, Integer.decode("0"));
    }

    // 11. Octal with minus
    @Test
    void testNegativeOctal() {
        assertEquals(-63, Integer.decode("-077")); // 7*8 + 7 = 63
    }
}
