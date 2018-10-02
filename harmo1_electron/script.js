$(function(){
    var save_btn = $("#save_button");
    var load_btn = $("#load_button");
    var sort_btn = $("#sort_button");
    var add_btn = $("#add_button");

    var nom = $("#nom");
    var prenom = $("#prenom");
    var tel = $("#tel");

    var tab = $("#carnet");

    var fs = require('fs');
    var app = require('electron').remote; 
    var dialog = app.dialog;



    var carnet = {
        personnes: new Array(),

        updateFromDom: function(){
            this.personnes = new Array();
            tab.html("");
            $('.personne_row').each(function(){
                var name = $(this).find('.personne_name').html();
                var firstname = $(this).find('.personne_firstname').html();
                var phone = $(this).find('.personne_phone').html();
                
                carnet.add(name, firstname, phone);
            });
        },

        add: function(name, firstname, phone){
            var p = {
                name : name,
                firstname :firstname,
                phone : phone,

                toString: function(){
                    return "<tr class='personne_row' data-index='"+carnet.personnes.indexOf(this)+"'><td class='personne_name'>"+this.name+"</td><td class='personne_firstname'>"+this.firstname+"</td><td class='personne_phone'>"+this.phone+"</td><td><button class=\"rm_button\">Supprimer</button></td></tr>";
                }
            }
            this.personnes.push(p);
            tab.append(p.toString());

            //on rebind l'event handler de la suppression
            $(".rm_button").one('click', function(){
                 remove_handler(this);
            })
        },

        remove: function(index){
            console.log(index);
            this.personnes.splice(index, 1);
            carnet.write();
        },

        sort: function(){
            this.personnes.sort(function(a, b){
                if (a.name.toLowerCase() < b.name.toLowerCase())
                    return -1;
                if (a.name.toLowerCase() > b.name.toLowerCase())
                    return 1;
                if (a.firstname.toLowerCase() < b.firstname.toLowerCase())
                    return -1;
                if (a.firstname.toLowerCase() > b.firstname.toLowerCase())
                    return 1;

                return 0;
            });
        },

        load: function(file){
            fs.readFile(file, (err, data) => {
                if (err) throw err;
                var obj = JSON.parse(data);
                
                carnet.personnes = new Array();
                obj.forEach(function(p){
                    carnet.add(p.name, p.firstname, p.phone);
                })
                carnet.write();
            });
            
        }, 

        save: function(file){
            var json_object = JSON.stringify(this.personnes);
            fs.writeFile(file, json_object, (err) => {
                if(err){
                    alert("An error ocurred creating the file "+ err.message)
                }
            });
        }, 

        write: function(){
            tab.html("");
            tab.html(this.toString());

            //on rebind l'event handler de la suppression
            $(".rm_button").one('click', function(){
                 remove_handler(this);
            })
        },

        toString: function(){
            var text = "";
            this.personnes.forEach(function(e){
                text += e.toString();
            })
            return text;
        }
    };

    var remove_handler = function(button){
        var td = $(button).parent().parent();
        var index = td.attr('data-index');
        carnet.remove(index);
    }

    save_btn.on('click', function(){
        dialog.showSaveDialog((fileName) => {
            if (fileName === undefined){
                console.log("You didn't save the file");
                return;
            }else{
                carnet.save(fileName);
            }

        }); 
    });

    load_btn.on('click', function(){
        dialog.showSaveDialog((fileName) => {
            if (fileName === undefined){
                console.log("You didn't save the file");
                return;
            }else{
                carnet.load(fileName);
            }

        }); 
    });

    sort_btn.on('click', function(){
        carnet.sort();
        carnet.write();
    });

    add_btn.on('click', function(e){
        e.preventDefault();
        var name= nom.val();
        var firstname = prenom.val();
        var phone = tel.val();
        
        carnet.add(name, firstname, phone);

        //Reinitialise les valeurs des champs
        $("#contact_form")[0].reset();
    });

    $(".rm_button").one('click', function(){
        remove_handler(this);
    });

    carnet.add('Michel', 'Jean', 1);
    carnet.add('Jichel', 'Mean', 2);
    carnet.add('Sichel', 'Zean', 3);
});