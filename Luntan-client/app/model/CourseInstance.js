Ext.define('Luntan.model.CourseInstance', {
    extend: 'Ext.data.Model',
	requires: ['Luntan.model.Examiner'],

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
		{name: 'ciDesignation', type: 'string'},
		{name: 'courseGroup', type:'string'},
		{name: 'courseDesignation', type:'string'},
		{name: 'preceedingCIId', type: 'int'},
		{name: 'economyDocId', type: 'int'},
		{name: 'balancedEconomyDocId', type: 'int'},
		{name: 'fundingModelId', type: 'int'},
		{name: 'extraDesignation', type: 'string'},
		{name: 'instanceCode', type: 'string'},
		{name: 'registeredStudents', type: 'int'},
		{name: 'startRegStudents', type: 'int'},
		{name: 'modelStudentNumber', type: 'int'},
		{name: 'modelCase', type: 'string'},
		{name: 'onRegs', type: 'boolean',  calculate: function (data) {
         		return data.modelCase.valueOf() == 'REG2YEARS';
     		}
		},
		{name: 'courseLeader', type: 'string'},
		{name: 'balanceRequest', type: 'boolean'},
		{name: 'firstInstance', type: 'boolean'},
		{name: 'note', type: 'string'},
//		{name: 'examiners', type: 'auto'},
		{name: 'grantDistribution', type: 'auto'},
		{name: 'IBG', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['IBG'] = parseFloat(v);
				}
				return typeof record.data.grantDistribution === "undefined" || record.data.grantDistribution === null ? '' : record.data.grantDistribution['IBG'];
			}
		},
		{name: 'ICM', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['ICM'] = parseFloat(v);
				}
				return typeof record.data.grantDistribution === "undefined" || record.data.grantDistribution === null ? '' : record.data.grantDistribution['ICM'];
			}
		},
		{name: 'IEG', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['IEG'] = parseFloat(v);
				}
				return typeof record.data.grantDistribution === "undefined" || record.data.grantDistribution === null ? '' : record.data.grantDistribution['IEG'];
			}
		},
		{name: 'IOB', type: 'float', 
			convert: function(v,record){
				if (typeof v !== "undefined" && v !== null) {
					record.data.grantDistribution['IOB'] = parseFloat(v);
				}
				return typeof record.data.grantDistribution === "undefined" || record.data.grantDistribution === null ? '' : record.data.grantDistribution['IOB'];
			}
		}
    ] 
    
//     hasMany:[{
// 			foreignKey: 'courseInstance_id',          /* rule 3, 5 */
// 			associationKey: 'examiners',    /* rule 4, 5 */
// 			name: 'examiners',              /* rule 6 */
// 			model: 'Luntan.model.Examiner'   /* rule 7 */
// 		}],


/* 
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});
