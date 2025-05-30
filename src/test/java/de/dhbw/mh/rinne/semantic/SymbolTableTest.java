package de.dhbw.mh.rinne.semantic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.dhbw.mh.rinne.CodeLocation;
import de.dhbw.mh.rinne.ast.AstFunctionDefinitionNode;
import de.dhbw.mh.rinne.ast.AstVariableDeclarationStmtNode;

class SymbolTableTest {

    private static final CodeLocation DUMMY_CODE_LOCATION = new CodeLocation(7, 11);

    private static final AstVariableDeclarationStmtNode VAR_DECL_GLOBAL_VAR1 = new AstVariableDeclarationStmtNode(
            DUMMY_CODE_LOCATION, "var1", null, null);

    private static final AstVariableDeclarationStmtNode VAR_DECL_NESTED_VAR1 = new AstVariableDeclarationStmtNode(null,
            "var1", null, null);

    private static final AstFunctionDefinitionNode FN_DECL_VAR1 = new AstFunctionDefinitionNode(null);

    @Test
    @DisplayName("Empty symbol table returns no result")
    void lookupInEmptyTableReturnsEmpty() {
        ScopeStack scope = new ScopeStack();
        scope.pushNewScope();
        assertThat(scope.lookupVariable("var1")).isEmpty();
    }

    @Test
    @DisplayName("Variable can be defined and resolved")
    void defineAndLookupVariable() {
        ScopeStack scope = new ScopeStack();
        scope.pushNewScope();
        scope.defineVariable("var1", VAR_DECL_GLOBAL_VAR1);

        var result = scope.lookupVariable("var1");

        assertThat(result).isPresent();
        assertThat(result.get().getDeclNode()).isSameAs(VAR_DECL_GLOBAL_VAR1);
    }

    @Test
    @DisplayName("Functions and variables can coexist with same name")
    void defineFunctionAndVariableWithSameName() {
        ScopeStack scope = new ScopeStack();
        scope.pushNewScope();
        scope.defineVariable("var1", VAR_DECL_GLOBAL_VAR1);
        scope.defineFunction("var1", FN_DECL_VAR1);

        var variable = scope.lookupVariable("var1");
        var function = scope.lookupFunction("var1");

        assertThat(variable).isPresent();
        assertThat(variable.get().getDeclNode()).isSameAs(VAR_DECL_GLOBAL_VAR1);

        assertThat(function).isPresent();
        assertThat(function.get().getDeclNode()).isSameAs(FN_DECL_VAR1);
    }

    @Test
    @DisplayName("Variable can be shadowed in nested scope")
    void shadowingInNestedScope() {
        ScopeStack scope = new ScopeStack();
        scope.pushNewScope();
        scope.defineVariable("var1", VAR_DECL_GLOBAL_VAR1);

        assertThat(scope.lookupVariable("var1")).get().extracting(SymbolTable.Entry::getDeclNode)
                .isSameAs(VAR_DECL_GLOBAL_VAR1);

        scope.pushNewScope();
        scope.defineVariable("var1", VAR_DECL_NESTED_VAR1);

        assertThat(scope.lookupVariable("var1")).get().extracting(SymbolTable.Entry::getDeclNode)
                .isSameAs(VAR_DECL_NESTED_VAR1);

        scope.exitScope();

        assertThat(scope.lookupVariable("var1")).get().extracting(SymbolTable.Entry::getDeclNode)
                .isSameAs(VAR_DECL_GLOBAL_VAR1);
    }

    @Test
    @DisplayName("Parent scopes are visible in nested scopes")
    void resolveVariableFromParentScope() {
        ScopeStack scope = new ScopeStack();
        scope.pushNewScope();
        scope.defineVariable("var1", VAR_DECL_GLOBAL_VAR1);
        scope.pushNewScope();

        assertThat(scope.lookupVariable("var1")).get().extracting(SymbolTable.Entry::getDeclNode)
                .isSameAs(VAR_DECL_GLOBAL_VAR1);
    }

    @Test
    @DisplayName("Redefining a variable in the same scope throws exception")
    void redefineVariableInSameScopeThrows() {
        ScopeStack scope = new ScopeStack();
        scope.pushNewScope();
        scope.defineVariable("var1", VAR_DECL_GLOBAL_VAR1);

        assertThatThrownBy(() -> scope.defineVariable("var1", VAR_DECL_GLOBAL_VAR1))
                .isInstanceOf(SemanticException.class).hasMessageContaining("'var1' already declared at 7:11");
    }

}
