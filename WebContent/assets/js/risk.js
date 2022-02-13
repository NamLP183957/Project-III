var list_li = document.getElementsByClassName("level");

for(var i = 0; i < list_li.length; ++i) {
   list_li[i].style.color = list_li[i].getAttribute("data-color");
}


var impact = document.getElementById('impact_score');
var likelihood = document.getElementById('likelihood_score');
var risk = document.getElementById('risk_score');
var risk_level = document.getElementById('risk_level');
var risk_scale = document.getElementById('risk_scale');
function calRiskScore() {
  risk.value = parseInt(impact.value) * parseInt(likelihood.value) / 100;

  for(var i = 0; i < risk_scale.options.length; ++i) {
    var min = risk_scale.options[i].getAttribute("data-min");
    var max = risk_scale.options[i].getAttribute("data-max");
    if(risk.value >= parseFloat(min) && risk.value <= parseFloat(max)) {
      risk_level.value = risk_scale.options[i].value;
    }
  }
}

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

  calRiskScore()
}



function changeScore(event) {
  var select = event.target;
  var input = event.target.nextElementSibling;
  var option = select.options[select.selectedIndex];

  input.min = option.getAttribute("data-min");
  input.max= option.getAttribute("data-max");
  // console.log(input.min);
  input.value = input.max;
  

  calRiskScore();
}

