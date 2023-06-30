function UpdateClnt(element) {
    var clntcd = document.getElementById("clnt_cd");
    clntcd.value = element.options[element.selectedIndex].text;
}

document.querySelector("form[name='frmJoin']").addEventListener("submit", function(event) {
    var selectedValue = document.getElementById("clnt_tmp").value;
    if (selectedValue === "") {
        event.preventDefault(); // 제출을 중지합니다.
        alert("드롭다운 값을 선택해주세요."); // 사용자에게 선택 필수 메시지를 알립니다.
    }
});

function maxLengthCheck(object) {
    if (object.value.length > 8) {
        object.value = object.value.slice(0, 8);
    }
}