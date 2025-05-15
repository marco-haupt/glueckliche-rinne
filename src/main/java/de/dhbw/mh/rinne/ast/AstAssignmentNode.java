package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstAssignmentNode extends AstStmtNode {
    private final String name;
    private final AstExpressionNode value;

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
}