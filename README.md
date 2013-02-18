# yoda-lang

A long time ago in a galaxy far, far away...

### DESCRIPTION
The general feel and use of the Yoda language is meant to mimic the speech patterns of the Jedi Master Yoda from Star Wars.
Although Yoda's speech patterns can be inconsistent, his sentences typically follow an object-subject-verb word order.
It is this pattern that Yoda-Lang replicates in terms of language syntax. When read aloud, all statements and expressions
in Yoda-Lang should sound like Yoda himself is talking.

### DESIGN CHOICES
Scope
    
    Yoda-Lang uses a specific style of scoping drawn from the wisdom of Master Yoda. When asked what is in a mysterious cave,
    Yoda responds "Only what you take with you." In this way, variables must be brought in to a lower scope as a parameter in 
    order to be used.

Booleans and Arithmetic
    
    Yoda-Lang uses a unique syntax pattern for boolean expressions and arithmetic. Like the rest of the language, the point is
    to make it sound like Yoda is talking. Thus, our Boolean expressions follow the pattern (operator operand operand is?).
    I.e. "greater than 3, y is?" Similarly, arithmetic follows the pattern (operator operand operand are). I.e. 
    "added 3 and y are."
    
Exceptions

    Yoda-Lang does not use exceptions because "do or do not, there is no try."
    
Typing
    
    Yoda-Lang uses strong, dynamic typing. While those who follow the light side of the force and those who follow
    the dark side cannot cooperate, it is easy for the light to fall to darkness.
    
### EXAMPLES

Yoda on the left; JavaScript on the right.

One line complete hello world script

    "Hello, republic" you print                             console.log("Hello, republic");

Variable Declaration

    "strong", force begins                                  var force = strong;
    800, years_training_jedi begins                         var years_training_jedi = 100;

Parallel Declaration

    3 and 5, x and y begin                                  var _1 = 3;
                                                            var _2 = 5;
                                                            x = _1;
                                                            y = _2;

Constants (compile-time error to update)

    "disbelief", REASON_FOR_FAILURE must be                 var REASON_FOR_FAILURE = "disbelief"

Assignment is not initialization

    1, x begins                                             var x = 1;
    (+ x 1 are), x becomes                                  x = x + 1;
    x you print                                             console.log(x);

Parallel Assignment

    3 and 5, x and y become                                 x = 3; y = 5;

Arithmetic Expression

    (* (/ y (- 4  x are) are) 2.5 are)                      y / (4 - x) * 2.5

Swap

    3, x begins                                             x = 3;
    10, y begins                                            y = 10;
    x and y, y and x become                                 _1 = x;
                                                            _2 = y;
                                                            y = _1;
                                                            x = _2;

Null/Null Test

    null                                                    null
    (= null x is?)                                          x === null

Booleans

    true                                                    true
    false                                                   false
    (< 3 4 is?)                                             4 < 3
    > 5 x is?)
    (!= 1 n is?)
    (((= 5 3 is?) & (>= 9 x is?)) |                         3 === 5 & x >= 9 | 1 != y / 4 & !found
     ((!= (/ y 4 are) 1 is?) & (!found)))

Falsehood

    0, 0.0, "", false, null                                 0, "", false, null, undefined, NaN

Arrays

    [2, 3, 5, 7, 11], p begins                              var p = [2, 3, 5, 7, 11];
    p[0] you print                                          console.log(p[0]);
    p, q begins                                             var q = p;
    [4, true, "000", q], a begins                           var a = [4, true, "000", q];
    a.()length, you print                                   console.log(a.length);

Last element (indices start at 0 from the left, -1 from the right)

    p[-1]                                                   p[a.length - 1]

Slice, first index inclusive, last index exclusive

    a[3:6]                                                  a.slice(3,6)
    a[1:]                                                   a.slice(1)
    a[1:-1]                                                 a.slice(1, a.length - 1)

String Literals

    "hi"                                                    "hi"

String Literal Escapes

    "\""                                                    "\""

Are strings mutable?

    "yoda", x begins                                        var x = "yoda";
    (+ x " loves pie" are), x becomes                       x += " loves pie";

String Comparison

    (= "hi" "bye" is?)                                      "hi" === "bye"

String Concatenation

    (+ "hi" "bye" are)                                      "hi" + "bye"

Function declaration, one parameter

    {(* x x are)} given x, square gives                     var square = function (x) {
                                                              return x * x;
                                                            };

Function declaration, two parameters

    {(+ x y are)} given x and y, plus gives                 var plus = function (x, y) {
                                                              return x + y;
                                                            };
Function declaration, zero parameters

    {0} given nothing, zero gives                           var zero = function () {
                                                              return 0;
                                                            };
