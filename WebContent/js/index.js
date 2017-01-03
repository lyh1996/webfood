var firstPage;
var favorable;
$(function() {
	// 页面加载所要做的判断

	$.ajax({
		url : "index.action",
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			if (data.code4 == 1) {  
				$("#loginimg").html(data.obj4[0].f_name);
				favorable=data.obj4[0];
			}
			if (data.code == 1) {
				showAll(data.obj);
			}
			if (data.code2 == 1) {
				showbag(data.obj2);
			}
			if (data.code3 == 1) {
				haslogined = true;

			} else {
				haslogined = false;

			}
			checklogin(data.obj3);
		}
	});
	// 

	// 查找所有food
	/*
	 * $.ajax({ url : "resfood.action", data : "op=findAll", type : "POST",
	 * dataType : "JSON", success : function(data) { if (data.code == 0) {
	 * alert("服务器错误" + data.msg); } else {
	 * 
	 * showAll(data.obj); } } }); //查找购物车 $.ajax({ url : "resorder.action", data :
	 * "op=findcartop", type : "POST", dataType : "JSON", success :
	 * function(data) { if (data.code == 0) { //alert("服务器错误" + data.msg); }
	 * else { showbag(data.obj); } } });
	 * 
	 * //判断是否登入 $.ajax({ url : "resuser.action", data : "op=islogin", type :
	 * "POST", dataType : "JSON", success : function(data) { if (data.code == 1) {
	 * haslogined=true; } else{ haslogined=false; } checklogin( data.obj ); }
	 * });
	 */
	// 删除所有food页面已加载完就清空购物车绑定清空事件
	$("#delall").click(function() {
		$.ajax({
			url : "resorder.action",
			data : "op=delall",
			type : "POST",
			dataType : "JSON",
			success : function(data) {
				if (data.code == 0) {
					alert("服务器错误" + data.msg);
				} else {

					showbag(data.obj);
				}
			}
		});
	});
	// 关闭登入
	$("#showlogin").click(showLogin);
	$("#unshow").click(unshow);
	// 关闭订单
	$("#unshowinfo").click(unshowinfo);
	// 验证码刷新
	$("#yzm_img").click(function() {
		$(this).attr("src", "verifyCode.action?" + new Date().getTime())
	})
	//浏览记录
	refreshlook();//读取cookie
	$("#showlook").click( showlookdiv);//给开关绑定事件
	//refreshlook(redisHistroy);
	//购物车收起
	$("#car").click(function(){
		$("#carbag").toggle();
	});

	// 结算按钮
	$("#btn").click(
			function() {
				$.ajax({
					url : "resuser.action",
					// data:$("#myform").serialize(),--会自动调用toJsonString方法
					data : "op=" + $("#op").val() + "&username="
							+ $("#username").val() + "&pwd=" + $("#pwd").val()
							+ "&valcode=" + $("#yzm").val(),
					type : "POST",
					dataType : "JSON",
					success : function(data) {
						if (data.code == 1) {
							unshow();
							haslogined = true;
							alert("登入成功");
							checklogin(data.obj);// 检测登入

						} else {
							alert("登入失败,原因:" + data.errorMsg);
							haslogined = false;
						}
					}
				});
			});
	// 提交按钮事件
	$("#submit").click(
			function() {
				$.ajax({
					url : "resorder.action",
					data : "op=comfirmorder&address=" + $("#address").val()
							+ "&tel=" + $("#tel").val() + "&deliverytime="
							+ $("#deliverytime").val() + "&ps="
							+ $("#ps").val(),
					dataType : "JSON",
					type : "POST",
					success : function(data) {
						if (data.code == 1) {
							unshowinfo();
							alert("购买成功");
						} else {
							alert("购买失败");
						}
					}
				});
			});

	// 点击下一页
	$("#nextPage").click(function() {
		thisPage = parseInt($("#ThisPage").text());
		thisPage += 1;
		$("#ThisPage").html(thisPage);
		thisPage = parseInt($("#ThisPage").text());
		if (thisPage > totalPage) {
			$("#ThisPage").html(totalPage);
			return;
		} else {
			$("li").remove();
			// document.removeClass("allfoods");// 移除html
			if( haslogined ){
				reidsrefreshlook(redisHistroy);
			}else{
				refreshlook();//读取cookie
			}
			
			
			showAll(allfoodsarr);

		}
	});
	// 点击上一页
	$("#upPage").click(function() {
		thisPage -= 1;
		$("#ThisPage").html(thisPage);
		thisPage = parseInt($("#ThisPage").text());
		if (thisPage <= 0) {
			$("#ThisPage").html(1);
			return;
		} else {
			$("li").remove();
			// document.removeClass("allfoods");// 移除html
			if( haslogined ){
				reidsrefreshlook(redisHistroy);
			}else{
				refreshlook();//读取cookie
			}
			showAll(allfoodsarr);

		}
	});
	// 首页
	$("#firstPage").click(function() {
		$("#ThisPage").html(1);
		$("li").remove();
		// document.removeClass("allfoods");// 移除html
		if( haslogined ){
			reidsrefreshlook(redisHistroy);
		}else{
			refreshlook();//读取cookie
		}
		showAll(allfoodsarr);
	});
	// 末页
	$("#endPage").click(function() {
		$("#ThisPage").html(totalPage);
		$("li").remove();
		// document.removeClass("allfoods");// 移除html
		if( haslogined ){
			reidsrefreshlook(redisHistroy);
		}else{
			refreshlook();//读取cookie
		}
		showAll(allfoodsarr);
	});
});
// 检测用户登录
function checklogin(obj) {
	if (haslogined) {
		haslogin(obj);
		return;
	} else {
		$("#showlogin").show();
		$("#exitspan").hide();
	}
}
// 显示登入的用户的信息
function haslogin(obj) {
	userid=obj.userid;
	$("#showlogin").hide();
	// yc.$("exitspan").style.display="block";
	$("#exitspan")
			.show()
			.html(
					"欢迎您"
							+ obj.username
							+ ",<a href='javascript:void()' onclick='javascript:clickexit()'>退出</a>");
	$.ajax({
		url : "Scan.action",
		type : "POST",
		data:"op=histroy&userid="+userid,
		dataType : "JSON",
		success : function(data) {
			if (data.code == 1) {
				//alert("成功");
				redisHistroy=data.obj;
				reidsrefreshlook(redisHistroy);
			}
		}

	});
	

}
// 用户退出
function clickexit(obj) {
	$.ajax({
		url : "resuser.action?op=logout",
		type : "GET",
		dataType : "JSON",
		success : function(data) {
			if (data.code == 1) {
				$("#showlogin").show();
				$("#exitspan").hide();
				haslogined = false;
				refreshlook();//读取cookie
			}
		}

	});

}

