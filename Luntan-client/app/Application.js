/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('Luntan.Application', {
    extend: 'Ext.app.Application',
    
    name: 'Luntan',

    stores: [
        // TODO: add global / shared stores here
    ],
    
    launch: function () {
        // TODO - Launch the application

        Ext.Ajax.on('requestexception', function (connection, response, requestOptions, listenerOptions) {
   			console.log("RequestException: " + response.status);
			if (response.status == 401) {			
				if(requestOptions.method == 'GET') {
					window.open(Luntan.data.Constants.BASE_URL.concat('InREST.html'))	
				} else {
					Ext.MessageBox.alert('Status', 'Du saknar behörighet för detta');
				}		
			} else {
				Ext.MessageBox.alert('Status', 'RESTful-kommunikation gick inte som det skulle: ' + response.statusText + ' Skyll inte på mig om saker inte fungerar från och med nu!');
			}
    	});

    },

    onAppUpdate: function () {
        Ext.Msg.confirm('Application Update', 'Den här applikationen har en uppdatering, ladda om?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
