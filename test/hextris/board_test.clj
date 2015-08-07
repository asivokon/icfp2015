(ns hextris.board-test
  (:require [clojure.test :refer :all]
            [hextris.board :refer :all]))

(def board1 (parse-board
  "
. . . . * * * * * * * . . . .
 . . . . * . . * . . * . . . .
. . . . * . . * . . * . . . .
 . . . . * . . . . . * . . . .
. . . . * . . . . . * . . . .
 . . . . . . . . . . . . . . .
. . . . . * . * . * . . . . .
 . . . . . . . . * . * . . . .
. . . . . . . . . . . . . . .
 . . . . * * * * * . * . . . .
. . . . . . . . . . . . . . .
 . . . . . . . . . . . . . . .
. . . . . . . . . . . . . . .  "))


(assert (not (empty? (:filled board1))) "board1 is not parsed correctly")

(print board1)

; # . #
(def unit1
  {:members [{:x 0 :y 0}
             {:x 2 :y 0}]
   :pivot {:x 1 :y 0}})


(deftest shift-unit-test
  (testing "shifting unit"
    (is (= #{{:x 10 :y 15}
             {:x 12 :y 15}}
           (:members (shift-unit unit1 15 10))))))

(deftest lock-unit-test
  (testing "modify board to lock unit"
    (is (contains? (:filled (lock-unit board1 unit1))
                   {:x 0 :y 0}))))

(deftest unit-valid-at-test
  (testing "detect if the unit can be placed at the board"
    (is (= true (unit-valid-at board1 unit1 0 0)))
    (is (= true (unit-valid-at board1 unit1 0 0)))
    (is (= false (unit-valid-at board1 unit1 2 4)))))
