use webfood
select * from resadmin;
insert into resadmin(raname,rapwd) values('a','0cc175b9c0f1b6a831c399e269772661');
insert into resadmin(raname,rapwd) values('b','0cc175b9c0f1b6a831c399e269772661');
 delete   from resadmin where raname='b'
--用户表初始化数据
insert into resuser(username,pwd ,email) values('a','0cc175b9c0f1b6a831c399e269772661','a@163.com');
insert into resuser(username,pwd ,email) values('b','0cc175b9c0f1b6a831c399e269772661','b@163.com');
 
--插入菜
insert into resfood (fname,normprice,realprice,detail,fphoto) values('素炒莴笋丝','22.0','20.0','营养丰富','500008.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('蛋炒饭','22.0','20.0','营养丰富','500022.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('酸辣鱼','42.0','40.0','营养丰富','500023.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('卤粉','12.0','10.0','营养丰富','500024.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('西红柿蛋汤','12.0','10.0','营养丰富','500025.jpg');

select * from resfood where fname like '素%'

insert into resfood (fname,normprice,realprice,detail,fphoto) values('炖鸡','102.0','100.0','营养丰富','500026.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('炒鸡','12.0','10.0','营养丰富','500033.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('炒饭','12.0','10.0','营养丰富','500034.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('手撕前女友','12.0','10.0','营养丰富','500035.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('面条','12.0','10.0','营养丰富','500036.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('端菜','12.0','10.0','营养丰富','500038.jpg');
insert into resfood (fname,normprice,realprice,detail,fphoto) values('酸豆角','12.0','10.0','营养丰富','500041.jpg');

--不测试：生成一条订单， a用户订了 1号菜一份 以及2号菜2份
insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) values(1,'湖南省衡阳市'.'13878789999',now(),now(),'送餐上门',0);

insert into resorderitem(roid ,fid,dealprice,num) values(1,1,20,1);

insert into resorderitem(roid ,fid,dealprice,num) values(1,2,20,1);
--注意以上三条语句要求在事物中处理

commit;

