Ext.define('Luntan.model.IndCourseCreditBasis', {
    extend: 'Ext.data.Model',
//	requires: ['Luntan.model.Examiner'],

	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/iccbs'),
		reader: {
			type: 'json',
			rootProperty: 'iccbs'
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
		{name: 'createdDate', type: 'date', format: 'yy-MM-dd'},
		{name: 'sent', type: 'date', format: 'yy-MM-dd'},
		{name: 'numberOfRegs', type: 'int'},
		{name: 'note', type: 'string'}
	]
});
