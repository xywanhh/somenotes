package util

import (
	"os/exec"
)

func RunGitCmd(gitcmd string, cmdpath string, args ...string) (string, error) {
	cmd := exec.Command(gitcmd, args...)
	cmd.Dir = cmdpath
	msg, err := cmd.CombinedOutput() // stdout+stderr
	return string(msg), err
}
