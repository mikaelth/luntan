Ext.define('Luntan.model.FundingModel', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/fundingmodels'),
		reader: {
			type: 'json',
			rootProperty: 'fundingmodels'
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
		{name: 'designation', type: 'string'},
		{name: 'numCourseInstances', type: 'int'},
		{name: 'expression', type: 'string'},
		{name: 'lastModifiedBy', type: 'string'},
		{name: 'lastModifiedDate', type: 'date', format: 'yy-MM-dd'},
		{name: 'note', type: 'string'},
		{name: 'valueTable', type: 'auto'}

    ]

/* 
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});


