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

	//ë©”ì„¸ì§€ êµ¬ì„±
	var message = {
		//push ë°›ê³ ???˜ëŠ” ? ì???id, ì¦?? ì?ë¡œë???ë°›ì? ê·¸ë“¤??? í°.
		registration_id : req.body.token, // required
		//ëª?ë°›ì•˜?????œë²„ê°€ ?€?¥í•˜ê³??ˆë‹¤ê°€ ë³´ë‚´ì£¼ê¸° ?„í•´ ?„ì˜??ê°’ì„ ê°–ê³  ?ˆë‹¤ê³???
		collapse_key : ''+ Math.floor(Math.random()*1000),
		//ë³´ë‚´???°ì´??
		data : JSON.stringify({body:req.body.msg, title:req.body.title})
	};

	//fcm.sendë¡?ë³´ë‚´??ê³³ì˜ ëª©ì ì§€??êµ¬ê? ?Œì´?´ë² ?´ìŠ¤ ?œë²„. êµ¬ê????Œì•„??? ì??¤ì—ê²?ë³´ë‚´ê³?ê²°ê³¼ê°’ì? ?°ë¦¬?ê²Œ ì¤„êº¼??
	//ê·¸ë¦¬ê³??¤ì— ì½œë°±?¨ìˆ˜ê°€ ?¤í–‰?˜ê² ì§€.	
	fcm.send(message, function(err, messageId) {
		//?œê???ê¹¨ì?ê¸??Œë¬¸???£ì—ˆ??
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
