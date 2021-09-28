Ext.define('Luntan.view.fundingmodels.FundingModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.fundingmodel',

    data: {
		current : {
			fm : null,
			tabledValues: [{number: 1, value: 1.5}, {number:2, value: 2.0}]
		}
    },
    
    stores: {
		fundingmodels : 'FundingModelStore',
		tabled : {
			type: 'store',
			autolaod: false,
			proxy: {
				type: 'memory',
				reader: {
					type: 'base',
					rootProperty: 'data'
				 },
		 
				writer: {
					type: 'base',
					clientIdProperty: 'clientId',
					writeAllFields: true,
					dateFormat: 'Y-m-d'
				 }
		 

			 }, 
			idProperty: 'number',
			fields: [
			   {name: 'number', type: 'string'},
			   {name: 'value', type: 'float'}
			]
		}    	
	},
	
	formulas: {
        currentFM: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{fmList.selection}', //--> reference configurated on the grid view (reference: fmList)
                deep: true
            },
            get: function(fm) {
            	this.set('current.fm', fm);
  				console.log(this.getStore('tabled').data.getAt(0));
                return fm;
            }
        }
 
 
	}

});
