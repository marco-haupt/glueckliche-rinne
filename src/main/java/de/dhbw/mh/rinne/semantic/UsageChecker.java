package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ErrorLogger;
import de.dhbw.mh.rinne.ast.AstSemanticVisitor;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;

public class UsageChecker extends AstSemanticVisitor<Void> {

    public UsageChecker(ErrorLogger logger) {
        super(logger);
    }

    public Void visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        String variableName = node.getName();
        if (!node.isUsed()) {
            reportSemanticError(node, "'" + variableName + "' is never used\n");
        }
        return visitChildren(node);
    }

}
