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



    var carnet = function(){ 
        var personnes = new Array();

        return{
            getPersonnes: function(){
                return personnes;
            },

            updateFromDom: function(){
                personnes = new Array();
                tab.html("");
                $('.personne_row').each(function(){
                    var name = $(this).find('.personne_name').html();
                    var firstname = $(this).find('.personne_firstname').html();
                    var phone = $(this).find('.personne_phone').html();
                    
                    carnet.add(name, firstname, phone);
                });
            },

            add: function(nom, prenom, tel){
                var p = function(){
                    var name = nom;
                    var firstname = prenom;
                    var phone = tel;

                    return {
                        getName : function(){
                            return name;
                        },

                        getFirstname : function(){
                            return firstname;
                        },

                        toString: function(){
                            return "<tr class='personne_row' data-index='"+personnes.indexOf(p)+"'><td class='personne_name'>"+name+"</td><td class='personne_firstname'>"+firstname+"</td><td class='personne_phone'>"+phone+"</td><td><button class=\"rm_button\">Supprimer</button></td></tr>";
                        }
                    }
                    
                }();
                personnes.push(p);
            },

            remove: function(index){
                console.log(index);
                personnes.splice(index, 1);
                carnet.write();
            },

            sort: function(){
                personnes.sort(function(a, b){
                    if (a.getName().toLowerCase() < b.getName().toLowerCase())
                        return -1;
                    if (a.getName().toLowerCase() > b.getName().toLowerCase())
                        return 1;
                    if (a.getFirstname().toLowerCase() < b.getFirstname().toLowerCase())
                        return -1;
                    if (a.getFirstname().toLowerCase() > b.getFirstname().toLowerCase())
                        return 1;

                    return 0;
                });
            },

            load: function(file){
                fs.readFile(file, (err, data) => {
                    if (err) throw err;
                    var obj = JSON.parse(data);
                    
                    personnes = new Array();
                    obj.forEach(function(p){
                        carnet.add(p.name, p.firstname, p.phone);
                    })
                    carnet.write();
                });
                
            }, 

            save: function(file){
                var json_object = JSON.stringify(personnes);
                fs.writeFile(file, json_object, (err) => {
                    if(err){
                        alert("An error ocurred creating the file "+ err.message)
                    }
                });
            }, 

            write: function(){
                tab.html("");
                tab.html(carnet.toString());
                console.log();

                //on rebind l'event handler de la suppression
                $(".rm_button").one('click', function(){
                     remove_handler(this);
                })
            },

            toString: function(){
                var text = "";
                personnes.forEach(function(e){
                    text += e.toString();
                })
                return text;
            }
        }
    }();

    var remove_handler = function(event){
        var td = $(event).parent().parent();
        console.log(td);
        var index = td.attr('data-index');
        console.log(index);
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
        carnet.write();
        //Reinitialise les valeurs des champs
        $("#contact_form")[0].reset();
    });

    $(".rm_button").one('click', function(){
        remove_handler(this);
    });
});