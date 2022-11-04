package exercise1;

public class Disc implements Comparable<Disc> {

	int size;

	public Disc(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return Integer.toString(this.size);
	}

	@Override
	public int compareTo(Disc o) {
		return this.size - o.size;
	}
}
