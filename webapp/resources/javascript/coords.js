const MAX_X = 3;
const MIN_X = -3;
const hiddenForm = document.getElementById("hiddenForm");
const r_pixels = 90;
const centerX = 125;
const centerY = 125;
var color;

function findNearest(value) {
	value = (value>MAX_X) ? MAX_X : Math.round(value);
	value = (value<MIN_X) ? MIN_X : Math.round(value);

	return value;
}

function changeR(r) {
	$('.draw_r').text(r);
	$('.draw_-r').text(-r);
	$('.draw_half_r').text(r/2);
	$('.draw_-half_r').text(-r/2);
	if(r!==prevR)
	{
		deleteCircles();
	}
}

function tryf() {

	const rect = document.getElementById("graphic").getBoundingClientRect();

	var cordX;
	var cordY;
	if(!isNaN(r)){
		const x = event.clientX - rect.left;
		const y = event.clientY - rect.top;

		cordX = findNearest((x - centerX) * r / r_pixels);
		cordY = Math.min(Math.max(-(y - centerY) * r / r_pixels, -4.99999), 2.99999);
		cordY = cordY.toFixed(5);

		const resX = cordX * r_pixels / r + centerX;
		const resY = centerY - cordY * r_pixels / r;
		color = setColor(cordX,cordY,r);
		changePoint(resX,resY);
		$('#error').text("");

		//fill hidden form
		document.getElementById("hiddenForm:pointX").value = cordX;
		document.getElementById("hiddenForm:pointY").value = cordY;
		document.getElementById("hiddenForm:pointR").value = r;
		document.getElementById("hiddenForm:submitSvg").onclick();
	}
	else{
		$('#error').text("Укажите значение R");
	}
}

function changePoint(x,y) {
	let svgns = "http://www.w3.org/2000/svg"; //библиотека
	let	container = document.getElementById( 'graphic' );
	let circle = document.createElementNS(svgns, 'circle');
	circle.setAttributeNS(null, 'cx', x);
	circle.setAttributeNS(null, 'cy', y);
	circle.setAttributeNS(null, 'r', 3);
	circle.setAttributeNS(null, 'fill', color);
	circle.setAttributeNS(null, 'stroke', color);
	circle.setAttributeNS(null, 'class', 'circles');
	container.appendChild(circle);
}

function setColor(x,y,r) {
	if (x >= 0) {
		if (y >= 0) {
			if (y <= (r/2 -x/2)) {
				return "#732ea3";
			}
		} else {
			if (y >= (-r/2) && x <= r) {
				return "#732ea3";
			}
		}
	} else {
		if (y >= 0) {
			if ((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(r / 2, 2)) {
				return "#732ea3";
			}
		}
	}
	return "red";
}

function deleteCircles() {
	let buttons = document.getElementsByClassName("circles");

	for (let i = 0; i < buttons.length; i++)
	{
		buttons[i].setAttribute("visibility", "hidden");
	}
}

function drawFromForm() {
	var formX = document.getElementById( 'coord:txt1' ).value;
	var formY = document.getElementById( 'coord:textfieldY' ).value;
	const resX = formX * r_pixels / r + centerX;
	const resY = centerY - formY * r_pixels / r;
	color = setColor(formX,formY,r);
	changePoint(resX,resY);

}