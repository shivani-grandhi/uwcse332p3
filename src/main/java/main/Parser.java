package main;

import cse332.exceptions.NotYetImplementedException;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    /**
     * Parse an adjacency matrix into an adjacency list.
     *
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list
     */
    public static ArrayList<HashMap<Integer, Integer>> parse(int[][] adjMatrix) {
        int length = adjMatrix[0].length;
        ArrayList<HashMap<Integer, Integer>> arraylist = new ArrayList<HashMap<Integer, Integer>>(length);
        for (int i=0; i < length; i++) {
            arraylist.add(i, new HashMap<Integer, Integer>());
        }
        for (int j = 0; j < length; j++) {
            for (int k = 0; k < adjMatrix.length; k++) {
                if (adjMatrix[j][k] != Integer.MAX_VALUE) {
                    arraylist.get(j).put(k, adjMatrix[j][k]);
                }
            }
        }
        return arraylist;
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list with incoming edges
     */
    public static ArrayList<HashMap<Integer, Integer>> parseInverse(int[][] adjMatrix) {
        int l = adjMatrix[0].length;
        ArrayList<HashMap<Integer, Integer>> al = new ArrayList<HashMap<Integer, Integer>>(l);
        for (int i=0; i<l; i++) {
            al.add(i, new HashMap<Integer, Integer>());
        }
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < l; j++) {
                if (adjMatrix[i][j] != Integer.MAX_VALUE) {
                    al.get(j).put(i, -adjMatrix[i][j]);
                }
            }
        }
        return al;
    }

}
