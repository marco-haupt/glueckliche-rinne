grammar Rinne;


program
  : (statement)* EOF
  ;

statement
  : functionDefinition
  | variableDeclaration
  | assignment
  | ifStatement
  | whileStatement
  | doWhileStatement
  | funcCall COMMA
  | returnStatement
  | druckeStatement
  ;


returnStatement
  : GEBE expression ZURÜCK COMMA
  ;

druckeStatement
  : DRUCKE expression COMMA
  ;

variableDeclaration
  : variableName=IDENTIFIER ALS type (ODER SO COMMA | INIT initialValue=expression COMMA)  # typedVariableDeclaration
  | variableName=IDENTIFIER INIT initialValue=expression COMMA                             # untypedVariableDeclaration
  ;

assignment
  : variableName=IDENTIFIER ASSIGN expression COMMA
  ;

ifStatement
  : WENN LPAREN condition RPAREN (statement)* ( ANSONSTEN (elseBlock+=statement)* )? PERIOD
  ;

whileStatement
  : WÄHREND LPAREN condition RPAREN (statement)* PERIOD
  ;

doWhileStatement
  : WIEDERHOLE (statement)* SOLANGE LPAREN condition RPAREN COMMA
  ;

functionDefinition
  : DEKLARIERE functionName=IDENTIFIER LPAREN MIT formalParameters RPAREN (statement)* PERIOD
  ;

formalParameters
  : OHNE
  | formalParameter ( UND formalParameter )*
  ;

formalParameter
  : parameterName=IDENTIFIER ALS type
  ;


funcCall
  : BITTE functionName=IDENTIFIER (MIT (actualParameters+=expression)+)?
  ;

expression
  : funcCall                                                 # functionCall
  | PLUS expression                                          # unaryExpression
  | lhs=expression operator=(STAR|DIV|MOD) rhs=expression    # multiplicativeExpression
  | lhs=expression operator=(PLUS|MINUS) rhs=expression      # additiveExpression
  | variableName=IDENTIFIER                                  # variableReference
  | lit=(LONG_LITERAL|DOUBLE_LITERAL|STRING_LITERAL)         # literalExpression
  | LPAREN expression RPAREN                                 # parenthesizedExpression
  ;

condition
  : lhs=expression operator=(LT|LE|GT|GE) rhs=expression
  | leftCond=condition operator=(EQ|NEQ) rightCond=condition
  | leftCond=condition operator=LAND rightCond=condition
  | leftCond=condition operator=LOR rightCond=condition
  | NOT condition
  | IDENTIFIER
  | WAHR
  | FALSCH
  ;

type
  : FLIEßZAHL
  | GANZZAHL
  | SCHNUR
  | WAHRHEITSWERT
  ;


LONG_LITERAL   : [0-9]+ ;
DOUBLE_LITERAL : [0-9]+ '.' [0-9]* | '.' [0-9]+ ;
STRING_LITERAL : '"' ( ~["\n\r] | '\\"' )* '"' ;


NOT : '!' ;

LT : '<' ;
LE : '<=' ;
GT : '>' ;
GE : '>=' ;

EQ : '==' ;
NEQ : '!=' ;

LAND : '&&' ;
LOR  : '||' ;

STAR  : '*' ;
DIV   : '/' ;
MOD   : '%' ;
PLUS  : '+' ;
MINUS : '-' ;

LPAREN     : '(' ;
RPAREN     : ')' ;
COMMA      : ',' ;
PERIOD     : ';' ;

ALS        : 'als' ;
ANSONSTEN  : 'ansonsten' ;
BITTE      : 'bitte' ;
DEKLARIERE : 'deklariere' ;
DRUCKE     : 'drucke' ;
FALSCH     : 'falsch' ;
FEHLER     : 'Fehler' ;
FLIEßZAHL  : 'Fließzahl' ;
GANZZAHL   : 'Ganzzahl' ;
GEBE       : 'gebe' ;
JA         : 'ja' ;
MIT        : 'mit' ;
NEIN       : 'nein' ;
ODER       : 'oder' ;
OHJE       : 'ohje' ;
OHNE       : 'ohne' ;
SCHNUR     : 'Schnur' ;
SO         : 'so' ;
SOLANGE    : 'solange' ;
UND        : 'und' ;
WAHR       : 'wahr' ;
WAHRHEITSWERT : 'Wahrheitswert' ;
WENN       : 'wenn' ;
WIEDERHOLE : 'wiederhole' ;
WÄHREND    : 'während' ;
ZURÜCK     : 'zurück' ;

INIT       : ':=' ;
ASSIGN     : '=' ;

NUMBER     : [0-9]+;
IDENTIFIER : [a-zA-Z][a-zA-Z0-9]* ;

WHITESPACE : [ \t\r\n]+ -> channel(HIDDEN) ;
