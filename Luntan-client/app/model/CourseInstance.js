Ext.define('Luntan.model.CourseInstance', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/cis'),
		reader: {
			type: 'json',
			rootProperty: 'cis'
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
		{name: 'locked', type: 'boolean'},
		{name: 'courseId', type: 'int'},
		{name: 'courseGroup', type:'string'},
		{name: 'courseDesignation', type:'string'},
		{name: 'preceedingCIId', type: 'int'},
		{name: 'economyDocId', type: 'int'},
		{name: 'balancedEconomyDocId', type: 'int'},
		{name: 'fundingModelId', type: 'int'},
		{name: 'extraDesignation', type: 'string'},
		{name: 'registeredStudents', type: 'int'},
		{name: 'startRegStudents', type: 'int'},
		{name: 'modelStudentNumber', type: 'int'},
		{name: 'balanceRequest', type: 'boolean'},
		{name: 'note', type: 'string'},
		{name: 'grantDistribution', type: 'auto'}

    ]

/* 
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});


