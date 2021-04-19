package main

import (
	"context"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials"
	"gorpcclient/services"
	"log"
)

func main() {
	creds, err := credentials.NewClientTLSFromFile("keys/server.crt", "xywhh")
	if err != nil {
		log.Fatal(err)
	}
	//conn, err := grpc.Dial(":8088", grpc.WithInsecure()) // 不需要证书验证
	conn, err := grpc.Dial(":8088", grpc.WithTransportCredentials(creds))
	if err != nil {
		log.Fatal(err)
	}
	defer conn.Close()

	rpcClient := services.NewProdServiceClient(conn)
	resp, err := rpcClient.GetProdStock(context.Background(),
		&services.ProdRequest{ProdId: 33})
	if err != nil {
		log.Fatal(err)
	}
	log.Print(resp.ProdStock)
}
