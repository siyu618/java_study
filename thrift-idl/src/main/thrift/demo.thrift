namespace java com.study.thrift.demo

service HelloWorldService {
    string sayHello(1:string userName)
}