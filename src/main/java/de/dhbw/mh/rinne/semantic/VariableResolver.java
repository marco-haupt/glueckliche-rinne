package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ErrorLogger;
import de.dhbw.mh.rinne.ast.AstSemanticVisitor;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;

public class VariableResolver extends AstSemanticVisitor<Void> {

    public VariableResolver(ErrorLogger logger) {
        super(logger);
    }

    public Void visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        String variableName = node.getName();
        try {
            scopes.defineVariable(variableName, node);
        } catch (SemanticException ex) {
            String bla = ex.getMessage();
            reportSemanticError(node, ex.getMessage());
        }
        return visitChildren(node);
    }

    public Void visitVariableReference(AstVariableReferenceNode node) {
        String variableName = node.getName();
        var varDef = scopes.lookupVariable(variableName);
        if (varDef.isEmpty()) {
            reportSemanticError(node, "cannot find symbol '" + variableName + "'\n");
        } else {
            node.setDeclNode(varDef.get());
        }
        return visitChildren(node);
    }

}