var redisHistroy;
var userid;
var haslogined = false;// 用来判断用户是否登录
var buyfoodidarr = [];
var allfoodsarr;
var thisPage
var totalPage;
function showAll(obj) {// 显示所有的商品
	allfoodsarr = obj;
	thisPage = parseInt($("#ThisPage").text());
	if (allfoodsarr.length % 5 == 0) {
		totalPage = allfoodsarr.length / 5;
	} else {
		totalPage = parseInt(allfoodsarr.length / 5) + 1;
	}
	$("#totalPage").html(totalPage);
	for (var i = (thisPage - 1) * 5; i < thisPage * 5; i++) {

		if (i == allfoodsarr.length) {
			return;
		}
		var onefood = allfoodsarr[i];
		var li = document.createElement("li");

		// 插入菜名
		var title = document.createElement("h3");
		title.innerHTML = onefood.fname;
		title.id = "fid" + onefood.fid;
		li.appendChild(title);

		// 插入菜单详情的div
		var fooddesc = document.createElement("div");
		yc.addClassName(fooddesc, "fooddesc");
		fooddesc.id = "fooddesc" + onefood.fid;
		var foodimg = document.createElement("img");
		foodimg.src = "../../uploadFile/" + onefood.fphoto;
		yc.addClassName(foodimg, "foodimg");
		fooddesc.appendChild(foodimg);
		var art = document.createElement("article");
		fooddesc.appendChild(art);
		yc.addClassName(art, "foodprice");

		var detail = document.createElement("p");
		if (onefood.detail) {
			detail.innerHTML = "菜品描述：" + onefood.detail;
		} else {
			detail.innerHTML = "菜品描述：无";
		}
		art.appendChild(detail);

		var normprice = document.createElement("p");
		yc.addClassName(normprice, 'normprice');
		normprice.innerHTML = "原价：￥" + onefood.normprice;
		art.appendChild(normprice);

		var realprice = document.createElement("p");
		yc.addClassName(realprice, 'realprice');
		realprice.innerHTML = "特价：￥" + onefood.realprice;
		art.appendChild(realprice);

		var buybtn = document.createElement("a");
		buybtn.innerHTML = "加入购物车";
		yc.addClassName(buybtn, "buybtn");
		art.appendChild(buybtn);
		var monthcountp = document.createElement("p");
		yc.addClassName(monthcountp, "monthcount");
		art.appendChild(monthcountp);
		fooddesc.style.display = "none";
		li.appendChild(fooddesc);

		document.getElementById("allfoods").appendChild(li);

		(function(index, id) {
			yc.addEvent(title, "click", function() {
				$.ajax({
					url : "Scan.action",
					type : "POST",
					data:"op=monthCount&fid="+allfoodsarr[index].fid,
					dataType : "JSON",
					success : function(data) {
						if (data.code == 1) {
							if(data.obj[0]==null){
								$(".monthcount").html( "当月销售量：0");
							}else{
								$(".monthcount").html( "当月销售量："+data.obj[0]);
							}
							
						}
					}

				});
				//monthcount=5;
				
				showdescs(id);
				// 存取浏览记录；未登入用户用cookie存取，已经登入的用户从cookie中拿到数据在存取到redis
				if(haslogined==false){
					if (Cookies.get(allfoodsarr[index].fid)) {
						Cookies.del(allfoodsarr[index].fid);
						Cookies.set(allfoodsarr[index].fid,
								encodeURI(allfoodsarr[index].fname), 30*60 * 24);
					} else {
						Cookies.set(allfoodsarr[index].fid,
								encodeURI(allfoodsarr[index].fname), 30*60 * 24);
					}
					refreshlook();
				}else{
					$.ajax({
						url : "Scan.action",
						type : "POST",
						data:"op=histroy&userid="+userid+"&data="+allfoodsarr[index].fid+"="+encodeURI(allfoodsarr[index].fname),
						dataType : "JSON",
						success : function(data) {
							if (data.code == 1) {
								//alert("成功");
								//redisHistroy=data.obj;
								reidsrefreshlook(data.obj);
							}
						}

					});
				}
				

			});
		})(i, onefood.fid);

		// 购物车
		(function(index) {
			$(buybtn).click(function() {

				var url = "resorder.action?num=1&op=order&fid=" + index;
				$.ajax({
					url : url,
					type : "GET",
					dataType : "JSON",
					success : function(data) {
						if (data.code == 1) {
							alert("下单成功");
							showbag(data.obj);
						} else {

							alert("下单失败");
						}

					}
				});
			});

		})(onefood.fid);

	}
}
// cookie浏览记录
function refreshlook() {// 显示用户浏览记录
	var cookiearr=Cookies.getall();
	yc.$("ull").innerHTML = "";
	var flag = 0;
	if (!document.cookie)
		return;
	for (var i = cookiearr.length - 1; i >= 0; i--) {
		var lii = document.createElement("li");
		yc.$('ull').appendChild(lii);
		//var matcharr = /([\u0391-\uFFE5]+)=(\d*)/.exec(cookiearr[i]);
		var matcharr=cookiearr[i].split("=");
		lii.innerHTML =decodeURI( matcharr[1]);
		flag++;

		(function(index) {
			yc.addEvent(lii, "click", function() {
				showdescs( parseInt(index) );
				window.scrollTo(0, 50 * index);
			});
		})(matcharr[0]);

		if (flag >= 9) {
			break;
		}
	}
}
//redis浏览记录
 
