package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;

public class AstVariableDeclarationStmtNode extends AstStmtNode {

    private final String name;
    private final RinneType type;
    private final AstExpressionNode initializer;
    private boolean isUsed = false;

    public AstVariableDeclarationStmtNode(CodeLocation codeLocation, String name, RinneType type,
            AstExpressionNode initializer) {
        super(codeLocation);
        this.name = name;
        this.type = type;
        this.initializer = initializer;
        this.children.add(initializer);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitVariableDeclarationStmt(this);
    }

    public String getName() {
        return name;
    }

    public AstExpressionNode getInitializer() {
        return initializer;
    }

    public void setUsed() {
        isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public RinneType getType() {
        return type;
    }

}
