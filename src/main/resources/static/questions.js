var gameFieldSelection = (function() {
  var availableSelections = 0,
  		email,
      init = function(selections, mail) {
        availableSelections = selections;
        email = mail;
		    var parent = $('<div />', {
		        class: 'grid',
		        width: 10  * 20,
		        height: 10  * 20
		    }).addClass('grid');

		    for (var i = 0; i < 10; i++) {
		        for(var p = 0; p < 10; p++) {
		            $('<div />', {
		                width: 20 - 1,
		                height: 20 - 1
		            }).addClass('cellField').click(function() {
                  var element = $(this);
                  if ((element.attr('selected') == null || element.attr('selected') === false) && availableSelections > 0) {
                    element.css('background', 'black').attr('selected', true);
                    availableSelections = --availableSelections;
                  } else if (element.attr('selected') === "selected") {
                    element.css('background', 'none').attr('selected', false);
                    availableSelections = ++availableSelections;
                  }
                }).prop("x", p).prop("y", i).appendTo(parent);
		        }
		    }

		    $('#field').html(parent);
		    $('#submitAns').text('Submit fields').attr('onclick', 'gameFieldSelection.send()');
		},
		getAndSend = function() {
			var jsonToSend = '{ "email": "' + email + '", "cells": [';
			$('[selected="selected"]').each(function( index ) {
			  var currentCell = $(this);
			  jsonToSend = jsonToSend + '{"x": ' + currentCell.prop('x') + ', "y": ' + currentCell.prop('y') + '},';
			});

			if ($('[selected="selected"]').length > 0)
				jsonToSend = jsonToSend.substring(0, jsonToSend.length - 1)
			jsonToSend = jsonToSend + ']}';

			$.ajax({
	        	  type: "POST",
	        	  url: "http://localhost:8080/field",
	        	  data: jsonToSend,
	        	  contentType: "application/json",
	        	  dataType: 'json',
	        	  success: function( data ) {
	                  $('body').html($('<div />').append($('<p />').text('Thank you for submiting answers')).addClass('jumbotron'));
	              }
	        	});
		};
  return {
    init: init,
    send: getAndSend
  };
})();

var source = $("#questionsForm").html(); 
        var template = Handlebars.compile(source);

          $.getJSON( "http://localhost:8080/ques", function( data ) {
            $('#field').append(template(data));
          });

        function send() {
        	var email = $('#email').val(),
          jsonToSend = '{ "name":"' + $('#name').val() + '", "email": "' + email + '", "answers": [';
          
          $('.question').each(function( index ) {
            var currentCell = $(this);
            jsonToSend = jsonToSend + '{"questionNumber": ' + currentCell.attr('number')
            	+ ', "selectedAnswer": "' + currentCell.find(':selected').val() + '"},';
          });

          jsonToSend = jsonToSend.substring(0, jsonToSend.length - 1) + ']}';
          
          $.ajax({
        	  type: "POST",
        	  url: "http://localhost:8080/ques",
        	  data: jsonToSend,
        	  contentType: "application/json",
        	  dataType: 'json',
        	  success: function( data ) {
        		  alert('You had: ' + data +' correct answers');
        		  gameFieldSelection.init(data, email);
              }
        	});

          //console.log(JSON.parse(jsonToSend));
        };