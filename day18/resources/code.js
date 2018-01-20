if (process == 0){
    // send 127 initial values
    var n = 622
    for (i=0; i< 127; i++){
        n = (n * 8505) % (2^31 - 1);
        n = ((n * 129749) + 1234) % (2^31 -1);
        send (n % 10000)
    }
}

var f = 0;
var prev, next;
do {
    prev = receive
    // send 127 values - terminate if next > previous
    for (var i = 0; i<126 && f==0; i++){
        next = receive
        if ( next < prev){
            send prev
        } else {
            send next
            f = 1;
        }
    }
    send prev;
} while ( f > 0)

// init in clojure
(defn calc [n] (-> n
                (* 8505)
                (mod Integer/MAX_VALUE)
                (* 129749)
                (+ 12345)
                (mod Integer/MAX_VALUE)))

(reduce (fn [ n i ]
           (let [next (calc n)]
            (println (.toString i) (mod (.toString next) 10000))
              next)) 622 (range 127))

expecting:
0 1342
1 8352
2 9308
3 9204
4 662
5 4555
6 9340
7 8692
8 4383
...
