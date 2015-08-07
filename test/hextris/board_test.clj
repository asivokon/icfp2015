(ns hextris.board-test
  (:require [clojure.test :refer :all]
            [hextris.board :refer :all]))

(def board1 (parse-board
  "
. . . . . . . . . . . . . . .
 . . . . . . . . . . . . . . .
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


; # . #
(def unit1
  {:members [{:x 0 :y 0}
             {:x 2 :y 0}]
   :pivot {:x 1 :y 0}})


(deftest unit-valid-at-test
  (testing "detect if the unit can be placed at the board"
    (is (= true (unit-valid-at board1 unit1 0 0)))
    (is (= true (unit-valid-at board1 unit1 0 0)))
    (is (= false (unit-valid-at board1 unit1 2 7)))))

