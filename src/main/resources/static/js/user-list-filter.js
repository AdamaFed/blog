function filterUsers() {
    var input = document.getElementById("search");
    var filter = input.value.toUpperCase();
    var table = document.querySelector("table");
    var tbody = table.querySelector("tbody");
    var tr = tbody.getElementsByTagName("tr");

    for (var i = 0; i < tr.length; i++) {
        var td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            var txtValue = td.textContent;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}
