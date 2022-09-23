Ext.define('Luntan.view.courses.ProgrammeController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.programmelist',

    
    onCreate: function()
    {
        var grid = this.getView();
         grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.Programme');
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	},

   	onUpdateInactive: function () {
   	},



   	init: function (view) {

	}

});
