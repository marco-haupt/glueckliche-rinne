package de.dhbw.mh.rinne.semantic;

import de.dhbw.mh.rinne.ErrorLogger;
import de.dhbw.mh.rinne.ast.AstSemanticVisitor;

public class FunctionRegistrar extends AstSemanticVisitor<Void> {

    protected FunctionRegistrar(ErrorLogger logger) {
        super(logger);
    }

}
