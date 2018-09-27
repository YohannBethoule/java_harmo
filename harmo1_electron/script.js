

$(function(){
    var save_btn = $("#save_button");
    var load_btn = $("#load_button");
    var sort_btn = $("#sort_button");
    var add_btn = $("#add_button");

    var nom = $("#nom");
    var prenom = $("#prenom");
    var tel = $("#tel");

    var tab = $("#carnet");

    save_btn.on('click', function(){
        //sauvegarder le carnet
    });

    load_btn.on('click', function(){
        //charger le carnet
    });

    sort_btn.on('click', function(){
        for (let i = 0; i < count; i++) {
            for (let j = 1; j < count - i; j++) {
                if (personnes[j].compareTo(personnes[j - 1]) < 0) { // j<j-1
                    Personne tmp = personnes[j];
                    personnes[j] = personnes[j - 1];
                    personnes[j - 1] = tmp;
                }
            }
        }
    });

    add_btn.on('click', function(){
        console.log('add');
        var personne = {
            name: nom.val(),
            firstname: prenom.val(),
            phone: tel.val(),

            toString: function(){
                return "<tr><td>"+this.name+"</td><td>"+this.firstname+"</td><td>"+this.phone+"</td><td><button class=\"rm_button\">Supprimer</button></td></tr>";
            }
        };

        //Ajoute la personne au tableau
        tab.append(personne.toString());

        //Refait le binding des boutons de suppression
        $(".rm_button").on('click', function(){
            console.log($(this).parent());
            $(this).parent().parent().remove();
        })

        //Reinitialise les valeurs des champs
        $("#contact_form")[0].reset();
    });

    $(".rm_button").on('click', function(){
        console.log($(this).parent());
        $(this).parent().parent().remove();
    })
});