document.addEventListener('DOMContentLoaded', function() {
	const clnt = document.getElementById("clnt_tmp");
	console.log(clnt);



	clnt.addEventListener("change", UpdateClnt)

});


function UpdateClnt(e) {
	console.log(e);
	var evnt = e.currentTarget;
	console.log(evnt);




	var selected = document.querySelectorAll('option:checked')

    console.log(selected[1].label);

	var clntnm = document.getElementById("clnt_nm");
	clntnm.value = selected[1].label;

}
