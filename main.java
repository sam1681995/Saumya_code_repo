/*
 
 */
package amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Saumya
 */
public class main {
 static class Pair implements Comparable<Pair>{
        String pairKey;
        String pairValue;

        Pair(String pairKey, String pairValue) {
            this.pairKey = pairKey;
            this.pairValue = pairValue;
        }

        public String getPairKey() {
            return pairKey;
        }

        public String getPairValue() {
            return pairValue;
        }

        public String toString() {
            return getPairKey() + " " + getPairValue();
        }

        @Override
        public int compareTo(Pair pair) {
            int compare = pairKey.compareTo(pair.getPairKey());
            if(compare == 0) {
                compare = pairValue.compareTo(pair.getPairValue());
            }
            return compare;
        }
    }
    static List<Pair> solve(List<Pair> lunchMenuPairs, List<Pair> teamCuisinePreferencePairs){
        //List<Pair> result= null;
         List<Pair> result= new ArrayList<>();
        for(Pair first : teamCuisinePreferencePairs){
            for(Pair name : lunchMenuPairs)
            {
                String a1 = first.getPairValue();
                String a2 = name.getPairValue();
                String a3 = first.getPairKey();
                String a4 = name.getPairKey();
                if(first.compareTo(name)==0)
                {
                    System.out.println(a3);
                    System.out.println(a4);
                    Pair pair = new Pair(a3,a4);
                    result.add(pair);
                }
            }
        }
        return result;
    }

    private static void readAndSetParameters(List<Pair> lunchMenuPairs, List<Pair> teamCuisinePreferencePairs) {
        int lunchMenuPairCount = -1;
        int teamCuisinePreferencePairCount = -1;

        String tempOption = null, tempOptionValue = null;
        System.out.println("Enter lunch menu pairs count : \n");
        try(Scanner in = new Scanner(System.in)) {
            lunchMenuPairCount = in.nextInt();
            while(lunchMenuPairCount > 0) {
                lunchMenuPairCount--;
                System.out.println("Enter lunch menu pairs : \n");
                System.out.println("Enter lunch : \n");
                tempOption = in.next();
                System.out.println("Enter menu : \n");
                tempOptionValue = in.next();
                Pair pair = new Pair(tempOption, tempOptionValue);
                lunchMenuPairs.add(pair);
            }
            
            System.out.println("Enter team cuisine pairs count : \n");
            teamCuisinePreferencePairCount = in.nextInt();
            while(teamCuisinePreferencePairCount > 0) {
                teamCuisinePreferencePairCount--;
                System.out.println("Enter team cuisine preference pairs : \n");
                System.out.println("Enter team : \n");
                tempOption = in.next();
                System.out.println("Enter cuisine : \n");
                tempOptionValue = in.next();
                Pair pair = new Pair(tempOption, tempOptionValue);
                teamCuisinePreferencePairs.add(pair);
            }
        }
    }

    private static void print(List<?> resultPairs) {
        for (Object pair : resultPairs) {
            System.out.println(pair);
        }
    }

    public static void main(String[] args){
        List<Pair> lunchMenuPairs = new LinkedList<>();
        List<Pair> teamCuisinePreferencePairs = new LinkedList<>();
        readAndSetParameters(lunchMenuPairs, teamCuisinePreferencePairs);
        List<Pair> result = solve( lunchMenuPairs, teamCuisinePreferencePairs );
        Collections.sort(result);
        print(result);
    }   
}
