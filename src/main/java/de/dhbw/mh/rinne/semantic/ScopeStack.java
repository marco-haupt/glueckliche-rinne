package de.dhbw.mh.rinne.semantic;

import java.util.Optional;
import java.util.Stack;

import de.dhbw.mh.rinne.ast.AstFunctionDefinitionNode;
import de.dhbw.mh.rinne.ast.AstNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;

public class ScopeStack {

    private Stack<Scope> stack = new Stack<>();

    public ScopeStack() {
        // stack.push(new Scope());
    }

    public Optional<SymbolTable.Entry> lookupVariable(String name) {
        return deepLookup("var:" + name);
    }

    public Optional<SymbolTable.Entry> lookupFunction(String name) {
        return deepLookup("fn:" + name);
    }

    private Optional<SymbolTable.Entry> deepLookup(String name) {
        for (int i = stack.size() - 1; i >= 0; i--) {
            Scope scope = stack.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return Optional.empty();
    }

    private Optional<SymbolTable.Entry> shallowLookup(String name) {
        return stack.peek().get(name);
    }

    public void defineVariable(String name, AstVariableDeclarationStmtNode node) {
        define("var:" + name, name, node);
    }

    public void defineFunction(String name, AstFunctionDefinitionNode node) {
        define("fn:" + name, name, node);
    }

    private void define(String id, String name, AstNode node) {
        var temp = shallowLookup(id);
        if (temp.isPresent()) {
            throw new SemanticException(node,
                    "'" + name + "' already declared at " + temp.get().getDeclNode().locationAsString() + "\n");
        }
        stack.peek().define(id, node);
    }

    public Scope pushNewScope() {
        Scope scope = new Scope();
        stack.push(scope);
        return scope;
    }

    public void enterScope(Scope scope) {
        stack.push(scope);
    }

    public void exitScope() {
        stack.pop();
    }

}
