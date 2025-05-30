package de.dhbw.mh.rinne.semantic;

import java.util.NoSuchElementException;

import de.dhbw.mh.rinne.ErrorLogger;
import de.dhbw.mh.rinne.RinneType;
import de.dhbw.mh.rinne.ast.AstSemanticVisitor;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;

public class TypeChecker extends AstSemanticVisitor<RinneType> {

    public TypeChecker(ErrorLogger logger) {
        super(logger);
    }

    public RinneType visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        visitChildren(node);
        var declType = node.getType();
        RinneType initType = null;
        try {
            initType = node.getInitializer().getType();
        } catch (NoSuchElementException | NullPointerException ex) {
        }

        if (initType == null) {
            return declType;
        }
        if (declType == null) {
            declType = initType;
        } else if (declType != initType) {
            reportSemanticError(node,
                    String.format("incompatible types: '%s' cannot be converted to '%s'\n", initType, declType));
        }
        return declType;
    }

    public RinneType visitVariableReference(AstVariableReferenceNode node) {
        visitChildren(node);
        try {
            return node.getType();
        } catch (NoSuchElementException ex) {
        }
        return null;
    }

}
