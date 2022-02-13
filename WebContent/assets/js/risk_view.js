/**
 * 
 */

function filterTable() {
	var el = document.getElementById("filter");
	var value = el.value;
	var num = document.getElementById("num-risk");
	
	var row = document.getElementsByTagName("tr");
	// row đầu chứa th, không chứa td
	// vì vậy chỉ số chạy từ 1
	var len = row.length;
	
	switch(value) {
		case "all": {
			for(var i = 1; i < len; ++i) {
				row[i].style.display = "table-row";
			}
			
			num.innerText = len - 1;
			break;
		}
		
		case "evaluated": {
			var count = 0;
			for(var i = 1; i < len; ++i) {
				// dùng innerText chứ không phải value
				if(row[i].getElementsByTagName("td")[4].innerText != "") {
					row[i].style.display = "table-row";
					count ++;
				} else {
					row[i].style.display = "none";
				}
			}
			
			num.innerText = count;
			break;
		}
		
		case "not-be-evaluated": {
			var count = 0;
			for(var i = 1; i < len; ++i) {
				if(row[i].getElementsByTagName("td")[4].innerText == "") {
					row[i].style.display = "table-row";
					count ++;
				} else {
					row[i].style.display = "none";
				}
			}
			
			num.innerText = count;
			break;
		}
		
		default: {
			var count = 0;
			for(var i = 1; i < len; ++i) {
				if(row[i].getElementsByTagName("td")[4].innerText == value) {
					row[i].style.display = "table-row";
					count ++;
				} else {
					row[i].style.display = "none";
				}
			}
			
			num.innerText = count;
		}
	}
		
}

function compareRow() {
		
}
	
function test() {
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      console.log(this.responseText);
	    }
	};

	xhttp.open("GET", "/Risk-Assessment/test", true);

	xhttp.send();
}