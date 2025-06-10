package de.dhbw.mh.rinne.semantic;

import java.util.NoSuchElementException;

import de.dhbw.mh.rinne.BinaryOperation;
import de.dhbw.mh.rinne.RinneType;
import de.dhbw.mh.rinne.ast.AstLiteralNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;
import de.dhbw.mh.rinne.ast.AstLiteralNode.Wahrheitswert;

public class TypeChecker extends BaseTypeChecker {

    @Override
    public RinneType visitPost(AstVariableDeclarationStmtNode node, RinneType initType) {
        var declType = node.getType();
        TypeCheckResult result = BaseTypeChecker.checkBinaryOperation(declType, BinaryOperation.ASSIGN, initType);
        switch (result.status()) {
            case OK:
                if (declType == null) {
                    declType = initType;
                }
                break;
            case NEEDS_CAST:
                var supertype = result.requiredType();
                if (declType != null && declType != supertype) {
                    reportSemanticError(node,
                            String.format("incompatible types: '%s' cannot be converted to '%s'", initType, declType));
                }
                if (initType != supertype) {
                    node.castInitializer(supertype);
                }
                break;
            case INCOMPATIBLE:
                reportSemanticError(node,
                        String.format("incompatible types: '%s' cannot be converted to '%s'", initType, declType));
                break;
        }
        return declType;
    }

    @Override
    public RinneType visitVariableReference(AstVariableReferenceNode node) {
        visitChildren(node);
        try {
            var varDef = scopes.lookupVariable(node.getName());
            if (varDef.isPresent()) {
                return varDef.get().getDeclNode().getType();
            }
        } catch (NoSuchElementException ex) {
        }
        return null;
    }

    // Team 1: Binary Operations

    // Team 2: Unary Operations

    // Team 3: Assignments

    // Team 5: Literals
    @Override
    public RinneType visitLiteral(AstLiteralNode node) {
        switch (node.getType()) {
            case FLIEßZAHL:
                break;
            case GANZZAHL:
                break;
            case SCHNUR:
                break;
            case WAHRHEITSWERT:
                if (node.getLexeme().equals("wahr")) {
                    node.setValue(new Wahrheitswert(true));
                } else if (node.getLexeme().equals("falsch")) {
                    node.setValue(new Wahrheitswert(false));
                }
                break;
            default:
                break;
        }
        return node.getType();
    }

    // Lecturer

}
