# yoda-lang

A long time ago in a galaxy far, far away...

# Mascot
![Yoda mascot](http://my.cs.lmu.edu/~awon/yodaMascot-border.jpg "Yoda Mascot")

# Theme Song
[Yoda theme-song](http://www.youtube.com/watch?v=WWTI93xI41w)

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

    3 and 5, x and y begin                                  var x = 3;
                                                            var y = 5;

Constants (compile-time error to update)

    "disbelief", REASON_FOR_FAILURE must be                 /* No constants in JavaScript */

Assignment is not initialization

    1, x begins                                             var x = 1;
    (+ x 1), x becomes                                      x = x + 1;
    x you print                                             console.log(x);

Parallel Assignment

    3 and 5 and 7 and 11, x and y and z and a become        var x = 3, y = 5, z = 7, a = 11;

Arithmetic Expression

    (* (/ y (- 4  x ) ) 2.5 )                               y / (4 - x) * 2.5

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
    (> 5 x is?)
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
    (on a length), you print                                console.log(a.length);

Last element (indices start at 0 from the left, -1 from the right)

    p[-1]                                                   p[a.length - 1]

String Literals

    "hi"                                                    "hi"

String Literal Escapes

    "\""                                                    "\""

Are strings mutable?

    "yoda", x begins                                        var x = "yoda";
    (+ x " loves pie" ), x becomes                          x += " loves pie";

String Comparison

    (= "hi" "bye" is?)                                      "hi" === "bye"

String Concatenation

    (+ "hi" "bye" )                                         "hi" + "bye"

Function declaration, one parameter

    {give back (* x x ) you must} given x, square gives     var square = function (x) {
                                                              return x * x;
                                                            };

Function declaration, two parameters

  {give back (+ x y ) you must } given x and y, plus gives  var plus = function (x, y) {
                                                              return x + y;
                                                            };
Function declaration, zero parameters

    {give back 0 you must } given nothing, zero gives       var zero = function () {
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

    (on 8 square)                                           square(8)
    (on 7 and 4 plus)                                       plus(7, 4)
    (on (on 8 and (- 2 (on 11 square)) plus) square)        square(plus(8, 2 - square(11)))
    (+ (on 155 and 71 bmi) (on nothing zero)) you print     console.log(bmi(155, 71) + zero());

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

    ((< 5 y is?) hmm? ++y hmm --y)                          5 < y ? y++ : y--;

For loops

    {x you print} as through 1 to 10 x runs                 for (var x = 1; i <= 10; i++) {
                                                              console.log(x);
                                                            }

    {                                                       for (var x = 1; i <= 10; i += 2) {
      (* x x ) you print                                       console.log(x * x);
    } as through 1 to 10 by 2 x runs                        }

    [spot, sparky, spike], pet_list begins                  var pet_list = [spot, sparky, spike]
    {                                                       for (var _1 = 0; _1 < pet_list.length; _1++) {
      (on dog bark)                                             var dog = pet_list[i];
      (on dog run)                                              dog.bark();
      (on dog sit)                                              dog.run();
    } as through pet_list dog runs                            dog.sit();
                                                            }

While loops

    {                                                       while (n != 1) {
      {(+ (* 3 n ) 1 ),n becomes} if(= 0 (% n 2) is?)         if (n % 2 === 0) {
      else {(/ n 2 ), n becomes                                n = 3 * n + 1;
    } while (!= 1 n is?)                                      } else {
                                                                n = n / 2;
                                                              }
                                                            }

Function with multiple returns

    {                                                       var isPrime = function (n) {
      {give back false you must} if (< 2 max is?)             if (max < 2) {
      {                                                         return false;
        {give back false you must} if (= 0 (% n d) is?)       }
      } as through 3 to (on n sqrt) d runs                        for (var d = 3; d <= Math.sqrt(n); d++) {
      give back true you must                                   if (n % d === 0) {
    } given n, is_prime gives                                     return false;
                                                                }
                                                              }
                                                              return true;
                                                            }

Closure

    {                                                       var counter = function (i) {
      0, i begins                                             var i = 0;
      give back {(+= i 1 )} given nothing you must            return function () {i += 1;};
    } given i, counter gives                                };



Binary, Octal, and Hex Literals

    0x, 0b, 0o





May the Force be with you.
