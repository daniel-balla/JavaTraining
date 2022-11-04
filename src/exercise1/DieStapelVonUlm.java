package exercise1;

public class DieStapelVonUlm {

	public static void main(String[] args) {

		Tower[] towers = new Tower[3];

		for (int i = 0; i < towers.length; i++) {
			towers[i] = new Tower();
		}

		int n = 6;

		for (int i = 0; i < n; i++) {
			towers[0].push(new Disc(n - i));
		}

		print(towers);
		solve(towers);
		print(towers);

	}

	public static void print(Tower[] towers) {
		System.out.println();
		for (Tower tower : towers) {
			System.out.println(tower);
		}
	}

	private static void solve(Tower[] towers) {
		move(towers, towers[0].size(), 0, 1, 2);
	}

	private static void move(Tower[] towers, int n, int a, int b, int c) {
		if (n > 0) {
			print(towers);
			move(towers, n - 1, a, c, b);

			Disc d = towers[a].pop();

			towers[c].push(d);

			move(towers, n - 1, b, a, c);
		}
	}
}
