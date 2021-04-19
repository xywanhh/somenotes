```
grpc
go微服务, go-kit, go网络编程， Gin

rpc 远程过程调用
原理：
首先建立tcp连接，然后互相传递数据（不一定有具体的协议）

整体来讲：
1. client发送数据（以字节流的形式）
2. 服务端接收，并解析。根据约定知道要执行什么。然后把结果返回给客户端。

RPC就是把
1. 上述过程封装下，使其操作更加优化（做到好像是在调用本地方法）
2. 使用一些大家都认可的协议，使其规范化
3. 做成一些框架。

gRPC
语言中立，平台中立，开源的远程过程调用（RPC)框架
支持java,c++，golang,php多个语言版本。
https://github.com/grpc/grpc-go

Protobuf
Google Protocol Buffer
轻便高效的序列化协议。可以用于网络通信和数据存储。
性能高，传输快，维护方便。
一些第三方rpc库都会支持protobuf
https://github.com/protocolbuffers/protobuf
golang库所属地址
https://github.com/golang/protobuf

https://github.com/protocolbuffers/protobuf/releases
protoc-3.15.8-win64.zip
protobuf.exe编译器，（使用不同的插件，比如protoc-gen-go、protoc-gen-gofast等等...）可以将.proto文件，转译成protobuf的原生数据结构。

go get github.com/golang/protobuf/protoc-gen-go
在GOPATH的bin目录下生产可执行文件.protobuf的编译插件protoc-gen-go，执行protoc命令会自动调用这个插件。

GoLand可以安装protobuf插件，方便protobuf中间文件查看

创建中间文件
syntax="proto3";
package services;
message ProdRequest {
	int32 prod_id=1; // id
}
message ProdResponse {
	int32 prod_stock=1; // id
}
然后执行:
protoc --go_out=../services/ Prod.proto

$ protoc *.proto --gofast_out=.

protoc --gofast_out=../services/ Prod.proto
protoc --gofast_out=plugins=grpc:../services/ Prod.proto

rpc 服务器环境的证书
自签证书，服务端加上证书验证
openssl.exe
server.crt
自签证书可能需要设置环境变量再运行执行
GODEBUG=x509ignoreCN=0

openssl.exe
1. genrsa -des3 -out server.key 2048
2. req -new -key server.key -out server.csr
3. rsa -in server.key -out server_no_pwd.key
4. x509 -req -days 365 -in server.csr -signkey server_no_pwd.key -out server.crt
```

****