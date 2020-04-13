Ext.define('Luntan.model.Examiner', {
    extend: 'Ext.data.Model',
 

	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/examiners'),
		reader: {
			type: 'json',
			rootProperty: 'examiners'
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
/* 
		{name: 'compositid',
			convert: function(value, model) {
				var data = model.getData();
				return data.courseId.toString() + '-' + data.ldapEntry;
			}
		},
 */
		{name: 'id', type: 'int'},
		{name: 'courseId', type: 'int'},
		{name: 'decisionId', type: 'int'},
		{name: 'decided', type: 'bool'},
		{name: 'ldapEntry', type: 'string'},
		{name: 'rank', type: 'int'},
		{name: 'note', type: 'string'}
    ]
});


