TODO: diamond operator
TODO: lambda
TODO:

# JAVA_PROGRAM := SINGLE_STMT*

# BLOCK := CODE_BLOCK | SINGLE_STMT
# CODE_BLOCK := \{ SINGLE_STMT* \}
# SINGLE_STMT := STMT | ;

# STMT := return EXPR; | throw EXPR; break; | continue; |
#         TRY_STMT | SWITCH_STMT | FOR_STMT | DO_STMT | WHILE_STMT | IF_STMT
#         ASSIGNMENT;

# TRY_STMT := try ( \( ASSIGNMENT_BLOCK \) )? CODE_BLOCK CATCH+ ( finally CODE_BLOCK )?
# ASSIGNMENT_BLOCK := (ARRAY_LITERAL LITERAL (=EXPR?);)+
# CATCH := catch \( (LITERAL \| )* LITERAL LITERAL \) CODE_BLOCK
# SWITCH_STMT := switch ...
# FOR_STMT := for \( ASSIGNMENT?; EXPR?; EXPR? \) BLOCK | ENHANCED_FOR_STMT
# ENHANCED_FOR_STMT := for \( ARRAY_LITERAL LITERAL : EXPR \) BLOCK
# DO_STMT := do BLOCK while PAR_EXPR
# WHILE_STMT := while PAR_EXPR BLOCK
# IF_STMT := if PAR_EXPR BLOCK (else IF_STMT)* (else BLOCK)?
# ASSIGNMENT := ARRAY_LITERAL LITERAL (=EXPR?) | LITERAL = EXPR | EXPR
# SWITCH_STMT := switch \( EXPR \) \{ (CASE_BLOCK)* (DEFAULT_BLOCK)? \}
# CASE_BLOCK := case EXPR: ( CODE_BLOCK | SINGLE_STMT* )
# DEFAULT_BLOCK := default: ( CODE_BLOCK | SINGLE_STMT* )

# EXPR := NEW_OBJECT | INCREMENT | DECREMENT | METHOD_CALL | ARRAY_LITERAL
# NEW_OBJECT := new LITERAL METHOD_INVOCATION |
#               new ARRAY_LITERAL{ (EXPR)? (, EXPR)* \} |
#               new LITERAL(\[NUMBER_VALUE\])+
# ARRAY_LITERAL := LITERAL(\[\])*
# INCREMENT := ++ATOM
# DECREMENT := --ATOM | MINUS
# METHOD_CALL := EQUAL ( .LITERAL METHOD_INVOCATION? )*
# METHOD_INVOCATION := \( (EXPR)? (, EXPR)* \)

# AND := OR (&& OR)*
# OR := EQUAL (|| EQUAL)*
# EQUAL := NOT_EQUAL (== NOT_EQUAL)*
# NOT_EQUAL := LESS_THAN (!= LESS_THAN)*
# LESS_THAN := LESS_THAN_EQUAL (< LESS_THAN_EQUAL)*
# LESS_THAN_EQUAL := GREATER_THAN (<= GREATER_THAN)*
# GREATER_THAN := GREATER_THAN_EQUAL (> GREATER_THAN_EQUAL)*
# GREATER_THAN_EQUAL := BIT_AND (>= BIT_AND)*

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

# ATOM := CAST | MINUS | NOT | NULL | THIS | LITERAL | TYPE_VALUE

# CAST := (PAR_EXPR)* (EXPR | PAR_EXPR)
# PAR_EXPR := \( EXPR \)

# MINUS := - EXPR
# NOT := ! EXPR

# NULL := null
# THIS := this
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
