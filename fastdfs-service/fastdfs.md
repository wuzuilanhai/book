使用docker简易搭建FastDFS
===

###1.获取docker镜像：
```
docker pull morunchang/fastdfs
```

###2.运行tracker：
```
docker run -d --name tracker --net=host morunchang/fastdfs sh tracker.sh
```

###3.运行storage：
```
docker run -d --name storage --net=host -e TRACKER_IP=<your tracker server address>:22122 morunchang/fastdfs sh storage.sh
```
  * 1.使用的网络模式是–net=host, <your tracker server address> 替换为你机器的Ip即可
  * 2.如果想要增加新的storage服务器，再次运行该命令，注意更换 新组名

###4.查看容器信息：
```
docker ps
```