;; ---------------------------------------------------------------

(require 2htdp/image)
(require 2htdp/universe)

;; CONSTANTS 
(define PIXELS/GRID 20) ; in pixels
(define BOARD-WIDTH 10) ; in grid coordinates
(define BOARD-HEIGHT 20) ; in grid coordinates
(define BACKGROUND (empty-scene (* PIXELS/GRID BOARD-WIDTH) 
                                (* PIXELS/GRID BOARD-HEIGHT)))
(define VELOCITY 4)

;; DATA DEFINITIONS

;; A Block is one of:
;; - empty
;; - (cons Posn [List-of Posn])
;; A Block has four posns, and 
;; the posns must be adjacent
;; i.e. There can be no gaps
;; in a Block

;; block-temp : Block -> ??
;; purpose statement
#; (define (block-temp b)
     (cond [(empty? b) ...]
           [else ... (first b) ...
                 ... (block-temp (rest b)) ...]))

;; examples of Block
(define block1 (list (make-posn 3 3)
                     (make-posn 3 4)
                     (make-posn 3 5)
                     (make-posn 4 5)))
(define block2 (list (make-posn 2 15)
                     (make-posn 3 15)
                     (make-posn 3 16)
                     (make-posn 4 16)))
(define block3 (list (make-posn 1 1)
                     (make-posn 1 2)
                     (make-posn 1 3)
                     (make-posn 2 2)))
(define block4 (list (make-posn 20 5)
                     (make-posn 19 5)
                     (make-posn 18 5)
                     (make-posn 17 5)))
(define block5 (list (make-posn 3 18)
                     (make-posn 4 18)
                     (make-posn 5 18)
                     (make-posn 4 19)))
(define block6 (list (make-posn 3 6)
                     (make-posn 3 7)
                     (make-posn 3 8)
                     (make-posn 3 9)))
(define block7 (list (make-posn -1 4)
                     (make-posn 0 4)
                     (make-posn 1 4)
                     (make-posn 2 4)))
(define block8 (list (make-posn 4 6)
                     (make-posn 4 7)
                     (make-posn 4 8)
                     (make-posn 4 9)))

(define-struct tetra (block color posn))
;; A Tetra is a structure:
;; (make-tetra Block Color Posn)
;; tetra-block represents the four posns
;; that make a tetra, tetra-color is the 
;; color, and tetra-posn is the rotation point
;; of the posn

;; tetra-temp : Tetra -> ??
;; purpose statement
#; (define (tetra-temp t)
     (... (tetra-block t) ...
          (tetra-color t) ...
          (tetra-posn t) ...))

;; examples of Tetra
(define t1 (make-tetra block1 "purple" (make-posn 3 4)))
(define t2 (make-tetra block2 "pink" (make-posn 15 3)))
(define t3 (make-tetra block5 "cyan" (make-posn 3 3)))

;; A List of Tetra (LOT) is one of:
;; - empty
;; - (cons Tetra LOT)

;; lot-temp : LOT -> ??
;; purpose statement
#; (define (lot-temp l)
     (cond [(empty? l) ...]
           [else ... (first l) ...
                 ... (lot-temp (rest l))]))

;; examples of lot
(define lot0 empty)
(define lot1 (list t1))
(define lot2 (list t1 t2)) 

(define-struct world (tetra lot))
;; The World is a structure:
;; (make-world Tetra LOT)
;; world-tetra represents the tetra
;; that is falling, world-lot represents
;; the pile of tetra at the bottom

;; examples of world
(define world1 (make-world t1 empty))
(define world2 (make-world t2 lot1))
(define world3 (make-world t1 (cons (make-tetra block3 "red" (make-posn 0 4)) empty)))
(define world4 (make-world t3 empty))
(define world5 (make-world t1 (list t2 t3)))


;; -----------------------   GRAPHICS  --------------------

;; place-posn/grid : Posn Color Image-> Image
;; put one square of a tetra
;; on the scene, according to grid coordinates

(check-expect (place-posn/grid (make-posn 3 4) "green" BACKGROUND)
              (place-image (overlay (square PIXELS/GRID "outline" "black")
                                    (square PIXELS/GRID "solid" "green"))
                           (* PIXELS/GRID 3.5)
                           (* PIXELS/GRID 4.5)
                           BACKGROUND))

