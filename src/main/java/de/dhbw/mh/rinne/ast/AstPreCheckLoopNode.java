package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;

public class AstPreCheckLoopNode extends AstStmtNode {

    private AstExpressionNode condition;
    private AstScopedStmtsNode scopedStmts;

    public AstPreCheckLoopNode(CodeLocation codeLocation, AstExpressionNode condition, AstScopedStmtsNode scopedStmts) {
        super(codeLocation);
        this.condition = condition;
        this.scopedStmts = scopedStmts;
        this.children.add(condition);
        this.children.add(scopedStmts);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitPreCheckLoop(this);
    }

}