function reidsrefreshlook(obj) {// 显示用户浏览记录 
	yc.$("ull").innerHTML = "";
	var flag = 0;
	for (var i = 0;i<=obj.length - 1; i++) {
		 
		var lii = document.createElement("li");
		yc.$('ull').appendChild(lii);
		//var matcharr = /([\u0391-\uFFE5]+)=(\d*)/.exec(cookiearr[i]);
		var matcharr=obj[i].split("=");
		lii.innerHTML =decodeURI( matcharr[1]);
		flag++;

		(function(index) {
			yc.addEvent(lii, "click", function() {
				showdescs( parseInt(index) );
				window.scrollTo(0, 50 * index);
			});
		})(matcharr[0]);

		if (flag >= 10) {
			break;
		}
	}
}

function showlookdiv(){
	if(yc.hasClassName("look","lookblock2")){
		yc.addClassName("look","lookblock3");
		yc.addClassName("showlook","showlookblock3");
		var removestr='yc.removeClassName("look","lookblock1");yc.removeClassName("showlook","showlookblock1");';
		removestr+='yc.removeClassName("look","lookblock2");yc.removeClassName("showlook","showlookblock2");'
		removestr+='yc.removeClassName("look","lookblock3");yc.removeClassName("showlook","showlookblock3");'
		setTimeout(removestr,300);
	}else{
		yc.addClassName("look","lookblock1");
		yc.addClassName("showlook","showlookblock1");
		setTimeout('yc.addClassName("look","lookblock2");yc.addClassName("showlook","showlookblock2");',300);
		// yc.addClassName("look","lookblock2");
		// yc.addClassName("showlook","showlookblock2");
	}
}