(define (place-posn/grid p c i)
  (place-image (overlay (square PIXELS/GRID "outline" "black")
                        (square PIXELS/GRID "solid" c))
               (* PIXELS/GRID (+ 0.5 (posn-x p)))
               (* PIXELS/GRID (+ 0.5 (posn-y p)))
               i))

;; place-block/grid : Block Color Image -> Image
;; place a block of a given color on 
;; an image, according to grid coordinates

(check-expect (place-block/grid block1 "purple" BACKGROUND)
              (place-posn/grid (make-posn 3 3) 
                               "purple" 
                               (place-posn/grid 
                                (make-posn 3 4) 
                                "purple"
                                (place-posn/grid 
                                 (make-posn 3 5) 
                                 "purple"
                                 (place-posn/grid 
                                  (make-posn 4 5) 
                                  "purple"
                                  BACKGROUND)))))

(define (place-block/grid b c i)
  (cond [(empty? b) i]
        [else (place-posn/grid (first b) c
                               (place-block/grid (rest b) c i))]))

;; place-tetra/grid : Tetra Image -> Image
;; place a tetra on an image, according to 
;; grid coordinates

(check-expect (place-tetra/grid t1 BACKGROUND)
              (place-block/grid (tetra-block t1) (tetra-color t1) BACKGROUND))

(check-expect (place-tetra/grid t2 BACKGROUND)
              (place-block/grid (tetra-block t2) (tetra-color t2) BACKGROUND))

(define (place-tetra/grid t i)
  (place-block/grid (tetra-block t) (tetra-color t) i))

;; place-lot/grid : LOT -> Image
;; place a list of tetra on BACKGROUND,
;; according to grid coordinates

(check-expect (place-lot/grid lot0) BACKGROUND)
(check-expect (place-lot/grid lot1) 
              (place-tetra/grid (first lot1)
                                (place-lot/grid (rest lot1))))

(define (place-lot/grid l)
  (cond [(empty? l) BACKGROUND]
        [else (place-tetra/grid (first l) 
                                (place-lot/grid (rest l)))]))


;; ----------------------   KEY-HANDLING   ----------------------

;; posn-rotate-ccw : Posn Posn -> Posn
;; Rotate the posn 90 degrees counterclockwise 
;; around the second posn 

(check-expect (posn-rotate-ccw (make-posn 5 5) (make-posn 4 4))
              (make-posn 5 3))
(check-expect (posn-rotate-ccw (make-posn 3 3) (make-posn 4 4))
              (make-posn 3 5))
(check-expect (posn-rotate-ccw (make-posn 7 6) (make-posn 6 5))
              (make-posn 7 4))
(check-expect (posn-rotate-ccw (make-posn 2 5) (make-posn 0 0))
              (make-posn 5 -2))

(define (posn-rotate-ccw a b)
  (make-posn (+ (posn-x b)
                 (- (posn-y a)
                    (posn-y b)))
             (- (posn-y b)
                (- (posn-x a)
                   (posn-x b)))))

;; rotate-block-ccw : Block Posn -> Block
;; Rotates the four posns of a block around a posn counterclockwise

(check-expect (rotate-block-ccw block3 (make-posn 1 2)) 
              (list (make-posn 0 2) (make-posn 1 2) (make-posn 2 2) (make-posn 1 1)))

(define (rotate-block-ccw lob apos)
  (cond [(empty? lob) empty]
        [(cons? lob) (cons (posn-rotate-ccw (first lob) apos) 
                           (rotate-block-ccw (rest lob) apos))]))

;; rotate-tetra-ccw : Tetra -> Tetra
;; Rotates the the tetra counterclockwise around tetra-posn

(check-expect (rotate-tetra-ccw (make-tetra block1 "orange" (make-posn 3 4)))
              (make-tetra (list (make-posn 2 4)
                                (make-posn 3 4)
                                (make-posn 4 4)
                                (make-posn 4 3)) "orange" (make-posn 3 4)))

(define (rotate-tetra-ccw atetra)
  (make-tetra (rotate-block-ccw (tetra-block atetra) (tetra-posn atetra))
              (tetra-color atetra)
              (tetra-posn atetra))) 

;; posn-rotate-cw : Posn Posn -> Posn
;; Rotate the posn 90 degrees clockwise 
;; around the second posn 

