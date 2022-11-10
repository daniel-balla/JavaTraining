/* Copyright (c) 2021 Deloitte. All rights reserved. */
package exercise6;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import exercise6.ast.AstInterpreter;
import exercise6.ast.SimpleAst;
import exercise6.ast.nodes.CalculateNode;
import exercise6.ast.nodes.PrintNode;

/**
 * Diese kleine feine Sprache benötigt noch einen Parser, der aus den Statements
 * einen Abstrakten Syntax Baum generiert, der danach durchlaufen und
 * interpretiert werden kann.
 * 
 * Außerdem könnte man die "Sprache" noch um ein paar Funktionalitäten
 * erweitern. z.B. um ein PRINTI statment, das den nächsten INPUT ausgiebt, oder
 * um ein CALCULATE was einen Arithmetischen Ausdruck entgegen nimmt und
 * evaluiert.
 */
class TestSimpleInterpretedLanguageRegexParser {

	private final Parser simpleLanguageParser = new ParserImp();
	private final Tokenizer tokenizer = new Tokenizer();

	@Test
	void testTokenizer() {
		final String input = "PRINT h;CALCULATE 8+9;";
		final String[][] parserInput = tokenizer.tokenize(input);
		final String[][] pI = {{"PRINT", "h"},{"CALCULATE", "8+9"}};
		assertArrayEquals(pI, parserInput);
	}
	
	@Test
	void testParser() {
		final String input = "PRINT h;CALCULATE 8+9;";
		final SimpleAst ast1 = new SimpleAst();
		ast1.addNode(new PrintNode("h", ast1));
		ast1.addNode(new CalculateNode("8+9", null));
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast2 = simpleLanguageParser.parse(parserInput);
		assertEquals(ast1.getNodes(), ast2.getNodes());
	}
	
	@Test
	void testPrint() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "PRINT Hello;PRINT World!;";
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("3.0", printMessages.get(0));
	}

	@Test
	void testCalculateMixed2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "CALCULATE (3*8/6)*0;";
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
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
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals(1, printMessages.size());
		assertEquals("-7.0", printMessages.get(0));
	}
	
	@Test
	void testVariable1() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x=7;CALCULATE x+1;";
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("8.0", printMessages.get(1));
	}
	
	@Test
	void testVariable2() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x=7;VARIABLE z=1;CALCULATE x+z;";
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("8.0", printMessages.get(2));
	}
	
	@Test
	void testVariable3() {
		final List<String> printMessages = new LinkedList<>();
		final String input = "VARIABLE x;VARIABLE z=1;VARIABLE y=2;VARIABLE x = CALCULATE y+z;PRINT (x);";
		final String[][] parserInput = tokenizer.tokenize(input);
		final SimpleAst ast = simpleLanguageParser.parse(parserInput);
		final AstInterpreter astInterpreter = new AstInterpreter.Builder()
				.printMessageConsumer(message -> printMessages.add(message)).build();
		astInterpreter.execute(ast);
		assertEquals("3.0", printMessages.get(3));
	}
}































