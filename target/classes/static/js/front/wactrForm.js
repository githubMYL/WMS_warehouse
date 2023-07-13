document.addEventListener('DOMContentLoaded', function() {
	const wactr_size = document.getElementById("wactr_size");
    console.log(wactr_size);
    wactr_size.addEventListener("change", UpdateSize)
});



function UpdateSize(e) {
	console.log(e);
	const evnt = e.currentTarget;
	var scale = document.getElementById("scale");

	console.log(e);
	console.log(this.value);

	if(this.value < 100) {
		scale.value="small";
	} else if(this.value < 1000) {
		scale.value="medium";
	} else if(this.value < 10000){
		scale.value="large";
	} else {
		scale.value="평 수는 숫자로 입력해주세요.";
	}
}