(check-expect (posn-rotate-cw (make-posn 5 5) (make-posn 4 4))
              (make-posn 3 5))
(check-expect (posn-rotate-cw (make-posn 3 3) (make-posn 4 4))
              (make-posn 5 3))
(check-expect (posn-rotate-cw (make-posn 7 6) (make-posn 6 5))
              (make-posn 5 6))
(check-expect (posn-rotate-cw (make-posn 2 5) (make-posn 0 0))
              (make-posn -5 2))

(define (posn-rotate-cw a b)
  (make-posn (- (posn-x b)
                 (- (posn-y a)
                    (posn-y b)))
             (+ (posn-y b)
                (- (posn-x a)
                   (posn-x b)))))

;; rotate-block-cw : Block Posn -> Block
;; Rotates the four posns of a block around a posn clockwise

(check-expect (rotate-block-cw (list (make-posn 1 1) 
                                     (make-posn 1 2) 
                                     (make-posn 1 3)
                                     (make-posn 2 2))
                               (make-posn 1 2)) 
              (list (make-posn 2 2)
                    (make-posn 1 2)
                    (make-posn 0 2)
                    (make-posn 1 3)))

(define (rotate-block-cw lob apos)
  (cond [(empty? lob) empty]
        [(cons? lob) (cons (posn-rotate-cw (first lob) apos) 
                           (rotate-block-cw (rest lob) apos))]))

;; rotate-tetra-cw : Tetra -> Tetra
;; Rotates the tetra clockwise around tetra-posn

(check-expect (rotate-tetra-cw (make-tetra block1 "orange" (make-posn 3 4)))
              (make-tetra (list (make-posn 4 4)
                                (make-posn 3 4)
                                (make-posn 2 4)
                                (make-posn 2 5)) "orange" (make-posn 3 4)))

(define (rotate-tetra-cw atetra)
  (make-tetra (rotate-block-cw (tetra-block atetra) (tetra-posn atetra))
              (tetra-color atetra)
              (tetra-posn atetra)))

;; move-block-left : Block -> Block
;; moves the block left 1 space on left keystroke

(check-expect (move-block-left (list (make-posn 1 1)
                                     (make-posn 1 2)
                                     (make-posn 1 3)
                                     (make-posn 2 2))) 
              (list (make-posn 0 1)
                    (make-posn 0 2) 
                    (make-posn 0 3)
                    (make-posn 1 2)))

(define (move-block-left lob)
  (cond [(empty? lob) empty]
        [(cons? lob) (cons (make-posn (- (posn-x (first lob)) 1)
                                      (posn-y (first lob)))
                           (move-block-left (rest lob)))]))

;; move-tetra-left : Tetra -> Tetra
;; moves the tetra left 1 space on left keystroke

(check-expect (move-tetra-left (make-tetra block1 "orange" (make-posn 3 4)))
              (make-tetra (list (make-posn 2 3)
                                (make-posn 2 4)
                                (make-posn 2 5)
                                (make-posn 3 5)) "orange" (make-posn 2 4)))

(define (move-tetra-left atetra)
  (make-tetra (move-block-left (tetra-block atetra)) 
              (tetra-color atetra) 
              (make-posn (sub1 (posn-x (tetra-posn atetra))) 
                         (posn-y (tetra-posn atetra)))))
                           
;; move-block-right : Block -> Block
;; moves the block right 1 space on left keystroke

(check-expect (move-block-right (list (make-posn 1 1) 
                                      (make-posn 1 2)
                                      (make-posn 1 3)
                                      (make-posn 2 2))) 
              (list (make-posn 2 1) 
                    (make-posn 2 2) 
                    (make-posn 2 3) 
                    (make-posn 3 2)))

(define (move-block-right lob)
  (cond [(empty? lob) empty]
        [(cons? lob) (cons (make-posn (+ (posn-x (first lob)) 1)
                                      (posn-y (first lob)))
                           (move-block-right (rest lob)))]))

;; move-tetra-right : Tetra -> Tetra
;; moves the tetra right 1 space on left keystroke

(check-expect (move-tetra-right (make-tetra block1 "orange" (make-posn 3 4)))
              (make-tetra (list (make-posn 4 3)
                                (make-posn 4 4)
                                (make-posn 4 5)
                                (make-posn 5 5)) "orange" (make-posn 4 4)))

