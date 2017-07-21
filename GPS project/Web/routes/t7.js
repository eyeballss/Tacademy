/**
 * 커넥션 풀을 이용하여 쿼리 처리
 * 커넥션을 획득 - 쿼리 수행 - 커넥션 반납 순.
 */

var pool = require('./t6');

//모든 점포를 가져오라!
exports.getAllStores = function(cb){
	// 1. 커넥션을 획득 
	pool.acquire(function(err, connection){ // 커넥션을 받는 메소드. pool이 갖고 있는 컨넥션 중 하나를 가져오세욤
		if(err){
			console.log("커넥션 획득 오류", err);
			return cb(err, null);
		}
		
		// 2. 쿼리 수행 
		var sql = "select * from stores ";//where `type`='COFFEEBEAN'";
		connection.query(sql, function(err1, rows){ //err1: 에러, rows: 결과

			// 3. 커넥션 반납 순.
			pool.release(connection); //connection을 반납함. 개발자한테 주는게 아니고 자기가 다시 가져가는 거임.
			
//			if(err1){
//				console.log("query 오류", err1);
//				return;
//			}
			
//			console.log( rows ); //쿼리의 결과문을 찍어보자.
			return cb(err1, rows);

		});
		
	}); //pool.acquire cb
}

// localhost:3000/coffee?t=starbucks
// get으로 뽑기 위해서는 => req.query.t
// post로 뽑기 위해서는 => req.body.t
exports.getAllStoresEx = function(query, cb){
	pool.acquire(function(err, connection){
		if(err){
			console.log("커넥션 획득 오류", err);
			return cb(err, null);
		}
		connection.query("select * from stores where `type`=?;",
				[query.t],
				function(err1, rows){
			pool.release(connection);
			return cb(err1, rows);
		});
	});
}

//type:브렌드, lat:위도, lng:경도, dist:거리
exports.getSpecificStore = function( param, cb ){
	pool.acquire(function(err, connection){
		if( err ){ cb( err, null ); return; }		
		connection.query("select * from " +
                "(" +
                "select " +
                "*, (" +
                "6371 * " +
                "ACOS(" +
                "COS(RADIANS(?))*" +
                "         COS(RADIANS(lat))*" +
                "         COS(RADIANS(lng)-RADIANS(?)) +" +
                "         SIN(RADIANS(?))*" +
                "         SIN(RADIANS(lat))         " +
                ")" +
                ") as dist " +
                "from stores where `type`=?" +
                ") as a where dist<=? order by dist asc;", 
				[ param.lat, param.lng, param.lat, param.type, param.dist ],
				function(err1, rows){
					pool.release(connection);
					cb( err1, rows );		
		});
	});	
}
