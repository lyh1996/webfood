--创建库，表，约束，过程，用户，权限等脚本
	create database webfoods;
	use webfood;
	 
	create table resadmin(
		raid int primary key auto_increment,
		raname varchar(50),
		rapwd varchar(50)
	)engine=MYISAM character set utf8;
	  
	create table resuser(
		userid int primary key auto_increment,
		username varchar(50),
		pwd varchar(50),
		email varchar(500)
	)engine=MYISAM character set utf8;
	
	--normprice :原价 realprice :现价 description ：简介 detail 详细的
	
	create table resfood(
		fid int primary key auto_increment,
		fname varchar(50),
		normprice numeric(8,2),
		realprice numeric(8,2),
		detail varchar(2000),
		fphoto varchar(1000)
	) engine=MYISAM character set utf8;
	
	--订单表 :roid :订单号：   userid :外键，下单的用户的编号  ordertime ：下单的时间  uname :收货人的姓名 deliverytype:收货的方式 payment :支付的方式  ps:附言
	drop table resorder
	create table resorder(
		roid int primary key auto_increment,
		userid int ,
		address varchar(500),
		tel varchar(100),
		ordertime date,
		deliverytime date,
		ps varchar(2000),
		status int 
	)engine=MYISAM character set utf8;
	
	--订单表的下单人与用户表中的客户端号又主外键的关系
	alter table resorder
		add constraint fk_resorder 
			foreign key (userid) references resuser(userid);
			
	--dealprice :成交价  roid 订单号 fid :商品号  num :数量
	
	create table resorderitem(
		roiid int primary key auto_increment,
		roid int ,
		fid int ,
		dealprice numeric(8,2),
		num int 
	)engine=MYISAM character set utf8;
	
	alter table resorderitem
		add constraint fk_resorderitem_roid
			foreign key(roid) references resorder(roid);
			
	alter table resorderitem
		add constraint fk_tbl_res_fid 
			foreign key(fid) references resfood(fid);
			
		//分页查询 
		SELECT * FROM resfood WHERE fid<1000 ORDER BY fid asc LIMIT 5;//上一页
			select * from resadmin;
			select * from resfood;
			select * from resuser;  
			select * from resorder;
			select * from resorderitem;
			select sum(num) from resorderitem where fid=3
	commit ;
	
	
	create table favorable(
		f_id int primary key auto_increment,
		f_name varchar(50),
		man varchar(50),
		jian varchar(50)
	)engine=MYISAM character set utf8;
	insert into favorable(f_name,man,jian) values('优惠多多，满100立减5','100','5')
	select * from favorable