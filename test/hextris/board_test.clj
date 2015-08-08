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

; # . #
(def unit1
  {:members [{:x 0 :y 0}
             {:x 2 :y 0}]
   :pivot {:x 1 :y 0}})


(deftest shift-unit-test
  (testing "shifting unit"
    (is (= #{{:x 10 :y 15}
             {:x 12 :y 15}}
           (:members (shift-unit unit1 10 15))))))

(deftest lock-unit-test
  (testing "modify board to lock unit"
    (is (contains? (:filled (lock-unit board1 unit1))
                   {:x 0 :y 0}))))

(deftest remove-unit-test
  (testing "removing unit from the board"
    (let [board-with-unit (lock-unit board1 unit1)]
      (is (not (contains? (:filled (remove-unit board-with-unit unit1)) {:x 0 :y 0}))))))

(deftest move-unit-test
  (testing "moving unit removes from previous location"
    (let [board-with-unit-0 (lock-unit board1 unit1)
          board-with-unit-1 (move-unit board-with-unit-0 unit1 1 1)]
      (is (contains? (:filled board-with-unit-0) {:x 0 :y 0}))
      (is (contains? (:filled board-with-unit-1) {:x 1 :y 1}))
      (is (not (contains? (:filled board-with-unit-1) {:x 0 :y 0}))))))

(deftest unit-valid-at-test
  (testing "detect if the unit can be placed at the board"
    (is (= true (unit-valid-at board1 unit1 0 0)))
    (is (= true (unit-valid-at board1 unit1 0 0)))
    (is (= false (unit-valid-at board1 unit1 2 7)))))
