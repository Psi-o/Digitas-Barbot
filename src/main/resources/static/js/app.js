$(document).foundation();


$('#fileUpload').submit(function (e) {
    e.preventDefault();
    document.getElementById("fileUpload").submit();
    return false;
});