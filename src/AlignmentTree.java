import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

public class AlignmentTree {

    private Collection<AlignmentTree> alignmentTrees = new ArrayList<>();
    private char alignmentCharX = ' ';
    private char alignmentCharY = ' ';
    private int nOfBranches = 0;


    public AlignmentTree() {
    }

    public void setAlignmentCharX(char alignmentCharX) {
        this.alignmentCharX = alignmentCharX;
    }

    public void setAlignmentCharY(char alignmentCharY) {
        this.alignmentCharY = alignmentCharY;
    }

    public AlignmentTree generateAlignmentTree(){
        AlignmentTree alignmentTree = new AlignmentTree();
        addAlignmentTree(alignmentTree);
        return alignmentTree;
    }

    public int getAlignmentTreeSize(){
        int sizeOfBranches = getSizeOfBranches();
        if(nOfBranches == 0) {
            return 1;
        }
        else{
            return sizeOfBranches;
        }

    }

    private int getSizeOfBranches(){
        int sizeOfBranches = 0;
        for(AlignmentTree alignmentTree: alignmentTrees){
            sizeOfBranches += alignmentTree.getAlignmentTreeSize();
        }
        return sizeOfBranches;
    }

    public void printTree(){
        printBranches("","");
    }

    public void printBranches(String prevXalignment, String prevYalignment){
        String currentAlignmentXString = prevXalignment + alignmentCharX;
        String currentAlignmentYString = prevYalignment + alignmentCharY;

        if(nOfBranches == 0){
            System.out.println(new StringBuilder(currentAlignmentXString).reverse().toString());
            System.out.println(new StringBuilder(currentAlignmentYString).reverse().toString());
            System.out.println();
        }
        else {
            for (AlignmentTree alignmentTree : alignmentTrees) {
                alignmentTree.printBranches(currentAlignmentXString, currentAlignmentYString);
            }
        }
    }

    private void addAlignmentTree(AlignmentTree alignmentTree){
        alignmentTrees.add(alignmentTree);
        nOfBranches++;
    }
}
