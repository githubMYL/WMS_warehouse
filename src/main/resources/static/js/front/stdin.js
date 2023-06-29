document.addEventListener('DOMContentLoaded', function() {
	const clnt = document.getElementById("clnt_tmp");
	const item = document.getElementById("item_tmp");
	var itemCd = document.getElementById("item_cd");
	var amount = document.getElementById('amount');
	var normal = document.getElementById('normal');
	var fault = document.getElementById('fault');

	console.log(normal);

	normal.addEventListener("change", sum);
	fault.addEventListener("change", sum);

	item.addEventListener("change", UpdateItem);
	clnt.addEventListener("change", UpdateClnt);
});


function UpdateClnt(e) {
	var datalist = document.getElementById("clnt_tmp").list;
	console.log(datalist);
	var clntcd = document.getElementById("clnt_cd");
	console.log(clntcd);
	var itemsel = document.getElementById("item_sel");

	var item_options = itemsel.querySelectorAll('option');

	for(var j = 0; j < datalist.options.length; j++){
		if(document.getElementById("clnt_tmp").value == datalist.options[j].value) {
			clntcd.value = datalist.options[j].label;
			for(var l = 0; l < item_options.length; l++) {
				if(clntcd.value != item_options[l].dataset.itemclntcd){
					itemsel.remove(l);
				}
			}
		}
	}
}


function UpdateItem(e) {
	var evnt = e.currentTarget;
	var selected = document.querySelector("select#item_sel")
	var datalist = evnt.list;
	var itemcd = document.getElementById("item_cd");
	var wactrCd;

	for(var j = 0; j < datalist.options.length; j++){
		if(this.value == datalist.options[j].value) {
			itemcd.value = datalist.options[j].label;
			dataset = datalist.options[j].dataset;
		}
	}


	var loc_sel = document.getElementById("loc_sel");
	var options = loc_sel.querySelectorAll('option');
	for(var k = 0; k < options.length; k++) {
		console.log(dataset.itemwactrcd + ', ' + options[k].dataset.locwactrcd);
		if(dataset.itemwactrcd != options[k].dataset.locwactrcd) {
			loc_sel.remove(k);
		}
	}

};

function sum() {
	if(!isNaN(parseInt(normal.value) + parseInt(fault.value))) {
		amount.value = parseInt(normal.value) + parseInt(fault.value);
	}
};