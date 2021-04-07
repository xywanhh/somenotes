package main

import (
	"fmt"
	"gogit/util"
)

func main() {
	gitpath := "E:\\githubRepositorys\\somenotes"
	msg, err := util.RunGitCmd("git", gitpath, "status", "--short")
	fmt.Println(msg, err)

	msg, err = util.RunGitCmd("git", gitpath, "remote", "-vv")
	fmt.Println(msg, err)

	msg, err = util.RunGitCmd("git", gitpath, "log")
	fmt.Println(msg, err)
}