// 显示购物车数据
// cart指的是购物车中的商品的数据：{”编号“：{resfood}，”编号“：{resfood}}

function showbag(cart) {
	var count = 0;// 统计总共有几个商品的条目

	for ( var key in cart) {
		if (cart.hasOwnProperty(key)) {
			count++;
		}
	}
	// 取出显示购物车信息的table
	var bag = $("#bagcontainer");
	// 如果购物车为空的话
	if (count <= 0) {
		removebuy();
		calprice(cart);
		bag.html("<tr><td><p>购物车是空的，赶紧选购吧</p></td></tr>");
		$(".carbag").css({
			"bottom" : "50px"
		});
		bag.css({
			"height" : "260px"
		});
		return;
	}
	// TODO:不为空的时候，则执行
	calprice(cart);
	addbuy();
	bag.html("");
	buyfoodidarr = [];
	var theight = 0;
	var str = "";
	for ( var key in cart) {
		if (cart.hasOwnProperty(key)) {
			var buyfood = cart[key];
			buyfoodidarr.push(key);

			str += "<tr>";
			str += "<td width='140px'>";
			str += buyfood.fname;
			str += "</td>";
			str += "<td width='130px' class='editfoodnum'>"
			str += "<span >" + buyfood.num + "</span>";
			str += "<b class='subfoodx' onclick='removefood(" + key
					+ ")'>X</b>";
			str += "<input type='button' value='+' onclick='editfood(1," + key
					+ ")'/>";
			str += "<input type='button' value='-' onclick='editfood(-1," + key
					+ ")'/>";
			str += "</td>";
			str += "<td width='80px'style='color:#F69C30'>";
			str += "￥" + (buyfood.num * buyfood.realprice);
			str += "</td>";
			str += "</tr>";
			theight++;
		}
	}
	bag.html(str);
	$(".carbag").css({
		"bottom" : "50px"
	});
	bag.height(theight * 40);

}
// 清除一项商品
function removefood(id) {
	$.ajax({
		url : "resorder.action?op=delorder&fid=" + id,
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			if (data.code == 1) {
				showbag(data.obj);
			} else {
				alert("清除失败！");
			}
		}
	});
}

