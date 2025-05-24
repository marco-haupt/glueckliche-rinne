package de.dhbw.mh.rinne;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import de.dhbw.mh.rinne.antlr.RinneBaseVisitor;
import de.dhbw.mh.rinne.antlr.RinneParser;
import de.dhbw.mh.rinne.ast.AstDruckeStmtNode;
import de.dhbw.mh.rinne.ast.AstExpressionNode;
import de.dhbw.mh.rinne.ast.AstFunctionCallNode;
import de.dhbw.mh.rinne.ast.AstNode;
import de.dhbw.mh.rinne.ast.AstProgramNode;
import de.dhbw.mh.rinne.ast.AstReturnStmtNode;
import de.dhbw.mh.rinne.ast.AstStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;
import de.dhbw.mh.rinne.ast.AstAssignmentNode;

public class AstBuilder extends RinneBaseVisitor<AstNode> {

    private CodeLocation getCodeLocation(ParserRuleContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        return new CodeLocation(line, column);
    }

    @Override
    public AstNode visitProgram(RinneParser.ProgramContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        List<AstStmtNode> statements = new ArrayList<>();
        for (var stmtCtx : ctx.statement()) {
            statements.add((AstStmtNode) visit(stmtCtx));
        }
        return new AstProgramNode(codeLoc, statements);
    }

    @Override
    public AstNode visitStatement(RinneParser.StatementContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitTypedVariableDeclaration(RinneParser.TypedVariableDeclarationContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();
        String type = ctx.type().getText();

        if (ctx.initialValue.isEmpty()) {
            return new AstVariableDeclarationStmtNode(codeLoc, name, type, null);
        }
        AstExpressionNode init = (AstExpressionNode) visit(ctx.initialValue);
        return new AstVariableDeclarationStmtNode(codeLoc, name, type, init);
    }

    @Override
    public AstNode visitVariableReference(RinneParser.VariableReferenceContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();

        return new AstVariableReferenceNode(codeLoc, name);
    }

    // Team 1
    @Override
    public AstNode visitAssignment(RinneParser.AssignmentContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();
        var value = (AstExpressionNode) visit(ctx.expression());
        return new AstAssignmentNode(codeLoc, name, value);
    }

    // Team 2
    @Override
    public AstNode visitFuncCall(RinneParser.FuncCallContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String func = ctx.functionName.getText();
        List<AstExpressionNode> args = new ArrayList<AstExpressionNode>();
        ctx.actualParameters.forEach(e -> args.add((AstExpressionNode) visit(e)));

        return new AstFunctionCallNode(codeLoc, func, args);
    }

    // Team 3

    // Team 4

    // Team 5

    // Team 6

    // Team 7
    @Override
    public AstNode visitDruckeStatement(RinneParser.DruckeStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        AstExpressionNode expression = (AstExpressionNode) visit(ctx.expression());
        return new AstDruckeStmtNode(codeLoc, expression);
    }

    // Team 8
    @Override
    public AstNode visitReturnStatement(RinneParser.ReturnStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        var expr = (AstExpressionNode) visit(ctx.expression());
        return new AstReturnStmtNode(codeLoc, expr);
    }

}
