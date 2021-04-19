package main

import (
	"fmt"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials"
	"gorpc/services"
	"log"
	"net/http"
)

func main() {
	// 添加证书，方便提供https服务
	creds, err := credentials.NewServerTLSFromFile("keys/server.crt",
		"keys/server_no_pwd.key")
	if err != nil {
		log.Fatal(err)
	}


	// rpc 服务端
	//rpcServer := grpc.NewServer() // 无证书验证
	rpcServer := grpc.NewServer(grpc.Creds(creds))
	services.RegisterProdServiceServer(rpcServer, new(services.ProdService))

	// rpc方式提供服务
	/*listen, err := net.Listen("tcp", ":8088")
	if err != nil {
		log.Fatal(err)
	}
	rpcServer.Serve(listen)*/

	// http方式提供服务
	mux := http.NewServeMux()
	mux.HandleFunc("/", func(writer http.ResponseWriter, request *http.Request) {
		fmt.Println(request)
		rpcServer.ServeHTTP(writer, request)
	})
	httpServer := &http.Server{
		Addr: ":8088",
		Handler: mux,
	}
	//httpServer.ListenAndServe() // 无证书
	httpServer.ListenAndServeTLS("keys/server.crt", "keys/server_no_pwd.key")
}
