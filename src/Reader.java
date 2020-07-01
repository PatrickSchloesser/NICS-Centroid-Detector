import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {

    private File optimization;

    private  ArrayList<ArrayList<Double>> atoms;
    private ArrayList<String> connectivityLines;

    public Reader(File file, String connectivity){
        optimization = file;
        String s[] = connectivity.split("\\r?\\n");
        connectivityLines = new ArrayList<>(Arrays.asList(s));

    }


    public ArrayList getConnectivitySimple(){

//        try {
//            BufferedReader br = new BufferedReader(new FileReader(optimization));
//            String oline = br.readLine();
//            System.out.println(oline);
//            System.out.println(oline.trim());
//
//            if(oline.trim() == "Entering Link 1 = C:\\G16W\\l1.exe PID=     24876."){
//                System.out.println(1);
//            }
//
//            while((oline = br.readLine())!= null){
//
//                String[] inspector = oline.split( " ");
//                System.out.println(Arrays.toString(inspector));
//                if(inspector.length > 2) {
//                    if(inspector[2].equals("opt")){
//                        break;
//                }
//                }
//
//            }
//
//            System.out.println(oline);
//            br.readLine();
//            String line = null;
            atoms = new ArrayList<ArrayList<Double>>();
            for(String s: connectivityLines){
//            while(!(line = br.readLine().trim()).equals("-------------------")){
//               ArrayList<String> numbers = (ArrayList<String>) Arrays.asList(line.split(" "));
//               System.out.println(numbers.get(0));
                String j = s.trim();
//                System.out.println("Trimmed:" + s);
                List<String> myList = new ArrayList<String>(Arrays.asList(j.split(" ")));
//                System.out.println(Arrays.toString(myList.toArray()));

                ArrayList<Double> converted = new ArrayList(myList.size()-1);
                if(myList.size() > 0) {
                    for (int i = 0; i < myList.size(); i++) {
                        converted.add(Double.parseDouble(myList.get(i)));
//                        System.out.println(i);
                    }

                }

                System.out.println(Arrays.toString(converted.toArray()));

                atoms.add(converted);




           }




//            for(int i =0; i < atoms.size(); i++){
//                    for(int j = 0; j < i; j++){
//                        for(int k = 0; k < atoms.get(j).size(); k=k+2){
//                            if(atoms.get(j).get(k) == i + 1){
//                                atoms.get(i).add((double) j+1);
//                                atoms.get(i).add(atoms.get(j).get(k+1));
//                            }
//                        }
//                    }
//
//            }
//
            System.out.println(atoms);

//            System.out.println(Arrays.toString(atoms.toArray()));

            return(atoms);


//        }
//        catch(Exception e) {
//            System.out.print(e);
//        }



    }


    public ArrayList getConnectivityComplex(){

//        try {
//            BufferedReader br = new BufferedReader(new FileReader(optimization));
//            String oline = br.readLine();
//            System.out.println(oline);
//            System.out.println(oline.trim());
//
//            if(oline.trim() == "Entering Link 1 = C:\\G16W\\l1.exe PID=     24876."){
//                System.out.println(1);
//            }
//
//            while((oline = br.readLine())!= null){
//
//                String[] inspector = oline.split( " ");
//                System.out.println(Arrays.toString(inspector));
//                if(inspector.length > 2) {
//                    if(inspector[2].equals("opt")){
//                        break;
//                    }
//                }
//
//            }

//            System.out.println(oline);
//            br.readLine();
//            String line = null;
            atoms = new ArrayList<ArrayList<Double>>();
            for(String s: connectivityLines){
//               ArrayList<String> numbers = (ArrayList<String>) Arrays.asList(line.split(" "));
//               System.out.println(numbers.get(0));
                if(s.trim().length() != 0) {
                    String j = s.trim();
//                    System.out.println("Trimmed:" + s);
                    List<String> myList = new ArrayList<String>(Arrays.asList(j.split(" ")));
//                    System.out.println(Arrays.toString(myList.toArray()));

                    ArrayList<Double> converted = new ArrayList(myList.size() - 1);
                    if (myList.size() > 1) {
                        for (int i = 1; i < myList.size(); i++) {
                            converted.add(Double.parseDouble(myList.get(i)));
                        }

                    }

//                    System.out.println(Arrays.toString(converted.toArray()));

                    atoms.add(converted);


                }
            }




            for(int i =0; i < atoms.size(); i++) {
                for (int j = 0; j < i; j++) {
                    for (int k = 0; k < atoms.get(j).size(); k = k + 2) {
                        if (atoms.get(j).get(k) == i + 1) {
                            atoms.get(i).add((double) j + 1);
                            atoms.get(i).add(atoms.get(j).get(k + 1));
                        }
                    }
                }

            }
            System.out.println(atoms);

//            System.out.println(Arrays.toString(atoms.toArray()));

            return(atoms);


//        }
//        catch(Exception e) {
//            System.out.print(e);
//        }



    }

//    public ArrayList allConnections(){
//        for(int i =0; i < atoms.size(); i++){
//            for(int j = 0; j < i; j++){
//                for(int k = 0; k < atoms.get(j).size(); k=k+2){
//                    if(atoms.get(j).get(k) == i + 1){
//                        atoms.get(i).add((double) j+1);
//                        atoms.get(i).add(atoms.get(j).get(k+1));
//                    }
//                }
//            }
//
//        }
//
//        return atoms;
//
//    }

    public ArrayList<String> getChargeAndMultiplicity() {

        ArrayList<String> chMult = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(optimization));
            String oline = br.readLine();

            while ((oline = br.readLine()) != null) {
                oline.trim();
                String[] inspector = oline.split(" ");
                if (inspector.length > 5) {
                    if (inspector[1].equals("Charge")) {
                        chMult.add(Integer.parseInt(inspector[4]) + " " + Integer.parseInt(inspector[7]));

                        while((oline = br.readLine()).split(" ").length !=0 ){
                            chMult.add(oline.trim());
                        }
                        break;
                    }
                }

            }
        }
        catch(Exception e) {
            System.out.print(e);
        }

        return chMult;
    }

    public ArrayList getCoordinates(){


        try {
            BufferedReader br = new BufferedReader(new FileReader(optimization));
            String oline = br.readLine();
           // System.out.println(oline);
           // System.out.println(oline.trim());

            if(oline.trim() == "Entering Link 1 = C:\\G16W\\l1.exe PID=     24876."){
                System.out.println(1);
            }

            int overflow = 0;

            while((oline = br.readLine())!= null){
                overflow++;
                if(overflow> 500){
                    System.out.println("Overflow VALUE: " + overflow);
                    break;
                }
                String trimmedLine = oline.trim();
                String[] inspector = oline.split( " ");
               // System.out.println(Arrays.toString(inspector));
                if(trimmedLine.equals("Input orientation:")) {
                    System.out.println("Found Coordinates");
                    break;
                }

            }

            for( int i =0; i<4; i++){
                br.readLine();
            }
            System.out.println(oline);
            String line = null;
            ArrayList<ArrayList<Double>> atoms = new ArrayList<>();
            if(overflow < 500) {
                while (!(line = br.readLine().trim()).equals("---------------------------------------------------------------------")) {
//               ArrayList<String> numbers = (ArrayList<String>) Arrays.asList(line.split(" "));
//               System.out.println(numbers.get(0));
                    overflow++;

                    System.out.println(overflow);
                    if (overflow > 500) {
                        break;
                    }
                    List<String> myList = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
                    System.out.println(Arrays.toString(myList.toArray()));

                    ArrayList<Double> converted = new ArrayList(myList.size());

                    for (String s : myList) {
                        converted.add(Double.parseDouble(s));
                    }

                    System.out.println(Arrays.toString(converted.toArray()));

                    atoms.add(converted);


                }

                System.out.println(" ");

                System.out.println(Arrays.toString(atoms.toArray()));
                ArrayList<ArrayList<Double>> shortenedAtoms = new ArrayList<>();

                for (ArrayList<Double> a : atoms) {
                    ArrayList<Double> coord = new ArrayList<>();
                    for (int i = 3; i < a.size(); i++) {
                        coord.add(a.get(i));
                    }
                    shortenedAtoms.add(coord);
                }
                System.out.println("SHORTENED ATOMS");
                System.out.println(shortenedAtoms);
                if (!shortenedAtoms.isEmpty()) {
                    System.out.println("THIS IS WRONG");
                    return (shortenedAtoms);
                }
            }
            else{
                BufferedReader zMatrixSearch = new BufferedReader(new FileReader(optimization));
                String bline= zMatrixSearch.readLine();
                while((bline = zMatrixSearch.readLine())!= null){
                    String trimmedLine = bline.trim();
                    String[] inspector = bline.split( " ");
                    // System.out.println(Arrays.toString(inspector));
                    if(trimmedLine.equals("Symbolic Z-matrix:")) {
                        zMatrixSearch.readLine();
                        break;
                    }

                }
                ArrayList<ArrayList<String>> atomCoords = new ArrayList<>();

                while(!(line = zMatrixSearch.readLine().trim()).equals("")){
//               ArrayList<String> numbers = (ArrayList<String>) Arrays.asList(line.split(" "));
//               System.out.println(numbers.get(0));

                    List<String> myList = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
                    System.out.println(Arrays.toString(myList.toArray()));

                    ArrayList<Double> converted = new ArrayList(myList.size());

                    atomCoords.add((ArrayList<String>) myList);


                }

                System.out.println(" ");

                System.out.println(Arrays.toString(atoms.toArray()));

                ArrayList<ArrayList<Double>> shortenedAtoms = new ArrayList<>();


                for(ArrayList<String> a: atomCoords){
                    ArrayList<Double> coord = new ArrayList<>();
                    for(int i=1; i < a.size(); i++){
                        coord.add(Double.parseDouble(a.get(i)));
                    }
                    shortenedAtoms.add(coord);
                }
                if(shortenedAtoms.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Input file does not include input orientation or symbolic Z matrix");
                }



                return shortenedAtoms;
            }




        }
        catch(Exception e) {
            System.out.print(e);
        }



        return null;    }

}
