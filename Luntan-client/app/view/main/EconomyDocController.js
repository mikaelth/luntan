Ext.define('Luntan.view.main.EconomyDocController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.edoclist',

    onCreate: function()
    {
        var grid = this.getView();
         grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.EconomyDoc');
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	init: function (view) {
        view.plugins[0].addListener('beforeEdit', function(rowEditing, context) {
			return !rowEditing.grid.viewModel.get('currentEconomyDoc').get('locked');
        });
	}
    
});
