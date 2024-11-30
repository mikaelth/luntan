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
		{name: 'courseEvalSetUp', type: 'boolean'},
		{name: 'studentDone', type: 'boolean'},
		{name: 'startDate', type: 'date', format: 'yy-MM-dd'},
		{name: 'registrationDate', type: 'date', format: 'yy-MM-dd'},
		{name: 'note', type: 'string'},
		{name: 'studentName', type: 'string'},
		{name: 'creditBasisRecId', type: 'int'},
		{name: 'courseInstanceId', type: 'int'},
		{name: 'economyDocId', type:'int'},
		
		{name: 'regDepartment', type: 'string'},
		{name: 'ibgReg', type: 'boolean'}
	]
});
