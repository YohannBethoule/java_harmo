$(function(){
    var save_btn = $("#save_button");
    var file = $("#file");
    var sort_btn = $("#sort_button");
    var add_btn = $("#add_button");

    var nom = $("#nom");
    var prenom = $("#prenom");
    var tel = $("#tel");

    var tab = $("#carnet");


    var carnet = {
        personnes: new Array(),

        updateFromDom: function(){
            this.personnes = new Array();
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
                    return "<tr class='personne_row'><td class='personne_name'>"+this.name+"</td><td class='personne_firstname'>"+this.firstname+"</td><td class='personne_phone'>"+this.phone+"</td><td><button class=\"rm_button\">Supprimer</button></td></tr>";
                }
            }
            this.personnes.push(p);
            tab.append(p.toString());

            //on rebind l'event handler de la suppression
            $(".rm_button").on('click', function(){
                var td = $(this).parent().parent();
                var name = td.find('.personne_name').html();
                var firstname = td.find('.personne_firstname').html();
                var phone = td.find('.personne_phone').html();
                carnet.remove(name, firstname, phone);
                $(this).parent().parent().remove();
            })
        },

        remove: function(name, firstname, phone){
            this.personnes.forEach(function(e){
                if(e.toString() == "<tr class='personne_row'><td class='personne_name'>"+name+"</td><td class='personne_firstname'>"+firstname+"</td><td class='personne_phone'>"+phone+"</td><td><button class=\"rm_button\">Supprimer</button></td></tr>"){
                    this.personnes.remove(e);
                }
            })
        },

        sort: function(){
            Array.sort(this.personnes);
        },

        load: function(){

        }, 

        save: function(){

        }, 

        write: function(){
            tab.html("");
            console.log(this.toString());
            tab.html(this.toString());

            //on rebind l'event handler de la suppression
            $(".rm_button").on('click', function(){
                var td = $(this).parent().parent();
                var name = td.find('.personne_name').html();
                var firstname = td.find('.personne_firstname').html();
                var phone = td.find('.personne_phone').html();
                carnet.remove(name, firstname, phone);
                $(this).parent().parent().remove();
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

    save_btn.on('click', function(){
        carnet.save();
    });

    file.on('change', function(){
        carnet.load();
    });

    sort_btn.on('click', function(){
        carnet.updateFromDom();
        carnet.sort();
        carnet.write();
    });

    add_btn.on('click', function(){
        var name= nom.val();
        var firstname = prenom.val();
        var phone = tel.val();
        
        carnet.add(name, firstname, phone);

        //Reinitialise les valeurs des champs
        $("#contact_form")[0].reset();
    });

    $(".rm_button").on('click', function(){
        var td = $(this).parent().parent();
        var name = td.find('.personne_name').html();
        var firstname = td.find('.personne_firstname').html();
        var phone = td.find('.personne_phone').html();
        carnet.remove(name, firstname, phone);
        $(this).parent().parent().remove();
    })
});