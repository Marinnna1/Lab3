var r;
var prevR;

function rButtonsValidate(input, value) {
	prevR=r;
	r = value;
	changeR(r);
	let buttons = document.getElementsByClassName("rButton");

	for (let i = 0; i < buttons.length; i++)
	{
		if (buttons[i].className.includes(" greenButton"))
		{
			buttons[i].className = buttons[i].className.replace(" greenButton", "");
		}
	}

	input.className = input.className + " greenButton";

}



   


