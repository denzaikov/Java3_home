import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/*
Даны классы Fruit, Apple extends Fruit, Orange extends Fruit;
Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
Для хранения фруктов внутри коробки можно использовать ArrayList;
Сделать метод getWeight(), который высчитывает вес коробки, зная вес одного фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f
 (единицы измерения не важны);
Внутри класса Box сделать метод compare(), который позволяет сравнить текущую коробку с той, которую подадут в compare() в качестве параметра.
 true – если их массы равны, false в противоположном случае. Можно сравнивать коробки с яблоками и апельсинами;
Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую. Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами.
 Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в первой;
Не забываем про метод добавления фрукта в коробку.
 */
public class Main {
    private static float min = 1.0f;
    private static float max = 1.5f;

    public static void main(String[] args) {
        //задача 1
        ArrayList<String> fruits = new ArrayList<>(Arrays.asList("яблоко", "апельсин", "мандарин", "банан", "манго", "ананас", "киви"));
        System.out.println("\n" + fruits);
        ArrayList resultSwapFruits = task1(fruits, 1, 3);
        System.out.println("\n" + resultSwapFruits);

        ArrayList<Integer> array = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\n" + array);
        ArrayList resultSwapArray = task1(array, 2, 4);
        System.out.println("\n" + resultSwapArray);

        //задача 2
        String[] arrayFruits = {"яблоко", "апельсин", "мандарин"};
        System.out.println("\n" + arrayFruits.getClass());
        ArrayList resultConvert = task2(arrayFruits);
        System.out.println("\n" + resultConvert.getClass());
        System.out.println("\n" + resultConvert);

        //задача 3
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        addFruitOnBox(appleBox, 15);
        addFruitOnBox(orangeBox, 12);

        System.out.println("Вес коробки с яблоками = "+ appleBox.getWeight());
        System.out.println("Вес коробки с апельсинами = "+ orangeBox.getWeight());

        if (appleBox.compareTo(orangeBox) == 0) {
            System.out.println("Коробки равны по весу");
        } else System.out.println("Коробки не равны по весу");

        Box<Apple> appleBox1 = new Box<>();
        Box<Orange> orangeBox1 = new Box<>();
        orangeBox.sprinkleFruit(orangeBox1);
        appleBox.sprinkleFruit(appleBox1);
    }

    //Задание №1 Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
    private static <T> ArrayList<T> task1(ArrayList<T> array, int currentIndex, int nextIndex) {
        swap(array, currentIndex, nextIndex);
        return array;
    }

    //Написать метод, который преобразует массив в ArrayList;
    private static <T> ArrayList<T> task2(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    private static <T> void swap(ArrayList<T> array, int i, int j) {
        Object firstElement = array.get(i);
        array.set(i, array.get(j));
        array.set(j, (T) firstElement);
    }

    private static <T> void addFruitOnBox(Box<T> fruitForBox, int col){

        for (int i = 0; i < col; i++) {
            float newWeightFruit = randFloat(min, max);
            fruitForBox.addFruit((T) new Fruit<T>(i, newWeightFruit));
        }
    }

    public static float randFloat(float min, float max) {
        Random rand = new Random();
        return rand.nextFloat() * (max - min) + min;
    }
}

