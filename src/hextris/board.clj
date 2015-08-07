(ns hextris.board
  (:require [clojure.set :refer [subset? union]]
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

  (let [stripped-whitespace (string/trim (string/replace s " " ""))
        lines (string/split-lines stripped-whitespace)
        filled (apply concat
                 (map-indexed (fn [row line]
                   (keep-indexed (fn [col chr]
                                   (if (= chr \*) [row col] nil))
                                 line))
                            lines))]
    {:height (count lines)
     :width (count (first lines))
     :filled (set (filter (complement nil?) filled))
     :units #{}
    }))
      
        

(defn shift-unit [unit dr dc]
  (update-in unit [:members] #(set (map (fn [{:keys [x y]}] {:x (+ x dc)
                                                             :y (+ y dr)})
                                        %))))

(defn unit-valid-at [board unit r c]
  (not (subset? (:members (shift-unit unit r c)) (:filled board))))


(defn lock-unit [board unit]
  (update-in board [:filled] #(set (union % (:members unit)))))