// 修改数量
function editfood(num, id) {
	$.ajax({
		url : "resorder.action?op=order&num=" + num + "&fid=" + id,
		type : "POST",
		dataType : "JSON",
		success : function(data) {
			if (data.code == 1) {
				showbag(data.obj);
			} else {

				alert("修改失败！");
			}
		}
	});
}
// 计算价格
function calprice(cart) {
	var price = 0;
	for ( var property in cart) {
		if (cart.hasOwnProperty(property)) {
			var food = cart[property];
			price += food.num * food.realprice;
		}
	}
	if(price>=parseInt(favorable.man)){
		price-=parseInt(favorable.jian);
	}
	if (yc.$("pricetext")) {
		$("#pricetext").html("￥" + price);
	} else {
		// var pricetext=document.createElement("p");
		// pricetext.id="pricetext";
		// pricetext.innerHTML="￥"+price;
		// yc.prependChild("car",pricetext);
		$("#car").html("<p id='pricetext'>￥" + price + "</p>")
	}

}
// 移除购买
function removebuy() {
	// yc.$("foodcount").innerHTML="购物车是空的";
	$("#foodcount").html("购物车是空的");
	// yc.removeClassName("foodcount","gotobuy");
	$("#foodcount").removeClass("gotobuy");// 移除html
	// yc.removeEvent("foodcount","click",tobuy);
	$("#foodcount").off("click");// 去除事件
}
// 去结算
function addbuy() {
	// yc.$("foodcount").innerHTML="去结算&gt;";
	$("#foodcount").html("去结算&gt;");
	// yc.addClassName("foodcount","gotobuy");
	$("#foodcount").addClass("gotobuy");
	// yc.addEvent("foodcount","click",tobuy);
	$("#foodcount").click(tobuy);
}
// 但结算的时候，要判断用户是否已经登入，没有则跳转登入窗口
function tobuy() {
	if (haslogined) {
		showinfo();
	} else {
		showLogin();
	}
}

function show() {

	yc.$('login').style.display = "block";
	// $("#login").show("fast");
	yc.$('mubu').style.display = "block";
	// $("#mubu").show("fast");
}
function unshow() {
	// yc.$('login').style.display = "none";
	$("#login").hide();
	// yc.$('mubu').style.display = "none";
	$("#mubu").hide();
}
// 关闭购物车地址显示框
function unshowinfo() {
	// yc.$('myinfo').style.display = "none";
	$("#myinfo").hide();
	// yc.$('mubu').style.display="none";
	$("#mubu").hide();
}

// 显示登入
function showLogin() {
	// yc.$('login').style.display = "block";
	$("#login").show();
	// yc.$('mubu').style.display = "block";
	$("#mubu").show();
	$("#yzm_img").attr("src", "verifyCode.action?" + new Date().getTime());
	$("#yzm").val("");

}
function showinfo() {
	yc.$('myinfo').style.display = "block";
	// $("#myinfo").show("fast");
	yc.$('mubu').style.display = "block";
	// $("#mubu").show("fast");
}

function showdescs(index) {// 显示菜品的详情
	var allfoods = yc.$("allfoods");
	var titles = allfoods.getElementsByTagName("h3");
	var title = yc.$("fid" + index);
	var descs = allfoods.getElementsByTagName("div");
	var desc = yc.$("fooddesc" + index);
	for (var j = 0; j < descs.length; j++) {
		if (descs[j] == desc)
			continue;
		descs[j].style.display = "none";

		if (index != allfoodsarr[allfoodsarr.length - 1].fid) {
			yc.removeClassName("fid" + allfoodsarr[allfoodsarr.length - 1].fid,
					"noradius");
		}
	}
	yc.toggleDisplay("fooddesc" + index, "block");
	if (index == allfoodsarr[allfoodsarr.length - 1].fid) {
		if (yc.hasClassName(title, "noradius")) {
			yc.removeClassName(title, "noradius");
		} else {
			yc.addClassName(title, "noradius");
		}
	}
}
