/**
 * 
 */

var sql = require('./t7.js');

exports.getAllStores = function(req, res){
	
	sql.getAllStores(function(err, rows){
		res.writeHead(200, {'Content-Type': 'text/plain; charset=utf8'});
		if(err)	    res.end(JSON.stringify({code : -1, body : []})); //에러나면 빈 값을 보냄
		else 		res.end(JSON.stringify({code : 0, body : rows}));
	});
	
};

exports.getAllStoresEx = function(req, res){
	sql.getAllStoresEx(req.query, function(err, rows){
//		if(err){
//			console.log('오류 발생', err);
//			return;
//		}
		res.writeHead(200, {'Content-Type': 'text/plain; charset=utf8'});
		if(err)	    res.end(JSON.stringify({code : -1, body : []})); //에러나면 빈 값을 보냄
		else 		res.end(JSON.stringify({code : 0, body : rows}));
	});
}

//특정 브랜드 정보를 거리 안에 들어온 것만 가까운 순으로 가져옴.
exports.getSpecificStore = function(req, res){
	sql.getSpecificStore(req.body, function(err, rows){
//		if(err){
//			console.log('오류 발생', err);
//			return;
//		}
		res.set('Content-Type','text/plain; charset=utf8');
		if(err)	    res.json({code : -1, body : [], msg:err}); //에러나면 빈 값을 보냄
		else 		res.json({code : 0, body : rows});
	});
}
