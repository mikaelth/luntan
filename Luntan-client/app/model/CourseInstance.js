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
		{name: 'grantDistribution', type: 'auto'},
		{name: 'IBG', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['IBG'] = parseFloat(v);
				}
				return record.data.grantDistribution['IBG'];
			}
		},
		{name: 'ICM', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['ICM'] = parseFloat(v);
				}
				return record.data.grantDistribution['ICM'];
			}
		},
		{name: 'IEG', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['IEG'] = parseFloat(v);
				}
				return record.data.grantDistribution['IEG'];
			}
		},
		{name: 'IOB', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['IOB'] = parseFloat(v);
				}
				return record.data.grantDistribution['IOB'];
			}
		},

    ]

/* 
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});


