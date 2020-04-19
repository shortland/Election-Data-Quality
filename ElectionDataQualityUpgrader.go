// +build ignore

package main

import (
    "fmt"
    "log"
    "net/http"
    "os/exec"
)

func handler(w http.ResponseWriter, r *http.Request) {
    if r.URL.Path[1:] != "pull" {
        fmt.Fprintf(w, "Invalid request!\n")
		return
    }

    fmt.Fprintf(w, "Attempting to upgrade...\n")

    out, err := exec.Command("sh", "ElectionUpgradeScript").Output()
    if err != nil {
		fmt.Fprintf(w, "Error encountered during upgrade: %s\n", err)
		return
    }

    fmt.Fprintf(w, "Response: %s\n", out)
}

func main() {
    http.HandleFunc("/", handler)
    log.Fatal(http.ListenAndServe(":1235", nil))
}
