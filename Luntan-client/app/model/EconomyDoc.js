Ext.define('Luntan.model.EconomyDoc', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/edocs'),
		reader: {
			type: 'json',
			rootProperty: 'edocs'
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
		{name: 'year', type: 'int'},
		{name: 'baseValue', type: 'int'},
		{name: 'note', type: 'string'},
		{name: 'numberOfCIs', type: 'int'},
		{name: 'accountedDepts', type: 'auto'}

    ]

/* 
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});


