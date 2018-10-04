var request = require("request").defaults({ encoding: null });
var fs = require('fs');
var app = require('electron').remote; 
var dialog = app.dialog;

var changeColor = function(color){
    img.drawImage(og_img, 0, 0);
    var imgData = img.getImageData(0,0,img.canvas.width, img.canvas.height);
    var data = imgData.data;

    switch(color){
        case "red":
            for(var i=0; i<data.length; i+=4) {
                data[i+1] = 0;
                data[i+2] = 0
            }
            img.putImageData(imgData, 0, 0);
            break;

        case "green":
            for(var i=0; i<data.length; i+=4) {
                data[i] = 0;
                data[i+2] = 0
            }
            img.putImageData(imgData, 0, 0);
            break;

        case "blue":
            for(var i=0; i<data.length; i+=4) {
                data[i+1] = 0;
                data[i] = 0
            }
            img.putImageData(imgData, 0, 0);
            break;

		case "grey":
            for(var i=0; i<data.length; i+=4) {
                var red = data[i];
                var green = data[i+1];
                var blue = data[i+2];

                data[i] = red*0.3 + green*0.59 + blue*0.11;
                data[i+1] = red*0.3 + green*0.59 + blue*0.11;
                data[i+2] = red*0.3 + green*0.59 + blue*0.11;
            }
            img.putImageData(imgData, 0, 0);
            break;
    }
}


var fileChooser = document.getElementById('file');
var og_img = document.getElementById('og_img');
var img = document.getElementById('img').getContext('2d');
var greyBtn = document.getElementById('grey_btn');
var colorSelect = document.getElementById('select_color');
var btnSave = document.getElementById('save');
fileChooser.addEventListener('change', function(){
    if (this.files && this.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            og_img.setAttribute('src', e.target.result);
        }
        reader.readAsDataURL(this.files[0]);
    };
});

og_img.onload = function(e){
    img.canvas.width = og_img.width;
    img.canvas.height = og_img.height;
    img.drawImage(og_img, 0, 0);
}

greyBtn.addEventListener('click', function(){
    changeColor("grey");
});

colorSelect.addEventListener('change', function(){
	changeColor(colorSelect.value);
});

btnSave.addEventListener('click', function(event){
    dialog.showSaveDialog((fileName) => {
        if (fileName === undefined){
            console.log("You didn't save the file");
            return;
        }else{
            // strip off the data: url prefix to get just the base64-encoded bytes
            var data = img.canvas.toDataURL('image/png').replace(/^data:image\/\w+;base64,/, "");
            var buf = new Buffer(data, 'base64');
            fs.writeFile(fileName, buf, (err) => {      
                if(err){
                    alert("An error ocurred creating the file "+ err.message)
                }
            });
        }
    });
});

