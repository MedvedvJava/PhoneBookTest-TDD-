import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PhoneBookTest {
    private static PhoneBook testPhoneBook;

    @BeforeAll
    public static void started() {
        System.out.println("All test starts!");
        testPhoneBook = new PhoneBook();
    }

    @ParameterizedTest
    @MethodSource("sourceAdd")
    public void testAdd(String inputName, String inputPhone, int expected) {
        int result = testPhoneBook.add(inputName, inputPhone);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> sourceAdd() {
        return Stream.of(
                Arguments.of("Vasya", "89031901362", 1),
                Arguments.of("Vasya", "89031901362", 1),
                Arguments.of("Petya", "89031901362", 2)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceAddNull")
    public void testAddNull(String inputName, String inputPhone) {
        assertThrows(NullPointerException.class, () -> testPhoneBook.add(inputName, inputPhone));
    }

    private static Stream<Arguments> sourceAddNull() {
        return Stream.of(
                Arguments.of(null, "89031901362"),
                Arguments.of("Petya", null),
                Arguments.of(null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceFindByNumber")
    public void testFindByNumber(String inputPhone, String expected) {
        testPhoneBook.add("Vasya", "89031901362");
        String result = testPhoneBook.findByNumber(inputPhone);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> sourceFindByNumber() {
        return Stream.of(
                Arguments.of("89031901362", "Vasya"),
                Arguments.of("", null),
                Arguments.of("89031112233", null)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceFindByName")
    public void testFindByName(String inputName, String expected) {
        testPhoneBook.add("Vasya", "89031901362");
        String result = testPhoneBook.findByName(inputName);
        assertEquals(expected, result);
    }

    private static Stream<Arguments> sourceFindByName() {
        return Stream.of(
                Arguments.of("Vasya", "89031901362"),
                Arguments.of("", null),
                Arguments.of("Ismail", null)
        );
    }

    @Test
    public void testPrintAllNames() {
        testPhoneBook.add("Petya", "89031901362");
        testPhoneBook.add("Anya", "89991112233");
        testPhoneBook.add("Igor", "89111231212");
        testPhoneBook.add("Vasya", "89111231219");

        Set<String> resultNames = testPhoneBook.printAllNames();
        Set<String> expectedNames = new TreeSet<>();
        expectedNames.addAll(Set.of("Vasya", "Igor", "Anya", "Petya"));
        assertEquals(expectedNames, resultNames);
    }

    @AfterAll
    public static void finishedAll() {
        testPhoneBook = null;
        System.out.println("We made all tests!");
    }
}
