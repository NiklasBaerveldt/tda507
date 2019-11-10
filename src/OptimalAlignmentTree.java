import java.util.ArrayList;
import java.util.Collection;

public class OptimalAlignmentTree {

    private Collection<OptimalAlignmentTree> optimalAlignmentTreeBranches = new ArrayList<>();
    private char alignmentCharX;
    private char alignmentCharY;
    private int nOfBranches = 0;

    public void setAlignmentCharX(char alignmentCharX) {
        this.alignmentCharX = alignmentCharX;
    }

    public void setAlignmentCharY(char alignmentCharY) {
        this.alignmentCharY = alignmentCharY;
    }

    public OptimalAlignmentTree growAlignmentTreeBranch(){
        OptimalAlignmentTree optimalAlignmentTree = new OptimalAlignmentTree();
        addAlignmentTree(optimalAlignmentTree);
        return optimalAlignmentTree;
    }

    public int getTotalNumberOfBranches(){

        if(nOfBranches == 0) {
            return 1;
        }
        else{
            int sizeOfBranches = getSizeOfBranches();
            return sizeOfBranches;
        }
    }

    private int getSizeOfBranches(){
        int sizeOfBranches = 0;
        for(OptimalAlignmentTree optimalAlignmentTree : optimalAlignmentTreeBranches){
            sizeOfBranches += optimalAlignmentTree.getTotalNumberOfBranches();
        }
        return sizeOfBranches;
    }

    public void printTree(){
        printBranches("","");
    }

    public void printBranches(String prevXalignment, String prevYalignment){
        String currentAlignmentXString = prevXalignment;
        String currentAlignmentYString = prevYalignment;

        // handle case when alignmentCharX and alignmentCharY is null.
        if(alignmentCharX != 0 && alignmentCharY != 0){
        currentAlignmentXString = prevXalignment + alignmentCharX;
        currentAlignmentYString = prevYalignment + alignmentCharY;
        }


        if(nOfBranches == 0){
            printBranchData(currentAlignmentXString,currentAlignmentYString);
            System.out.println();
        }
        else {
            for (OptimalAlignmentTree optimalAlignmentTree : optimalAlignmentTreeBranches) {
                optimalAlignmentTree.printBranches(currentAlignmentXString, currentAlignmentYString);
            }
        }
    }

    private void printBranchData(String currentAlignmentXString, String currentAlignmentYString){

        String correctlyOrderedAlignmentXString = new StringBuilder(currentAlignmentXString).reverse().toString();
        String correctlyOrderedAlignmentYString = new StringBuilder(currentAlignmentYString).reverse().toString();

        int alignmentLength = correctlyOrderedAlignmentXString.length();
        int alignmentNum = 0;
        String alignmentIndicators = "";

        for(int i = 0; i < alignmentLength; i++){
            if(correctlyOrderedAlignmentXString.charAt(i) == correctlyOrderedAlignmentYString.charAt(i)){
                alignmentIndicators += "|";
                alignmentNum++;
            }
            else{
                alignmentIndicators += " ";
            }
        }
        System.out.println(correctlyOrderedAlignmentXString);
        System.out.println(alignmentIndicators);
        System.out.println(correctlyOrderedAlignmentYString);
        System.out.println("PercentIdentity:" + ((int)(getPercentageIdentity(alignmentNum, alignmentLength)*100)) + "%");
    }

    private static double getPercentageIdentity(int alignmentNum, int divisionNum){
        return (double)alignmentNum/(double)divisionNum;
    }

    private void addAlignmentTree(OptimalAlignmentTree optimalAlignmentTree){
        optimalAlignmentTreeBranches.add(optimalAlignmentTree);
        nOfBranches++;
    }
}
