public class GlobalAlignment {

    //The calculation of the percent identity is calculated by
    //dividing the matching pairs with the length of the alignment.


    public static final int MAX_LENGTH = 100;

    public static final int MATCH_SCORE = 2;
    public static final int MISMATCH_SCORE = -1;
    public static final int GAP_PENALTY = -2;

    public static final int STOP = 0;
    public static final int UP = 1;
    public static final int LEFT = 10;
    public static final int DIAG = 100;

    public static void main(String[] args) {

        int i, j;
        int score, tmp;


        String X = "ATTTTA";
        String Y = "ATTA";

        System.out.println("INPUT");
        System.out.println("String X: " + X);
        System.out.println("String Y: " + Y);
        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("OUTPUT");

        int F[][] = new int[MAX_LENGTH + 1][MAX_LENGTH + 1];     /* score matrix */
        int trace[][] = new int[MAX_LENGTH + 1][MAX_LENGTH + 1]; /* trace matrix */

        int m = X.length();
        int n = Y.length();


        //
        // Initialise matrices
        //

        F[0][0] = 0;
        trace[0][0] = STOP;
        for (i = 1; i <= m; i++) {
            F[i][0] = F[i - 1][0] + GAP_PENALTY;
            trace[i][0] = STOP;
        }
        for (j = 1; j <= n; j++) {
            F[0][j] = F[0][j - 1] + GAP_PENALTY;
            trace[0][j] = STOP;
        }


        //
        // Fill matrices
        //

        for (i = 1; i <= m; i++) {

            for (j = 1; j <= n; j++) {

                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    score = F[i - 1][j - 1] + MATCH_SCORE;
                } else {
                    score = F[i - 1][j - 1] + MISMATCH_SCORE;
                }
                trace[i][j] = DIAG;

                tmp = F[i - 1][j] + GAP_PENALTY;

                if( tmp == score){
                    trace[i][j] += UP;
                }

                if (tmp > score) {
                    score = tmp;
                    trace[i][j] = UP;
                }

                tmp = F[i][j - 1] + GAP_PENALTY;

                if( tmp == score){
                    trace[i][j] += LEFT;
                }

                if (tmp > score) {
                    score = tmp;
                    trace[i][j] = LEFT;
                }

                F[i][j] = score;
            }
        }


        //
        // Print score matrix
        //

        System.out.println("Score matrix:");
        System.out.print("      ");
        for (j = 0; j < n; ++j) {
            System.out.print("    " + Y.charAt(j));
        }
        System.out.println();
        for (i = 0; i <= m; i++) {
            if (i == 0) {
                System.out.print(" ");
            } else {
                System.out.print(X.charAt(i - 1));
            }
            for (j = 0; j <= n; j++) {
                System.out.format("%5d", F[i][j]);
            }
            System.out.println();
        }
        System.out.println();


        //
        // Trace back from the lower-right corner of the matrix
        //

        i = m;
        j = n;

        OptimalAlignmentTree optimalAlignmentTreeRoot = new OptimalAlignmentTree();
        traceBack(optimalAlignmentTreeRoot,trace,X,Y,i,j);


        System.out.println("Number of paths:" + optimalAlignmentTreeRoot.getTotalNumberOfBranches());
        System.out.println();
        optimalAlignmentTreeRoot.printTree();

        int hammingDistance = getHammingDistance(X,Y);
        if(hammingDistance == -1){
            System.out.println("Hammingdistance: not applicable");
        }
        else{
            System.out.println("Hammingdistance:" + (getHammingDistance(X, Y)));
        }

    }



    private static int getHammingDistance(String s1, String s2) {
        if (s1.length() != s2.length()) {
            System.out.println("Strings are not of equal length");
            return -1;
        }
        int hammingDistance = 0;
        int len = s1.length();

        for (int i = 0; i < len; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                hammingDistance++;
            }
        }
        return hammingDistance;
    }

    private static void traceBack(OptimalAlignmentTree optimalAlignmentTree, int[][] trace, String X, String Y, int i, int j){ // denna kan läggas in i alignment tree som buildtree
        OptimalAlignmentTree newBranch;

        if ( trace[i][j] != STOP ) {

            switch ( trace[i][j] ) {

                case DIAG:
                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchDiag(newBranch,trace,X,Y,i,j);
                    break;

                case LEFT:
                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchLeft(newBranch,trace,X,Y,i,j);
                    break;

                case UP:
                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchUp(newBranch,trace,X,Y,i,j);
                    break;

                case (DIAG + LEFT):
                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchDiag(newBranch,trace,X,Y,i,j);

                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchLeft(newBranch,trace,X,Y,i,j);
                    break;

                case (DIAG + UP):
                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchDiag(newBranch,trace,X,Y,i,j);

                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchUp(newBranch,trace,X,Y,i,j);
                    break;

                case (UP + LEFT):
                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchUp(newBranch,trace,X,Y,i,j);

                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchLeft(newBranch,trace,X,Y,i,j);
                    break;

                case(UP + LEFT + DIAG):
                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchUp(newBranch,trace,X,Y,i,j);

                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchLeft(newBranch,trace,X,Y,i,j);

                    newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                    branchDiag(newBranch,trace,X,Y,i,j);
                    break;
            }
        }
        else {

            if ( i>0 ) {
                newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                branchUp(newBranch,trace,X,Y,i,j);
            }

            if ( j>0 ) {
                newBranch = optimalAlignmentTree.growAlignmentTreeBranch();
                branchLeft(newBranch,trace,X,Y,i,j);
            }
        }
    }

    private static void branchUp(OptimalAlignmentTree newBranch, int[][] trace, String X, String Y, int i, int j){
        newBranch.setAlignmentCharX(X.charAt(i - 1));
        newBranch.setAlignmentCharY('-');
        traceBack(newBranch, trace, X, Y, i - 1, j);
    }

    private static void branchDiag(OptimalAlignmentTree newBranch, int[][] trace, String X, String Y, int i, int j){
        newBranch.setAlignmentCharX(X.charAt(i - 1));
        newBranch.setAlignmentCharY(Y.charAt(j - 1));
        traceBack(newBranch, trace, X, Y, i - 1, j - 1);;
    }

    private static void branchLeft(OptimalAlignmentTree newBranch, int[][] trace, String X, String Y, int i, int j){
        newBranch.setAlignmentCharX('-');
        newBranch.setAlignmentCharY(Y.charAt(j - 1));
        traceBack(newBranch, trace, X, Y, i, j - 1);
    }
}
