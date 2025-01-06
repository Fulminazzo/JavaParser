<p align="center">
    <a href="https://app.codacy.com/gh/Fulminazzo/mojito/"><img src="https://tokei.rs/b1/github/Fulminazzo/mojito?category=code&style=flat" alt="Lines of Code" /></a>
    <a href="../../releases/latest"><img src="https://img.shields.io/github/v/release/Fulminazzo/mojito?display_name=tag&color=red" alt="Latest version" /></a>
    <!--<a href="https://app.codacy.com/gh/Fulminazzo/mojito/"><img src="https://tokei.rs/b1/github/Fulminazzo/mojito?category=test&style=flat" alt="Lines of Tests" /></a>-->
</p>
<p align="center">
    <img src="https://fulminazzo.it/badge/coverage/Fulminazzo/mojito/gradle.yml" alt="Tests coverage" />
    <a href="https://app.codacy.com/gh/Fulminazzo/mojito/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade"><img src="https://app.codacy.com/project/badge/Grade/c9b24e43cb7c4658975624cc9862a8d3" alt="Codacy Grade" /></a>
</p>
<p align="center">
    <img src="https://img.shields.io/badge/Yes%2C%20I%20love-writing%20tests-00aa00?style=for-the-badge&labelColor=1FE417" alt="" />
</p>

Introduction on what the project is


Usage:
command line

import

## Grammar
The following is the grammar respected by the parser:

```
JAVA_PROGRAM := SINGLE_STMT*

BLOCK := CODE_BLOCK | SINGLE_STMT
CODE_BLOCK := \{ SINGLE_STMT* \}
SINGLE_STMT := STMT | ;

STMT := return EXPR; | throw EXPR; break; | continue; | 
        TRY_STMT | SWITCH_STMT | FOR_STMT | DO_STMT | WHILE_STMT | IF_STMT
        ASSIGNMENT;

TRY_STMT := try ( \( ASSIGNMENT_BLOCK \) )? CODE_BLOCK CATCH+ ( finally CODE_BLOCK )?
ASSIGNMENT_BLOCK := (ARRAY_LITERAL LITERAL ( = EXPR? ); )+
CATCH := catch \( ( LITERAL \| )* LITERAL LITERAL \) CODE_BLOCK

SWITCH_STMT := switch \( EXPR \) \{ (CASE_BLOCK)* (DEFAULT_BLOCK)? \}
CASE_BLOCK := case EXPR: ( CODE_BLOCK | SINGLE_STMT* )
DEFAULT_BLOCK := default: ( CODE_BLOCK | SINGLE_STMT* )

FOR_STMT := for \( ASSIGNMENT?; EXPR?; EXPR? \) BLOCK | ENHANCED_FOR_STMT
ENHANCED_FOR_STMT := for \( ARRAY_LITERAL LITERAL : EXPR \) BLOCK

DO_STMT := do BLOCK while PAR_EXPR
WHILE_STMT := while PAR_EXPR BLOCK
IF_STMT := if PAR_EXPR BLOCK ( else IF_STMT )* ( else BLOCK )?

ASSIGNMENT := ARRAY_LITERAL LITERAL ( = EXPR? ) | LITERAL = EXPR | EXPR

EXPR := NEW_OBJECT | INCREMENT | DECREMENT | AND
NEW_OBJECT := new LITERAL METHOD_INVOCATION |
              new ARRAY_LITERAL{ ( EXPR )? ( , EXPR )* \} |
              new LITERAL ( \[ NUMBER_VALUE \] )+
ARRAY_LITERAL := LITERAL ( \[\] )* | LITERAL ( \[ [0-9]+ \] )+

INCREMENT := ++ATOM
DECREMENT := --ATOM | MINUS

AND := OR (&& OR)*
OR := EQUAL (|| EQUAL)*
EQUAL := NOT_EQUAL (== NOT_EQUAL)* <br/>
NOT_EQUAL := LESS_THAN (!= LESS_THAN)* <br/>
LESS_THAN := LESS_THAN_EQUAL (< LESS_THAN_EQUAL)* <br/>
LESS_THAN_EQUAL := GREATER_THAN (<= GREATER_THAN)* <br/>
GREATER_THAN := GREATER_THAN_EQUAL (> GREATER_THAN_EQUAL)* <br/>
GREATER_THAN_EQUAL := BIT_AND (>= BIT_AND)*

BIT_AND := BIT_OR ( (& BIT_OR)* | (&= BIT_OR) )
BIT_OR := BIT_XOR ( (| BIT_XOR)* | (|= BIT_XOR) )
BIT_XOR := LSHIFT ( (^ LSHIFT)* | (^= LSHIFT) )

LSHIFT := RSHIFT ( (<< RSHIFT)* | (<<= RSHIFT) )
RSHIFT := URSHIFT ( (>> URSHIFT)* | (>>= URSHIFT) )
URSHIFT := ADD ( (>>> ADD)* | (>>>= ADD) )

ADD := SUB ( (+ SUB)* | (+= SUB) | ++ )
SUB := MUL ( (- MUL)* | (-= MUL) | -- )
MUL := DIV ( (* DIV)* | (*= DIV) )
DIV := MOD ( (/ MOD)* | (/= MOD) )
MOD := UNARY_OPERATION ( (% UNARY_OPERATION)* | (%= UNARY_OPERATION) )

UNARY_OPERATION := CAST | MINUS | NOT | METHOD_CALL

CAST := (PAR_EXPR)* ( EXPR | PAR_EXPR )
PAR_EXPR := \( EXPR \)

MINUS := - EXPR
NOT := ! EXPR

METHOD_CALL := ATOM ( .LITERAL METHOD_INVOCATION? )*
METHOD_INVOCATION := \( (EXPR)? (, EXPR)* \)

ATOM := NULL | THIS | ARRAY_LITERAL | TYPE_VALUE
NULL := null
THIS := this
LITERAL := [a-zA-Z_](?:[a-zA-Z0-9._]*[a-zA-Z0-9_])*

TYPE_VALUE := NUMBER_VALUE | LONG_VALUE | DOUBLE_VALUE | FLOAT_VALUE |
              BOOLEAN_VALUE | CHAR_VALUE | STRING_VALUE

NUMBER_VALUE := [0-9]+
LONG_VALUE := ([0-9]+)[Ll]?
DOUBLE_VALUE := [0-9]+(?:.[0-9]+)?(?:E[-0-9]+)?[Dd]?
FLOAT_VALUE := [0-9]+(?:.[0-9]+)?(?:E[-0-9]+)?[Ff]?
BOOLEAN_VALUE := true|false
STRING_VALUE := \"((?:[^\"]|\\\")*)\
CHAR_VALUE := '([^\r\n\t \]|\\[rbnft\\\"\'])'
```
