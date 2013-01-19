# yoda-lang

I'm not a Star Wars fan, but most of the students in the 2013 Compilers class said traditional languages were boring.

### EXAMPLES

Yoda on the left; JavaScript on the right.

One line complete hello world script

    "Hello, republic" you print                     console.log("Hello, republic");

Declaration

    "strong", force is                              var force = strong;
    100, score is                                   var score = 100;

Parallel declaration

    3 and 5, x and y are                            _1 = 3;
                                                    _2 = 5;
                                                    x = _1;
                                                    y = _2;

Constants (compile-time error to update)

    10, NUMBER_OF_DIGITS must be                    var NUMBER_OF_DIGITS = 10;
    1 and 2, UNO and DOS must be                    var UNO = 1;
                                                    var DOS = 2;
                                                    
Assignment is not distinguishable from initialization.  All that is, was always.

    1, x is                                         var x = 1;
    x + 1, x is                                     x = x + 1;
    x you print                                     console.log(x);

Arithmetic expression

    y / (4 - x) * 2.5                               y / (4 - x) * 2.5

Swap

    3, x is                                         x = 3;
    10, y is                                        y = 10;
    x and y, y and x are                            _1 = x;
                                                    _2 = y;
                                                    y = _1;
                                                    x = _2;

Booleans

    true                                            true
    false                                           false
    4 < 3                                           4 < 3
    3 = 5 & x >= 9 | 1 != y / 4 & !found            3 === 5 & x >= 9 | 1 != y / 4 & !found

Arrays

    [2, 3, 5, 7, 11], p is                          var p = [2, 3, 5, 7, 11];
    p[0] you print                                  console.log(p[0]);
    p, q is                                         var q = p;
    [4, true, "000", q], a is                       var a = [4, true, "000", q];
    length a, you print                             console.log(a.length);

Last element (indices start at 0 from the left, -1 from the right)

    p[-1]                                           p[a.length - 1]

Slice, first index inclusive, last index exclusive

    a[3:6]                                          a.slice(3,6)

Unshift, shift, push, pop
    
    3 ^ a                                           [3].concat(a)
    3 ^= a                                          a.unshift(3)
    a[1]                                            a.slice(1)
    a ^ 3                                           a.concat([3])
    a ^= 3                                          a.push([3])
    a[1:-1]                                         a.slice(1, a.length - 1)

Function declaration, one parameter

    {x * x} given x, square gives                   var square = function (x) {
                                                      return x * x;
                                                    };

Function declaration, two parameters

    {x + y} given x and y, plus gives               var plus = function (x, y) {
                                                      return x + x;
                                                    };
Function declaration, zero parameters

    {0} given nothing zero gives                    var zero = function () {
                                                      return 0;
                                                    };
Function declaration, multiline

    {                                               var bmi = function (pounds, inches) {
      0.45359237, KILOGRAMS_PER_POUND is              var KILOGRAMS_PER_POUND = 0.45359237;
      0.0254, METERS_PER_INCH is                      var METERS_PER_INCH = 0.0254;
      pounds * KILOGRAMS_PER_POUND, kilos is          var kilos = pounds * KILOGRAMS_PER_POUND;
      inches * METERS_PER_INCH, meters is             var inches = inches * METERS_PER_INCH;
      kilos / (meters * meters) you return            return kilos / (meters * meters)
    } given pounds and inches, bmi gives            };

Function call

    square(8)                                       square(8)
    plus(7, 4)                                      plus(7, 4) 
    square(plus(8, 2 - square(11)))                 square(plus(8, 2 - square(11)))
    bmi(155, 71) + zero() you print                 console.log(bmi(155, 71) + zero());
 
Procedure declaration (function that does not return anything)

    {                                               var greet_her = function (name) {
      "Hello, " ^ name you print                      console.log("Hello, " + name);
    } given name greet_her does                     };

    {                                               var greet = function () {
      "Hello" you print                               console.log("Hello");
    } given nothing greet does                      };
    
Procedure call

    "Alice" you greet_her                           greet_her("Alice");
    
    you greet                                       greet();
 
Multiline subroutine declaration

    {                                               var counter = function () {
      "one" you print                                 console.log("one");
      "two" you print                                 console.log("two");
      "three" you print                               console.log("three");
    } given nothing counter does                    };

Function as parameter

    {f(f(x))} given f and x, twice gives            var twice = function (x) {
                                                      return f(f(x));
                                                    };
                                                    
