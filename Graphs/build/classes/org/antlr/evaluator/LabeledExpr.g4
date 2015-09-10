grammar LabeledExpr; // name of my grammar

prog:   stat+ ;

stat:   expr NEWLINE                # printExpr
    |   ID '=' DOUBLE NEWLINE         # assign
    | 	expr '=' expr NEWLINE		# evaluate
    |   NEWLINE                     # blank
    ;

expr:   expr op=('*'|'/'| '^') expr # MulDivPow
    |   expr op=('+'|'-') expr      # AddSub
    |   DOUBLE                      # double
    | 	INT							# int
    |   ID                          # id
    |   '(' expr ')'                # parens
    ;

MUL :   '*' ; // assigns token name to '*' used above in grammar
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
POW :   '^'	;
ID  :   [a-zA-Z]+ ;      // match identifiers
INT :   [0-9]+ ;         // match integers
DOUBLE: ('-')?([0-9]+)('.'[0-9]+)?;
//DOUBLE: ('-')?(INT)+ ('.'INT)? ; // match double
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace


