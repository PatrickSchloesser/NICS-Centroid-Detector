import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultReader {

    private File inputFile;
    private int atoms;
    private ArrayList<ArrayList<Double>> coords;
    private ArrayList<Double> values;
    private ArrayList<String> names;

    public ResultReader(File file, Integer atoms){
        inputFile = file;
        this.atoms = atoms;

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String oline = br.readLine();
            System.out.println(oline);
             oline = br.readLine();
             System.out.println(!(oline = br.readLine().trim()).equals("Population analysis using the SCF Density."));

             coords = new ArrayList<>();
             values = new ArrayList<>();
             names  = new ArrayList<>();

            while (!(oline = br.readLine().trim()).equals("Population analysis using the SCF Density.")) {
//                System.out.println(0);
//                System.out.println(oline);

                String trimmedLine = oline.trim();
//                System.out.println(2);

                List<String> inspector = new ArrayList<String>(Arrays.asList(oline.split("\\s+")));
//                System.out.println(3);

//                System.out.println(inspector);

                // System.out.println(Arrays.toString(inspector));

                if (inspector.get(0).contains("Bq")) {
                    ArrayList<Double> coordinates = new ArrayList<>();
                    coordinates.add(Double.parseDouble(inspector.get(1)));
                    coordinates.add(Double.parseDouble(inspector.get(2)));
                    coordinates.add(Double.parseDouble(inspector.get(3)));

//                    System.out.println(coordinates);

                    coords.add(coordinates);
                }
                System.out.println(4);

                if(inspector.size() > 1) {
                    if (inspector.get(1).equals("Bq") && inspector.get(2).equals("Isotropic")) {
                        names.add(inspector.get(0));
                        values.add(Double.parseDouble(inspector.get(4)) * -1.0);

                    }
                }

            }
        }
            catch(Exception e){
                e.printStackTrace();
            }

//            System.out.println(names);
//            System.out.println(coords);
//            System.out.println(values);


}

    public ArrayList<String> interpret(){
        ArrayList<String> csv = new ArrayList<>();

        csv.add("Ghost Atom,NICS Value, Distance from Centroid (Angstroms)");
        int centroids = names.size()/ (atoms+1);

        for(int i = 0; i< names.size(); i = i + ((2*atoms)+1)){
            csv.add(names.get(i) + "," + values.get(i) + "," + 0.0);
//            System.out.println(csv.get(i+1));
            for(int j = i+1; j<= i+ 2*atoms; j++ ){
                double distance = Math.sqrt(Math.pow((coords.get(i).get(0)-coords.get(j).get(0)),2) +
                        Math.pow((coords.get(i).get(1)-coords.get(j).get(1)),2) +
                        Math.pow((coords.get(i).get(2)-coords.get(j).get(2)),2));

                csv.add(names.get(j) + "," + values.get(j) + "," + distance);
//                System.out.println(csv.get(j+1));
            }
        }




        return csv;
    }
}
