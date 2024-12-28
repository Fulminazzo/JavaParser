TODO: diamond operator
TODO: lambda
TODO: this
TODO: try, catch and throws
TODO: null

# JAVA_PROGRAM := SINGLE_STMT*

# BLOCK := CODE_BLOCK | SINGLE_STMT
# CODE_BLOCK := \{ SINGLE_STMT* \}
# SINGLE_STMT := STMT | ;

# STMT := return EXPR; | break; | continue; |
#         SWITCH_STMT | FOR_STMT | DO_STMT | WHILE_STMT | IF_STMT
#         ASSIGNMENT;

# SWITCH_STMT := switch ...
# FOR_STMT := for \( ASSIGNMENT?; EXPR?; EXPR? \) BLOCK | ENHANCED_FOR_STMT
# ENHANCED_FOR_STMT := for \( LITERAL(\[\])* LITERAL : EXPR \) BLOCK
# DO_STMT := do BLOCK while PAR_EXPR
# WHILE_STMT := while PAR_EXPR BLOCK
# IF_STMT := if PAR_EXPR BLOCK (else IF_STMT)* (else BLOCK)?
# ASSIGNMENT := LITERAL(\[\])? LITERAL (=EXPR?) | LITERAL = EXPR | EXPR

# EXPR := NEW_OBJECT | INCREMENT | DECREMENT | METHOD_CALL
# NEW_OBJECT := new LITERAL METHOD_INVOCATION |
#               new LITERAL\[\]\{ (EXPR)? (, EXPR)* \} |
#               new LITERAL\[NUMBER_VALUE\]
# INCREMENT := ++ATOM
# DECREMENT := --ATOM | MINUS
# METHOD_CALL := EQUAL ( METHOD_INVOCATION )*
# METHOD_INVOCATION := \( (EXPR)? (, EXPR)* \)

# EQUAL := NOT_EQUAL (== NOT_EQUAL)*
# NOT_EQUAL := LESS_THAN (!= LESS_THAN)*
# LESS_THAN := LESS_THAN_OR_EQUAL (< LESS_THAN_OR_EQUAL)*
# LESS_THAN_OR_EQUAL := GREATER_THAN (<= GREATER_THAN)*
# GREATER_THAN := GREATER_THAN_OR_EQUAL (> GREATER_THAN_OR_EQUAL)*
# GREATER_THAN_OR_EQUAL := AND (>= AND)*
# AND := OR (&& OR)*
# OR := BIT_AND (|| BIT_AND)*

# BIT_AND := BIT_OR ( (& BIT_OR)* | (&= BIT_OR) )
# BIT_OR := BIT_XOR ( (| BIT_XOR)* | (|= BIT_XOR) )
# BIT_XOR := LSHIFT ( (^ LSHIFT)* | (^= LSHIFT) )
# LSHIFT := RSHIFT ( (<< RSHIFT)* | (<<= RSHIFT) )
# RSHIFT := URSHIFT ( (>> URSHIFT)* | (>>= URSHIFT) )
# URSHIFT := ADD ( (>>> ADD)* | (>>>= ADD) )
# ADD := SUB ( (+ SUB)* | (+= SUB) | ++ )
# SUB := MUL ( (- MUL)* | (-= MUL) | -- )
# MUL := DIV ( (* DIV)* | (*= DIV) )
# DIV := MOD ( (/ MOD)* | (/= MOD) )
# MOD := ATOM ( (% ATOM)* | (%= ATOM) )

# ATOM := CAST | MINUS | NOT | LITERAL | TYPE_VALUE

# CAST := (PAR_EXPR)* PAR_EXPR
# PAR_EXPR := \( EXPR \)

# MINUS := - EXPR
# NOT := ! EXPR

# LITERAL := [a-zA-Z_](?:[a-zA-Z0-9._]*[a-zA-Z0-9_])*

# TYPE_VALUE := NUMBER_VALUE | LONG_VALUE |
#               DOUBLE_VALUE | FLOAT_VALUE |
#               BOOLEAN_VALUE | CHAR_VALUE | STRING_VALUE
# NUMBER_VALUE := [0-9]+
# LONG_VALUE := [0-9]+[Ll]?
# DOUBLE_VALUE := [0-9]+(?:.[0-9]+)?(?:E[-0-9]+)?[Dd]?
# FLOAT_VALUE := [0-9]+(?:.[0-9]+)?(?:E[-0-9]+)?[Ff]?
# BOOLEAN_VALUE := true|false
# CHAR_VALUE := '([^\r\n\t \\]|\\[rbnft\\"'])'
# STRING_VALUE := "((?:[^"]|\")*")
