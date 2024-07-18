### npm 配置国内镜像源

```bash

# 查询当前使用的镜像源
npm get registry

# 设置为淘宝镜像源
npm config set registry https://registry.npmmirror.com/

# 腾讯云镜像源
https://mirrors.cloud.tencent.com/npm/
# 淘宝镜像源
https://registry.npmmirror.com/

# 还原为官方镜像源
npm config set registry https://registry.npmjs.org/

# 验证是否成功
npm get registry

```
