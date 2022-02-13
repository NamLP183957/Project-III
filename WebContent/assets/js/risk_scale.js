// var list_li = document.getElementsByClassName("level");

// for(var i = 0; i < list_li.length; ++i) {
//   list_li[i].style.color = list_li[i].getAttribute("data-color");
// }



function imposeMinMax(el){
  if(el.value == "") {
    el.value = el.min;
  }
  if(el.value != ""){
    if(parseInt(el.value) < parseInt(el.min)){
      el.value = el.min;
    }
    if(parseInt(el.value) > parseInt(el.max)){
      el.value = el.max;
    }
  }
}


function addInput(input_id, container_id) {
  var input_element = document.getElementById(input_id);
  var container_element = document.getElementById(container_id);
  
  var value = input_element.value;
  if(value=="") {
    return;
  }

  var list = container_element.getElementsByClassName(`${input_id}_label`);
  for(var i = 0; i < list.length; ++i) {
    if(value == list[i].value) {
      return;
    }
  }


  input_element.value = "";

  // lỗi ở value
  // change value không làm thay đổi code html đã load
  
  // container_element.innerHTML += 
  // `<div>
  //   <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
  //   <input type="text" class="${input_id}_label" name="${input_id}_label" value="${value}" required>
  //   <input type="number" name="${input_id}_score" value="" min="0" max="100" onblur="imposeMinMax(this)" required>
  //   <input type="color" name="${input_id}_color" oninput="changeColor(event)">
  // </div>`;
  str = 
  `<div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="${input_id}_label" name="${input_id}_label" value="${value}" required>
    <input type="number" name="${input_id}_score" value="" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="${input_id}_color" oninput="changeColor(event)">
    <input type="hidden" name="${input_id}_id" value="-1">
  </div>`;
  container_element.insertAdjacentHTML("beforeend", str);
}

function addInputRisk() {
  var input_element = document.getElementById('risk');
  var container_element = document.getElementById('risk_container');
  
  var value = input_element.value;
  if(value=="") {
    return;
  }

  var list = container_element.getElementsByClassName(`risk_label`);
  for(var i = 0; i < list.length; ++i) {
    if(value == list[i].value) {
      return;
    }
  }

  input_element.value = "";

  var threshold = 0;
  if(container_element.getElementsByTagName("div").length > 0) {
    var last_div = container_element.lastElementChild;
    threshold = parseInt(last_div.getElementsByTagName('input')[2].value) + 1;
  }
  

  str = 
  `<div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="risk_label" name="risk_label" value="${value}" required>
    <input type="number" name="risk_score_min" value="${threshold}" min="0" max="100" readonly required>
    <input type="number" name="risk_score_max" value="${threshold}" min="${threshold}" max="100" onblur="imposeMinMaxRisk(event)" required>
    <input type="color" name="risk_color" oninput="changeColor(event)">
    <input type="hidden" name="risk_level_id" value="-1">
  </div>`;
  container_element.insertAdjacentHTML("beforeend", str);
}

function imposeMinMaxRisk(event) {
  el = event.target;
  if(el.value == "") {
    el.value = el.min;
  }
  if(el.value != ""){
    if(parseInt(el.value) < parseInt(el.min)){
      el.value = el.min;
    }
    if(parseInt(el.value) > parseInt(el.max)){
      el.value = el.max;
    }
  }

  var div = event.target.parentElement;
  while(div.nextElementSibling) {
    var next_el = div.nextElementSibling;
    // console.log(next_el);

    var input = div.getElementsByTagName('input')[2];
    // console.log(input);
    var tmp = parseInt(input.value) + 1;

    var list_input = next_el.getElementsByTagName('input');
    list_input[1].value = tmp;
    list_input[2].value = tmp;
    list_input[2].min = tmp;

    div = next_el;
  }
}

function removeInput(event) {
  var element = event.target.parentElement;
  element.remove();
}


function changeColor(event) {
  var el = event.target;
  var parent = event.target.parentElement;
  var input_text = parent.getElementsByTagName("input")[0];
  input_text.style.color = el.value;
  console.log(el.value)
}

function setDefaultImpact() {
  var container = document.getElementById("impact_container");
  container.innerHTML = 
  `
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="impact_label" name="impact_label" value="Không đáng kể" required>
    <input type="number" name="impact_score" value="0" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="impact_color" oninput="changeColor(event)">
  </div>
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="impact_label" name="impact_label" value="Thấp" required>
    <input type="number" name="impact_score" value="10" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="impact_color" oninput="changeColor(event)">
  </div>
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="impact_label" name="impact_label" value="Trung bình" required>
    <input type="number" name="impact_score" value="50" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="impact_color" oninput="changeColor(event)">
  </div>
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="impact_label" name="impact_label" value="Cao" required>
    <input type="number" name="impact_score" value="100" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="impact_color" oninput="changeColor(event)">
  </div>
  `
}

function setDefaultLikelihood() {
  var container = document.getElementById("likelihood_container");
  container.innerHTML = 
  `
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="likelihood_label" name="likelihood__label" value="Không đáng kể" required>
    <input type="number" name="likelihood_score" value="0" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="likelihood_color" oninput="changeColor(event)">
  </div>
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="likelihood_label" name="likelihood_label" value="Thấp" required>
    <input type="number" name="likelihood_score" value="10" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="likelihood_color" oninput="changeColor(event)">
  </div>
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="likelihood_label" name="likelihood_label" value="Trung bình" required>
    <input type="number" name="likelihood_score" value="50" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="likelihood_color" oninput="changeColor(event)">
  </div>
  <div>
    <i class="fas fa-minus-circle" onclick="removeInput(event)"></i>
    <input type="text" class="likelihood_label" name="impact_label" value="Cao" required>
    <input type="number" name="likelihood_score" value="100" min="0" max="100" onblur="imposeMinMax(this)" required>
    <input type="color" name="likelihood_color" oninput="changeColor(event)">
  </div>
  `
}


