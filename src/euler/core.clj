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

(defn factor? [x i]
  (zero? (mod x i)))

(defn find-factor [x i]
  (if (factor? x i)
    (/ x i)))

(defn find-factors [x] (->> (range 1 (long (math/ceil (math/sqrt x))))
       (filter #(factor? x %))
       (mapcat #(vector % (/ x %)))
       distinct))  

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

(defn split-space [x]
  (str/split x #" "))

(defn split-newline [x]
  (str/split x #"\n"))

(defn string-to-int [x]
  (Integer/parseInt x))

(def matrix-problem-11
  (map #(map string-to-int %)
       (map split-space
            (split-newline grid-20x20))))

(defn drop-every-nth
  ([x n]
   (drop-every-nth x n 0))
  ([x n c]
  	(let [size (count x)]
      (if (zero? size) nil
      	(if (= n (inc c))
          (drop-every-nth (rest x) n 0)
          (cons 
           (first x)
           (drop-every-nth (rest x) n (inc c))))))))

(defn downright-diags [data n m]
     (if (= 0 (count data)) nil
           (cons
            (take n
                  (apply concat (partition 1 (+ 1 m) data)))
            (downright-diags (rest data) n m))))

(defn get-downright-diags-of [matrix n]
  (let [m (count (nth matrix 0)) data (apply concat matrix)]
        (filter #(= n (count %))
                (downright-diags data n m))))

(defn get-upright-diags-of [matrix n]
  (get-downright-diags-of (map reverse matrix) n))

(defn get-4-in-line [x]
  (partition 4 1 x))

(def quadruplet-downright-diags
  (drop-every-nth
   (drop-every-nth
    (drop-every-nth
     (get-downright-diags-of matrix-problem-11 4) 20) 19) 18))

(def quadruplet-upright-diags
  (drop-every-nth
   (drop-every-nth
    (drop-every-nth
     (get-upright-diags-of matrix-problem-11 4) 20) 19) 18))

(def quadruplet-in-line
  (apply concat
         (map get-4-in-line matrix-problem-11)))

(defn transpose [m]
  (apply map list m))

(def quadruplet-in-column
  (apply concat
         (map get-4-in-line
              (transpose matrix-problem-11))))

(defn apply* [x]
  (apply * x))

(defn euler-project-11 []
  (apply max
         (map apply*
              (concat
               quadruplet-upright-diags
               quadruplet-downright-diags
               quadruplet-in-line
               quadruplet-in-column))))

(defn nth-triangle [n]
  (if (zero? n) 0
      (+ n (nth-triangle (dec n)))))

(def triangles
  (map nth-triangle (naturals)))

(defn euler-project-12 []
  (first
   (filter #(> (count (find-factors %)) 500)
           triangles)))

(def large-numbers-problem-13
  [37107287533902102798797998220837590246510135740250
46376937677490009712648124896970078050417018260538
74324986199524741059474233309513058123726617309629
91942213363574161572522430563301811072406154908250
23067588207539346171171980310421047513778063246676
89261670696623633820136378418383684178734361726757
28112879812849979408065481931592621691275889832738
44274228917432520321923589422876796487670272189318
47451445736001306439091167216856844588711603153276
70386486105843025439939619828917593665686757934951
62176457141856560629502157223196586755079324193331
64906352462741904929101432445813822663347944758178
92575867718337217661963751590579239728245598838407
58203565325359399008402633568948830189458628227828
80181199384826282014278194139940567587151170094390
35398664372827112653829987240784473053190104293586
86515506006295864861532075273371959191420517255829
71693888707715466499115593487603532921714970056938
54370070576826684624621495650076471787294438377604
53282654108756828443191190634694037855217779295145
36123272525000296071075082563815656710885258350721
45876576172410976447339110607218265236877223636045
17423706905851860660448207621209813287860733969412
81142660418086830619328460811191061556940512689692
51934325451728388641918047049293215058642563049483
62467221648435076201727918039944693004732956340691
15732444386908125794514089057706229429197107928209
55037687525678773091862540744969844508330393682126
18336384825330154686196124348767681297534375946515
80386287592878490201521685554828717201219257766954
78182833757993103614740356856449095527097864797581
16726320100436897842553539920931837441497806860984
48403098129077791799088218795327364475675590848030
87086987551392711854517078544161852424320693150332
59959406895756536782107074926966537676326235447210
69793950679652694742597709739166693763042633987085
41052684708299085211399427365734116182760315001271
65378607361501080857009149939512557028198746004375
35829035317434717326932123578154982629742552737307
94953759765105305946966067683156574377167401875275
88902802571733229619176668713819931811048770190271
25267680276078003013678680992525463401061632866526
36270218540497705585629946580636237993140746255962
24074486908231174977792365466257246923322810917141
91430288197103288597806669760892938638285025333403
34413065578016127815921815005561868836468420090470
23053081172816430487623791969842487255036638784583
11487696932154902810424020138335124462181441773470
63783299490636259666498587618221225225512486764533
67720186971698544312419572409913959008952310058822
95548255300263520781532296796249481641953868218774
76085327132285723110424803456124867697064507995236
37774242535411291684276865538926205024910326572967
23701913275725675285653248258265463092207058596522
29798860272258331913126375147341994889534765745501
18495701454879288984856827726077713721403798879715
38298203783031473527721580348144513491373226651381
34829543829199918180278916522431027392251122869539
40957953066405232632538044100059654939159879593635
29746152185502371307642255121183693803580388584903
41698116222072977186158236678424689157993532961922
62467957194401269043877107275048102390895523597457
23189706772547915061505504953922979530901129967519
86188088225875314529584099251203829009407770775672
11306739708304724483816533873502340845647058077308
82959174767140363198008187129011875491310547126581
97623331044818386269515456334926366572897563400500
42846280183517070527831839425882145521227251250327
55121603546981200581762165212827652751691296897789
32238195734329339946437501907836945765883352399886
75506164965184775180738168837861091527357929701337
62177842752192623401942399639168044983993173312731
32924185707147349566916674687634660915035914677504
99518671430235219628894890102423325116913619626622
73267460800591547471830798392868535206946944540724
76841822524674417161514036427982273348055556214818
97142617910342598647204516893989422179826088076852
87783646182799346313767754307809363333018982642090
10848802521674670883215120185883543223812876952786
71329612474782464538636993009049310363619763878039
62184073572399794223406235393808339651327408011116
66627891981488087797941876876144230030984490851411
60661826293682836764744779239180335110989069790714
85786944089552990653640447425576083659976645795096
66024396409905389607120198219976047599490197230297
64913982680032973156037120041377903785566085089252
16730939319872750275468906903707539413042652315011
94809377245048795150954100921645863754710598436791
78639167021187492431995700641917969777599028300699
15368713711936614952811305876380278410754449733078
40789923115535562561142322423255033685442488917353
44889911501440648020369068063960672322193204149535
41503128880339536053299340368006977710650566631954
81234880673210146739058568557934581403627822703280
82616570773948327592232845941706525094512325230608
22918802058777319719839450180888072429661980811197
77158542502016545090413245809786882778948721859617
72107838435069186155435662884062257473692284509516
20849603980134001723930671666823555245252804609722
53503534226472524250874054075591789781264330331690])

(def sum-13
  (reduce + large-numbers-problem-13))

(defn euler-project-13 []
  sum-13)

(defn find-next-problem-14 [n]
  (if (odd? n) (-> n
               (* 3)
               (+ 1))
      (-> n
          (/ 2))))

(defn chain-length-from [x] 
  (count
   (take-while #(not (= 1 %))
               (iterate find-next-problem-14 x))))

(defn euler-project-14 []
  (apply max-key chain-length-from
         (take 999999 (naturals))));;lololol

(defn factorial [n]
  (reduce * (range 1N (inc n))))

(defn binomial-coefficient [n k]
  (let [rprod (fn [a b] (reduce * (range a (inc b))))]
    (/ (rprod (- n k -1N) n) (rprod 1N k))))

(defn euler-project-15 []
  (binomial-coefficient 40 20))

(defn exp [x n]
  (apply * (repeat n x)))


(defn sum-numbers-in-number [n]
  (apply +
         (map char-to-int
              (seq
               (str n)))))



(defn euler-project-16 []
  (sum-numbers-in-number (exp 2N 1000N)))

  
  (defn div [x y]
  (int (math/floor (/ x y))))

(defn number-to-words [x]
  (if (and (> x 99) (not (zero? (mod x 100))))
    (str
     (number-to-words (div x 100))
     " hundred and "
     (number-to-words (mod x 100)))
  (if (and (> x 19) (not (zero? (mod x 10))))
    (str
     (number-to-words (* 10 (div x 10)))
     "-"
     (number-to-words (mod x 10)))
  (case x
    0 ""
    1 "one"
    2 "two"
    3 "three"
    4 "four"
    5 "five"
    6 "six"
    7 "seven"
    8 "eight"
    9 "nine"
    10 "ten"
    11 "eleven"
    12 "twelve"
    13 "thirteen"
    14 "fourteen"
    15 "fifteen"
    16 "sixteen"
    17 "seventeen"
    18 "eighteen"
    19 "nineteen"
    20 "twenty"
    30 "thirty"
    40 "forty"
    50 "fifty"
    60 "sixty"
    70 "seventy"
    80 "eighty"
    90 "ninety"
    100 "one hundred"
    200 "two hundred"
    300 "three hundred"
    400 "four hundred"
    500 "five hundred"
    600 "six hundred"
    700 "seven hundred"
    800 "eight hundred"
    900 "nine hundred"
    1000 "one thousand"))))

(defn number-letter-in-number [x]
  (count (filter #(and (not (= % \space)) (not (= % \-))) (seq (number-to-words x)))))

(defn euler-project-17 []
  (apply +
         (map number-letter-in-number
              (take 1000 (naturals)))))
(def pyramid-18 
"75
95 64
17 47 82
18 35 87 10
20 04 82 47 65
19 01 23 75 03 34
88 02 77 73 07 63 67
99 65 04 28 06 16 70 92
41 41 26 56 83 40 80 70 33
41 48 72 33 47 32 37 16 94 29
53 71 44 65 25 43 91 52 97 51 14
70 11 33 28 77 73 17 78 39 68 17 57
91 71 52 38 17 14 91 43 58 50 27 29 48
63 66 04 68 89 53 67 30 73 16 69 87 40 31
04 62 98 27 23 09 70 98 73 93 38 53 60 04 23")


(def pyramid-number-18
  (reverse
   (map #(map string-to-int %)
        (map split-space
             (split-newline pyramid-18)))))


(defn find-optimal-path [p]
  (if (= 1 (count p)) (first (nth p 0))
      (let [max-first-row (map #(apply max %)
                               (partition 2 1 (nth p 0)))
            next-row (nth p 1)
            rest-pyramid (drop 2 p)]
        (recur 
         (cons
          (map #(apply + %)
               (transpose
                (list max-first-row next-row)))
          rest-pyramid)))))

(defn euler-project-18 []
  (find-optimal-path pyramid-number-18))

(defn bisextile? [year]
  (if (factor? year 400) true
      (if (and (factor? year 4) (not (factor? year 100))) true
          false)))

(defn days-in-month [month bisextile?]
  (let [leap (if bisextile? 1 0)]
    (case month
    :january 31
    :february (+ 28 leap)
    :march 31
    :april 30
    :may 31
    :june 30
    :july 31
    :august 31
    :september 30
    :october 31
    :november 30
    :december 31)))

(def next-month
  {:january :february
   :february :march
   :march :april
   :april :may
   :may :june
   :june :july
   :july :august
   :august :september
   :september :october
   :october :november
   :november :december
   :december :january})

  
(def next-day-of-week
  {:monday :tuesday
   :tuesday :wednesday
   :wednesday :thursday
   :thursday :friday
   :friday :saturday
   :saturday :sunday
   :sunday :monday})

(def days
  (iterate next-day-of-week :monday))

(defn count-days-since
  ([date-since date-when] (count-days-since date-since date-when 0))
  ([date-since date-when count-days]
   (let [{day :day month :month year :year} date-when]
     count-days)))


(defn next-date [current-date]
  (let [{current-month :month current-year :year current-day :day current-day-of-week :day-of-week} current-date
        bisextile (bisextile? current-year)
        days-in-current-month (days-in-month current-month bisextile)
        switch-month (= days-in-current-month current-day)
        next-day (if switch-month 1 (inc current-day))
        next-month (if switch-month
                     (next-month current-month)
                     current-month) 
        next-year (if (and switch-month (= current-month :december))
                    (inc current-year)
                    current-year)
        next-day-of-week (next-day-of-week current-day-of-week)]
    {:day next-day :month next-month :year next-year :day-of-week next-day-of-week}))

(def calendar-since-1900
  (iterate next-date {:day 1 :month :january :year 1900 :day-of-week :monday}))

(def calendar-since-1901
  (drop-while (fn [date]
                (let [{year :year} date]
                  (< year 1901)))
              calendar-since-1900))

(defn euler-project-19 []
  (count
   (filter (fn [date]
                   (let [{day :day day-of-week :day-of-week} date]
                     (and
                      (= day-of-week :sunday)
                      (= day 1))))
                 (take-while (fn [date]
                               (let [{year :year month :month day :day} date]
                                 (not (and (= year 2001) (= month :january) (= day 1)))))
                             calendar-since-1901))))

(defn euler-project-20 []
  (sum-numbers-in-number (factorial 100N)))
