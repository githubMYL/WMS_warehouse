document.addEventListener('DOMContentLoaded', function() {
    const clnt = document.getElementById("clnt_tmp");
    var selected = document.querySelector("select#clnt_sel")

    clnt.addEventListener("change", UpdateClnt)
});

function UpdateClnt(e) {
    var evnt = e.currentTarget;
    var selected = document.querySelector("select#clnt_sel")
    var datalist = evnt.list;
    var clntcd = document.getElementById("clnt_cd");

    for(var j = 0; j < datalist.options.length; j++){
        if(this.value == datalist.options[j].value) {
            clntcd.value = datalist.options[j].label;
        }
    }
}

function maxLengthCheck(object){
    if (object.value.length > object.maxLength){
        object.value = object.value.slice(0, object.maxLength);
    }
}