Function declaration, multiline

    {                                                       var bmi = function (pounds, inches) {
      0.45359237, KILOGRAMS_PER_POUND begins                  var KILOGRAMS_PER_POUND = 0.45359237;
      0.0254, METERS_PER_INCH begins                          var METERS_PER_INCH = 0.0254;
      (* pounds KILOGRAMS_PER_POUND are), kilos begins        var kilos = pounds * KILOGRAMS_PER_POUND;
      (* inches METERS_PER_INCH are), meters begins           var inches = inches * METERS_PER_INCH;
      give back (/ kilos (* meters meters are)) you must      return kilos / (meters * meters)
    } given pounds and inches, bmi gives                    };

Function call

    (8)square                                               square(8)
    (7, 4)plus                                              plus(7, 4)
    ((8, (- 2 (11)square are))plus)square                   square(plus(8, 2 - square(11)))
    (+ (155, 71)bmi ()zero are) you print                   console.log(bmi(155, 71) + zero());

Procedure declaration (function that does not return anything)

    {                                                       var greet_her = function (name) {
      (+ "Hello, " name are) you print                              console.log("Hello, " + name);
    } given name, greet_her does                            };

    {                                                       var greet = function () {
      "Hello" you print                                       console.log("Hello");
    } given nothing, greet does                             };

    {                                                       var counter = function () {
      "one" you print                                         console.log("one");
      "two" you print                                         console.log("two");
      "three" you print                                       console.log("three");
    } given nothing, counter does                           };

    {                                                       var echo = function (s, n) {
      {s you print} as through 0 to n _1 runs                 for (var _1 = 0; _1 < n; _1++) {
    } given s and n, echo does                                  console.log(s);
                                                              }
                                                            }

Procedure call

    ("Alice")greet_her                                      greet_her("Alice");

    ()greet                                                 greet();

    ("NO", 5)echo                                           echo("NO", 5);

Function as parameter

    {((x)f)f} given f and x, twice gives                    var twice = function (x) {
                                                              return f(f(x));
                                                            };

Anonymous function

    {(* 3 x are)} given x                                   function (x) {return 3 * x;}

    ({(* x x are)} given x, 9)twice                         twice(function (x) {return x * x;}, 9)

Conditional Expressions

    {3 you print} if (> 2 x is?)                            if (x > 2) {console.log(3);}

    {give back false you must} if (!found)                  if (!found) {return false;}

    {give back "A" you must} if (>= 90 grade is?)           if (grade >= 90) {
    else {give back "B" you must} if (>= 80 grade is?)        return "A";
    else {give back "C" you must} if (>= 70 grade is?)      } else if (grade >= 80) {
    else {give back "D" you must} if (>= 60 grade is?)        return "B";
    else {give back "F" you must}                           } else if (grade >= 70) {
                                                              return "C";
                                                            } else if (grade >= 60) {
                                                              return "D";
                                                            } else {
                                                              return "F";
                                                            }

    ((< 5 y is?) hmm? y++ hmm y--)                          5 < y ? y++ : y--;

For loops

    {x you print} as through 1 to 10 x runs                 for (var x = 1; i <= 10; i++) {
                                                              console.log(x);
                                                            }

    {                                                       for (var x = 1; i <= 10; i += 2) {
      (* x x are) you print                                       console.log(x * x);
    } as through 1 to 10 by 2 x runs                        }

    [spot, sparky, spike], pet_list begins                  var pet_list = [spot, sparky, spike]
    {                                                       for (var _1 = 0; _1 < pet_list.length; _1++) {
      dog.()bark;                                             var dog = pet_list[i];
      dog.()run;                                              dog.bark();
      dog.()sit;                                              dog.run();
    } as through pet_list dog runs                            dog.sit();
                                                            }

While loops

    {                                                       while (n != 1) {
      {(+ (* 3 n are) 1 are),n becomes} if(= 0 (% n 2) is?)   if (n % 2 === 0) {
      else {(/ n 2 are), n becomes                                n = 3 * n + 1;
    } while (!= 1 n is?)                                      } else {
                                                                n = n / 2;
                                                              }
                                                            }

Function with multiple returns

    {                                                       var isPrime = function (n) {
      {give back false you must} if (< 2 max is?)             if (max < 2) {
      {                                                         return false;
        {give back false you must} if (= 0 (% n d) is?)       }
      } as through 3 to (n)sqrt d runs                        for (var d = 3; d <= Math.sqrt(n); d++) {
      give back true you must                                   if (n % d === 0) {
    } given n, is_prime gives                                     return false;
                                                                }
                                                              }
                                                              return true;
                                                            }

Closure

    {                                                       var counter = function (i) {
      0, i begins                                             var i = 0;
      give back {(+= i 1 are)} given nothing you must         return function () {i += 1;};
    } given i, counter gives                                };

Objects

    {: x 1 , : y 5}, training begins                        {x: 1, y: 5};

    {}, training begins                                     {}

    {}, universe training begins                            var universe = {}

    {                                                       var circle = {
      : x 0,                                                  x: 0,
      : y 0,                                                  y: 0,
      : color "black",                                        color: "black",
      : radius 1,                                             radius: 1,
      {                                                       area: function ()
        * (radius, 2)power ()PI are                             return Math.pow(this.radius, 2) * Math.PI;
      }, given nothing area gives                             }
    }, circle training begins                               };

    {} to be circle, c1 training begins                     var c1 = Object.create(circle);

