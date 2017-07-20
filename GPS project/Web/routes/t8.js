/**
 * 
 */

var sql = require('./t7.js');

exports.getAllStores = function(req, res){
	
	sql.getAllStores(function(err, rows){
//		if(err){
//			console.log('오류 발생', err);
//			return;
//		}
		res.writeHead(200, {'Content-Type': 'text/plain; charset=utf8'});
	    res.end(JSON.stringify(rows));
	});
	
};

exports.getAllStoresEx = function(req, res){
	sql.getAllStoresEx(req.query, function(err, rows){
//		if(err){
//			console.log('오류 발생', err);
//			return;
//		}
		res.writeHead(200, {'Content-Type': 'text/plain; charset=utf8'});
	    res.end(JSON.stringify(rows));
	});
}