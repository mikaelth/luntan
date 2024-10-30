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
		{name: 'department', type: 'string', /* calculate: function (data) {return 'IBG'} */ },
		{name: 'teacherType', type: 'string'},
		{name: 'note', type: 'string'}
	]
});
