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

	console.log(clntcd_value);
	var itemsel = document.getElementById("item_sel");

	var item_options = itemsel.querySelectorAll('option');


	var item_datalist = document.getElementById("item");




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
	var clntcd_value = clntcd.value;
	const url = `ajaxstdin?clntCd=${clntcd_value}`;
	commonLib.ajaxLoad(url)
	.then((data) => {
						data = JSON.parse(data);
						console.log(data);
						var item_str = '<select id="item_sel">';


						for(var i = 0; i < data.length; i++) {
							item_str += '<option label=';
							item_str += data[i].itemCd;
							item_str += ' value=';
							item_str += data[i].itemNm;
							item_str += '></option>';
						}
						item_str += '</select>'
						item_datalist.innerHTML = item_str;

					})
					.catch((err) => {
						console.error(err);
					});

}


function UpdateItem(e) {
	var selected = document.querySelector("select#item_sel")
	var datalist = document.getElementById("item");
	var itemcd = document.getElementById("item_cd");

	var wactrCd;
	var loc_datalist = document.getElementById("loc");

	for(var j = 0; j < datalist.options.length; j++){
		if(document.getElementById("item_tmp").value == datalist.options[j].value) {
			itemcd.value = datalist.options[j].label;
			dataset = datalist.options[j].dataset;
		}
	}
	var itemcd_value = itemcd.value;

	const url = `ajaxstdin/getLoc?itemCd=${itemcd_value}`;
        	commonLib.ajaxLoad(url)
        	.then((data) => {
            					data = JSON.parse(data);
            					console.log(data);
            					var loc_str = '<select id="loc_sel">';


            					for(var i = 0; i < data.length; i++) {
            						loc_str += '<option label=';
            						loc_str += data[i].wactr_cd;
            						loc_str += ' value=';
            						loc_str += data[i].loc_nm;
            						loc_str += '></option>';
            					}
            					loc_str += '</select>'
            					loc_datalist.innerHTML = loc_str;

            				})
            				.catch((err) => {
            					console.error(err);
            				});

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