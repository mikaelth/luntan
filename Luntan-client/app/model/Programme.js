Ext.define('Luntan.model.Programme', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/programmes'),
		reader: {
			type: 'json',
			rootProperty: 'programmes'
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
		{name: 'code', type: 'string'},
		{name: 'linkId', type: 'string'},
		{name: 'seName', type: 'string'},
		{name: 'direction', type: 'string'},
		{name: 'selmaPath', type: 'string'},
		{name: 'note', type: 'string'},
		{name: 'inactive', type: 'boolean'},
		{name: 'programDirector', type: 'string'}

    ]

/*
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});


