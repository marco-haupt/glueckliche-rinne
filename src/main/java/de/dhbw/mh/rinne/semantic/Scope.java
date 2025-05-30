package de.dhbw.mh.rinne.semantic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.dhbw.mh.rinne.ast.AstNode;

public class Scope {

    private final Map<String, SymbolTable.Entry> symbols = new HashMap<>();

    public boolean containsKey(String name) {
        return symbols.containsKey(name);
    }

    public Optional<SymbolTable.Entry> get(String name) {
        return Optional.ofNullable(symbols.get(name));
    }

    public void define(String name, AstNode node) {
        symbols.put(name, new SymbolTable.Entry(node));
    }

}
