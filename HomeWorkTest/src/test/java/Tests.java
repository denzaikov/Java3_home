
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Tests {

    private static CalculatorApp calculator;

    @BeforeAll
    static void init() {
        calculator = new CalculatorApp();
    }

    public static Stream<Arguments> generatorArrayOffByNumber() {
        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(new int[]{1, 7}, new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}));
        list.add(Arguments.arguments(new int[]{1,7, 5, 3, 2}, new int[]{1, 2, 4, 1, 7, 5, 3, 2}));
        list.add(Arguments.arguments(new int[]{}, new int[]{1, 2, 4}));
        list.add(Arguments.arguments(new int[]{1,7}, new int[]{1,2,4,4,2,3,4,1,7}));
        return list.stream();
    }

    public static Stream<Arguments> generatorThrows() {
        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(new RuntimeException("Массив должен содержать минимум 1 четверку"), new int[]{1, 2, 5, 6}));
        list.add(Arguments.arguments(new RuntimeException("Массив должен содержать минимум 1 четверку"), new int[]{1,2,2,3,1,7}));
        list.add(Arguments.arguments(new RuntimeException("Массив должен содержать минимум 1 четверку"), new int[]{}));
        return list.stream();
    }

    public static Stream<Arguments> generatorBooleanArray() {
        List<Arguments> list = new ArrayList<>();
        list.add(Arguments.arguments(true, new int[]{1, 1, 1, 4, 4, 1, 4, 4}));
        list.add(Arguments.arguments(false, new int[]{1, 1, 1, 1, 1, 1}));
        list.add(Arguments.arguments(false, new int[]{4, 4, 4, 4}));
        list.add(Arguments.arguments(false, new int[]{1, 4, 4, 1, 1, 4, 3 }));
        list.add(Arguments.arguments(false, new int[]{14, 41, 11, 44 }));
        return list.stream();
    }

    @ParameterizedTest
    @MethodSource("generatorArrayOffByNumber")
    public void testArray(int[] result, int[] arr){
        Assertions.assertArrayEquals(result, calculator.resultArray(arr));
    }
    @ParameterizedTest
    @MethodSource("generatorThrows")
    public void testThrows(RuntimeException result, int[] arr){
        Assertions.assertThrows(result.getClass(), () -> calculator.resultArray(arr));
    }

    @ParameterizedTest
    @MethodSource("generatorBooleanArray")
    public void testBool(boolean result, int[] arr){

        Assertions.assertEquals(result, calculator.checkArray(arr));
    }
}