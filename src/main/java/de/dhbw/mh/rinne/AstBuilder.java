package de.dhbw.mh.rinne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.dhbw.mh.rinne.ast.*;
import org.antlr.v4.runtime.ParserRuleContext;

import de.dhbw.mh.rinne.antlr.RinneBaseVisitor;
import de.dhbw.mh.rinne.antlr.RinneParser;

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

    // Team 2

    // Team 3

    @Override
    public AstNode visitFunctionDefinition(RinneParser.FunctionDefinitionContext ctx) {
        CodeLocation codeLoc = getCodeLocation(ctx);

        String functionName = ctx.functionName.getText();
        HashMap<String, String> args = new HashMap<>();

        for (var parameter : ctx.formalParameters().formalParameter()) {
            String name = parameter.parameterName.getText();
            String type = parameter.type().getText();

            args.put(name, type);
        }

        List<AstStmtNode> body = new ArrayList<>();

        for (var bodyStatement : ctx.statement()) {
            AstStmtNode statement = (AstStmtNode) visit(bodyStatement);
            body.add(statement);
        }

        return new AstFunctionDefinitionNode(codeLoc, functionName, args, body);
ea   }

    // Team 4

    // Team 5

    // Team 6

    // Team 7

    // Team 8

}
