Ext.define('Luntan.model.IndCourseTeacher', {
    extend: 'Ext.data.Model',
//	requires: ['Luntan.model.Teacher'],

	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/icts'),
		reader: {
			type: 'json',
			rootProperty: 'icts'
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
		{name: 'assignmentId', type: 'int'},
		{name: 'ldapEntry', type: 'string'},
		{name: 'teacherType', type: 'string'},
		{name: 'teachFactor', type: 'int'},
		{name: 'notUU', type: 'boolean'},
		{name: 'external', type: 'boolean'},
		{name: 'name', type: 'string'},
		{name: 'fullDepartment', type: 'string'},
		{name: 'phone', type: 'string'},
		{name: 'email', type: 'string'},
		{name: 'note', type: 'string'}
	]
});
