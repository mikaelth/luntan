Ext.define('Luntan.model.ExaminersList', {
    extend: 'Ext.data.Model',
 

	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/examinerslists'),
		reader: {
			type: 'json',
			rootProperty: 'examinerslists'
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
		{name: 'board', type: 'string'},
		{name: 'decisionDate', type: 'date', format: 'yy-MM-dd'},
		{name: 'decided', type: 'bool'},
		{name: 'note', type: 'string'},
		{name:'defaultExaminers', type: 'auto'}
    ]
});


