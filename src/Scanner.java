import java.lang.reflect.Array;
import java.util.ArrayList;

public class Scanner {

    private ArrayList<ArrayList<Double>> geometries;
    private ArrayList<ArrayList<Integer>> combinations;
    private ArrayList<Integer> atoms;
    private double height;
    private double xInterval;
    private ArrayList<ArrayList<ArrayList<Double>>> features;
    private ArrayList<Double> norm;
    private double d;


    public Scanner(Double xInterval, Double height, ArrayList<ArrayList<Double>> coordinates, ArrayList<ArrayList<ArrayList<Double>>> features, ArrayList<Integer> atoms ){

        geometries = coordinates;
        combinations = new ArrayList<>();
        this.atoms = atoms;
        this.height = height;
        this.features = features;
        this.xInterval = xInterval;
        setup();




    }

    private void setup(){
         norm = findNormal();

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

         d = (x * norm.get(0)) + (y * norm.get(1)) + (z * norm.get(2));

    }

    public ArrayList<ArrayList<Double>> compute(){
        ArrayList<ArrayList<Double>> totalCoords = new ArrayList<>();

        for( ArrayList<ArrayList<Double>> a: features){
             ArrayList<ArrayList<Double>> finalCoords = new ArrayList<>();


            for( ArrayList<Double> b: a){
                // If it is a set of coordinates
                if(b.get(0).equals(0.0)){
                    ArrayList<Double> threeDim = new ArrayList(b.subList(1,3));
                    finalCoords.add(threeDim);
                }
                // if it is a specific atom
                else if(b.get(0).equals(1.0)){
                    finalCoords.add(geometries.get((int) (b.get(1) -1)));
                }
                // If it is a pair of atoms
                else if(b.get(0).equals(2.0)){
                    ArrayList<Double> bondAve = new ArrayList<>();
                    ArrayList<Double> firstBond = geometries.get((int) (b.get(1) - 1));
                    ArrayList<Double> secondBond = geometries.get((int) (b.get(2) - 1));
                    bondAve.add((firstBond.get(0) + secondBond.get(0))/2);
                    bondAve.add((firstBond.get(1) + secondBond.get(1))/2);
                    bondAve.add((firstBond.get(2) + secondBond.get(2))/2);

                    finalCoords.add(bondAve);

                }
                // If it is a ring
                else if(b.get(0).equals(3.0)){
                    b.remove(0);
                    double xSum = 0;
                    double ySum = 0;
                    double zSum = 0;
                    for(Double d: b){
                        ArrayList<Double> localCoords = geometries.get( (int) (d - 1));
                        xSum += localCoords.get(0);
                        ySum += localCoords.get(1);
                        zSum += localCoords.get(2);
                    }
                    xSum = xSum/ b.size();
                    ySum = ySum/ b.size();
                    zSum = zSum/ b.size();

                    ArrayList<Double> centroidCoords = new ArrayList<>();
                    centroidCoords.add(xSum);
                    centroidCoords.add(ySum);
                    centroidCoords.add(zSum);
                    finalCoords.add(centroidCoords);

                }
            }

            for(int i = 0; i< finalCoords.size(); i++){
                finalCoords.set(0, convertToPlane(finalCoords.get(0)));

            }

            ArrayList<ArrayList<Double>> vectors = new ArrayList<>();

            for(int i = 1; i< finalCoords.size(); i++){
                ArrayList<Double> localVector = new ArrayList<>();

                Double magnitude = Math.sqrt((finalCoords.get(i).get(0) - finalCoords.get(i-1).get(0))*(finalCoords.get(i).get(0) - finalCoords.get(i-1).get(0)) +
                        (finalCoords.get(i).get(1) - finalCoords.get(i-1).get(1))*(finalCoords.get(i).get(1) - finalCoords.get(i-1).get(1)) +
                        (finalCoords.get(i).get(2) - finalCoords.get(i-1).get(2))*(finalCoords.get(i).get(2) - finalCoords.get(i-1).get(2)));

                localVector.add((finalCoords.get(i).get(0) - finalCoords.get(i-1).get(0))/ magnitude);
                localVector.add((finalCoords.get(i).get(1) - finalCoords.get(i-1).get(1))/ magnitude);
                localVector.add((finalCoords.get(i).get(2) - finalCoords.get(i-1).get(2))/ magnitude);
                vectors.add(localVector);

            }


            for(int i = 1; i< finalCoords.size(); i++){
                totalCoords.add(finalCoords.get(0));
                Double distance = Math.sqrt((finalCoords.get(i).get(0) - finalCoords.get(i-1).get(0))*(finalCoords.get(i).get(0) - finalCoords.get(i-1).get(0)) +
                        (finalCoords.get(i).get(1) - finalCoords.get(i-1).get(1))*(finalCoords.get(i).get(1) - finalCoords.get(i-1).get(1)) +
                        (finalCoords.get(i).get(2) - finalCoords.get(i-1).get(2))*(finalCoords.get(i).get(2) - finalCoords.get(i-1).get(2)));
                Double distMeasure = (double) xInterval;



                while(distMeasure < distance){
                     ArrayList<Double> scanLocalCoord = new ArrayList<>();
                     scanLocalCoord.add(finalCoords.get(i-1).get(0) + (vectors.get(i-1).get(0)*xInterval));
                     scanLocalCoord.add(finalCoords.get(i-1).get(1) + (vectors.get(i-1).get(1)*xInterval));
                     scanLocalCoord.add(finalCoords.get(i-1).get(2) + (vectors.get(i-1).get(2)*xInterval));
                     totalCoords.add(scanLocalCoord);
                     distMeasure+= xInterval;
                }

                totalCoords.add(finalCoords.get(i));
            }
        }




        return totalCoords;
    }

    private ArrayList<Double> convertToPlane(ArrayList<Double> coords){

        double t = (d- ((coords.get(0)* norm.get(0)) + (coords.get(1)* norm.get(1)) + (coords.get(2)* norm.get(2)))/ ((norm.get(0)* norm.get(0))+(norm.get(1)* norm.get(1))+(norm.get(2)* norm.get(2))));

        double x = coords.get(0) + (norm.get(0) *t);
        double y = coords.get(1) + (norm.get(1) *t);
        double z = coords.get(2) + (norm.get(2) *t);
        ArrayList<Double> newCoords = new ArrayList<>();
        newCoords.add(x);
        newCoords.add(y);
        newCoords.add(z);


        return newCoords;
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
