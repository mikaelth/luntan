Ext.define('Luntan.view.main.EDGrantListController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.edgrantlist',

    onCreate: function()
    {
        var grid = this.getView(),
        	edocid = grid.viewModel.get('current.edoc.id');
       	grid.findPlugin('rowediting').cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.EconomyDocGrant');
        r.set('economyDocId',edocid);
        r.set('grantDistribution',{});
		var rec = grid.getStore().insert(0, r);
        grid.findPlugin('rowediting').startEdit(rec[0]);
        
    },

   	init: function (view) {

		view.lookupReference('btnCreate').setBind({disabled: '{current.edoc.locked}'});	
		view.lookupReference('btnRemove').setBind({disabled: '{current.edoc.locked}'});	


        view.findPlugin('rowediting').addListener('beforeEdit', function(rowEditing, context) {
			return !rowEditing.grid.viewModel.get('current.edoc').get('locked');
        });
	}
    
});
