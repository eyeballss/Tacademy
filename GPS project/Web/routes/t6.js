/**
 * 
 */

var Pool = require('generic-pool').Pool;
var mysql = require('mysql'); //여기서만 mysql을 땡긴다.

var con_pool = new Pool({
	name : 'mysql connection pool', //커넥션 이름
	create : function( cb ){ //여기서 cb란 
		var query ={
				host : '...',
				user : '..',
				password : '..',
				database : '..',
			};
		var con = mysql.createConnection(query);
		con.connect(function(err){
			//성공 여부 값 및 커넥션 객체 자체를 콜백을 통해 Pool 객체에게 돌려줌.
			//
			cb(err, con);
		});
	}, //연결 작업
	destroy : function( conn ){
		conn.end();
	}, //해제 작업
	max : 15, //최대 커넥션 수
	min : 10, //평상시 최초 유지 커넥션 수
	log : false,//true, //로그르 볼 것이냐 말 것이냐
	idleTimeoutMillis : 1000*600 //아이들 상태로 변경되는 주기 시간
});

//서버 재 가동이나 임의 재가동시 커넥션을 닫아 줘야 함.
//process는 node가 제공하는 녀석
//종료 이벤트가 발생 한다면,
process.on('exit', function(){
	//drain으로 pool 객체를 닫음
	con_pool.drain(function(){
		con_pool.destroyAllNow();
	}); 
});

//코드 어디서라도 예외 상황이 발생 시 잡음.
//여기 한 군데만 넣어두면 전체 코드에 영향을 줌!
process.on('uncaughtException', function(err){
	console.log('uncaughtException 오류 : ', err);
});

//객체의 모듈화! 이렇게 객체를 지정해두면 다른 곳에서 끌어다 쓸 수 있다!
//객체 내부의 개별 멤버 변수나 멤버 메소드들을 모듈화하기에는 처리량이 많으니,
//객체로 싸서 객체 하나를 모듈화하면 객체의 모든 멤버가 모듈화 됨.
module.exports = con_pool;








