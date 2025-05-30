package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ast.AstNode;

public interface SymbolTable {

    public static class Entry {

        private final AstNode declNode;

        public Entry(AstNode declNode) {
            this.declNode = declNode;
        }

        public AstNode getDeclNode() {
            return declNode;
        }

    }

}