Math

    (3)abs                                                  Math.abs(3);
    (2.3)floor                                              Math.floor(2.3);
    (2)cos                                                  Math.cos(2);
    ()PI                                                    Math.PI
    (x, 2)power                                             Math.pow(x, 2);
    (x)sqrt                                                 Math.sqrt(x);

Binary, Octal, and Hex Literals

    0x, 0b, 0o                                              0x, parseInt("1001", 2), parseInt("1001", 8)

### SYNTAX

Here is a brief EBNF for the macrosyntax.  Here syntax categories and compound tokens are shown in all caps, and reserved word tokens are shown in lowercase.  Symbols are always quoted.  The meta symbols are the usual ones: `|` for alternatives, `*` for zero or more, `+` for one or more, `?` for zero or one, and parentheses for grouping.

The tokens `NUMLIT`, `STRLIT`, `ID`, and `BR` are defined in the microsyntax below.

    SCRIPT        →  (STMT BR)+
    STMT          →  DEC
                  |  ASSIGNMENT
                  |  PRINTSTMT
                  |  RETURNSTMT
                  |  CONDITIONAL
                  |  TIMESLOOP
                  |  FORLOOP
                  |  WHILELOOP
                  |  PROCCALL
                  |  EXP
    DEC           →  VARDEC | CONSTDEC | PROCDEC | FUNDEC
    VARDEC        →  EXP (and EXP)* ',' ID (and ID)* (begin | begins)
                  |  from EXP',' ID begins
    CONSTDEC      →  EXP ',' ID must be
    PROCDEC       →  BLOCK given PARAMS ',' ID does
    FUNDEC        →  BLOCK given PARAMS ',' ID gives
    PARAMS        →  nothing
                  |  ID (and ID)*
    ASSIGNMENT    →  EXP (and EXP)* ',' ID (and ID)* (become | becomes)
    PRINTSTMT     →  EXP you print
    RETURNSTMT    →  give back EXP you must
    CONDITIONAL   →  BLOCK if EXPR (BR (else BLOCK if EXPR BR)* else BLOCK)?
    TIMESLOOP     →  BLOCK EXP times
    FORLOOP       →  BLOCK as through RANGE ID runs
    WHILELOOP     →  BLOCK while EXP
    PROCCALL      →  FUNCALL
    BLOCK         →  '{' (STMT)+ '}'
                  |  '{' (STMT BR)+ '}'
    EXP           →  '(' EXP '|' EXP ')'
                  | EXP1
    EXP1          →  '(' EXP1 '&' EXP1 ')'
                  |  EXP2
    EXP2          →  '(' RELOP EXP2 EXP2 is'?' ')'
                  |  '(' ARITHOP EXP2 (EXP2)+ ')'
                  |  EXP3
    EXP3          →  '(' UNARYOP? EXP3 ')'
                  |  EXP4
    EXP4          →  '(' EXP4 hmm'?' EXP4 hmm EXP4 ')'
                  |  EXP5
    EXP5          →  LIT | ID | ARRAY | ARRAYLOOKUP | OBJECT | ANONFUN | FUNCALL
    LIT           →  true | FALSE | NUMLIT | STRLIT
    ARRAY         →  '[' ']'
                  |  '[' BR? EXP (',' BR? EXP)* BR? ']'
    ARRAYLOOKUP   →  ID'['NUMLIT (':' NUMLIT)?]'
    OBJECT        →  '{'(':' ID EXP)*'}' (to be ID)? ',' ID? training begins
    ANONFUN       →  BLOCK given ARGS
    FUNCALL       →  (ID'.')?'('ARGS')'(ID | ANONFUN)
    ARGS          →  ARGS1 (',' ARGS1)*
    ARGS1         →  EXP6
    RELOP         →  '<' | '<=' | '=' | '!=' | '>=' | '>'
    ARITHOP       →  '*' | '/' | '+' | '-' | '^'
    UNARYOP       →  '!'

The Microsyntax is informally defined as follows:

    RANGE         →  ((NUMLIT | ID | FUNCALL) (to | through))? (NUMLIT | ID | FUNCALL) (by EXP3)?

    ID            →  [a-Z]+([-_a-Z0-9])*
    BR            →  NEWLINE

    NUMLIT        →  (0x | 0b | 0o)? [0-9]+('.'[0-9]*)?
    STRLIT        →  '"' (^(")* ('\''"')?)* '"'

    COMMENTS      →  '<(-_-)>' .* NEWLINE
                  |  '<(-.-)>' .* '<(-.-)>'
                  
                  
                  
                  
                  
May the Force be with you.
