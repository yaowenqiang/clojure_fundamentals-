> https://github.com/ctford/functional-composition


# Operation Forms

+ ( op ..)
  + op can be one of:
    + Special operators macro
    + Expression that yields a function
    + Something invocable

# Literals

+ 42          ; Long
+ 6.022e23    ; Double
+ 42N         ; BigInt
+ 1.0M        ; BigDecimal
+ 22/7        ; Ratio
+ "hello"     ; String
+ \e          ; Character
+ true false  ; Booleans
+ nil         ; null
+ Fred *bob*  ; Symbols
+ :alpha :beta ; Keywords


## Data Structures

+ (4 : alpha 3.0)      ; List
+ [2 "Hello" 99]       ; Vector
+ {:a 1, :b 2}         ; Map
+ {:a 1 :b 2}          ; Map
+ #{alice jim bob}     ; Set

## Metadata

(with-meta [1 2 3 ] {:example true})
;; => [1 2 3]

(meta (with-meta [1 2 3] {:example true}))
;; => {:example true}


## Reader Macros

| Reader Macro    | Expansion                   |
|-----------------|-----------------------------|
| 'foo            | (quote foo)                 |
| #'foo           | (var foo)                   |
| @foo            | (deref foo)                 |
| #(+ % 5)        | fn([x] (+ x 5))             |
| ^{:key val} foo | (with-meta foo {:key val})  |
| ':key foo       | (with-meta foo {:key true}) | 


> user => (doc when) ;; get help

> (find-doc "sequence")
> (apropos "sequence")
> (apropos "map")
> (source "take")
> (dir "clojure.repo")
> 


## Leiningen Directory Structure


| Path        | Purpose                     |
|-------------|-----------------------------|
| project.clj | Project/build config        |
| classes     | Compiled bytecode           |
| lib/        | Dependent JARs              |
| public/     | HTML/CSS/JS files for web   |
| src/        | Clojure source              |
| test/       | Unit tests                  |


## Maven Directory Structure


| Path              | Purpose                     |
|-------------------|-----------------------------|
| pom.xml           | Project/build config        |
| target/classes    | Compiled bytecode           |
| ~/.m2/repository/ | Dependent JARs              |
| src/main/clojure/ | Clojure source              |
| src/test/clojure/ | Unit tests                  |


## Functions

> (fn [message] (print message))


### Invoking Functions 

+ fn makes anonymous functions
+ invoke a function with fn itself in function position

> (fn [message] (print message))
### Invoking Functions 

+ fn makes anonymous functions
+ invoke a function with fn itself in function position


(    (fn [message] (print message))
    "hello world!"
)


### Naming Functions

+ fn makes anonymous functions
+ Store function in a named Var for later use
+ invoke as list with name in function position

(def messenger (fn [msg] (print msg)))
(defn messenger [msg] (print msg))
(messenger "Hello world")

## Let

+ let binds symbols to immutablevalues
  + Values may be literals or expressions
+ Bound symbols are available in lexical scope


(defn messenger [msg]
 (let [
    a 7
    b 5
    c (capitalize msg)]
    (println a b c)
 ); end of 'let' scop
) ; end of function


(let [x 10 y 20 ] + x y)


### Multi-arity functions

+ Can overload function by arity
  + Arity number of arguments
+ Each arity is a list([args*] body*)
+ One arity can invoke another



(defn messenger
;; no args, call self with default msg
([] messenger "Hello World")
;; one arg, print it
([msg] (print msg))
)

(messenger)
;; Hello world!
(messenger "Hello class!")
;; Hello class!

### Variadic functions

+ Variadic function of indefinite arity
  + Only one allowed when overloading on arity
+ &symbol in params
  + Next param collects all remaining args
  + Collected args represented as sequence


(defn messenger [greeting & who]
    (print greeting who))

(messenger "Hello" "World" "class")
;; Hello (world class)

### Apply

+ Invokes functions on arguments
+ Final argument is a sequence
+ "Unpacks" remaining arguments from sequence

(let [a 1
      b 2
      more '(3 4)]
 (apply f a b more))

 ;; this invokes (f 1 2 3 4)



(defn messenger [greeting & who]
    ;; apply gets args out of sequence
    (apply print greeting who))

(messenger "Hello" "World" "Class")

(let [numbers '(1 2 3)]
    (apply + numbers)
)


### Closures

+ fn 'closes' over surrounding lexical scope
  + Creates a closure
+ closed over references persist beyound lexical scope



(defn messenger-builder [greeting]
    (fn [who] (print greeting who))) ;; closes over greeting

;; greeting provided here, then goes out of scope

(def hello-er (messenger-builder "Hello world"))

;; greeting still available because hello-er is closure
(hello-er "world!")
;; Hello world!;


### Invoking Java Code

| Task          | java              | Clojure         |
| ----          | ----              | ----            |
| instantiation | new Widget("Foo") | (Widget. "Foo") |
| Instance method | rnd.nextInt()| (.nextInt rndFoo") |
| Instance field | object.field | (.-field object") |
| Static method | Math.sqrt(25) | (Math/sqrt 25) |
| Static field | Math.PI | (Math/PI) |


### Chaining Access

| Language          | Syntax        |
| ----          | ----              |
| Java | person.getAddress().getZipCode() |
| Clojure | (.getZipCode (.getAddress person))|
| Clojure Sugar |(.. person getAddress getZipCode) |
| Static method | Math.sqrt(25) |
| Static field | Math.PI |


### Java Methods vs. Functions

+ Java methods are not Clojure functions
+ Can't store them, pass them as argument
+ Can wrap them in functions when necessary


;; make a function to invoke .length on arg

(fn [obj] (.length obj))



### Terse fn reader macro

+ Terse form #() for short fns defined inline
  + Single argument %
  + Multiple args %1, %2, %3
  + Variadic: %& for remaining args


;; a function to invoke .length on arg

#(.length %)
