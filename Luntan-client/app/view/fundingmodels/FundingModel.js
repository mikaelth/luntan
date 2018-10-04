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
			],
/* 
			data: [{number: 1, value: 1.5}, {number:2, value: 2.0}]			
 */
		}    	
	},
	
	formulas: {
        currentFM: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{fmList.selection}', //--> reference configurated on the grid view (reference: ouList)
                deep: true
            },
            get: function(fm) {
            	this.set('current.fm', fm);
  				console.log(this.getStore('tabled').data.getAt(0));
           	if (fm != null) {
					this.set('current.tabledValues', Object.keys(fm.get('valueTable')).map( function(e) 
						{
							return {number: e, value: fm.get('valueTable')[e]};
//							return [e, fm.get('valueTable')[e]}];
						})
					);
				console.log(this.get('current'));
					this.getStore('tabled').loadData(this.get('current.tabledValues'));
 				console.log(this.getStore('tabled').data.getAt(0));
 				console.log(this.getStore('tabled').data.getAt(1));
           	}
                return fm;
            }
        },
 
/* 
        currentTabled: {
            // We need to bind deep to be notified on each model change
            bind: {
                bindTo: '{fmList.selection.valueTable}', //--> reference configurated on the grid view (reference: ouList)
                deep: true
            },
            get: function(vt) {
            	this.set('current.tabledValues', vt);
                return vt;
            }
        }
 */
 
	}

});
