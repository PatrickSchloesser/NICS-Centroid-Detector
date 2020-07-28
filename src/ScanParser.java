import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class ScanParser {

    private String input;

    public ScanParser(String input){

        this.input = input;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> parse(){

        String s[] = input.split("\\r?\\n");
        ArrayList<String> scanLines = new ArrayList<>(Arrays.asList(s));
        System.out.println("Lines:" + scanLines);

        ArrayList<ArrayList<ArrayList<Double>>> allJobs = new ArrayList<>();

        for(String f: scanLines){
            String j = f.trim();
                System.out.println("Trimmed:" + s);
            List<String> myList = new ArrayList<>(Arrays.asList(StringUtils.substringsBetween(f, "(",")")));
            System.out.println("Between Parens" + myList);

            ArrayList<ArrayList<Double>> singleJob = new ArrayList<>();

            for(String k: myList){
                k.trim();
            List<String> innerList = new ArrayList<>(Arrays.asList(k.split(" ")));

            System.out.println(innerList);
            ArrayList<Double> pointInJob = new ArrayList<>();

            if(innerList.size() == 1){
                System.out.println(1);
                pointInJob.add(1.0);
                pointInJob.add(Double.parseDouble(innerList.get(0)));
            }

            else if(innerList.size() == 2){
                System.out.println(2);

                pointInJob.add(2.0);
                pointInJob.add(Double.parseDouble(innerList.get(0)));
                pointInJob.add(Double.parseDouble(innerList.get(1)));

            }
            else if(innerList.size() == 3){
                System.out.println(3);

                if(innerList.get(0).length() > 1){
                    pointInJob.add(0.0);
                    pointInJob.add(Double.parseDouble(innerList.get(0)));
                    pointInJob.add(Double.parseDouble(innerList.get(1)));
                    pointInJob.add(Double.parseDouble(innerList.get(2)));
                }
                else{
                    pointInJob.add(3.0);
                    pointInJob.add(Double.parseDouble(innerList.get(0)));
                    pointInJob.add(Double.parseDouble(innerList.get(1)));
                    pointInJob.add(Double.parseDouble(innerList.get(2)));
                }
            }
            else if(innerList.size()> 3){
                System.out.println(4);

                pointInJob.add(3.0);
                for(String m: innerList){
                    pointInJob.add(Double.parseDouble(m));

                }
            }
            else if(innerList.size() == 0){
                JOptionPane.showMessageDialog(null, "Please fill empty parentheses in the NICS Scan field");

            }

            System.out.println(pointInJob);
            singleJob.add(pointInJob);

            }

            allJobs.add(singleJob);

        }

        return allJobs;

    }


}
