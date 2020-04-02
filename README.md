# springboot_vue_blog

**Vue2.0到3.x小白入门教程(基础+实战+vuex+axios+全网最火教程)---博客部分**
 
 https://www.bilibili.com/video/BV1qb41187fZ?p=28
----
1. blog文件夹中是vue实现的源码,如下指令当前文件夹终端运行
  * vue install
  * vue run server
2. springboot-blog文件夹是后端实现代码,数据库library,用户名root,密码root,表自动创建,监听端口8081,Idea 打开运行


**注意事项**  
  为了防止博客类型渲染有问题,创建好数据库表后,使用如下查询语句生成表内容  
  ```
  BEGIN;
  INSERT INTO `t_type` VALUES (1, 'Vue.js');
  INSERT INTO `t_type` VALUES (2, 'Node.js');
  INSERT INTO `t_type` VALUES (3, 'React.js');
  INSERT INTO `t_type` VALUES (4, 'Angular4.js');
  COMMIT;
  ```
