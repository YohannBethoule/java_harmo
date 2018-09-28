const {app, BrowserWindow, Menu, shell, dialog} = require('electron')
const os = require('os');
const fs = require('fs');


function openImage(fileName){
    fs.readFile(fileName, function(err, data){
        if(err){
            alert('There has been an error reading the file');
        }else{

        }
    });
}

function createWindow () {
    // Cree la fenetre du navigateur.
    let win = new BrowserWindow({width: 800, height: 600});

    let template = [
        {
            label: 'File',
            submenu: [
                {
                    label: 'Open Image',
                    accelerator: 'Ctrl&O',
                    role: 'open',
                    click: () => {
                        dialog.showOpenDialog( os.homedir(), function(filepaths) {
                                console.log('ALEEEERTE');
                                //read image (note: use async in production)
                                var _img = fs.readFileSync(filepaths[0]).toString('base64');
                                //example for .png
                                var _out = '<img src="data:image;base64,' + _img + '" />';
                                //render/display

                                win.webContents.executeJavaScript("document.getElementById('image_container').insertAdjacentHTML('beforeend', _out);")

                                return;
                            });
                    }
                },
                {
                    label: 'Save',
                    accelerator: 'Ctrl&S',
                    role: 'save',

                }
            ]
        }
    ]
    const menu = Menu.buildFromTemplate(template);
    Menu.setApplicationMenu(menu);
    win.webContents.openDevTools();

    // et charge le index.html de l'application.
    win.loadFile('index.html');
    console.log('alert');

}

app.on('ready', createWindow);

