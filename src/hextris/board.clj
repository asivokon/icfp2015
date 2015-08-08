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
                                                             :y (+ y dy)}) %))))

(defn unit-valid-at [board unit r c]
  (empty? (set/intersection
            (set (:members (shift-unit unit r c)))
            (set (:filled board)))))


(defn lock-unit [board unit]
  (update-in board [:filled] #(set (set/union % (:members unit)))))

(defn remove-unit [board unit]
  (update board :filled #(set/difference % (:members unit))))

(defn move-unit
  "Removes unit from its old place and puts in a new place"
  [board unit dx dy]

  (lock-unit (remove-unit board unit) (shift-unit unit dx dy)))


(defn apply-command [board unit command]
  (case command
    [:move-w (move-unit board unit -1 0)
     :move-e (move-unit board unit +1 0)
     :move-sw (move-unit board unit -1 +1)
     :move-se (move-unit board unit +1 +1)
     (print "Unknown command" command)]))
