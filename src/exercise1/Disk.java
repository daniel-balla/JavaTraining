package exercise1;

public class Disk {

	int size;

	public Disk(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return Integer.toString(this.size);
	}
}
