import java.util.*;
import java.util.stream.*;
// 
// A skeleton for homework 5
// Solve the partition problem, now using backtracking
//
public class PartitionSkeleton {

    private static int arrayOfWeights[];

    private static boolean evaluate(Set <Integer> part0,
				    Set <Integer> part1) {
	int mask = 1;
	int n = arrayOfWeights.length;
	int sum0s = 0;
	int sum1s = 0;
	for(int i : part1) {
	    
		sum1s = sum1s + arrayOfWeights[i];
	}
	for(int i : part0) {
	    
		sum0s = sum0s + arrayOfWeights[i];
	}

	if (sum1s == sum0s) {
	    return true;
	}
	else {
	    return false;
	}
    }
    
    static boolean backtrack(Set < Integer > s0,Set < Integer > s1,int n) {
	if(s0.size() + s1.size() == n) {
		if(evaluate(s0,s1)) {
			return true;
		}
		else {
			return false;
		}
	}
	else {
		int depth = s0.size() + s1.size();
		s0.add(depth);
		if(backtrack(s0, s1, n)) {
			return true;
		}
		else {
			s0.remove(depth);
			s1.add(depth);
			if(backtrack(s0, s1, n)) {
				return true;
			}
			else {
				s1.remove(depth);
				return false;
			}
		}
	}

  }

    public static void main(String args[]) {

	Scanner scanner = new Scanner(System.in);
	int n = scanner.nextInt();
	arrayOfWeights = new int [n];
	for(int i = 0;i < n;i++) {
	    arrayOfWeights[i] = scanner.nextInt();
	}
	boolean solutionFound = false;
	TreeSet < Integer > solution1s = new TreeSet< Integer > ();
	TreeSet < Integer > solution0s = new TreeSet< Integer > ();
        solutionFound = backtrack(solution0s,solution1s,n);
	if (solutionFound) {
		System.out.println("Solution Found!");
		System.out.println("Set 0: " + solution0s + " \n" + "Set1: " + solution1s);
	}
	else {
	    System.out.println("No solution was found.");
	}
	    
    }
}