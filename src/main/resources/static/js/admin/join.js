document.addEventListener('DOMContentLoaded', function() {
	const clnt = document.getElementById("clnt_tmp");
	console.log(clnt);

	clnt.addEventListener("change", UpdateClnt)

});


function UpdateClnt(e) {
	console.log(e);
	var evnt = e.currentTarget;
	console.log(evnt);

	var clientList = evnt.dataset.clntlist;
	console.log(clientList);

	var text = evnt.value;
	console.log(text);

	for(var i in clientList) {
		if(clientList[i].clntCd == text) {
			console.log(clientList[i].clntNm);
		}
	}



}
