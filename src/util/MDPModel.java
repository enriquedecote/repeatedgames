/*******************************************************************************
 * This file has been adapted from Team’s REshaping of MOdels for Rapid execution (TREMOR)
 * at http://teamcore.usc.edu/projects/dpomdp/
 * 
 * Please send an email to: jemc@inaoep.mx for comments or to become part of this project.
 * Contributors:
 *     Enrique Munoz de Cote - initial API and implementation
 ******************************************************************************/
package util;

import java.io.*;
import java.util.*;

/**
 *
 * @author enrique
 * @modified 
 */
public class MDPModel implements Serializable {

    int states;
    //int actions; //0 - North, 1 - East, 2 - South, 3 - West
    int actions; //0 - North, 1 - South, 2 - Obs North, 3 - Obs South
    int observations;
    int horizon;
    double epsilon;
    double maxVal;
    double[][][] transitionMatrix;
    double[][][] rewardMatrix;
    boolean modified = false;
    int debrisInd = -1;
    public int startingStateNo = -1;

    public MDPModel() {
    }

    public void setStates(int states) {
        this.states = states;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public void initialize() {
        transitionMatrix = new double[actions][states][states];
        rewardMatrix = new double[actions][states][states];
    }

    public void setHorizon(int horizon) {
        this.horizon = horizon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public void setTransition(int s, int a, int s1, double prob) {
        transitionMatrix[a][s][s1] = prob;
    }


    public double getTransition(int s, int a, int s1) {
        return transitionMatrix[a][s][s1];
    }


    public void setRewardEntry(int s, int a, int s1, double val) {
        rewardMatrix[a][s][s1] = val;
    }

    public double getRewardEntry(int s, int a, int s1) {
        return rewardMatrix[a][s][s1];
    }


    public void setActionTransitions(int a, double[][] transitions) {
        transitionMatrix[a] = transitions;
    }



  

    public double round(double a, int places) {
        long l = Math.round(a * Math.pow(10, places-1));

        double fin = (double) l / (double) Math.pow(10, places-1);

        return fin;
    }


    public void printModel(String fileName) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        pw.println("discount:0.95");
        pw.println("values: reward");
        pw.println("states:" + states);
        pw.println("action:" + actions);
        pw.println("observations:" + observations);
        pw.println("epsilon:" + epsilon);

        pw.print("start:");
        pw.println("");

        /* Printing transition functions */
        for (int i = 0; i < actions; i++) {
            pw.println("T:" + i);
            printMatrix(pw, transitionMatrix[i]);
        }

        for (int i = 0; i < actions; i++) {
            for (int j = 0; j < states; j++) {
                for (int k = 0; k < states; k++) {
                    pw.println("R:" + i + ":" + j + ":" + k + ":* " + rewardMatrix[i][j][k]);
                }
            }
        }
        pw.close();
    }

    public void printMatrix(PrintWriter pw, double[][] matrix) throws IOException {
        double epsilon = 0.0000001;
        for (int i = 0; i < matrix.length; i++) {
            double consistencyCheck = 0;
            //System.out.println("matrix length: " + matrix[i].length);
            for (int j = 0; j < matrix[i].length; j++) {
                consistencyCheck += matrix[i][j];
                pw.print(round(matrix[i][j], 4));
                //System.out.println(i+", "+j+": "+round(matrix[i][j], 4));
                //pw.print(matrix[i][j]);
                if (j != matrix[i].length - 1) {
                    pw.print(" ");
                    //System.out.print(" ");
                }
            }
            //System.out.println("consistency: " + consistencyCheck);

            if (Math.abs(consistencyCheck-1) > epsilon) {
                System.out.println("huk!!!: " + consistencyCheck);
                pw.close();
                System.exit(1);
            }
            pw.println("");
            //System.out.println();
        }
    }
}