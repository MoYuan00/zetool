#### 单线程运行：拷贝规模：n = 1000000

Spring BeanUtils used time:13894
```
{"name":"a new name","time":"Jan 2, 2021 3:08:12 PM","count":2,"address":["1","1","new"],"fathers":[null,null,{"name":"father","tags":["new tag",null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123","2"],"prices":{},"friendListNames":[["new friend name"]]}
{"name":"A","time":"Jan 2, 2021 3:08:12 PM","count":1,"address":["1","1","new"],"fathers":[null,null,{"name":"father","tags":["new tag",null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123","2"],"prices":{},"friendListNames":[["new friend name"]]}
```
cglib BeanCopier used time:356
```
{"name":"a new name","time":"Jan 2, 2021 3:08:26 PM","count":2,"address":["1","1","new"],"fathers":[null,null,{"name":"father","tags":["new tag",null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123","2"],"prices":{},"friendListNames":[["new friend name"]]}
{"name":"A","time":"Jan 2, 2021 3:08:27 PM","count":1,"address":["1","1","1"],"fathers":[null,null,{"name":"father","tags":[null,null,"tag"],"homeTags":[[[null,"tag"],null]]}],"friends":[null,null,"123"],"prices":{},"friendListNames":[]}
```
1used time:921
```
{"name":"a new name","time":"Jan 2, 2021 3:08:27 PM","count":2,"address":["1","1","new"],"fathers":[null,null,{"name":"father","tags":["new tag",null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123","2"],"prices":{},"friendListNames":[["new friend name"]]}
{"name":"A","time":"Jan 2, 2021 3:08:27 PM","count":1,"address":["1","1","new"],"fathers":[null,null,{"name":"father","tags":["new tag",null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123","2"],"prices":{},"friendListNames":[["new friend name"]]}
```
5used time:3655
```
{"name":"a new name","time":"Jan 2, 2021 3:08:27 PM","count":2,"address":["1","1","new"],"fathers":[null,null,{"name":"father","tags":["new tag",null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123","2"],"prices":{},"friendListNames":[["new friend name"]]}
{"name":"A","time":"Jan 2, 2021 3:08:27 PM","count":1,"address":["1","1","1"],"fathers":[null,null,{"name":"father","tags":[null,null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123"],"prices":{},"friendListNames":[]}
```
6used time:3213
```
{"name":"a new name","time":"Jan 2, 2021 3:08:31 PM","count":2,"address":["1","1","new"],"fathers":[null,null,{"name":"father","tags":["new tag",null,"tag"],"homeTags":[[["new home tag","tag"],null]]}],"friends":[null,null,"123","2"],"prices":{},"friendListNames":[["new friend name"]]}
{"name":"A","time":"Jan 2, 2021 3:08:31 PM","count":1,"address":["1","1","1"],"fathers":[null,null,{"name":"father","tags":[null,null,"tag"],"homeTags":[[[null,"tag"],null]]}],"friends":[null,null,"123"],"prices":{},"friendListNames":[]}
```
### 多线程运行：线程数：5000

Spring BeanUtils used time:4144

Cglib BeanCopier used time:464

1used time:1178

5used time:1910

6used time:1715