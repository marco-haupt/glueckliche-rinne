package de.dhbw.mh.rinne;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import de.dhbw.mh.rinne.antlr.RinneBaseVisitor;
import de.dhbw.mh.rinne.antlr.RinneParser;
import de.dhbw.mh.rinne.ast.AstAssignmentNode;
import de.dhbw.mh.rinne.ast.AstDruckeStmtNode;
import de.dhbw.mh.rinne.ast.AstExpressionNode;
import de.dhbw.mh.rinne.ast.AstIfElseStmtNode;
import de.dhbw.mh.rinne.ast.AstExpressionStmtNode;
import de.dhbw.mh.rinne.ast.AstFunctionCallNode;
import de.dhbw.mh.rinne.ast.AstNode;
import de.dhbw.mh.rinne.ast.AstPreCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstProgramNode;
import de.dhbw.mh.rinne.ast.AstReturnStmtNode;
import de.dhbw.mh.rinne.ast.AstScopedStmtsNode;
import de.dhbw.mh.rinne.ast.AstStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;
import de.dhbw.mh.rinne.ast.AstVariableReferenceNode;
import de.dhbw.mh.rinne.ast.AstPostCheckLoopNode;
import de.dhbw.mh.rinne.ast.AstFunctionDefinitionNode;
import de.dhbw.mh.rinne.ast.AstParameterNode;
import de.dhbw.mh.rinne.ast.AstParameterListNode;

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
        // TODO: Refactor this method to use rule labels for direct access to alternatives
        // and avoid the current if-else cascade when checking which child is non-null.
        if (ctx.funcCall() != null) {
            var functionCallNode = (AstFunctionCallNode) visitFuncCall(ctx.funcCall());
            return new AstExpressionStmtNode(functionCallNode);
        }
        return visitChildren(ctx);
    }

    @Override
    public AstNode visitTypedVariableDeclaration(RinneParser.TypedVariableDeclarationContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();
        var type = RinneType.from(ctx.type().getText());

        if (ctx.initialValue == null || ctx.initialValue.isEmpty()) {
            return new AstVariableDeclarationStmtNode(codeLoc, name, type, null);
        }
        AstExpressionNode init = (AstExpressionNode) visit(ctx.initialValue);
        return new AstVariableDeclarationStmtNode(codeLoc, name, type, init);
    }

    @Override
    public AstNode visitUntypedVariableDeclaration(RinneParser.UntypedVariableDeclarationContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        String name = ctx.variableName.getText();

        if (ctx.initialValue == null || ctx.initialValue.isEmpty()) {
            return new AstVariableDeclarationStmtNode(codeLoc, name, null, null);
        }
        AstExpressionNode init = (AstExpressionNode) visit(ctx.initialValue);
        return new AstVariableDeclarationStmtNode(codeLoc, name, null, init);
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

    @Override
    public AstNode visitFunctionDefinition(RinneParser.FunctionDefinitionContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        String functionName = ctx.functionName.getText();

        AstParameterListNode parameters = (AstParameterListNode) visit(ctx.formalParameters());

        List<AstStmtNode> body = new ArrayList<>();

        for (var bodyStatement : ctx.statement()) {
            AstStmtNode statement = (AstStmtNode) visit(bodyStatement);
            body.add(statement);
        }

        return new AstFunctionDefinitionNode(codeLoc, functionName, parameters, body);
    }

    @Override
    public AstNode visitFormalParameters(RinneParser.FormalParametersContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        List<AstParameterNode> parameters = new ArrayList<>();

        for (var parameter : ctx.formalParameter()) {
            parameters.add((AstParameterNode) visit(parameter));
        }

        return new AstParameterListNode(codeLoc, parameters);
    }

    @Override
    public AstNode visitFormalParameter(RinneParser.FormalParameterContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        String name = ctx.parameterName.getText();
        String type = ctx.type().getText();

        return new AstParameterNode(codeLoc, name, type);
    }

    // Team 4
    @Override
    public AstNode visitIfStatement(RinneParser.IfStatementContext ctx){
        CodeLocation codeLoc = getCodeLocation(ctx);
        // AstExpressionNode condition = (AstExpressionNode) visit(ctx.condition());   -> TODO: wait for condition implementation
        AstExpressionNode condition = new AstVariableReferenceNode(codeLoc, "temp");
        List<AstStmtNode> statements = new ArrayList<>();
        List<AstStmtNode> elseBlock = new ArrayList<>();

        for (var stmtsCtx : ctx.statement()) {
            statements.add((AstStmtNode) visit(stmtsCtx));
        }

        for (var stmtCtx : ctx.elseBlock) {
            elseBlock.add((AstStmtNode) visit(stmtCtx));
        }

        AstIfElseStmtNode node = new AstIfElseStmtNode(codeLoc,condition,statements,elseBlock);
        return node;
    }


    // Team 5
    public AstNode visitDoWhileStatement(RinneParser.DoWhileStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        String condition = ctx.condition().getText();

        List<AstStmtNode> body = new ArrayList<>();

        for (var bodyStatement : ctx.statement()) {
            AstStmtNode statement = (AstStmtNode) visit(bodyStatement);
            body.add(statement);
        }
        return new AstPostCheckLoopNode(codeLoc, condition, body);
    }

    // Team 6
    public AstNode visitWhileStatement(RinneParser.WhileStatementContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);
        AstExpressionNode condition = (AstExpressionNode) visit(ctx.condition());

        AstScopedStmtsNode scopedStmts = new AstScopedStmtsNode(codeLoc);
        for (var stmtCtx : ctx.statement()) {
            AstStmtNode stmtNode = (AstStmtNode) visit(stmtCtx);
            scopedStmts.add(stmtNode);
        }

        return new AstPreCheckLoopNode(codeLoc, condition, scopedStmts);
    }

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

    @Override
    public AstNode visitCondition(RinneParser.ConditionContext ctx) {
        if (ctx.getChildCount() != 1) {
            return null;
        }
        if (ctx.IDENTIFIER() != null) {
            CodeLocation codeLoc = getCodeLocation(ctx);
            String name = ctx.IDENTIFIER().getText();
            return new AstVariableReferenceNode(codeLoc, name);
        }
        return null;
    }

}