(define (move-tetra-right atetra)
  (make-tetra (move-block-right (tetra-block atetra)) 
              (tetra-color atetra) 
              (make-posn (add1 (posn-x (tetra-posn atetra))) 
                         (posn-y (tetra-posn atetra)))))


;; block-hit-right? : Block -> Boolean
;; if a block hits the right edge of the board

(check-expect (block-hit-right? block1) false)
(check-expect (block-hit-right? block4) true)

(define (block-hit-right? lob)
  (cond [(empty? lob) false]
        [(cons? lob) (or (> (posn-x (first lob)) 8)
                         (block-hit-right? (rest lob)))]))

;; block-hit-left? : Block -> Boolean
;; if a block hits the left edge of the board

(check-expect (block-hit-left? block1) false)
(check-expect (block-hit-left? (list (make-posn -2 5)
                                     (make-posn -1 5)
                                     (make-posn 0 5)
                                     (make-posn 1 5))) true)

(define (block-hit-left? lob)
  (cond [(empty? lob) false]
        [(cons? lob) (or (< (posn-x (first lob)) 1)
                         (block-hit-left? (rest lob)))]))


;; allow-rotation-ccw? : Tetra -> Boolean
;; should we allow a tetra to rotate counterclockwise?

(check-expect (allow-rotation-ccw? t1) true)
(check-expect (allow-rotation-ccw? (make-tetra block4 "green" (make-posn 19 5))) false)

(define (allow-rotation-ccw? t)
  (not (or (block-hit-left? (tetra-block (rotate-tetra-ccw t)))
           (block-hit-right? (tetra-block (rotate-tetra-ccw t))))))

;; allow-rotation-cw? : Tetra -> Boolean
;; should we allow a tetra to rotate clockwise?

(check-expect (allow-rotation-cw? t1) true)
(check-expect (allow-rotation-cw? (make-tetra block4 "green" (make-posn 19 5))) false)

(define (allow-rotation-cw? t)
  (not (or (block-hit-left? (tetra-block (rotate-tetra-cw t)))
           (block-hit-right? (tetra-block (rotate-tetra-cw t))))))


;; allow-left-shift? : Tetra LOT -> Boolean
;; should the block be allowed to move left
;; when it is not on the left border
;; i.e. is the left side of the tetra
;; not touching any of the tetra in the 
;; list of tetra

(define (allow-left-shift? t l)
  (not (stop-tetra? (move-tetra-left t) l)))

(check-expect (allow-left-shift? t1 (cons t2 empty)) true)

;; allow-right-shift? : Tetra LOT -> Boolean
;; should the block be allowed to move right
;; when it is not on the right border
;; i.e. is the right side of the tetra
;; not touching any of the tetra in the 
;; list of tetra

(check-expect (allow-right-shift? t1 (cons t2 empty)) true)

(define (allow-right-shift? t l)
  (not (stop-tetra? (move-tetra-right t) l)))


;; ----------------------  OTHER FUNCTIONS  --------------------

;; move-block-down : Block -> Block
;; moves a block down each tick

(check-expect (move-block-down (list (make-posn 1 1) 
                                     (make-posn 1 2) 
                                     (make-posn 1 3) 
                                     (make-posn 2 2))) 
              (list (make-posn 1 (+ 1 1)) 
                    (make-posn 1 (+ 1 2)) 
                    (make-posn 1 (+ 1 3)) 
                    (make-posn 2 (+ 1 2))))

(define (move-block-down lob)
  (cond [(empty? lob) empty]
        [(cons? lob) (cons (make-posn (posn-x (first lob)) 
                                      (+ 1 (posn-y (first lob))))
                           (move-block-down (rest lob)))]))


;; move-tetra-down : Tetra -> Tetra
;; moves a tetra down each tick

(check-expect (move-tetra-down (make-tetra block1 "orange" (make-posn 3 4)))
              (make-tetra (list (make-posn 3 (+ 1 3))
                                (make-posn 3 (+ 1 4))
                                (make-posn 3 (+ 1 5))
                                (make-posn 4 (+ 1 5))) "orange" 
                                                       (make-posn 3 (+ 1 4))))

(define (move-tetra-down atetra)
  (make-tetra (move-block-down (tetra-block atetra))
              (tetra-color atetra)
              (make-posn (posn-x (tetra-posn atetra))
                         (+ 1 (posn-y (tetra-posn atetra))))))

