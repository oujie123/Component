###### 1. 模块间通信的方式

* 使用eventbus的方式，缺点：EventBean维护成本比较高，不好去管理
* 使用广播的方式，缺点：不好管理，都统一发出去了
* 使用隐式意图的方式，缺点：在AndroidManifes.xml里面配置xml写的太多了
* 使用类加载的方式，可取，但是缺点，容易写错类名，但是可以克服
* 使用全局Map的方式，缺点：要注册很多对象



![1](E:\programe\AndroidProjectGithub\Component\doc\1.jpg)



![2](E:\programe\AndroidProjectGithub\Component\doc\2.jpg)