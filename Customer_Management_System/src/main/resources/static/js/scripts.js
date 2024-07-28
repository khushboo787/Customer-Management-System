function searchCustomers() {
    var input = document.getElementById("search");
    var filter = input.value.toLowerCase();
    var table = document.getElementById("customerTableBody");
    var tr = table.getElementsByTagName("tr");
    for (var i = 0; i < tr.length; i++) {
        var td = tr[i].getElementsByTagName("td");
        var found = false;
        for (var j = 0; j < td.length; j++) {
            if (td[j].innerHTML.toLowerCase().indexOf(filter) > -1) {
                found = true;
                break;
            }
        }
        if (found) {
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
    }
}

function syncCustomers() {
    fetch('/syncCustomers')
        .then(response => response.json())
        .then(data => {
            var tableBody = document.getElementById("customerTableBody");
            tableBody.innerHTML = "";
            data.forEach(customer => {
                var row = `<tr>
                    <td>${customer.firstName}</td>
                    <td>${customer.lastName}</td>
                    <td>${customer.street}</td>
                    <td>${customer.address}</td>      
                    <td>${customer.city}</td>
                    <td>${customer.state}</td>
                    <td>${customer.email}</td>
                    <td>${customer.phone}</td>
                    <td>
                        <button onclick="editCustomer(${customer.id})">Edit</button>
                        <button onclick="deleteCustomer(${customer.id})">Delete</button>
                    </td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Error:', error));
}

function editCustomer(id) {
    // Implement edit customer functionality
}

function deleteCustomer(id) {
    // Implement delete customer functionality
}