;; block-bottom : Block -> Boolean
;; are any of the posns in a block at the bottom of the screen

(check-expect (block-bottom block1) false)
(check-expect (block-bottom block5) true)

(define (block-bottom lob)
  (cond [(empty? lob) false]
        [(cons? lob) (or (>= (posn-y (first lob)) (- BOARD-HEIGHT 1))
                         (block-bottom (rest lob)))]))

;; compare-posn2block : Posn Block -> Boolean
;; determines whether a posn hits another block

(check-expect (compare-posn2block (make-posn 3 2) block1) true)
(check-expect (compare-posn2block (make-posn 3 1) block1) false)

(define (compare-posn2block p lob)
  (cond [(empty? lob) false]
        [(cons? lob) (or (and (= (+ 1 (posn-y p)) (posn-y (first lob)))
                              (= (posn-x p) (posn-x (first lob))))
                         (compare-posn2block p (rest lob)))]))

;; compare-block? : Block Block -> Boolean
;; determines whether a block hits anther block

(check-expect (compare-block? block1 block2) false)
(check-expect (compare-block? block1 block6) true)
(check-expect (compare-block? block1 empty) false)

(define (compare-block? lob1 lob2)
  (cond [(empty? lob2) false]
        [(empty? lob1) false]
        [(cons? lob2) (or (compare-posn2block (first lob1) lob2)
                          (compare-block? (rest lob1) lob2))]))

;; block-hit? : Block LOT -> Boolean
;; determines whether a block hits another tetra in the list

(check-expect (block-hit?
               block1 
               (list (make-tetra block5 "red" (make-posn 5 5))
                     (make-tetra 
                      block6 "yellow" (make-posn 5 10)))) true)
(check-expect (block-hit? block1 empty) false)

(define (block-hit? lob lot)
  (cond [(empty? lot) false]
        [(cons? lot) (or (compare-block? lob (tetra-block (first lot)))
                         (block-hit? lob (rest lot)))]))
  

;; stop-tetra? : Tetra LOT -> Boolean
;; should the block stop moving downwards?

(check-expect (stop-tetra? t1 empty) false)
(check-expect (stop-tetra? t1 (list t2)) false)
(check-expect (stop-tetra? t1 (cons (make-tetra 
                                     block6 "yellow" 
                                     (make-posn 5 10)) empty)) true)
(check-expect (stop-tetra? 
               (make-tetra block5 "yellow" (make-posn 5 10)) empty) true)


(define (stop-tetra? atetra alot)
  (cond [(block-bottom (tetra-block atetra)) true]
        [(empty? alot) false]
        [(block-hit? (tetra-block atetra) alot) true]
        [else false]))

;; new-tetra : World -> Tetra
;; creates a new, random tetra

(define (new-tetra w)
  (cond [(= 0 (random 7)) 
         (make-tetra (list (make-posn 4 0)
                           (make-posn 4 1)
                           (make-posn 5 0)
                           (make-posn 5 1))
                     "green"
                     (make-posn 4.5 0.5))]
        [(= 1 (random 7))
         (make-tetra (list (make-posn 3 0)
                           (make-posn 4 0)
                           (make-posn 5 0)
                           (make-posn 6 0))
                     "blue"
                     (make-posn 4 0))]
        [(= 2 (random 7))
         (make-tetra (list (make-posn 3 1)
                           (make-posn 4 1)
                           (make-posn 5 1)
                           (make-posn 5 0))
                     "purple"
                     (make-posn 5 0))]
        [(= 3 (random 7))
         (make-tetra (list (make-posn 3 1)
                           (make-posn 4 1)
                           (make-posn 5 1)
                           (make-posn 3 0))
                     "cyan"
                     (make-posn 3 0))]
        [(= 4 (random 7))
         (make-tetra (list (make-posn 3 1)
                           (make-posn 4 1)
                           (make-posn 5 1)
                           (make-posn 4 0))
                     "orange"
                     (make-posn 4 0))]
        [(= 5 (random 7))
         (make-tetra (list (make-posn 3 0)
                           (make-posn 4 0)
                           (make-posn 4 1)
                           (make-posn 5 1))
                     "pink"
                     (make-posn 4 0))]
        [(= 6 (random 7))
         (make-tetra (list (make-posn 3 1)
                           (make-posn 4 1)
                           (make-posn 4 0)
                           (make-posn 5 0))
                     "red"
                     (make-posn 4 0))]
        [else (new-tetra w)]))

