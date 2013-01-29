# yoda-lang

I'm not a Star Wars fan, but most of the students in the 2013 Compilers class said traditional languages were boring.  They're probably right.

### EXAMPLES

Yoda on the left; JavaScript on the right.

One line complete hello world script

    "Hello, republic" you print                     console.log("Hello, republic");

Variable Declaration

    "strong", force begins                          var force = strong;
    800, years_traing_jedi begins                   var years_training_jedi = 100;

Parallel declaration

    3 and 5, x and y begin                          var _1 = 3;
                                                    var _2 = 5;
                                                    x = _1;
                                                    y = _2;

Constants (compile-time error to update)

    "disbelief", REASON_FOR_FAILURE must be         var REASON_FOR_FAILURE = "disbelief"
                                                    
Assignment is not initialization

    1, x begins                                     var x = 1;
    + x 1, x becomes                                x = x + 1;
    x you print                                     console.log(x);

Parallel Assignment

    3 and 5, x and y become                         x = 3; y = 5;

Arithmetic expression

    / y * (- 4  x) 2.5                               y / (4 - x) * 2.5

Swap

    3, x begins                                     x = 3;
    10, y begins                                    y = 10;
    x and y, y and x become                         _1 = x;
                                                    _2 = y;
                                                    y = _1;
                                                    x = _2;

Null/Null Test

    null                                            null
    null x is?                                      x === null

Booleans

    true                                                true
    false                                               false
    < 3 4 is?                                           4 < 3
    > 5 x is?
    != 1 n is?
    = 5 3 is? & >= 9 x is? | != / y 4 1 is? & !found    3 === 5 & x >= 9 | 1 != y / 4 & !found

Falsehood

    0, 0.0, "", false, null                          0, "", false, null, undefined, NaN

Arrays

    [2, 3, 5, 7, 11], p begins                      var p = [2, 3, 5, 7, 11];
    p[0] you print                                  console.log(p[0]);
    p, q begins                                     var q = p;
    [4, true, "000", q], a begins                   var a = [4, true, "000", q];
    a.length, you print                             console.log(a.length);

Last element (indices start at 0 from the left, -1 from the right)

    p[-1]                                           p[a.length - 1]

Slice, first index inclusive, last index exclusive

    a[3:6]                                          a.slice(3,6)
    a[1:]                                           a.slice(1)
    a[1:-1]                                         a.slice(1, a.length - 1)

String Literals

    "hi"                                            "hi"

String Literal Escapes

    "\""                                            "\""

Are strings mutable?

    "yoda", x begins                                var x = "yoda";
    += x " loves pie"                               x += " loves pie";

String Comparison

    = "hi" "bye" is?                                "hi" === "bye"

String Concatenation

   + "hi" "bye"                                     "hi" + "bye"


Function declaration, one parameter

    {x * x} given x, square gives                   var square = function (x) {
                                                      return x * x;
                                                    };

Function declaration, two parameters

    {x + y} given x and y, plus gives               var plus = function (x, y) {
                                                      return x + y;
                                                    };
Function declaration, zero parameters

    {0} given nothing, zero gives                   var zero = function () {
                                                      return 0;
                                                    };
Function declaration, multiline

    {                                               var bmi = function (pounds, inches) {
      0.45359237, KILOGRAMS_PER_POUND begins          var KILOGRAMS_PER_POUND = 0.45359237;
      0.0254, METERS_PER_INCH begins                  var METERS_PER_INCH = 0.0254;
      pounds * KILOGRAMS_PER_POUND, kilos begins      var kilos = pounds * KILOGRAMS_PER_POUND;
      inches * METERS_PER_INCH, meters begins         var inches = inches * METERS_PER_INCH;
      give back kilos / (meters * meters) you must    return kilos / (meters * meters)
    } given pounds and inches, bmi gives            };

Function call

    (8)square                                       square(8)
    (7, 4)plus                                      plus(7, 4) 
    ((8, (2 - (11)square))plus)square               square(plus(8, 2 - square(11)))
    (155, 71)bmi + zero() you print                 console.log(bmi(155, 71) + zero());
 
