package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.UnaryOperation;

public class AstUnaryExpressionNode extends AstExpressionNode {

    private final UnaryOperation operator;
    private final AstExpressionNode expr;

    public AstUnaryExpressionNode(CodeLocation codeLocation, UnaryOperation operator, AstExpressionNode expr) {
        super(codeLocation);
        this.operator = operator;
        this.expr = expr;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitUnaryExpression(this);
    }

}