;; block-too-high? : Block -> Boolean
;; is the block off the board too high

(check-expect (block-too-high? block1) false)
(check-expect (block-too-high? block3) true)

(define (block-too-high? b)
  (cond [(empty? b) false]
        [else (or (<= (posn-y (first b)) 1)
                  (block-too-high? (rest b)))]))

;; tetra-too-high? : Tetra -> Boolean
;; is the tetra off the board too high

(check-expect (tetra-too-high? t1) false)
(check-expect (tetra-too-high? (make-tetra block3 "olive" (make-posn 1 1))) true)

(define (tetra-too-high? t)
  (block-too-high? (tetra-block t)))


;; ----------------------     BIG-BANG   -------------------------

;; render : World -> Image
;; render the current world 
;; as an image for big-bang

(check-expect (render world1)
              (place-tetra/grid (world-tetra world1)
                                (place-lot/grid (world-lot world1))))

(define (render w)
  (place-tetra/grid (world-tetra w)
                    (place-lot/grid (world-lot w))))

;; key-handler : World Key -> World
;; Moves the tetra based on the given keystroke

(check-expect (key-handler world0 "right")
              (make-world (move-tetra-right (world-tetra world0)) (world-lot world0)))
(check-expect (key-handler world0 "left")
              (make-world (move-tetra-left (world-tetra world0)) (world-lot world0)))
(check-expect (key-handler world0 "a")
              (make-world (rotate-tetra-ccw (world-tetra world0)) (world-lot world0)))
(check-expect (key-handler world0 "s")
              (make-world (rotate-tetra-cw (world-tetra world0)) (world-lot world0)))
(check-expect (key-handler world0 "p")
              world0)

(define (key-handler w k)
  (cond [(and (key=? k "right") 
              (not (block-hit-right? (tetra-block (world-tetra w))))
              (allow-right-shift? (world-tetra w) (world-lot w)))
         (make-world (move-tetra-right (world-tetra w)) (world-lot w))]
        [(and (key=? k "left")
              (not (block-hit-left? (tetra-block (world-tetra w))))
              (allow-left-shift? (world-tetra w) (world-lot w)))
         (make-world (move-tetra-left (world-tetra w)) (world-lot w))]
        [(and (key=? k "a") 
              (allow-rotation-ccw? (world-tetra w)))
         (make-world (rotate-tetra-ccw (world-tetra w)) (world-lot w))]
        [(and (key=? k "s") 
              (allow-rotation-cw? (world-tetra w)))
         (make-world (rotate-tetra-cw (world-tetra w)) (world-lot w))]
        [else w]))

;; tetra-tick : World -> World
;; Moves the tetra downward and introduce a new tetra to the game

(check-expect (tetra-tick world0) 
              (make-world (move-tetra-down (world-tetra world0)) (world-lot world0)))
(check-random (tetra-tick world4)
              (make-world (new-tetra world4)
                          (cons (world-tetra world4) (world-lot world4))))
              

(define (tetra-tick w)
  (cond [(stop-tetra? (world-tetra w) (world-lot w)) 
         (make-world (new-tetra w) 
                     (cons (world-tetra w) (world-lot w)))]
        [else (make-world (move-tetra-down (world-tetra w))
                          (world-lot w))]))

;; game-over? : World -> Boolean
;; Determines whether a list of tetras within a world reach the top of the board

(check-expect (game-over? world0) false)
(check-expect (game-over? world3) true)

(define (game-over? w)
  (cond [(empty? (world-lot w)) false]
        [else (or (tetra-too-high? (first (world-lot w)))
                  (game-over? (make-world (world-tetra w)
                                          (rest (world-lot w)))))]))

(define world0 (make-world (new-tetra world1) empty))

;; main : World -> Number
;; counts the number of blocks
;; placed on the board
;; also main function:
;; example input (main world0)

(define (main w)
  (* 4 (length (world-lot 
           (big-bang world0
                     (stop-when game-over?)
                     (to-draw render)
                     (on-tick tetra-tick (/ 1 VELOCITY))
                     (on-key key-handler))))))
