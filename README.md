# springboot2.x 集成 sa-token 并
用来测试一些小玩意儿，自娱自乐

## 定时任务
使用springboot自带的spring task来实现定时任务，
值得注意的是，这种实现定时任务的方式为单线程，
如果定时任务很多的情况下，一旦某一个定时任务阻塞，
将会阻塞之后所有的定时任务，故需要开启异步执行，
即多线程，配置异步执行的方式有两种，
分别在AsyncTaskConfig1和AsyncTaskConfig2中体现。

[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)
