import java.util.ArrayList;

public class CenterFinder {

    private ArrayList<ArrayList<Integer>> cycles;
    private ArrayList<ArrayList<Double>> geometries;
    private ArrayList<ArrayList<Integer>> combinations;
    private Double length;
    private Double placement;


    public CenterFinder(ArrayList<ArrayList<Integer>> cycles, ArrayList<ArrayList<Double>> geometries, double span, double interval){
      this.cycles = cycles;
      this.geometries = geometries;
      length = span;
      placement = interval;
      combinations = new ArrayList<>();
    }

    public ArrayList<ArrayList<Double>> atoms(){
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        System.out.println("GEOMETRIES: " + geometries);
        System.out.println("CENTERS");
        for(ArrayList<Integer> a: cycles){
            ArrayList<Double> coord = new ArrayList<>();
            double x = 0.0;
            double y = 0.0;
            double z = 0.0;
            for(int b: a){
                x = x + geometries.get(b-1).get(0);
                y = y + geometries.get(b-1).get(1);
                z = z + geometries.get(b-1).get(2);
                System.out.println(b + ": " + geometries.get(b-1).get(0) + "," + geometries.get(b-1).get(1) + "," + geometries.get(b-1).get(2));



            }
            coord.add(x / (double) a.size());
            coord.add(y / (double) a.size());
            coord.add(z / (double) a.size());
            System.out.println(a + ": " + coord);
            coordinates.add(coord);

            if(length!= 0.0 && placement!= 0.0){
                combinations.clear();
                ArrayList<Integer> data = new ArrayList();
                for(int b: a){
                    data.add(1);
                }

                getCombinations(a,data, 0, a.size()-1, 0,3);

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

                System.out.println(normal);

                Double magnitude = Math.sqrt(normal.get(0)*normal.get(0) + normal.get(1) * normal.get(1) + normal.get(2) * normal.get(2));

                normal.set(0, normal.get(0) / magnitude);
                normal.set(1, normal.get(1) / magnitude);
                normal.set(2, normal.get(2) / magnitude);

                System.out.println( "Unit Vector" + normal);

                for(Double i = placement; i<=length; i+= placement){
                    ArrayList<Double> above = new ArrayList<>();
                    above.add(coord.get(0) + (i * normal.get(0)));
                    above.add(coord.get(1) + (i * normal.get(1)));
                    above.add(coord.get(2) + (i * normal.get(2)));

                    coordinates.add(above);
                }

                for(Double i = placement; i<= length; i+= placement){
                    ArrayList<Double> above = new ArrayList<>();
                    above.add(coord.get(0) - (i * normal.get(0)));
                    above.add(coord.get(1) - (i * normal.get(1)));
                    above.add(coord.get(2) - (i * normal.get(2)));

                    coordinates.add(above);
                }



            }





        }


        return coordinates;

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