Anonymous function

    twice({x * x} given x, 9)                       twice(function (x) {return x * x;}, 9)
                                                    
If statements

    {3 you print} if x > 2                          if (x > 2) {console.log(3);}
    
    {false you return} if !found                    if (!found) {return false;}

    {"A" you return} if grade >= 90                 if (grade >= 90) {
    else {"B" you return} if grade >= 80              return "A";
    else {"C" you return} if grade >= 70            } else if (grade >= 80) {
    else {"D" you return} if grade >= 60              return "B";
    else {"F you return}                            } else if (grade >= 70) {
                                                      return "C";
                                                    } else if (grade >= 60) {
                                                      return "D";
                                                    } else {
                                                      return "F";
                                                    }
    
For loops

    {x you print} as through 1 to 10 x runs         for (var x = 1; i <= 10; i++) {
                                                      console.log(x);
                                                    }
    
    {                                               for (var x = 1; i <= 10; i += 2) {
      x * x you print                                 console.log(x * x);
    } as through 1 to 10 by 2 x runs                }
    
    [spot, sparky, spike], pet_list is              var pet_list = [spot, sparky, spike]
    {                                               for (var _1 = 0; _1 < pet_list.length; _1++) {
      dog.bark();                                     var dog = pet_list[i];
      dog.run();                                      dog.bark();
      dog.sit();                                      dog.run();
    } as through pet_list dog runs                    dog.sit();
                                                    }
                                                    
While loops

    {                                               while (n != 1) {
      {3 * n + 1, n is} if n % 2 = 0                  if (n % 2 === 0) {
      else {n / 2, n is}                                n = 3 * n + 1;
    } while n != 1                                    } else {
                                                        n = n / 2;
                                                      }
                                                    }
                                                    
Function with multiple returns

    {                                               var isPrime = function (n) {
      {false you return} if max < 2                   if (max < 2) { 
      {                                                 return false;
        {false you return} if n % d = 0               }
      } as through 3 to sqrt(n) d runs                for (var d = 3; d <= Math.sqrt(n); d++) {
      true you return                                   if (n % d === 0) {
    } given n is_prime gives                              return false;
                                                        }
                                                      }
                                                      return true;
                                                    }
                                                        
List comprehension

    [x * x for x in [1, 2, 3, 4]]
    
    [x / 2 for x in a]
    
    [[x, y] for x in 3.range for y in 5.range]
    
    [x ** 2 for x in 20.range if x % 2 == 0]
    
Closure

    {                                               var counter = function (i) {
      0, i is                                         var i = 0;
      {i += 1} given nothing you return               return function () {i += 1;};
    } given i counter gives                         };

Objects

Math

Classes

### SYNTAX

The official syntax is coming soon.  For now, here is a brief EBNF for the macrosyntax.

    SCRIPT        &rarr;  (STMT BR)+
    STMT          &rarr;  DEC 
                  |  ASSIGNMENT
                  |  PRINTSTMT
                  |  RETURNSTMT
                  |  CONDITIONAL
                  |  LOOP
                  |  PROCCALL
    DEC           &rarr;  EXP ',' ID is
                  |  BLOCK given PARAMS ID gives
    ASSIGNMENT    &rarr;  EXP ',' ID is
    PRINTSTMT     &rarr;  EXP you print
    RETURNSTMT    &rarr;  EXP you return
    CONDITIONAL   &rarr;  BLOCK if EXPR BR (else BLOCK if EXPR BR)* else BLOCK
    LOOP          &rarr;  FORLOOP | WHILELOOP
    FORLOOP       &rarr;  BLOCK as through RANGE ID runs
    WHILELOOP     &rarr;  BLOCK while EXP
    PROCCALL      &rarr;  ARGS you ID
    BLOCK         &rarr;  '{' STMT '}'
                  |  '{' (STMT BR)+ '}'
    EXP           &rarr; EXP1 ('|' EXP1)*
    EXP1          &rarr; EXP2 ('&' EXP2)*
    EXP2          &rarr; EXP3 (RELOP EXP3)?
    EXP3          &rarr; EXP4 (MULOP EXP4)*
    EXP4          &rarr; EXP5 (ADDOP EXP5)*
    EXP5          &rarr; UNARYOP? EXP6
    EXP6          &rarr; LIT | ID | ARRAY | OBJECT | ANONSUBROG | FUNCALL
    
    
    
                
