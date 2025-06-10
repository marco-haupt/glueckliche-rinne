package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public class AstAssignmentNode extends AstStmtNode {
    private final String name;
    private AstExpressionNode value;

    public AstAssignmentNode(CodeLocation codeLocation, String name, AstExpressionNode value) {
        super(codeLocation);
        this.name = name;
        this.value = value;
        this.children.add(value);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitAssignment(this);
    }

    public AstExpressionNode value() {
        return value;
    }

    public String name() {
        return name;
    }

    public void castValue(RinneType targetType) {
        value = new AstCastNode(value, targetType);
    }
}
