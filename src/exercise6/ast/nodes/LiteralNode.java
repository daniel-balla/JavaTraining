package exercise6.ast.nodes;

import java.util.List;

import exercise6.ast.Node;
import exercise6.ast.SimpleAst;

public class LiteralNode extends ValueProviderNode {

	public LiteralNode(String value, SimpleAst ast) {
		if (Character.isDigit(value.charAt(0))) {
			this.value = Double.parseDouble(value);
		} else {
			List<Node> nodes = ast.getNodes();
			for (int i = 0; i < nodes.size(); i++) {
				if (nodes.get(i) instanceof VariableDeclarationNode) {
					this.value = ((VariableDeclarationNode) nodes.get(i)).value;
				}
			}
		}
	}
	
	public LiteralNode(Double number) {
		this.value = number;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}
}
