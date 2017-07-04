
/*
 * GET users listing.
 */



	var mysql = require("mysql");
	var query ={
			host : 'mysqlfortest.ci8xodqwvnwq.us-east-2.rds.amazonaws.com',
			user : 'root',
			password : 'rootroot',
			database : 'MySQLForTest',
		};
	
	
exports.list = function(req, res){
  res.send("respond with a resource");
};

exports.join = function(req, res){

	console.log("data from client : "+req.body());
	var connection = mysql.createConnection(query);

	connection.connect(function(){
    	connection.query("insert into users (userEmail, userPwd, hp, os, model, `uuid`, token) " +
    			"values (?, ?, ?, ?, ?, ? ,?);", 
    			[req.body.user.email, 
    				req.body.user.password , 
    				req.body.user.number, 
    				req.body.user.os,
    				req.body.user.model, 
    				req.body.user.uuid, 
    				req.body.user.token,  ], 
    			/*
    			 *      int id;
     String email;
     String password;
     String date;
     String number;
     String os;
     String model;
     String uuid;
    			 */
    		function(error, results, fields) {
    		if (error)
    			throw error;
//    		console.log('result : \n', results);
    		res.send(JSON.stringify(results));
    		
    		
    		connection.end();
    		if(error){//db가 잘못 되었을 때.
    			res.send('{"code":-1, "msg": "'+error+'"}');
    		}else{//db가 잘 되긴 했는데..
    			//적용이 되어을 때
    			if(results.affectedRows>0){
        			res.send('{"code":-1, "msg": "successed"}');
    			}else{//적용이 안 되었을 때
        			res.send('{"code":-1, "msg": "failed"}');
    			}
    		}
    	});
	});
	
}

exports.sqlConnect = function (req, res) {

	
//	var connection = mysql.createConnection({
//		host : 'localhost',
//		user : 'root',
//		password : 'root',
//		database : 'db',
//	});

	var connection = mysql.createConnection(query);
	
	connection.connect(function(){
    	connection.query("SELECT * from users where email=? and password=?;", 
    			[req.query.email, req.query.password ], 
    		function(error, results, fields) {
    		if (error)
    			throw error;
//    		console.log('result : \n', results);
    		res.send(JSON.stringify(results));
    		
    		
    		connection.end();
    	});
	});



};
