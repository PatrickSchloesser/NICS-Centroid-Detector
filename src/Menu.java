import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Menu implements ActionListener {

    // Button for selecting the file
    private static JButton file;
    private static JButton file2;

    // Selected file
    private File sheet;
    // Jframe for holding the objects
    private static JFrame frame;
    private static File filee;
    private static JTextField basisSet;
    private static JTextField checkPointFile;
    private static JTextField processors;
    private static JTextField memory;
    private static JTextArea connectivityField;
    private static Checkbox mirror;
    private static JTextField length;
    private static JTextField placement;
    private static JCheckBox includeCycles;
    private static JTextArea additionalCycles;
    private static JCheckBox onlyAdditionalCycles;
    private static JTextArea scanPoints;
    private static JTextField scanHeight;
    private static JTextField scanInterval;





    public Menu(){
        frame = new JFrame("NICS Centroid Detector");
        // Setting frame to center of the screen
        frame.setLocationRelativeTo(null);
        // Setting color of frame
        frame.getContentPane().setBackground(Color.decode("#9eacc1"));
        // Setting size of frame
        frame.setSize(500, 500);
        file = new JButton("Prepare Absolute Magnetic Shielding Calculation");
        file2 = new JButton("Interpret NICS Values");

//        String[] basisSets = {"Basis Set","STO-3G","3-21G","6-31G","6-31G'","6-311G","cc-pVDZ","cc-pVTZ","cc-pVQZ","LanL2DZ","LanL2MB","SDD","DGDZVP","DGDZVP2","DGTZVP","GEN","GENECP"};
        String[] wfunctions = {"Approximation","Hartree-Fock","MP2","MP4"};
        basisSet = new JTextField();
//        basisSet.setSelectedIndex(0);
        JLabel basisLabel = new JLabel(" Basis Set");
        JLabel checkPointFileLabel = new JLabel(" Check Point File");
        JLabel memoryLabel = new JLabel(" Memory");
        JLabel processorsLabel = new JLabel(" Processors");
        JLabel connectivityLabel = new JLabel(" Connectivity");
        JLabel axialLabel = new JLabel (" Axial Dummy Atom Placement");
        JLabel additionalLabel = new JLabel(" Additional Nonstandard Cycles (atoms separated by spaces with one cycle per line)");

        JLabel scanLabel = new JLabel(" NICS Scan");

        JLabel scanIntervalLabel = new JLabel("Scan Interval");
        JLabel scanHeightLabel = new JLabel("Scan Height");


        JPanel basiser = new JPanel(new BorderLayout());
        basiser.add(basisLabel, BorderLayout.NORTH);
        basiser.add(basisSet, BorderLayout.SOUTH);


        JPanel lengthPanel = new JPanel(new BorderLayout());
        JLabel lengthLabel = new JLabel("       Maximum      " );
        length = new JTextField("0.0");
        lengthPanel.add(lengthLabel, BorderLayout.NORTH);
        lengthPanel.add(length, BorderLayout.SOUTH);



        JPanel placementPanel = new JPanel(new BorderLayout());
        JLabel placementLabel = new JLabel("       Interval       " );
        placement = new JTextField("0.0");
        placementPanel.add(placementLabel, BorderLayout.NORTH);
        placementPanel.add(placement, BorderLayout.SOUTH);

        mirror = new Checkbox("Mirror");


        JPanel mirroring = new JPanel();
        mirroring.add(lengthPanel);
        mirroring.add(placementPanel);


        JPanel allMirror = new JPanel(new BorderLayout());
        allMirror.add(axialLabel, BorderLayout.NORTH);
        allMirror.add(mirroring, BorderLayout.SOUTH);



        connectivityField = new JTextArea(8,20);
        JScrollPane scroll = new JScrollPane(connectivityField);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);



        checkPointFile = new JTextField();
        includeCycles = new JCheckBox();
        processors = new JTextField();
        memory = new JTextField();
        additionalCycles = new JTextArea(4, 20);
        scanPoints = new JTextArea(4, 20);

        JScrollPane scanScroll = new JScrollPane(scanPoints);
        scanScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        scanHeight = new JTextField("0.0");
        scanInterval = new JTextField("0.0");



        file.addActionListener(this);
        file2.addActionListener(this);

        JScrollPane additions = new JScrollPane(additionalCycles);
        additions.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


        JPanel checkpt = new JPanel(new BorderLayout());
        checkpt.add(checkPointFileLabel, BorderLayout.NORTH);
        checkpt.add(checkPointFile, BorderLayout.SOUTH);

        JPanel addition = new JPanel(new BorderLayout());
        addition.add(additionalLabel, BorderLayout.NORTH);
        addition.add(additions, BorderLayout.SOUTH);

        JPanel checkPanel = new JPanel(new BorderLayout());
        includeCycles = new JCheckBox("Add Associated Cycles to Input File ");
        includeCycles.setHorizontalTextPosition(SwingConstants.LEFT);
        checkPanel.add(includeCycles, BorderLayout.SOUTH);

        JPanel onlyAddition = new JPanel(new BorderLayout());
        onlyAdditionalCycles = new JCheckBox("Include Only NonStandard Cycles ");
        onlyAdditionalCycles.setHorizontalTextPosition(SwingConstants.LEFT);
        onlyAddition.add(onlyAdditionalCycles, BorderLayout.SOUTH);

        JPanel processor = new JPanel(new BorderLayout());
        processor.add(processorsLabel, BorderLayout.NORTH);
        processor.add(processors, BorderLayout.SOUTH);

        JPanel memor = new JPanel(new BorderLayout());
        memor.add(memoryLabel, BorderLayout.NORTH);
        memor.add(memory, BorderLayout.SOUTH);

        JPanel cont = new JPanel(new BorderLayout());
        cont.add(connectivityLabel, BorderLayout.NORTH);
        cont.add(scroll, BorderLayout.SOUTH);

        file.setAlignmentX(Component.CENTER_ALIGNMENT);
        file2.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel heightPanel = new JPanel(new BorderLayout());
        heightPanel.add(scanHeightLabel, BorderLayout.NORTH);
        heightPanel.add(scanHeight, BorderLayout.SOUTH);

        JPanel intervalPanel = new JPanel(new BorderLayout());
        intervalPanel.add(scanIntervalLabel, BorderLayout.NORTH);
        intervalPanel.add(scanInterval, BorderLayout.SOUTH);

        JPanel scanCoordPane = new JPanel();
        scanCoordPane.add(heightPanel);
        scanCoordPane.add(intervalPanel);

        JPanel nicsScan = new JPanel(new BorderLayout());
        nicsScan.add(scanLabel, BorderLayout.NORTH);
        nicsScan.add(scanScroll,BorderLayout.SOUTH);




        JPanel choices = new JPanel(new BorderLayout());
        choices.add(file);
        choices.add(file2);
        choices.add(basiser, BorderLayout.WEST);
        choices.add(checkpt, BorderLayout.WEST);
        choices.add(processor, BorderLayout.WEST);
        choices.add(memor, BorderLayout.WEST);
        choices.add(addition, BorderLayout.WEST);
        choices.add(onlyAddition, BorderLayout.WEST);
        choices.add(allMirror);
        choices.add(checkPanel,BorderLayout.WEST);
        choices.add(cont);
        choices.add(nicsScan);
        choices.add(scanCoordPane);



//        file.setAlignmentX(Component.CENTER_ALIGNMENT);
//        file2.setAlignmentX(Component.CENTER_ALIGNMENT);

        choices.setLayout(new BoxLayout(choices, BoxLayout.PAGE_AXIS));

        frame.add(choices, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == file){
            File gaussianFile = null;
            File outFile = null;

            try {
                String.valueOf(memory.getText());
                Double.parseDouble(processors.getText());
                String.valueOf(checkPointFile.getText());
                String.valueOf(basisSet.getText());
                String.valueOf(connectivityField.getText());
                Double.parseDouble(length.getText());
                Double.parseDouble(placement.getText());
                Double.parseDouble(scanHeight.getText());
                Double.parseDouble(scanInterval.getText());


            } catch (Exception ex) {
                // Ensuring user can only enter fields that are filled
                JOptionPane.showMessageDialog(null, "Please ensure all fields have values and that the following fields have numerical values: \n" +
                        " -Processors \n -Connectivity \n -Maximum \n -Interval");
                return;

            }


            Boolean includingCycles = includeCycles.isSelected();
            String basis = String.valueOf(basisSet.getText());
            String chkP = checkPointFile.getText();
            String proc = processors.getText();
            String mem = memory.getText();
            String con = connectivityField.getText();
            String axialLength = length.getText();
            String axialPlacement = placement.getText();
            Boolean onlyExtraneous = onlyAdditionalCycles.isSelected();
            String extraCycles = additionalCycles.getText();

            String scanCoords = scanPoints.getText();
            Double scanHeightParam = Double.parseDouble(scanHeight.getText());
            Double scanIntervalParam = Double.parseDouble(scanInterval.getText());




            Double span = Double.parseDouble(length.getText());
                Double interval = Double.parseDouble(placement.getText());


            if(onlyExtraneous && extraCycles.trim().equals("")){
                JOptionPane.showMessageDialog(null, "Please specify non-standard cycles");

                return;

            }

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open Optimization File");
            int success = fc.showOpenDialog(frame);
            fc.setDialogTitle("Name Gaussian Input File");
            if (success == JFileChooser.APPROVE_OPTION) {
                // Instantiating file
                gaussianFile = fc.getSelectedFile();
//                System.out.println(gaussianFile);
            }
            else{
                return;
            }
            int userSelection = fc.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                 outFile = fc.getSelectedFile();
//                System.out.println(outFile);
            }
            else{
                return;
            }

//            BufferedReader br = new BufferedReader(new FileReader(gaussianFile));
            Reader reader = new Reader(gaussianFile,con);
            ArrayList coordinates = reader.getCoordinates();

            CycleDetector cycles = new CycleDetector(reader);
            ArrayList<ArrayList<Integer>> cycleList = cycles.allCycles();

            String[] s = extraCycles.split("\\r?\\n");
            ArrayList<String> allAdditional = new ArrayList<>(Arrays.asList(s));


            ArrayList<ArrayList<Integer>> addedCycles = new ArrayList<>();
            if(!extraCycles.trim().equals("")){
                for(String j: allAdditional){
                    j.trim();
                    ArrayList<String> myList = new ArrayList<String>(Arrays.asList(j.split(" ")));
                    ArrayList<Integer> converted = new ArrayList<>();

                    for(String i: myList){
                        converted.add(Integer.parseInt(i));
                        if(Integer.parseInt(i)> coordinates.size() -1){

                            JOptionPane.showMessageDialog(null,"Atom " + i + " is not a member of the input molecule" );
                            return;
                        }

                    }
//                    if(!cycles.checkConnected(converted)){
//                        JOptionPane.showMessageDialog(null, "Cycle " + converted + " is not a possible ring in the input molecule ");
//                        return;
//                    }
                    addedCycles.add(converted);


                }
                cycleList.addAll(addedCycles);
                if(onlyExtraneous){
                    cycleList = addedCycles;
                }


            }




//            System.out.println("CycleList: " + cycleList);
//            System.out.println("Coords" + coordinates);


            CenterFinder center = new CenterFinder(cycleList, coordinates, span, interval);
            System.out.println(center.atoms());

            ArrayList<ArrayList<Double>> allCoords = center.atoms();

            if(!scanCoords.trim().equals("")){
                System.out.println(scanCoords);
                ScanParser sParse = new ScanParser(scanCoords);
                ArrayList<ArrayList<ArrayList<Double>>> parseResults = sParse.parse();

                System.out.println("PARSE RESULTS:" +parseResults);

                ArrayList<Integer> atoms = new ArrayList<>();

                for(int i = 0; i < coordinates.size(); i++){
                    atoms.add(i+1);
                }

                Scanner scan = new Scanner(scanIntervalParam, scanHeightParam, coordinates, parseResults, atoms );

                ArrayList<ArrayList<Double>> finalScanCoords = scan.compute();

                allCoords.addAll(finalScanCoords);

            }



//            ScanParser scanParse = new ScanParser()
            FileCreator output = new FileCreator(reader.getChargeAndMultiplicity(),allCoords, basis, chkP, mem, proc, outFile.getPath(), cycleList, includingCycles);
            output.writetoOut();

        }
        else {

            String dummyCount = JOptionPane.showInputDialog("How many dummy atoms are placed above each centroid (integer)?");

            try{
                Integer.parseInt(dummyCount);
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"The input is not an integer");
                return;
            }

            System.out.println(dummyCount);
            File gaussianFile = null;
            File outFile = null;

            JFileChooser fc = new JFileChooser();
            int success = fc.showOpenDialog(frame);
            if (success == JFileChooser.APPROVE_OPTION) {
                // Instantiating file
                gaussianFile = fc.getSelectedFile();
                System.out.println(gaussianFile);
            }
            else{
                return;
            }
            int userSelection = fc.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                outFile = fc.getSelectedFile();
                System.out.println(outFile);
            }
            else{
                return;
            }

            ResultReader read = new ResultReader(gaussianFile, Integer.parseInt(dummyCount));
            ArrayList<String> out = read.interpret();
            Charset utf8 = StandardCharsets.UTF_8;

            String path = outFile.getPath();

            try {
                Files.write(Paths.get(path), out, utf8);

            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }
    }

    public static void main(String[] args){

        Menu menu = new Menu();
//    File testFile = new File("/Users/patrickschloesser/Desktop/TESTFILES/ACTUALCYCLOBUT.LOG");
//
//
//    Reader reader = new Reader(testFile, "yoyo");
//    ArrayList coordinates = reader.getCoordinates();

//
//
////              ArrayList allConnections = reader.allConnections();
//        CycleDetector cycles = new CycleDetector(reader);
//        ArrayList<ArrayList<Integer>> cycleList = cycles.allCycles();
//
//        System.out.println("CycleList: " + cycleList);
//        System.out.println("Coords" + coordinates);
//
//        CenterFinder center = new CenterFinder(cycleList, coordinates);
//        System.out.println(center.atoms());
//
//        FileCreator output = new FileCreator(reader.getChargeAndMultiplicity(),center.atoms(), "hf/6-31g(d)",
//                "benzene_hf631gd_nmr_nics", "10", "20", "/Users/patrickschloesser/Desktop/TestFiles/test.txt");
//        output.writetoOut();



    }



}
