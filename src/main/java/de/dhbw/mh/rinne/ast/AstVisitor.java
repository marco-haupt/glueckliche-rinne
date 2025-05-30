package de.dhbw.mh.rinne.ast;

class AstVisitor<T> {

    T visit(AstNode node) {
        return node.accept(this);
    }

    T visitChildren(AstNode node) {
        for (AstNode child : node.getChildren()) {
            child.accept(this);
        }
        return null;
    }

    T visitAssignment(AstAssignmentNode node) {
        return visitChildren(node);
    }

    T visitDruckeStmt(AstDruckeStmtNode node) {
        return visitChildren(node);
    }

    T visitExpression(AstExpressionNode node) {
        return visitChildren(node);
    }

    T visitExpressionStmt(AstExpressionStmtNode node) {
        return visitChildren(node);
    }

    T visitFunctionCall(AstFunctionCallNode node) {
        return visitChildren(node);
    }

    T visitFunctionDefinition(AstFunctionDefinitionNode node) {
        return visitChildren(node);
    }

    T visitParameterList(AstParameterListNode node) {
        return visitChildren(node);
    }

    T visitOhjeStmt(AstOhjeStmtNode node) {
        return visitChildren(node);
    }

    T visitParameter(AstParameterNode node) {
        return visitChildren(node);
    }

    T visitPostCheckLoop(AstPostCheckLoopNode node) {
        return visitChildren(node);
    }

    T visitPreCheckLoop(AstPreCheckLoopNode node) {
        return visitChildren(node);
    }

    T visitProgram(AstProgramNode node) {
        return visitChildren(node);
    }

    T visitReturnStmt(AstReturnStmtNode node) {
        return visitChildren(node);
    }

    T visitIfElseStmt(AstIfElseStmtNode node) {
        return visitChildren(node);
    }

    T visitVariableDeclarationStmt(AstVariableDeclarationStmtNode node) {
        return visitChildren(node);
    }

    T visitVariableReference(AstVariableReferenceNode node) {
        return visitChildren(node);
    }

    T visitScopedStatements(AstScopedStmtsNode node) {
        return visitChildren(node);
    }

}
