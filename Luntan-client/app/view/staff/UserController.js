Ext.define('Luntan.view.staff.UserController', {
    extend: 'Luntan.view.BasicController',

    alias: 'controller.userlist',

    onCreate: function()
    {
        var grid = this.getView();
         grid.plugins[0].cancelEdit();

        // Create a model instance
        var r = Ext.create('Luntan.model.User');
		var rec = grid.getStore().insert(0, r);
        grid.plugins[0].startEdit(rec[0]);
        
    },

   	onBeforeRender: function (grid) {
   	}

    
});
