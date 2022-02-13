function checkTag(select_id, container_id) {
  var select_element = document.getElementById(select_id);
  var container_element = document.getElementById(container_id);

  var values = [];
  var inputs = container_element.getElementsByTagName("input");
  for(var i = 0; i < inputs.length; ++i) {
    if(inputs[i].type == "hidden") {
      values.push(inputs[i].value);
    }
  }

  for(var i = 1; i < select_element.options.length; ++i) {
    if(values.includes(select_element.options[i].value)){
      select_element.options[i].setAttribute("disabled", "true");
    }
  }

}

function addTag(select_id, container_id) {
  var select_element = document.getElementById(select_id);
  var container_element = document.getElementById(container_id);

  if(select_element.selectedIndex == 0) {
    return;
  }
  select_element.options[select_element.selectedIndex].setAttribute("disabled", "true");
  
  var value = select_element.value;
  var text = select_element.options[select_element.selectedIndex].innerText;

  select_element.selectedIndex = 0;

  container_element.innerHTML += 
  `<div>
    <i class="fas fa-minus-circle" onclick="removeTag('${select_id}', event)"></i>
    <input type="hidden" name="${select_id}" value="${value}">
    <input type="text" readonly value="${text}" disabled>
  </div>`;
  // console.log(value);
}

function removeTag(select_id, event) {
  var select_element = document.getElementById(select_id);
  var element = event.target.parentElement;

  var value = element.getElementsByTagName("input")[0].value;

  for(var i = 1; i < select_element.options.length; ++i) {
    if(select_element.options[i].value == value) {
      select_element.options[i].removeAttribute("disabled");
    }
  }
  
  element.remove();
  // console.log(element);
  // console.log(value);
  // console.log(select_element.options);
}