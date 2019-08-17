Ext.define('Luntan.view.fundingmodels.EconomyDocModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.edocmodel',

/* 
	data: {
 
		current : {
			edoc : null
		}
 
	},
 */
	stores: {
		departments: 'DepartmentStore',
		grantkinds: 'EDGKindStore',
		edocgrants: {			
			type: 'chained',
			source: 'EDGStore',
			filters: [{property: 'economyDocId', value: '{current.edoc.id}', exactMatch: true}],
//			sorters: [{property:'courseName', direction: 'ASC'}],
//			groupField: 'courseGroup'
		}
	},
	formulas: {
		currentEconomyDoc: {
			// We need to bind deep to be notified on each model change
			bind: {
				bindTo: '{edocList.selection}', //--> reference configurated on the grid view (reference: ouList)
				deep: true
			},
			get: function(edoc) {
				this.set('current.edoc', edoc);
				return edoc;
			},
		}
	}

});
