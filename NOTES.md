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

> #(.length %)

## Names and Namespaces

### Namespaces in the REPL

+ in-ns switches to namespace
  + Creates namespace if it doesn't exist
+ Argument is a symbol, must be quoted
+ REPL always starts in namespace "user"

user => (in-ns 'foo.bar.baz)
;;  => null'
foo.bar.baz=>

### Namespace Operations

+ Load:find source on classpath & eval it
+ Alias: make shorter name for namespace-qualified symbols
+ Refer: copy symbol bindings from another namespace into current namespace
+ Import: make java class names available in current namespace


#### require

+ Loads the namespace if not already loaded
  + Argument is a symbol, must be quoted
+ Have to refer to things with-fully-qualified names

(require 'clojure.set)
;;=> nil
(clojure.set/union #{1 2} #{2 3 4})
;=> #{ 1 2 3 4 }


#### require : as

+ Loads the namespace if not already loaded
  + Argument is a vector, must be quoted
+ Aliases the namespace to alternate name

(require '[clojure.set :as set])
;;=> null
;; 'set' is an alias for 'clojure.set'
(set/union #{1 2} #{3 4})
;=> #{1 2 3 4}


#### Use

+ Loads the namespaces if not already loaded
  + Argument is a symbol, must be quoted
+ Refer all symbols into current namespace
+ Warns when symbols clash
+ Not recommended except for REPL exploration



(use 'clojure.string)
;; warning

(reverse "Hello")


#### use :only

+ Loads the namespace if not already loaded
  + Argument is a vector, must be quoted
+ Refers only specified symbols into current namespace

(use '[clojure.string : only (join)])
(join "," [1 2 3])
;; => "1,2,3"


(user 'clojure.java.io)
(ns-publics 'clojure.java.io)
(keys(ns-publics 'clojure.java.io))
(dir clojure.java.io)

(user '[clojure.string :only (join)])
(join "," ["a" "b"])
;; "a,b"


#### Reloading Namespaces

+ By default, namespaces are loaded only once
+ use and require take optional flags to force reload

(require 'foo.bar :reload)

(require 'foo.bar :reload-all')


#### Import

+ Makes java classes available w/o package prefix in current namespace
  + Argument is a list, quoting is optional
+ Does not support aliases/renaming
+ Does not support Java's import *


(import (java.io FileReader File))
(FileReader. (File, "readme.txt"))


#### Namespaces and Files

+ For require/use to work, have to find code defining namespace
+ Clojure converts namespace name to path and look on CLASSPATH
  + Dots in namespace name become /
  + Hyphens become underscores
+ Idiomatic to define namespace per file


#### na Declaration

+ Creates namespace and loads, aliases what you need
  + At top of file
+ Refers all of clojure.core
+ Imports all of java.lang


;; in file foo/bar/baz_quux.clj
(ns foo.bar.baz-quus)


#### ns: require

+ Loads other namespace with optional alias
  + Arguments are not quoted

(ns my.coo.project
    ( :require [some.ns.foo : as foo]))
(foo/function-in-foo)


#### ns: use

+ Loads other namespace and refers symbols into namespace
  + Arguments are not quoted

(ns my.coo.project
    (use [some.ns.foo :only [bar baz]]))
(bar) ;;=> (some.ns.foo/bar)



#### ns :import

+ Loads java library and refers symbols into namespace
  + Arguments are not quoted

(ns ny.cool.project
    (:import (java.io File Writer))
)
File ;;=> java.io.File


#### ns Complete Example

(ns name
    (:require [some.ns.foo :as foo]
              [other.ns.bar :as bar])
    (:use [this.ns.baz :only (a b c)]
          [this.ns.quux :only (d e f)])
    (:import (java.io File FileWriter)
             (java.net URL URI)))


(ns namespace-example
    (:require (clojure.set :as s))
    (:use [clojure.java.io :only (delete-file)])
)

(defn do-union [& sets]
    (apply s/union sets)
)

(defn elete-old-files [& files]
    (doseq [f files]
        (delete-file f )))

> *user=> ns* ;; to see what namespace current using


### Private Vars

+ Add ^:private metadata to a definition
  + defn- is shoutcut for defn ^:private
+ Prevents automatic refer with use
+ Prevents acciental reference by qualified symbol
+ Not truly hidden: can work around


#### the-ns

+ Namespaces are first class objects
+ But their names are not normal symbols

(clojure.core)
(the-ns 'clojure.core)


#### Namespace Introspection

+ ns-name : namespace name, as a symbol
+ ns-map: map of all symbols
  + ns-interns: only defd Vars
  + ns-publics: only public Vars
+ ns-imports: only imported classes
+ ns-refers : only Vars from other namespaces
+ ns-aliases : map of all alias
+ clojure.repl/dir : print public Vars

## collections


Wisdom of the Ancients

"It is better to have 100 functions operate on one data structure than to have 10 functions operate on 10 data structure." - Alan.J.Perlis

### Working With Data


+ Clojure provides  extensive facilities for representing and manipulating data
+ Small number of data structures
+ Seq abstraction common across data structure & more
+ Large library of functions across all of them

### Immutability

+ The values of simple types are immutable
  + 4,0.5,true
+ In Clojure , the values of compound data structure are immutable too
  + Key to Clojure's concurrency model
+ Code never changes values, generates new ones to refer to instead
+ Persistent data structures ensure this is efficient in time and space

### Persistent Data Structures

+ New values built from old values + modifications
+ New values are not full copies
+ New value and old value are both available after 'changes'
+ Collection maintains its performance guarantees for most operations 
+ All Clojure data structures are persistent

### Concrete Data Structures

+ Sequential
  + List , Vector
+ Associative
  + Map, Vector
+ Both types support declarative destructuring


#### Lists

+ Singly-linked lists
+ Prepend: O(1)
+ Lookup: O(1) at head, O(n) anywhere else


()
(1 2 3)
(list 1 2 3)
'(1 2 3)'
(conj '(2 3) 1')


#### Vectors

+ Indexed, random-access, array-like
+ Append: O(1)
+ Lookup: O(1)

[]
[1 2 3]
(vector 1 2 3)
(vec '(1 2 3)')
(nth [1 2 3] 0)
(conj [1 2] 3)

[1 2 (+ 1 2)]
'[1 2 (+ 1 2)]
(def lst '[1 2 3])


### Maps

+ Kye => value, hash table, dictionary
+ Insert and lookup: O(1)
+ Unordered


{}

{:a 1 :b 2}
(:a {:a 1 :b 2})
({:a 1 :b 2} :a)
(assoc {:a 1 :b 2})
(dissoc {:a 1} :a)
(conj {} {: a 1})


#### Nest Access

+ Helper functions access data via path specified by keys

(def jdoe {:name "Jone Doe", :address {:zip 27705}})
(get-in jode [:address :zip])
(get jode :name "default name")
(assoc-in jode [:address :zip] 27521)
(assoc jode :age 22)
(update-in jdoe [:address :zip] inc)


### sets

+ Set of distinct values
+ Insert: O(1)
+ Member?: O(1)
+ Unordered



> #{}
> #{:a :b}
> (#{:a :b} :a)
> (conj #{} :a)
> (contains? #{:a} :a)


#### clojure.set Examples

(require '[clojure.set :as set]')
(set/union #{:a} #{:b})
(set/difference #{:a :b} #{:a})
(set/intersection #{:a :b} #{:b :c})

## Destructuring

+ Declarative way to pull apart compound data
  + vs explicit, verbose access
+ Works for both sequential and associative data structure
+ Nests for deep, arbitrary access


### Where You Can Destructure

+ Destructuring works in fn adn defn params, let bindings
  + Adn anything built on top of them

#### Sequential Destructuring

+ Provide vector of symbols to bind by position
  + Bind to nil if there's no data


(def stuff [7 8 9 10 11])
(let [[a b c] stuff]
    (list (+ a b) (+ b c)))


+ Can get *everything else* with &
  + value is a sequence

(def stuff [7 8 9 10 11])
(let [[a & others] stuff]
    (println a)
    (println others))

+ Idionmatic to use _ for values you don't care about


(def stuff [7 8 9 10 11])
(let [[_ & others] stuff]
    (println others))

(defn rest-names [[_ & rest-names]]
    rest-names
)


### Associative Destructuring

+ Provide map of symbols to bind by key
  + Bind to nil if there no value


(def m {:a 7 :b 4})
(let [{a :a b :b} m]
    (a b))

+ Keys can be inferred from vector of symbols to bind

(def m {:a 7 :b 4})
(let [{:keys [a b]} m]
    [a b])

+ Use :or to provide default value for bound keys


(def m {:a 7 :b 4})
(let [{:keys [a b c]
       :or {c 3}} m]
    [a b c])


### Named Arguments

+ Applying vector of keys to & binding emulated named args


(defn game [planet & {:keys [human-players computer-players]}]
    (println "Total players:" (+ human-players computer-players)))

(game "Mars" :human-players 1 :computer-players 2)
;; Total players 3


(defn draw-point [& {x :x y :y}]
    [x y])


(defn draw-point [& {:keys [x y z]
                     :or {x 0 y 0 z 0}}]
    [x y z])


## Sequences

### Sequences

+ Abstraction for representing iteration
+ Backed by a data structure  or a function
  + can be lazy and/or'infinite'
+ Fundation for large library of functions


### Sequence API

+ (seq coll)
  + if collection is non-empty, return seq object on it else, nil
  + Can't recover input source from seq
+ (first coll)
  + Returns the first element
+ (rest coll)
  + Returns a sequence of the rest of the element
+ (cons x coll)
  + Returns a new sequence first is x, rest is coll

### Sequences Over Structures

+ Can treat any Clojure data structure as a seq
  + Associative structures treated as sequence of paires

(def a-list '(1 2 3 )') ;=>  #'user/-alist'

### Sequences Over Functions

+ Can map a generator function to a seq
+ Seq is lazy, can be infinite
  + Can process more then  fits in memory


(def a-range (range 1 4)) ;>; #'user/a-range'


### Sequences in the REPL

+ REPL always print sequences with parens
  + But it's not a list!
+ infinite sequences take a long time to print


(set! *print-length* 10); only print 10 things


### Sequences Library

+ Generators
  + list, vector, map, SQL ResultSet, Stream, Directory, Iterator, XML...
+ Operations
  + map, filter, reduce, count, some, replace ...
+ generators * Operators = Power!


#### Create a Sequence

(seq [1 2 3])
(range) ;=> (0 1 2 ... infinite)
(ragne 3 ) ;=> (0 1 2)
(range 1 7 2) ;=> (1 3 5)
(iterate #(* 2 %) 2) ;=> (2 4 8 16 ... infinite)
(re-seq #"[aeiou]" "clojure") ;=> ("o" "u" "e')


#### Seq in, Seq out

(take 3 (range))
(drop 3 (range))
(map #(* %%) [0 1 2 3])
(filter een? (range))
(apply str (interpose "," (range 3)))

#### Using a Seq

(reduce + (range 4))
(reduce + 10 (range 4))
(into #{} "hello")
{into {} [[:x 1] [:y 2]]}
(some {2 :b 3 :c} [1 nil 2 3])

;; fibnacci sequences

(iterate (fn [[ a b ]]
    [b (+ a b)])
    [0 1]
)

(def fibs
  (map first 
    (iterate (fn [[ a b ]]
    [b (+ a b)])
    [0 1])))

(take 5 fibs)
(map inc (take 5 fibs))



### Adopting the Sequence Mindset

+ Sequence library surface space is big
+ Most things you want to do are in there somewhere
+ If you find yourself explicitly iterating, look for a function
+ The Clojure Cheatsheet helps
+ https://clojure.org/cheatsheet
