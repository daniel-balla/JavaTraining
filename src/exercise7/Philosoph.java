package exercise7;

public class Philosoph extends Thread {

	Fork leftFork;
	Fork rightFork;

	public Philosoph(Fork leftFork, Fork rightFork) {
		this.leftFork = leftFork;
		this.rightFork = rightFork;
	}

	public void run() {
		if(rightFork.i < leftFork.i) {
			Fork temp = leftFork;
			leftFork = rightFork;
			rightFork = temp;
		}
		if (leftFork.taken == true) {
			System.out.println(getName() + " Waiting for left Fork");
		}
		try {
			takeFork(leftFork);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(getName() + " Taking left Fork");

		if (rightFork.taken == true) {
			System.out.println(getName() + " Waiting for right Fork");
		}
		try {
			takeFork(rightFork);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(getName() + " Taking right Fork");
		System.out.println(getName() + " Eating");

		try {
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(getName() + " Eaten, putting forks back");

		try {
			putForkBack(leftFork);
			putForkBack(rightFork);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void takeFork(Fork fork) throws InterruptedException {
		if(fork.taken == false) {
			fork.taken = true;
		} else {
			synchronized(fork) {
				while(fork.taken == true) {
					fork.wait();
				}
			}
		}
	}
	
	public void putForkBack(Fork fork) throws InterruptedException {
		fork.taken = false;
		synchronized(fork) {
			fork.notifyAll();
		}
	}
}
