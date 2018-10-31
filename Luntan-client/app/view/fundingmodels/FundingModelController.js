Ext.define('Luntan.view.fundingmodels.FundingModelController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.fundingmodel',

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
		grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.FundingModel');

		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	}

    
});
