package exercise6.Tokenizer;

public class Token {

	public KeyWord keyWord;
	public String text;
	
	public Token(KeyWord keyWord, String text) {
		this.keyWord = keyWord;
		this.text = text;
	}
	
	@Override
	public String toString() {
		return keyWord + " " + text;
	}
	
	@Override
	public boolean equals(Object o) {
		Token other = (Token)o;
		return this.keyWord.equals(other.keyWord) && this.text.equals(other.text);
	}
}
