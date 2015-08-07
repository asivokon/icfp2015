(ns hextris.board
  (:require [clojure.set :refer [subset?]]
            [clojure.string :as string]
            [clojure.core :refer :all]))

(defn parse-board
  "Parse board from text string, e.g.
  
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
    . . . . . . . . . . . . . . .
  "
  [s]

  (let [stripped-whitespace (string/trim (string/replace " " "" s))
        lines (string/split-lines stripped-whitespace)
        filled (map-indexed (fn [row line]
                   (map-indexed (fn [col chr]
                                  (if (= chr '*') [row col] nil))
                                line))
                 lines)]
    {:height (count lines)
     :width (count (first lines))
     :filled (set (filter nil? filled))
     :units #{}
    }))
      
        


(defn unit-valid-at [board unit r c]
  (not (subset? (:members unit) (:filled board))))

(defn move-unit [unit dr dc]
  (assoc unit :members (map (fn [{:keys [x y]}] {:x (+ x dc)
                                           :y (+ y dr)})
                            (:members unit))))

(defn enplace-unit [board unit]
  board)
