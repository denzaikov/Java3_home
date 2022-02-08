public class Fruit<T> {
    private int numFruit;
    private float weightFruit;

    public Fruit(int numFruit, float weightFruit) {
        this.numFruit = numFruit;
        this.weightFruit = weightFruit;
    }

    public float getWeightFruit() {
        return weightFruit;
    }
}
