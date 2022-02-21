import java.util.Arrays;

public class CalculatorApp {

    private  int searchedNumber = 4;

    public void main(String[] args) {

        int[] sourceArray = {1, 2, 4, 4, 2, 3, 1, 4, 1, 7};
        System.out.println(Arrays.toString(resultArray(sourceArray)));
    }

    public  int[] resultArray(int[] sourceArray) {

        int lastIndexArray = -1;

        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] == searchedNumber) {
                lastIndexArray = i;
            }
        }
        if (lastIndexArray == -1) {
            throw new RuntimeException("Массив должен содержать минимум 1 четверку");
        }
        int[] resultArray = new int[sourceArray.length - lastIndexArray - 1];
        System.arraycopy(sourceArray, lastIndexArray + 1, resultArray, 0, resultArray.length);
        return resultArray;
    }

    public Boolean checkArray(int[] sourceArray) {

        boolean findFirstElement = false;
        boolean findSecondElement = false;

        for (int i = 0; i < sourceArray.length; i++) {
            if (sourceArray[i] == 1) {
                findFirstElement = true;
            } else if (sourceArray[i] == 4) {
                findSecondElement = true;
            } else return false;
        }
        return findFirstElement & findSecondElement;
    }
}