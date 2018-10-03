const {app, BrowserWindow, Menu, shell, dialog} = require('electron')
const os = require('os');
const fs = require('fs');

function createWindow () {
    // Cree la fenetre du navigateur.
    let win = new BrowserWindow({width: 1200, height: 800});
    win.webContents.openDevTools();
    // et charge le index.html de l'application.
    win.loadFile('index.html');
    console.log('alert');

}

app.on('ready', createWindow);

