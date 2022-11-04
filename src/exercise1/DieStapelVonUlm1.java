package exercise1;

import java.util.Stack;

public class DieStapelVonUlm1 {

	public static void main(String[] args) {

		Disc a1 = new Disc(1);
		Disc b1 = new Disc(2);
		Disc b2 = new Disc(2);
		Disc c1 = new Disc(3);
		Disc c2 = new Disc(3);
		Disc c3 = new Disc(3);
		Stack<Disc> blaustein = new Stack<Disc>();
		blaustein.add(c3);
		blaustein.add(c2);
		blaustein.add(c1);
		blaustein.add(b1);
		blaustein.add(b2);
		blaustein.add(a1);
		Stack<Disc> münsterplatz = new Stack<Disc>();
		Stack<Disc> münster = new Stack<Disc>();
		build(6, blaustein, münsterplatz, münster);
		System.out.println(blaustein);
		System.out.println(münsterplatz);
		System.out.println(münster);

	}

	public static void build(int n, Stack<Disc> a, Stack<Disc> b, Stack<Disc> c) {
		if (n > 0) {
			build(n - 1, a, c, b);
			Disc d = a.pop();
			c.push(d);
			build(n - 1, b, a, c);
		}
	}

}
