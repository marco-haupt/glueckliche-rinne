package de.dhbw.mh.rinne.ast;

import de.dhbw.mh.rinne.ErrorLogger;
import de.dhbw.mh.rinne.semantic.ScopeStack;
import de.dhbw.mh.rinne.semantic.SemanticException;

public abstract class AstSemanticVisitor<T> extends AstVisitor<T> {

    private static final boolean EXCEPTION_BASED = false;
    private final ErrorLogger logger;

    protected ScopeStack scopes = new ScopeStack();

    protected AstSemanticVisitor(ErrorLogger logger) {
        this.logger = logger;
    }

    protected void reportSemanticError(AstNode node, String message) {
        if (EXCEPTION_BASED) {
            throw new SemanticException(node, message);
        } else {
            logger.error(node.locationAsString() + ": " + message);
        }
    }

    protected void withNewScope(Runnable block) {
        scopes.pushNewScope();
        try {
            block.run();
        } finally {
            scopes.exitScope();
        }
    }

    public T visit(AstNode node) {
        return node.accept(this);
    }

    public T visitChildren(AstNode node) {
        for (AstNode child : node.getChildren()) {
            if (child == null) {
                continue;
            }
            child.accept(this);
        }
        return null;
    }

    public T visitAssignment(AstAssignmentNode node) {
        return visitChildren(node);
    }

    public T visitDruckeStmt(AstDruckeStmtNode node) {
        return visitChildren(node);
    }

    public T visitExpression(AstExpressionNode node) {
        return visitChildren(node);
    }

    public T visitExpressionStmt(AstExpressionStmtNode node) {
        return visitChildren(node);
    }

    public T visitFunctionCall(AstFunctionCallNode node) {
        return visitChildren(node);
    }

    public T visitFunctionDefinition(AstFunctionDefinitionNode node) {
        return visitChildren(node);
    }

    public T visitOhjeStmt(AstOhjeStmtNode node) {
        return visitChildren(node);
    }

    public T visitParameter(AstParameterNode node) {
        return visitChildren(node);
    }

    public T visitPostCheckLoop(AstPostCheckLoopNode node) {
        return visitChildren(node);
    }

    public T visitPreCheckLoop(AstPreCheckLoopNode node) {
        return visitChildren(node);
    }

    public T visitProgram(AstProgramNode node) {
        scopes.enterScope(node.getScope());
        T temp = visitChildren(node);
        scopes.exitScope();
        return temp;
    }

    public T visitReturnStmt(AstReturnStmtNode node) {
        return visitChildren(node);
    }

    public T visitIfElseStmt(AstIfElseStmtNode node) {
        return visitChildren(node);
    }

    public T visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        return visitChildren(node);
    }

    public T visitVariableReference(AstVariableReferenceNode node) {
        return visitChildren(node);
    }

    public T visitScopedStatements(AstScopedStmtsNode node) {
        scopes.enterScope(node.getScope());
        T temp = visitChildren(node);
        scopes.exitScope();
        return temp;
    }

}
