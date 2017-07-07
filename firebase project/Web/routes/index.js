/*
 * GET home page.
 */
var FCM = require('fcm').FCM;

var apiKey = 'secret'
var fcm = new FCM(apiKey);

exports.index = function(req, res) {
	res.render('index', {
		title : 'Eyeballs Push Server'
	});
};

exports.sendPush = function(req, res) {
	console.log("Token : " + req.body.token);
	console.log("Message : " + req.body.msg);

	//메세지 구성
	var message = {
		//push 받고자 하는 유저의 id, 즉 유저로부터 받은 그들의 토큰.
		registration_id : req.body.token, // required
		//못 받았을 때 서버가 저장하고 있다가 보내주기 위해 임의의 값을 갖고 있다고 함
		collapse_key : ''+ Math.floor(Math.random()*1000),
		//보내는 데이터
		data : JSON.stringify({body:req.body.msg, title:req.body.title})
	};

	//fcm.send로 보내는 곳의 목적지는 구글 파이어베이스 서버. 구글이 알아서 유저들에게 보내고 결과값은 우리에게 줄꺼야.
	//그리고 뒤에 콜백함수가 실행되겠지.	
	fcm.send(message, function(err, messageId) {
		//한글이 깨지기 때문에 넣었음
		res.writeHead(200, {'Content-Type':'text/html;charset=utf-8'});
		if (err) {
//			console.log("Something has gone wrong!");
			res.end("<script>alert('Sending Failed : "+err+"');" +
					"history.back();</script>");
		} else {
//			console.log("Sent with message ID: ", messageId);
			res.end("<script>alert('Sending succeeded : "+messageId+"');" +
			"history.back();</script>");
		}
	});
};
