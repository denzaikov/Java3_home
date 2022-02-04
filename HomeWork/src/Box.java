import java.util.ArrayList;

public class Box<T> implements Comparable<Box> {
    private ArrayList<T> obj = new ArrayList<>();

    public Box() {}

    public void addFruit(T fruit){
        obj.add(fruit);
    }

    public <T> float getWeight(){
        float sumWeight = 0;
        ArrayList<T> fruits = (ArrayList<T>) obj;

        for (int i = 0; i < fruits.size() ; i++) {
            Fruit fruit = (Fruit)fruits.get(i);
            sumWeight += fruit.getWeightFruit();
        }
        return sumWeight;
    }

    public void sprinkleFruit(Box<T> box){

        for (int i = 0; i < obj.size(); i++) {
            box.addFruit(obj.get(i));
        }
        obj.clear();

//        for (T fruit: obj) {
//             box.addFruit(fruit);
//             obj.remove(fruit);
//        }
    }
    @Override
    public int compareTo(Box o) {
        return (int) (getWeight() - o.getWeight());
    }
}
