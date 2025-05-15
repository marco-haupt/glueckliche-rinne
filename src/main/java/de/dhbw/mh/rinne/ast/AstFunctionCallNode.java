package de.dhbw.mh.rinne.ast;

import java.util.List;

import de.dhbw.mh.rinne.CodeLocation;

public class AstFunctionCallNode extends AstExpressionNode {
    private final String functionName;
    private final List<AstExpressionNode> arguments;

    public AstFunctionCallNode(CodeLocation codeLocation, String functionName, List<AstExpressionNode> arguments) {
        super(codeLocation);
        this.functionName = functionName;
        this.arguments = arguments;
        arguments.forEach(e -> this.children.add(e));
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitFunctionCall(this);
    }

}