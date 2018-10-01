Ext.define('Luntan.model.User', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/users'),
		reader: {
			type: 'json',
			rootProperty: 'users'
         },
		writer: {
			type: 'json',
			clientIdProperty: 'clientId',
			writeAllFields: true,
			dateFormat: 'Y-m-d'
         }

     }, 
	idProperty: 'id',
    fields: [
		{name: 'id', type: 'int'},
		{name: 'username', type: 'string'},
		{name: 'firstName', type: 'string'},
		{name: 'lastName', type: 'string'},
		{name: 'email', type: 'string'},
		{name: 'note', type: 'string'},
		{name: 'userRoles', type: 'auto'}

    ],

    validators: {
    	username: 'presence',
    	userRoles: 'presence'
    }

});


