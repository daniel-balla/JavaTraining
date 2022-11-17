package exercise6.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import exercise6.Parser.ExpressionParser;
import exercise6.Parser.Parser;
import exercise6.Parser.ParserImp;
import exercise6.Tokenizer.KeyWord;
import exercise6.Tokenizer.Token;
import exercise6.Tokenizer.TokenizerSt;
import exercise6.ast.AstInterpreter;
import exercise6.ast.SimpleAst;
import exercise6.ast.nodes.CalculateNode;
import exercise6.ast.nodes.PrintNode;

class ParserImpTests {

	private final Parser simpleLanguageParser = new ParserImp();
	private final ExpressionParser expressionParser = new ExpressionParser();
	private final TokenizerSt tokenizerSt = new TokenizerSt();
	private final Token eight = new Token(KeyWord.Integer, "8");
	private final Token nine = new Token(KeyWord.Integer, "9");
	private final Token plus = new Token(KeyWord.Plus, "+");
	private final Token print = new Token(KeyWord.Command, "PRINT");
	private final Token calc = new Token(KeyWord.Command, "CALCULATE");
	private final Token semi = new Token(KeyWord.SemiColon, ";");

	@Test
	void testParser() {
//		final String input = "PRINT \"h\";CALCULATE 8+9;";
		final SimpleAst ast1 = new SimpleAst();
		List<Token> tok = Arrays.asList(print, new Token(KeyWord.StringLiteral, "h"), semi, calc, eight, plus, nine, semi);
		List<Token> exp = Arrays.asList(eight, plus, nine);
		ast1.addNode(new PrintNode("h", ast1));
		ast1.addNode(new CalculateNode(expressionParser.parse(exp, null), null));
		final SimpleAst ast2 = simpleLanguageParser.parse(tok);
		assertEquals(ast1.getNodes().get(0), ast2.getNodes().get(0));
//		assertEquals(ast1.getNodes().get(1), ast2.getNodes().get(1));
	}

	@Test
	void testPrint() {
		final List<String> printMessages = new LinkedList<>();
//		final String input = "PRINT \"Hello\";PRINT \"World!\";";
		List<Token> tok = Arrays.asList(print, new Token(KeyWord.StringLiteral, "Hello"), semi, print,
				new Token(KeyWord.StringLiteral, "World!"), semi);
		final SimpleAst ast = simpleLanguageParser.parse(tok);
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(2, printMessages.size());
		assertEquals("Hello", printMessages.get(0));
		assertEquals("World!", printMessages.get(1));
	}

	@Test
	void testInput() {
		final Deque<String> inputMessages = new LinkedList<>();
		inputMessages.add("Some Input");

		final String input = "INPUT;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.inputMessageProvider(() -> inputMessages.pop()).build();
		astInterpreter.execute(ast);
		assertEquals(0, inputMessages.size());
	}

	@Test
	void testPrinti1() {
		final Deque<String> inputMsg = new LinkedList<>();
		final List<String> printMsg = new LinkedList<>();
		inputMsg.add("Hello");

		final String input = "INPUT;PRINTI;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(msg -> printMsg.add(msg)).inputMessageProvider(() -> inputMsg.pop()).build();
		astInterpreter.execute(ast);
		assertEquals("Hello", printMsg.get(0));
		assertEquals(0, inputMsg.size());
	}

	@Test
	void testPrinti2() {
		final Deque<String> inputMsg = new LinkedList<>();
		final List<String> printMsg = new LinkedList<>();
		inputMsg.add("Ding");
		inputMsg.add("Dong");
		inputMsg.add("Dong");

		final String input = "INPUT;INPUT;PRINTI;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(msg -> printMsg.add(msg)).inputMessageProvider(() -> inputMsg.pop()).build();
		astInterpreter.execute(ast);
		assertEquals("Dong", printMsg.get(0));
		assertEquals(1, inputMsg.size());
	}

	@Test
	void testCalculateMult() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 3*4;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("12.0", printMessages.get(0));
	}

	@Test
	void testCalculateAddi() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 6+11;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("17.0", printMessages.get(0));
	}

	@Test
	void testCalculateSub() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 12-11;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("1.0", printMessages.get(0));
	}

	@Test
	void testCalculateDiv() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 10/5;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("2.0", printMessages.get(0));
	}

	@Test
	void testCalculateExp1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 2^2;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("4.0", printMessages.get(0));
	}

	@Test
	void testCalculateExp2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 5+3^2;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("14.0", printMessages.get(0));
	}

	@Test
	void testCalculateParen1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 2*(2+3);";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("10.0", printMessages.get(0));
	}

	@Test
	void testCalculateParen2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE ((2*(2+3)));";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("10.0", printMessages.get(0));
	}

	@Test
	void testCalculateMixed1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 3+4*2/(1-5)^2^3;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("3.001953125", printMessages.get(0));
	}

	@Test
	void testCalculateMixed2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE (3*8/6)*0;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("0.0", printMessages.get(0));
	}

	@Test
	void testCalculateNegative() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE 1-8;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("-7.0", printMessages.get(0));
	}

	@Test
	void testVariable1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x = 7;CALCULATE x+1;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("8.0", printMessages.get(1));
	}

	@Test
	void testVariable2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x = 7;VARIABLE z = 1;CALCULATE x+z;";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("8.0", printMessages.get(2));
	}

	@Test
	void testVariable3() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x;VARIABLE z = 1;VARIABLE y = 2;VARIABLE x = CALCULATE y+z;PRINT (x);";
		final SimpleAst ast = simpleLanguageParser.parse(tokenizerSt.tokenize(input));
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("3.0", printMessages.get(3));
	}

}
