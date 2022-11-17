package exercise6.Tokenizer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TokenizerSt {

	public List<Token> tokenize(String str) {
		List<Token> tokens = new LinkedList<>();
		String[] split = str.split(";");
		for(String s : split) {
			tokens = createTokens(s, tokens);
		}
		return tokens;
	}

	public static List<Token> createTokens(String str, List<Token> tokens) {
		Set<String> commands = new HashSet<>();
		commands.add("PRINT");
		commands.add("PRINTI");
		commands.add("INPUT");
		commands.add("CALCULATE");
		commands.add("VARIABLE");
		StringBuilder tokenBuilder = new StringBuilder();
		int j = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				tokenBuilder.append(str.charAt(i));
			} else {
				break;
			}
			j++;
		}
		String token = tokenBuilder.toString();
		if (commands.contains(token)) {
			Token t = new Token(KeyWord.Command, token);
			tokens.add(t);
		} else if (token.length() == 1) {
			Token t = null;
			if (Character.isLetter(token.charAt(0))) {
				t = new Token(KeyWord.Variable, token);
			} else if (Character.isDigit(token.charAt(0))) {
				t = new Token(KeyWord.Integer, token);
			} else if (token.equals("=")) {
				t = new Token(KeyWord.Equals, token);
			}
			tokens.add(t);
		} else if (token.charAt(0) == '"') {
			token = token.substring(1, token.length() - 1);
			Token t = new Token(KeyWord.StringLiteral, token);
			tokens.add(t);
		} else {
			Token t = null;
			for (int i = 0; i < token.length(); i++) {
				if (Character.isAlphabetic(token.charAt(i))) {
					t = new Token(KeyWord.Variable, Character.toString(token.charAt(i)));
				} else if (Character.isDigit(token.charAt(i))) {
					StringBuilder num = new StringBuilder();
					for(int h = i; h < token.length(); h++) {
						if(Character.isDigit(token.charAt(h))) {
							num.append(token.charAt(h));
							i = h;
						} else {
							break;
						}
					}
					t = new Token(KeyWord.Integer, num.toString());
				} else {
					String txt = Character.toString(token.charAt(i));
					switch (token.charAt(i)) {
					case '(':
						t = new Token(KeyWord.LeftParenthesis, txt);
						break;
					case ')':
						t = new Token(KeyWord.RightParenthesis, txt);
						break;
					case '+':
						t = new Token(KeyWord.Plus, txt);
						break;
					case '-':
						t = new Token(KeyWord.Minus, txt);
						break;
					case '*':
						t = new Token(KeyWord.Multiplication, txt);
						break;
					case '/':
						t = new Token(KeyWord.Divide, txt);
						break;
					case '^':
						t = new Token(KeyWord.Exponent, txt);
						break;
					}
				}
				tokens.add(t);
			}
		}
		if (j != str.length()) {
			String ret = str.substring(j+1, str.length());
			createTokens(ret, tokens);
		} else {
			tokens.add(new Token(KeyWord.SemiColon, ";"));
		}
		return tokens;
	}
}
