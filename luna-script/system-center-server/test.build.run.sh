#!/bin/bash

# Linux chmod +x run.sh

# 项目名称
project="luna-server-system-center"

# 获取当前日期和时间
today=$(date +%Y%m%d%H%M%S)
version=$today
image="$project:$version"

# 构建Docker镜像
sudo docker build -t "$image" .

# 删除现有的容器，如果存在
if sudo docker ps -a --format '{{.Names}}' | grep -Eq "^luna-server-system-center\$"; then
    sudo docker rm -f luna-server-system-center
fi

# 启动新的容器
sudo docker run -d -p 11000:80 -e SPRING_PROFILES_ACTIVE=test --name luna-server-system-center --restart always "$image"
