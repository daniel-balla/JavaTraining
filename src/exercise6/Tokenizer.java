package exercise6;

public class Tokenizer {

	public String[][] tokenize(String str) {
		int l = (int) str.chars().filter(c -> c == ';').count();
		String[][] arr = new String[l][2];
		String[] op = str.split("[;]");
		for (int k = 0; k < op.length; k++) {
			String n = "";
			StringBuilder node = new StringBuilder();
			for (int j = 0; j < op[k].length(); j++) {
				if (op[k].charAt(j) != ' ') {
					node.append(op[k].charAt(j));
				} else {
					break;
				}

				n = node.toString();
			}
			op[k] = op[k].replaceFirst(n, "");
			op[k] = op[k].replaceFirst(" ", "");
			arr[k][0] = n;
			arr[k][1] = op[k];
		}
		return arr;
	}
}
