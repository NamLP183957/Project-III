function filterTable() {
	var el = document.getElementById("filter");
	var value = el.value;
	var num = document.getElementById("num-trouble");
	
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
		
		default: {
			var count = 0;
			
			for(var i = 1; i < len; ++i) {
				// chú ý dùng trim()?
				
				if(row[i].getElementsByTagName("td")[3].innerText.trim() == value) {
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

