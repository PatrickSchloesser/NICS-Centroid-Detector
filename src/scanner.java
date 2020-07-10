import java.util.ArrayList;

public class scanner {

    private ArrayList<ArrayList<Double>> geometries;
    private ArrayList<ArrayList<Integer>> combinations;
    private ArrayList<Integer> atoms;
    private double height;


    public scanner(Double xInterval, Double height, ArrayList<ArrayList<Double>> coordinates, ArrayList<ArrayList<ArrayList<Double>>> features, ArrayList<Integer> atoms ){

        geometries = coordinates;
        combinations = new ArrayList<>();
        this.atoms = atoms;
        this.height = height;
        setup();




    }

    private void setup(){
        ArrayList<Double> norm = findNormal();

        double x = 0.0;
        double y = 0.0;
        double z = 0.0;

        for(int a: atoms){
            x+= geometries.get(a-1).get(0);
            y+= geometries.get(a-1).get(0);
            z+= geometries.get(a-1).get(0);

        }

        x = x / atoms.size();
        y = y / atoms.size();
        z = z / atoms.size();

        // find plane equation here

        x+= norm.get(0) * height;
        y+= norm.get(1) * height;
        z+= norm.get(2) * height;

        Double d = (x * norm.get(0)) + (y * norm.get(1)) + (z * norm.get(2));

    }

    public ArrayList<ArrayList<Double>> compute(){





        return null;
    }

    private ArrayList<Double> findNormal(){

        combinations.clear();

        ArrayList<Integer> data = new ArrayList<>();
        for(int a: atoms){
            data.add(1);
        }
        getCombinations(atoms,data, 0, atoms.size()-1, 0,3);


        ArrayList<Double> normal = new ArrayList<>();
        normal.add(0.0);
        normal.add(0.0);
        normal.add(0.0);

        for(ArrayList<Integer> c: combinations){

            ArrayList<Double> firstVector = new ArrayList();
            ArrayList<Double> secondVector = new ArrayList<>();

            firstVector.add(geometries.get(c.get(1)-1).get(0) - geometries.get(c.get(0)-1).get(0));
            firstVector.add(geometries.get(c.get(1)-1).get(1) - geometries.get(c.get(0)-1).get(1));
            firstVector.add(geometries.get(c.get(1)-1).get(2) - geometries.get(c.get(0)-1).get(2));

            secondVector.add(geometries.get(c.get(2)-1).get(0) - geometries.get(c.get(0)-1).get(0));
            secondVector.add(geometries.get(c.get(2)-1).get(1) - geometries.get(c.get(0)-1).get(1));
            secondVector.add(geometries.get(c.get(2)-1).get(2) - geometries.get(c.get(0)-1).get(2));

            normal.set(0, normal.get(0) + (firstVector.get(1) * secondVector.get(2)) - (firstVector.get(2) * secondVector.get(1)));
            normal.set(1, normal.get(1) + (firstVector.get(2) * secondVector.get(0)) - (firstVector.get(0) * secondVector.get(2)));
            normal.set(2, normal.get(2) + (firstVector.get(0) * secondVector.get(1)) - (firstVector.get(1) * secondVector.get(0)));

        }

        normal.set(0, normal.get(0) / combinations.size());
        normal.set(1, normal.get(1) / combinations.size());
        normal.set(2, normal.get(2) / combinations.size());
        Double magnitude = Math.sqrt(normal.get(0)*normal.get(0) + normal.get(1) * normal.get(1) + normal.get(2) * normal.get(2));

        normal.set(0, normal.get(0) / magnitude);
        normal.set(1, normal.get(1) / magnitude);
        normal.set(2, normal.get(2) / magnitude);

        return normal;
    }

    private ArrayList<Double> getCombinations(ArrayList<Integer> cycle, ArrayList<Integer> data, int start,
                                              int end, int index, int k){

        if (index == k)
        {
            ArrayList<Integer> acc = new ArrayList<>();
            for (int j=0; j<k; j++) {
                acc.add(data.get(j));
            }
            combinations.add(acc);


        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= k-index; i++)
        {
            data.set(index,cycle.get(i));
            getCombinations(cycle, data, i+1, end, index+1, k);
        }


        return null;
    }




}