Procedure declaration (function that does not return anything)

    {                                               var greet_her = function (name) {
      "Hello, " ^ name you print                      console.log("Hello, " + name);
    } given name, greet_her does                    };

    {                                               var greet = function () {
      "Hello" you print                               console.log("Hello");
    } given nothing, greet does                     };
    
    {                                               var counter = function () {
      "one" you print                                 console.log("one");
      "two" you print                                 console.log("two");
      "three" you print                               console.log("three");
    } given nothing, counter does                   };

    {                                               var echo = function (s, n) {
      {s you print} n times                           for (var _1 = 0; _1 < n; _1++) {
    } given s and n, echo does                          console.log(s);
                                                      }
                                                    }

Procedure call

    "Alice" greet_her you must                      greet_her("Alice");
    
    greet you must                                  greet();
    
    ("NO", 5) echo you must                         echo("NO", 5);
 
Function as parameter

    {((x)f)f} given f and x, twice gives            var twice = function (x) {
                                                      return f(f(x));
                                                    };
                                                    
Anonymous function

    {* 3 x} given x                                 function (x) {return 3 * x;}

    ({* x x} given x, 9) twice                      twice(function (x) {return x * x;}, 9)
                                                    
Conditional Expressions

    {3 you print} if > 2 x is?                          if (x > 2) {console.log(3);}
    
    {give back false you must} if !found                if (!found) {return false;}

    {give back "A" you must} if >= 90 grade is?         if (grade >= 90) {
    else {give back "B" you must} if >= 80 grade is?      return "A";
    else {give back "C" you must} if >= 70 grade is?    } else if (grade >= 80) {
    else {give back "D" you must} if >= 60 grade is?      return "B";
    else {give back "F" you must}                       } else if (grade >= 70) {
                                                          return "C";
                                                        } else if (grade >= 60) {
                                                          return "D";
                                                        } else {
                                                          return "F";
                                                        }

    < 5 y hmm? y++ hmm y--                              5 < y ? y++ : y--;

Switch Statement

    *to be determined*

For loops

    {x you print} as through 1 to 10 x runs         for (var x = 1; i <= 10; i++) {
                                                      console.log(x);
                                                    }
    
    {                                               for (var x = 1; i <= 10; i += 2) {
      * x x you print                                 console.log(x * x);
    } as through 1 to 10 by 2 x runs                }
    
    [spot, sparky, spike], pet_list begins          var pet_list = [spot, sparky, spike]
    {                                               for (var _1 = 0; _1 < pet_list.length; _1++) {
      dog.bark();                                     var dog = pet_list[i];
      dog.run();                                      dog.bark();
      dog.sit();                                      dog.run();
    } as through pet_list dog runs                    dog.sit();
                                                    }
                                                    
While loops

    {                                               while (n != 1) {
      {+ * 3 n 1, n becomes} if = 0 % n 2 is?         if (n % 2 === 0) {
      else {/ n 2, n becomes}                           n = 3 * n + 1;
    } while != 1 n is?                                } else {
                                                        n = n / 2;
                                                      }
                                                    }
                                                    
Function with multiple returns

    {                                               var isPrime = function (n) {
      {give back false you must} if < 2 max is?       if (max < 2) { 
      {                                                 return false;
        {give back false you must} if = 0 % n d is?   }
      } as through 3 to __ n d runs                   for (var d = 3; d <= Math.sqrt(n); d++) {
      give back true you must                           if (n % d === 0) {
    } given n, is_prime gives                             return false;
                                                        }
                                                      }
                                                      return true;
                                                    }
                                                        
List comprehension

    [* x x for x in [1, 2, 3, 4]]
    
    [/ x 2 for x in a]
    
    [[x, y] for x in 0:3 for y in 4:5]
    
    [** x 2 for x in 0:20 if = 0 % x 2 is?]
    
