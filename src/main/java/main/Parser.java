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
        int cost = 0;
        int l = adjMatrix[0].length;
        ArrayList<HashMap<Integer, Integer>> al = new ArrayList<HashMap<Integer, Integer>>(l);
        for (int i=0; i<l; i++) {
            al.add(i, new HashMap<Integer, Integer>());
        }
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] != Integer.MAX_VALUE) {//!= 0 || adjMatrix[i][j] != Integer.MAX_VALUE) {//Integer.MAX_VALUE) {
                    al.get(i).put(j, adjMatrix[i][j]);
                }
            }
        }

        return al;
    }

    /**
     * Parse an adjacency matrix into an adjacency list with incoming edges instead of outgoing edges.
     * @param adjMatrix Adjacency matrix
     * @return Adjacency list with incoming edges
     */
    public static Object parseInverse(int[][] adjMatrix) {
        throw new NotYetImplementedException();
    }

}
