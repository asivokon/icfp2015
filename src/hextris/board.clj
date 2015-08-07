(ns hextris.board
  (:require [clojure.set :as set]
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
                                   (if (= chr \*) {:x row :y col} nil))
                                 line))
                            lines))]
    {:height (count lines)
     :width (count (first lines))
     :filled (set filled)
     :units #{}
    }))
      
        

(defn shift-unit [unit dx dy]
  (update-in unit [:members] #(set (map (fn [{:keys [x y]}] {:x (+ x dx)
                                                             :y (+ y dy)})
                                        %))))

(defn unit-valid-at [board unit r c]
  (empty? (set/intersection
            (set (:members (shift-unit unit r c)))
            (set (:filled board)))))


(defn lock-unit [board unit]
  (update-in board [:filled] #(set (set/union % (:members unit)))))
