Ext.define('Luntan.model.EconomyDocGrant', {
    extend: 'Ext.data.Model',
	proxy: {
		type: 'rest',
		url: Luntan.data.Constants.BASE_URL.concat('rest/edocgrants'),
		reader: {
			type: 'json',
			rootProperty: 'edocgrants'
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
		{name: 'economyDocId', type: 'int'},
		{name: 'itemDesignation', type: 'string'},
		{name: 'note', type: 'string'},
		{name: 'grantKind', type: 'string'},
		{name: 'totalGrant', type: 'float'},
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

/* 
    validators: {
    	code: 'presence',
    	seName: 'presence'
    }
 */

});


