Ext.define('Luntan.model.Course', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/courses'),
		reader: {
			type: 'json',
			rootProperty: 'courses'
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
		{name: 'inactive', type: 'boolean'},
		{name: 'code', type: 'string'},
		{name: 'courseGroup', type: 'string'},
		{name: 'board', type: 'string'},
		{name: 'seName', type: 'string'},
		{name: 'designation', type: 'string'},
		{name: 'credits', type: 'float'},
		{name: 'formName', type: 'string', calculate: function (data) {return data.code.concat(data.code == '' ? '':' ').concat(data.seName)}},
		{name: 'note', type: 'string'}

    ]

/* 
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});