Closure

    {                                               var counter = function (i) {
      0, i begins                                     var i = 0;
      give back {+= i 1} given nothing you must       return function () {i += 1;};
    } given i, counter gives                        };

Objects

    {: x 1 , : y 5}, training begins                {x: 1, y: 5};
    
    {}, training begins                             {}
    
    {}, universe training begins                    var universe = {}
    
    {                                               var circle = {
      : x 0,                                          x: 0,
      : y 0,                                          y: 0,
      : color "black",                                color: "black",
      : radius 1,                                     radius: 1,
      {                                               area: function ()
        * ** radius 2 PI                                return Math.pow(this.radius, 2) * Math.PI;
      }, given nothing area gives                     }
    }, circle training begins                       };
    
    {} as circle,c1 training begins                 var c1 = Object.create(circle);
    
Math

    (3)abs                                           Math.abs(3);
    (2.3)floor                                       Math.floor(2.3);
    (2)cos                                           Math.cos(2);
    the PI                                          Math.PI
    ** x 2                                          Math.pow(x, 2);
    __ x                                            Math.sqrt(x);

Binary, Octal, and Hex Literals

    0x, 0b, 0o                                      0x, parseInt("1001", 2), parseInt("1001", 8)

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
    DEC           →  VARDEC | CONSTDEC | PROCDEC | FUNDEC
    VARDEC        →  EXP (and EXP)* ',' ID (and ID)* (begin | begins)
                  |  from EXP, ID begins
    CONSTDEC      →  EXP ',' ID must be
    PROCDEC       →  BLOCK given PARAMS ',' ID does
    FUNDEC        →  BLOCK given PARAMS ',' ID gives
    PARAMS        →  nothing
                  |  ID (and ID)*
    ASSIGNMENT    →  EXP (and EXP)* ',' ID (and ID)* (become | becomes)
    PRINTSTMT     →  EXP you print
    RETURNSTMT    →  give back EXP you must
    CONDITIONAL   →  BLOCK if EXPR BR (else BLOCK if EXPR BR)* else BLOCK
    TIMESLOOP     →  BLOCK EXP times
    FORLOOP       →  BLOCK as through RANGE ID runs
    WHILELOOP     →  BLOCK while EXP
    PROCCALL      →  (ARGS)* ID you must
    BLOCK         →  '{' (STMT)+ '}'
                  |  '{' (STMT BR)+ '}'
    EXP           →  EXP1 ('|' EXP1)*
    EXP1          →  EXP2 ('&' EXP2)*
    EXP2          →  RELOP EXP3 EXP3 is?
    EXP3          →  (MULOP EXP4)* EXP4
    EXP4          →  (ADDOP EXP5)* EXP5
    EXP5          →  UNARYOP? EXP6
    EXP6          →  EXP7 hmm'?' EXP hmm EXP
    EXP7          →  LIT | ID | ARRAY | OBJECT | ANONFUN | FUNCALL
    LIT           →  true | false | NUMLIT | STRLIT
    ARRAY         →  '[' ']'
                  |  '[' BR? EXP (',' BR? EXP)* BR? ']'
    OBJECT        →  '{'(':' ID EXP)*'}' (as ??object type??)? ',' ID training begins
    ANONFUN       →  BLOCK given ARGS
    FUNCALL       →  '('ARGS')'(ID | ANONFUN)
    RELOP         →  '<' | '<=' | '=' | '!=' | '>=' | '>'
    MULOP         →  '*' | '/'
    ADDOP         →  '+' | '-' | '^'
    UNARYOP       →  '!'
    
The Microsyntax is informally defined as follows:
    
    ARGS      →  ARGS1 (',' ARGS1)*
    ARGS1     →  EXP7
    
    ID        →  STRLIT(STRLIT | NUMLIT)*
    BR        → NEWLINE
    
    NUMLIT    → [0-9]+
    STRLIT    → [a-Z]+([-_]?[a-Z])*
    
    COMMENTS  → '^^' ()* NEWLINE
                 |  '^^*' ()* '*^^'
    
    
    
                
