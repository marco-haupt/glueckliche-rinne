package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.semantic.Scope;

public class AstScopedStmtsNode extends AstNode {

    private final Scope scope;

    public AstScopedStmtsNode(CodeLocation codeLocation) {
        super(codeLocation);
        this.scope = new Scope();
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitScopedStatements(this);
    }

    public void add(AstStmtNode stmt) {
        this.children.add(stmt);
    }

    public Scope getScope() {
        return scope;
    }

}
