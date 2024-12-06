Ext.define('Luntan.model.Teacher', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/teachers'),
		reader: {
			type: 'json',
			rootProperty: 'staff'
         },
		writer: {
			type: 'json',
			clientIdProperty: 'clientId',
			writeAllFields: true,
			dateFormat: 'Y-m-d'
         }

     },
	idProperty: 'employeeNumber',
    fields: [
		{name: 'employeeNumber', type: 'string'},
		{name: 'username', type: 'string'},
		{name: 'name', type: 'string'},
		{name: 'familyName', type: 'string'},
		{name: 'givenName', type: 'string'},
		{name: 'title', type: 'string'},
		{name: 'department', type: 'string'},
		{name: 'fullDepartment', type: 'string'},
		{name: 'phone', type: 'string'},
		{name: 'mail', type: 'string'},
		{name: 'examinerEligible', type: 'boolean'},
		{name: 'biologySection', type: 'boolean'}
    ],

    validators: {
    }

});


