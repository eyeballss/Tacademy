/*
 * GET home page.
 */
var FCM = require('fcm').FCM;

var apiKey = 'secret';
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
		//push 받고???�는 ?��???id, �??��?로�???받�? 그들???�큰.
		registration_id : req.body.token, // required
		//�?받았?????�버가 ?�?�하�??�다가 보내주기 ?�해 ?�의??값을 갖고 ?�다�???
		collapse_key : ''+ Math.floor(Math.random()*1000),
		//보내???�이??
		data : JSON.stringify({body:req.body.msg, title:req.body.title})
	};

	//fcm.send�?보내??곳의 목적지??구�? ?�이?�베?�스 ?�버. 구�????�아???��??�에�?보내�?결과값�? ?�리?�게 줄꺼??
	//그리�??�에 콜백?�수가 ?�행?�겠지.	
	fcm.send(message, function(err, messageId) {
		//?��???깨�?�??�문???�었??
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
