Ext.define('Luntan.model.IndCourseReg', {
    extend: 'Ext.data.Model',
//	requires: ['Luntan.model.Examiner'],

	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/icrs'),
		reader: {
			type: 'json',
			rootProperty: 'icrs'
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
		{name: 'courseInstanceId', type: 'int'},
		{name: 'economyDocId', type:'int'},
		{name: 'creditBasisRecId', type: 'int'},
		{name: 'startDate', type: 'date', format: 'yy-MM-dd'},
		{name: 'registrationDate', type: 'date', format: 'yy-MM-dd'},
		{name: 'studentName', type: 'string'},
		{name: 'regDepartment', type: 'string'},
		{name: 'note', type: 'string'},
		{name: 'studentDone', type: 'boolean'},
		{name: 'courseEvalSetUp', type: 'boolean'}
	]
});
