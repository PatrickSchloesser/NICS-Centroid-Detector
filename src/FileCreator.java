import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileCreator {

    private ArrayList<String> startingAtoms;
    private ArrayList<ArrayList<Double>> centroids;
    private String path;

    public FileCreator(ArrayList<String> startingAtoms, ArrayList<ArrayList<Double>> centroids, String basisSet,
                       String fileName, String mem, String proc, String path, ArrayList<ArrayList<Integer>> cycles, Boolean includeCycles){
        int size = startingAtoms.size();
        this.startingAtoms = startingAtoms;
        this.centroids = centroids;
        this.path = path;
        startingAtoms.add(0, " ");
        startingAtoms.add(0, "Title Card Required");
        startingAtoms.add(0, " ");
        startingAtoms.add(0, "# nmr=giao " + basisSet);
        startingAtoms.add(0, "%chk=" + fileName);
        startingAtoms.add(0, "%mem=" + mem);
        startingAtoms.add(0, "%nprocshared=" + proc);
        if(includeCycles) {
            for (int i = 0; i < centroids.size(); i++) {
                startingAtoms.add("Bq"   + "                 " + String.format("%.12f", centroids.get(i).get(0)) + "   "
                        + String.format("%.12f", centroids.get(i).get(1)) + "   "
                        + String.format("%.12f", centroids.get(i).get(2))
                        + "      " + "!" + cycles.get((int) ((double) i / ((double) centroids.size() / (double) cycles.size()))));
            }
        }
        else{
            for (int i = 0; i < centroids.size(); i++) {
                startingAtoms.add("Bq" + "                 " + String.format("%.12f", centroids.get(i).get(0)) + "   "
                        + String.format("%.12f", centroids.get(i).get(1)) + "   "
                        + String.format("%.12f", centroids.get(i).get(2)));
            }
        }
        startingAtoms.add("\r\n");
        System.out.println(startingAtoms);
    }

    public void writetoOut(){

        Charset utf8 = StandardCharsets.UTF_8;

        String fileName = "test.txt";
        File dir = new File ("/Users/patrickschloesser/Desktop/TestFiles");
        File actualFile = new File (dir, fileName);

        try {
            Files.write(Paths.get(path), startingAtoms, utf8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
