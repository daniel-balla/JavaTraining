package exercise1;

import java.util.Stack;

public class DieStapelVonUlm {

	public static void main(String[] args) {
		int n = 6;
//		buildTower(n, "blaustein", "münsterplatz", "münster");
		Disk a1 = new Disk(1);
		Disk b1 = new Disk(2);
		Disk b2 = new Disk(2);
		Disk c1 = new Disk(3);
		Disk c2 = new Disk(3);
		Disk c3 = new Disk(3);
		Stack<Disk> blaustein = new Stack<Disk>();
		blaustein.add(c3);
		blaustein.add(c2);
		blaustein.add(c1);
		blaustein.add(b1);
		blaustein.add(b2);
		blaustein.add(a1);
		Stack<Disk> münsterplatz = new Stack<Disk>();
		Stack<Disk> münster = new Stack<Disk>();
		buildTowerStacks(n, blaustein, münsterplatz, münster);
		System.out.println(blaustein);
		System.out.println(münsterplatz);
		System.out.println(münster);

	}

	public static void buildTower(int n, String blaustein, String münsterplatz, String münster) {

		if (n == 1) {
			System.out.println("Bewege Stein 1 von Teller " + blaustein + " nach Teller " + münster);
			return;
		}
		buildTower(n - 1, blaustein, münsterplatz, münster);
		System.out.println("Bewege Stein " + n + " von Teller " + blaustein + " nach Teller " + münster);
		buildTower(n - 1, münsterplatz, münster, blaustein);
	}

//	public static void buildTowerStacks(int n, Stack<Disk> start, Stack<Disk> middle, Stack<Disk> goal) {
//		if (n == 1) {
//			if(goal.size() == 0 || goal.peek().size > start.peek().size) {
//				goal.push(start.pop());
//			} else if(middle.size() == 0 || middle.peek().size > start.peek().size) {
//				middle.push(start.pop());
//			}
//			return;
//		}
//		buildTowerStacks(n - 1, start, middle, goal);
//		if (middle.size() == 0 || middle.peek().size > start.peek().size) {
//			middle.push(start.pop());
//		} else if (goal.size() == 0 || goal.peek().size > start.peek().size) {
//			goal.push(start.pop());
//		}
//		if (start.size() > 0 && start.peek().size <= middle.peek().size) {
//			middle.push(start.pop());
//		}
//		buildTowerStacks(n - 1, goal, start, middle);
//	}
	
	public static void buildTowerStacks(int n, Stack<Disk> start, Stack<Disk> middle, Stack<Disk> goal) {
		if(n > 0) {
			buildTowerStacks(n-1, start, goal, middle);
			Disk a = start.pop();
			goal.push(a);
			buildTowerStacks(n-1, goal, middle, start);
		}
	}
}
