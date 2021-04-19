package main

import (
	"fmt"
	"net/http"
	"sync"
)

var lock sync.Mutex
var count int

func main() {
	http.HandleFunc("/test", handler)
	http.HandleFunc("/count", sum)
	http.ListenAndServe("localhost:8088", nil)
}

func handler(w http.ResponseWriter, r *http.Request) {
	lock.Lock()
	count++
	lock.Unlock()
	fmt.Fprintf(w, "URL.Path=%q\n", r.URL.Path)
}

func sum(w http.ResponseWriter, r *http.Request) {
	lock.Lock()
	fmt.Fprintf(w, "Count %d\n", count)
	lock.Unlock()
}



