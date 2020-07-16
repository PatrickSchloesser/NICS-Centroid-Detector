import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class scanParser {

    private String input;

    public scanParser(String input){

        this.input = input;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> parse(){

        String s[] = input.split("\\r?\\n");
        ArrayList<String> scanLines = new ArrayList<>(Arrays.asList(s));

        for(String f: scanLines){
            String j = f.trim();
//                System.out.println("Trimmed:" + s);
            List<String> myList = new ArrayList<String>(Arrays.asList(j.split(" ")));
        }

        return null;

    }


}
