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
(defn messenger fn [msg] (print msg))
(messenger "Hello world")
