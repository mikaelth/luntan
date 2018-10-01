Ext.define('Luntan.view.fundingmodels.FMTabledController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.fundingtable',

/* 
    onSelectionChange: function(sm, rec)
    {
		console.log(rec);

        this.lookupReference('btnRemove').enable();
        
        this.getViewModel().getStore('tabled').load('{currentFM.valueTable}')
    },
    
 */
    onCreate: function()
    {
        var grid = this.getView();
//			thisYear = this.getViewModel().get('current.year');
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.FundingModel');

//        r.set('year',thisYear);
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	}

    
});
