package ru.denzaikov.geekbrains;

public class MyTest {

    @BeforeSuite
    public static void test1() {
        System.out.println("test1 BeforeSuite");
    }

    @Test
    public static void test2() {
        System.out.println("test2");
    }

    @Test
    public static void test3() {
        System.out.println("test3");
    }

    @Test(priority = 2)
    public static void test4() {
        System.out.println("test4 priority = 2");
    }

    @Test(priority = 7)
    public static void test5() {
        System.out.println("test5 priority = 7");
    }
    @Test(priority = 1)
    public static void test6() {
        System.out.println("test6 priority = 1");
    }

    @AfterSuite
    public static void test7After(){
        System.out.println("test7 AfterSuite");
    }
}
