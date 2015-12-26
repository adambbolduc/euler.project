(ns euler.core
  (:require [clojure.math.numeric-tower :as math]
            (incanter [core :refer [kronecker]])
            [clojure.string :as str])) 

(defn naturals
  ([] (naturals 1))
  ([n] (lazy-seq (cons n ( naturals (inc n))))))

(defn n-divisible-by-m? [n m]
  (zero? (mod n m)))

(defn list-n-mult-y [n y]
  (filter (fn [a] (n-divisible-by-m? a y))
          (take n (naturals))))

(defn euler-project-1 []
  (reduce +
          (distinct
           (concat (list-n-mult-y 999 3) (list-n-mult-y 999 5)))))

(defn fib [a b]
  (lazy-seq (cons a (fib b (+ b a)))))

(defn fibonacci [] (fib 1 1))

(defn euler-project-2 []
  (apply +
         (filter even?
                 (take 33 (fibonacci)))))

(defn prime? [x]
  (let [root (math/ceil (math/sqrt x))]
    (if (= x 1) false
        (if (= x 2) true
            (loop [i 2]
              (if (> i  root) true
                  (if (zero? (mod x i)) false
                      (recur (+ i 1)))))))))

(defn find-factor [x i]
  (if (zero? (mod x i))
    (/ x i)))

(defn factors-large-number [i]
  (find-factor 600851475143 i))

(def prime-factor
  (filter prime?
          (filter some?
                  (map factors-large-number (naturals)))))

(defn euler-project-3 []
  (take 1 prime-factor))

(defn palindrome? [x]
  (let [lazy-seq-x (lazy-seq (str x))]
    (loop [i 0 j (- (count lazy-seq-x) 1)]
      (if (>= i j) true
          (if (not (= (nth lazy-seq-x i) (nth lazy-seq-x j))) false
              (recur (+ i 1) (- j 1)))))))

(defn three-digit-multiples []
  (map int
       (let [three-digit-numbers (take 999 (naturals))]
         (lazy-seq (kronecker three-digit-numbers three-digit-numbers)))))

(defn euler-project-4 []
  (first
   (filter palindrome?
           (sort > (three-digit-multiples)))))

(defn euler-project-5 []
  (first
   (filter (fn [a] (n-divisible-by-m? a 2))
   (filter (fn [a] (n-divisible-by-m? a 3))
   (filter (fn [a] (n-divisible-by-m? a 4))
   (filter (fn [a] (n-divisible-by-m? a 5))
   (filter (fn [a] (n-divisible-by-m? a 6))
   (filter (fn [a] (n-divisible-by-m? a 7))
   (filter (fn [a] (n-divisible-by-m? a 8))
   (filter (fn [a] (n-divisible-by-m? a 9))
   (filter (fn [a] (n-divisible-by-m? a 10))
   (filter (fn [a] (n-divisible-by-m? a 11))
   (filter (fn [a] (n-divisible-by-m? a 12))
   (filter (fn [a] (n-divisible-by-m? a 13))
   (filter (fn [a] (n-divisible-by-m? a 14))
   (filter (fn [a] (n-divisible-by-m? a 15))
   (filter (fn [a] (n-divisible-by-m? a 16))
   (filter (fn [a] (n-divisible-by-m? a 17))
   (filter (fn [a] (n-divisible-by-m? a 18))
   (filter (fn [a] (n-divisible-by-m? a 19))
   (filter (fn [a] (n-divisible-by-m? a 20))
          (naturals)))))))))))))))))))))) 

(defn sum-square-difference [n]
  (*
   (/ n 2)
   (+
    (/ n 2)
    (/ 1 3))
   (-
    (* n n)
    1)))

(defn euler-project-6 []
  (sum-square-difference 100))

(defn euler-project-7 []
  (last
   (take 10001
         (filter prime?
                 (naturals)))))

(def thousand-digit-number-string
  "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450")

(defn char-to-int [c]
  (-
   (int c)
   (int \0)))

(defn array-of-numbers []
  (map char-to-int (lazy-seq thousand-digit-number-string)))

(defn n-partition-of-coll [n coll]
  (partition n 1 coll))

(defn euler-project-8 []
  (apply max
         (map #(apply * %)
              (n-partition-of-coll 13 (array-of-numbers)))))

(def triplets
  (apply concat
  (for [i (range 1 1000)]
    (for [j (range 1 (- 1001 i))
          :let [c (- 1000 (+ j i))]]
      [i j c]))))

(defn pitagorian? [vect]
  (let [a (nth vect 0)
        b (nth vect 1)
        c (nth vect 2)]
    (=
     (+
      (* a a)
      (* b b))
     (* c c))))

(defn euler-project-9 []
  (apply *
         (nth 
          (filter pitagorian? triplets)
          0)))

(defn euler-project-10 []
  (apply +
         (filter prime?
                 (take 2000000 
                       (naturals)))))

(def grid-20x20 "08 02 22 97 38 15 00 40 00 75 04 05 07 78 52 12 50 77 91 08
49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 04 56 62 00
81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 03 49 13 36 65
52 70 95 23 04 60 11 42 69 24 68 56 01 32 56 71 37 02 36 91
22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80
24 47 32 60 99 03 45 02 44 75 33 53 78 36 84 20 35 17 12 50
32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70
67 26 20 68 02 62 12 20 95 63 94 39 63 08 40 91 66 49 94 21
24 55 58 05 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72
21 36 23 09 75 00 76 44 20 45 35 14 00 61 33 97 34 31 33 95
78 17 53 28 22 75 31 67 15 94 03 80 04 62 16 14 09 53 56 92
16 39 05 42 96 35 31 47 55 58 88 24 00 17 54 24 36 29 85 57
86 56 00 48 35 71 89 07 05 44 44 37 44 60 21 58 51 54 17 58
19 80 81 68 05 94 47 69 28 73 92 13 86 52 17 77 04 89 55 40
04 52 08 83 97 35 99 16 07 97 57 32 16 26 26 79 33 27 98 66
88 36 68 87 57 62 20 72 03 46 33 67 46 55 12 32 63 93 53 69
04 42 16 73 38 25 39 11 24 94 72 18 08 46 29 32 40 62 76 36
20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 04 36 16
20 73 35 29 78 31 90 01 74 31 49 71 48 86 81 16 23 57 05 54
01 70 54 71 83 51 54 69 16 92 33 48 61 43 52 01 89 19 67 48")

(def example (map split-space (str/split grid-20x20 #"\n")))
