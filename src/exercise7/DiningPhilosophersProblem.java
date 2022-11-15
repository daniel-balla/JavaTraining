package exercise7;

public class DiningPhilosophersProblem {

	static Fork fork;
	static Fork fork2;

	public static void main(String[] args) throws InterruptedException {
		Fork[] forks = { new Fork(0), new Fork(1), new Fork(2), new Fork(3), new Fork(4) };
		Philosoph[] ph = new Philosoph[5];
		for (int i = 0; i < 5; i++) {
			fork = forks[i];
			fork2 = forks[(i + 1) % 5];
			ph[i] = new Philosoph(fork, fork2);
		}
		for (int j = 0; j < 5; j++) {
			//Thread.sleep(10);
			ph[j].start();
		}
	}
}
