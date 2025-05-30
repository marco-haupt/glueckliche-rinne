package de.dhbw.mh.rinne.ast;

import java.util.Optional;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.RinneType;
import de.dhbw.mh.rinne.semantic.SymbolTable;

public class AstVariableReferenceNode extends AstExpressionNode {

    private final String name;
    private Optional<SymbolTable.Entry> declaration = Optional.empty();

    public AstVariableReferenceNode(CodeLocation codeLocation, String name) {
        super(codeLocation);
        this.name = name;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visitVariableReference(this);
    }

    public String getName() {
        return name;
    }

    public void setDeclNode(SymbolTable.Entry declNode) {
        declaration = Optional.of(declNode);
        var varDeclNode = (AstVariableDeclarationStmtNode) declNode.getDeclNode();
        varDeclNode.setUsed();
    }

    public RinneType getType() {
        var declNode = (AstVariableDeclarationStmtNode) declaration.get().getDeclNode();
        return declNode.getType();
    }

}
